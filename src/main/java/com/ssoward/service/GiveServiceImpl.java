package com.ssoward.service;

import com.ssoward.model.Award;
import com.ssoward.model.Employee;
import com.ssoward.model.Give;
import com.ssoward.model.enums.GivesStatusEnum;
import com.ssoward.model.enums.GivesTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.*;

/**
 * Created by ssoward on 3/5/14.
 */

@Service
public class GiveServiceImpl implements GiveService{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    @Autowired
    AwardsService awardService;

    @Override
    public void saveGive(Give g) {
        String sql = "insert into give (id, user, receivedDt, givenDt, receivedBy, status, giveType, spentDt, complement, award, givenTo, comment) " +
                "values (null, ?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, g.user, g.receivedDt, g.givenDt, g.receivedBy, g.status.name(), g.type.name(), g.spentDt, g.complement, g.award, g.givenTo, g.comment);
    }

    @Override
    public void deleteSave(Long id) {
        jdbcTemplate.update("delete from give where id = ?", id);
    }

    @Override
    public void createGive(GivesTypeEnum gtEnum, String user) {
        Give g = new Give();
        g.setType(gtEnum);
        g.setStatus(GivesStatusEnum.TOBE_GIVEN);
        g.setReceivedBy(gtEnum.name());
        g.setReceivedDt(new Date());
        g.setUser(user);
        saveGive(g);
    }

    @Override
    public List<Give> getGives(String userName) {
        List<Give> uList = new ArrayList<Give>();
        List<Map<String, Object>> l = null;
        if(userName != null){
            l = jdbcTemplate.queryForList("select * from give g where g.user = ? ", userName);
        }else{
            l = jdbcTemplate.queryForList("select * from give");
        }
        buildGive(l, uList);
        return uList;
    }

    @Override
    public List<Give> getBucks(String userName) {
        List<Give> uList = new ArrayList<Give>();
        List<Map<String, Object>> l = null;
        String sql = "select * from give g where g.status = ? and g.givenTo = ?";
        l = jdbcTemplate.queryForList(sql, new Object[] {GivesStatusEnum.GIVEN.name(), userName});
        buildGive(l, uList);
        return uList;
    }

    @Override
    //fetch all gives that have been given
    public List<Give> getAllPraises() {
        List<Give> uList = new ArrayList<Give>();
        List<Map<String, Object>> l = null;
        String sql = "select g.id, receivedDt, givenDt, receivedBy, status, giveType, spentDt, g.complement, award, comment, \n" +
                "  CONCAT(z.first_name, ' ', z.last_name)  as user,\n" +
                "  CONCAT(u.first_name , ' ', u.last_name) as givenTo, c.name as complementName\n" +
                "  from give g \n" +
                "      join users u on u.username = g.givenTo\n" +
                "      join users z on z.username = g.user\n" +
                "      join complements c on c.id = g.complement  where g.givenTo is not null";
        l = jdbcTemplate.queryForList(sql);
        buildGive(l, uList);
        return uList;
    }

