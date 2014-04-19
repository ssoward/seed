package com.ssoward.service;

import com.ssoward.model.Employee;
import com.ssoward.model.Give;
import com.ssoward.model.enums.GivesStatusEnum;
import com.ssoward.model.enums.GivesTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

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

    @Override
    public void saveGive(Give give) {   //id user receivedDt givenDt receivedBy status giveType spentDt
        jdbcTemplate.update("insert into give values (null,?,?,?,?,?,?,?,?)",
                give.getUser(),
                give.getReceivedDt(),
                give.getGivenDt(),
                give.getReceivedBy(),
                give.getStatus().name(),
                give.getType().name(),
                give.getSpentDt(),
                give.getPraise()
        );
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
        String sql = "select g.* from give g join praise p on p.id = g.praise where g.status = ? and p.praisee = ?";
        l = jdbcTemplate.queryForList(sql, new Object[] {GivesStatusEnum.GIVEN.name(), userName});
        buildGive(l, uList);
        return uList;
    }


    @Override
    public List<Give> getGives() {
        return getGives(null);
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
        jdbcTemplate.update("update give set receivedDt = ?, givenDt = ?, receivedBy = ?, status = ?, giveType = ?, spentDt = ?, praise = ? where id = ?",
                g.getReceivedDt(), g.getGivenDt(), g.getReceivedBy(), g.getStatus().name(), g.getType().name(), g.getSpentDt(), g.getPraise(), g.getId());
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
        g.setPraise(praiseId);
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
                u.setStatus(GivesStatusEnum.getForName((String)m.get("status")));
                u.setType(GivesTypeEnum.getForName((String) m.get("giveType")));
                u.setSpentDt((Date) m.get("spentDt"));
                String s = (String) m.get("award");
                u.setAward(s!=null?Long.parseLong(s):null);
                try{u.setPraise(((Integer) m.get("praise")).longValue());}catch(NullPointerException e){/*null value*/}
                uList.add(u);
            }
        }
    }
}
