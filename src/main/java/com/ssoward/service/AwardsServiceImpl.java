package com.ssoward.service;

import com.ssoward.model.Award;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

/**
 * Created by ssoward on 3/17/14.
 */

@Service
public class AwardsServiceImpl implements AwardsService{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

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
}