    @Override
    public boolean hasMonthlyGives(Employee employee) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int yes = jdbcTemplate.queryForObject("select count(*) from give where year(receivedDt) = ? and month(receivedDt) = ? and user=?", new Object[]{year, month, employee.getEmail()}, Integer.class);
        return yes>0;
    }

    @Override
    public void updateGive(Give g) {
        String sql = "update give set user = ?, receivedDt = ?, givenDt = ?, receivedBy = ?, status = ?, giveType = ?, spentDt = ?, complement = ?, award = ?, givenTo = ?, comment = ?, combinationPurchase = ? where id = ?";
        jdbcTemplate.update(sql, g.user, g.receivedDt, g.givenDt, g.receivedBy, g.status.name(), g.type.name(), g.spentDt, g.complement, g.award!=null?g.award.getId():null, g.givenTo, g.comment, g.combinationPurchase, g.id);
    }

    @Override
    public void expireOldPoints(Employee employee) {
        //any points not used this month/year are automatically expired.
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        jdbcTemplate.update("update give g set g.status = ? where (year(receivedDt) != ? or month(receivedDt) != ?) and user=?",
                new Object[]{GivesStatusEnum.EXPIRED.name(), year, month, employee.getEmail()});
    }

    @Override
    public boolean awardPointsForGiveParticipation(Employee employee) {
        //check current month and last month to see if all gives are given
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int yes = jdbcTemplate.queryForObject("select count(*) from give " +
                        "where year(receivedDt) = ? and month(receivedDt) = ? and user=? " +
                        "and (status = ? or status = ?) and giveType = ? and giveType != ?",
                new Object[]{year, month, employee.getEmail(), GivesStatusEnum.GIVEN.name(),
                        GivesStatusEnum.GIVEN_SPENT.name(), GivesTypeEnum.MONTHLY.name(),
                        GivesTypeEnum.PARTICIPATION.name()}, Integer.class);

        return yes>2;
    }

    @Override
    public void awardPoint(GivesTypeEnum type, String user, GivesStatusEnum given, Long praiseId) {
        Give g = new Give();
        g.setType(type);
        g.setStatus(given);
        g.setReceivedBy(type.name());
        g.setReceivedDt(new Date());
        g.setUser(user);
        g.setComplement(praiseId);
        saveGive(g);
    }

    @Override
    public void updateMonthlyForParticipation(Employee employee) {
        jdbcTemplate.update("update give set giveType = ? where giveType = ? and user = ?",
                new Object[]{GivesTypeEnum.MONTHLY_USED.name(), GivesTypeEnum.MONTHLY.name(), employee.getEmail()});
    }


    private void buildGive(List<Map<String, Object>> l, List<Give> uList) {
        if(l != null){
            for (Map<String, Object> m : l) {
                Give u = new Give();
                u.setId(((Integer) m.get("id")).longValue());
                u.setUser((String) m.get("user"));
                u.setReceivedDt((Date) m.get("receivedDt"));
                u.setGivenDt((Date) m.get("givenDt"));
                u.setReceivedBy((String) m.get("receivedBy"));
                u.setStatus(GivesStatusEnum.getForName((String) m.get("status")));
                u.setType(GivesTypeEnum.getForName((String) m.get("giveType")));
                u.setSpentDt((Date) m.get("spentDt"));
                u.setComplementName((String) m.get("complementName"));
                String s = (String) m.get("award");
                u.setAward(s!=null?new Award(Long.parseLong(s)):null);
                try{u.setComplement(((Integer) m.get("complement")).longValue());}catch(NullPointerException e){/*null value*/}
                try{u.setCombinationPurchase(((Integer) m.get("combinationPurchase")).longValue());}catch(NullPointerException e){/*null value*/}
                u.setGivenTo((String)m.get("givenTo"));
                u.setComment((String) m.get("comment"));
                u.setAwardReceivedDt((Date) m.get("awardReceivedDt"));
                u.setDistributed(u.getAwardReceivedDt()!=null);
                uList.add(u);
            }
        }
    }

    @Override
    public List<Give> fetchPurchasedLogs() {
        List<Give> uList = new ArrayList<Give>();
        List<Map<String, Object>> l = null;
        String sql = "select g.id, receivedDt, givenDt, receivedBy, status, giveType, spentDt, g.complement, award, comment, awardReceivedDt, \n" +
                "  CONCAT(z.first_name, ' ', z.last_name)  as user,\n" +
                "  CONCAT(u.first_name , ' ', u.last_name) as givenTo, c.name as complementName\n" +
                "  from give g \n" +
                "      join users u on u.username = g.givenTo\n" +
                "      join users z on z.username = g.user\n" +
                "      join complements c on c.id = g.complement  where status in (?, ?)";
        l = jdbcTemplate.queryForList(sql, GivesStatusEnum.GIVEN_SPENT.name(), GivesStatusEnum.AWARD_RECEIVED.name());
        buildGive(l, uList);
        List<Award> aList = awardService.getAwards();
        for(Give g: uList){
            for(Award a: aList){
                if(a.getId().equals(g.getAward().getId())){
                    g.setAward(a);
                }
            }
        }
        return uList;
    }

    @Override
    public Give getGive(Long id) {
        List<Give> uList = new ArrayList<Give>();
        List<Map<String, Object>> l = null;
        l = jdbcTemplate.queryForList("select * from give g where g.id = ? ", id);
        buildGive(l, uList);
        return uList!=null&&uList.size()>0?uList.get(0):null;
    }
}
