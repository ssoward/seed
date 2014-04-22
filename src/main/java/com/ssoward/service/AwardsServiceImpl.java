package com.ssoward.service;

import com.ssoward.model.Award;
import com.ssoward.model.Employee;
import com.ssoward.model.Give;
import com.ssoward.model.enums.GivesStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by ssoward on 3/17/14.
 */

@Service
public class AwardsServiceImpl implements AwardsService{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    @Autowired
    GiveService giveService;

    @Override
    public Long saveAward(final Award a) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "insert into awards (id, name, title, cost, createdDt, createdBy) values(null, ?, ?, ?, now(), ?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, a.getName());
                preparedStatement.setString(2, a.getName());
                preparedStatement.setString(3, "99");
                preparedStatement.setString(4, userService.getLoggedInUser().getEmail());
                return preparedStatement;
            }
        }, keyHolder);
//        jdbcTemplate.update("insert into awards (id, name, createdDt, createdBy) values(null, ?, now(), ?)",
//                a.getName(),
//                userService.getLoggedInUser().getEmail());
        Long id = keyHolder.getKey().longValue();
        return id;
    }

    @Override
    public List<Award> getAwards() {
        List<Award> l = jdbcTemplate.query("select * from awards", new RowMapper() {
            @Override public Award mapRow(ResultSet rs, int i) throws SQLException {
                return hydrateAward(rs);
            }
        });
        return l;
    }

    private Award hydrateAward(ResultSet rs) throws SQLException {
        Award prop = new Award();
        prop.setId(rs.getLong("id"));
        prop.setName(rs.getString("name"));
        prop.setCreatedDt(rs.getDate("createdDt"));
        prop.setCreatedBy(rs.getString("createdBy"));
        prop.setTitle(rs.getString("title"));
        prop.setCost(rs.getString("cost"));
        return prop;
    }

    @Override
    public void deleteAward(Long id) {
        jdbcTemplate.update("delete from awards where id = ?", id);
    }

    @Override
    public Award getAward(Long id) {
        Award l = jdbcTemplate.queryForObject("select * from awards where id = ?", new RowMapper<Award>() {
            @Override public Award mapRow(ResultSet rs, int i) throws SQLException {
                return hydrateAward(rs);
            }
        }, id);
        return l;
    }

    @Override
    public void updateAward(Award award) {
        jdbcTemplate.update("update awards set title = ?, cost = ? where id = ?",
                award.getTitle(), award.getCost(), award.getId());

    }

    @Override
    public void decrementBucks(Award a) throws InsufficientResourcesException {
        Employee emp = userService.getLoggedInUser();
        if(emp.getUnspentBucks() == null || emp.getUnspentBucks()<1){
            throw new InsufficientResourcesException("No Bread");
        }
        int l = Integer.parseInt(a.getCost());
        Long combinationPurchase = null;
        for(int i = 0; i<l; i++) {
            for (Give g : emp.getBucks()) {
                if (g.getStatus().equals(GivesStatusEnum.GIVEN)) {
                    GivesStatusEnum gse = GivesStatusEnum.GIVEN_SPENT;
                    if(i>0){
                        gse = GivesStatusEnum.USED_IN_COMBINATION;
                        g.setCombinationPurchase(combinationPurchase);
                    }else {
                        combinationPurchase = g.getId();
                    }
                    g.setStatus(gse);
                    g.setSpentDt(new java.util.Date());
                    g.setAward(a);
                    giveService.updateGive(g);
                    break;
                }
            }
        }
    }

    //fetch give to retain status when g.getDistributed = null || false
    @Override
    public void awardDistributed(Give give) {
        if(give.getDistributed()!= null && give.getDistributed()){
            give.awardReceivedDt = new Date();
            give.status = GivesStatusEnum.AWARD_RECEIVED;
        }else{
            give.awardReceivedDt = null;
            give.status = GivesStatusEnum.GIVEN_SPENT;
        }
        jdbcTemplate.update("update give set status = ?, awardReceivedDt = ? where id = ?",
                give.getStatus().name(), give.getAwardReceivedDt(), give.getId());
    }
}
