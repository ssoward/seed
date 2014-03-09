package com.ssoward.service;

import com.ssoward.model.Employee;
import com.ssoward.model.Give;
import com.ssoward.model.enums.GivesStatusEnum;
import com.ssoward.model.enums.GivesTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ssoward on 2/15/14.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    GiveService giveService;

    @Override
    public Employee getLoggedInUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getUser(user.getUsername());
    }

    @Override
    public List<Employee> getUsers() {
        List<Employee> uList = new ArrayList<Employee>();
        List<Map<String, Object>> l = jdbcTemplate.queryForList("select * from users u join authorities a on a.username = u.username");
        buildUser(l, uList);
        List<Give> gives = getGives();
        for(Give g: gives){
            for(Employee e: uList){
                if(g.getUser().equals(e.getEmail())){
                    e.setGives(e.getGives()!=null?e.getGives():new ArrayList<Give>());
                    e.getGives().add(g);
                    break;
                }
            }
        }
        for(Employee e: uList){
            e.setUnspentCount(SeedUtil.getUnusedGives(e));
        }
        return uList;
    }

    @Override
    public List<Give> getGives() {
        List<Give> uList = new ArrayList<Give>();
        List<Map<String, Object>> l = jdbcTemplate.queryForList("select * from give");
        buildGive(l, uList);
        return uList;
    }


//    public Integer getUnspentCount() {
//        int i = 0;
//        if(gives!= null){
//            for(Give g: gives){
//                if(GivesStatusEnum.TOBE_GIVEN.equals(g.getStatus())){
//                    i++;
//                }
//            }
//        }
//        return i;
//    }

    @Override
    public Employee getUser(String userName) {
        List<Employee> uList = new ArrayList<Employee>();
        List<Map<String, Object>> l = jdbcTemplate.queryForList("select * from users u join authorities a on a.username = u.username where a.username = ?", userName);
        buildUser(l, uList);
        return uList != null?uList.get(0):null;
    }

    private void buildGive(List<Map<String, Object>> l, List<Give> uList) {
        for (Map<String, Object> m : l) {
            Give u = new Give();
            u.setId(((Integer) m.get("id")).longValue());
            u.setUser((String) m.get("user"));
            u.setReceivedDt((Date) m.get("receivedDt"));
            u.setGivenDt((Date) m.get("givenDt"));
            u.setReceivedBy((String) m.get("receivedBy"));
            u.setStatus(GivesStatusEnum.getForLabel((String)m.get("status")));
            u.setType(GivesTypeEnum.getForLabel((String) m.get("giveType")));
            u.setSpentDt((Date) m.get("spentDt"));
            uList.add(u);
        }
    }
    private void buildUser(List<Map<String, Object>> l, List<Employee> uList) {
        for (Map<String, Object> m : l) {
            String email = (String) m.get("username");
            String auth = (String) m.get("authority");
            Employee u = new Employee();
            u.setFirstName((String) m.get("first_name"));
            u.setLastName((String) m.get("last_name"));
            u.setPassword((String) m.get("password"));
            u.setAuth(auth);
            u.setEmail(email);
            uList.add(u);
        }
    }

    @Override
    public void saveUser(Employee praiser) {
        //check if this is an update to the user
        Integer i = jdbcTemplate.queryForObject("select count(*) from users where username = ?", new Object[] {praiser.getEmail()}, Integer.class);
        if(i == 1){
            boolean changePassword = true;
            //only change password if it has been changed from ****
            int ii = praiser.password.indexOf('*');
            if(ii > -1){
                changePassword = false;
            }
            if(changePassword){
                jdbcTemplate.update("update users set first_name = ?, last_name = ?, password = ? where username = ?",
                        praiser.getFirstName(),
                        praiser.getLastName(),
                        praiser.password,
                        praiser.getEmail());
            }else{
                jdbcTemplate.update("update users set first_name = ?, last_name = ? where username = ?",
                        praiser.getFirstName(),
                        praiser.getLastName(),
                        praiser.getEmail());
            }

            jdbcTemplate.update("update authorities set authority = ? where username = ?",
                    praiser.getAuth(),
                    praiser.getEmail());
        }
        else{
            jdbcTemplate.update("insert into users values (?,?,1,?,?)",
                    praiser.getEmail(),
                    praiser.getPassword(),
                    praiser.getFirstName(),
                    praiser.getLastName()
            );

            jdbcTemplate.update("insert into authorities values (?, 'ROLE_AUTH')",
                    praiser.getEmail());
        }
        if(praiser.unspentCount != null){
            int unspentCount = SeedUtil.getUnusedGives(praiser);

            //check if unspent count is greater or less than
            if(unspentCount<praiser.unspentCount){
                while(unspentCount<praiser.unspentCount){
                    unspentCount++;
                    Give g = new Give();
                    g.setType(GivesTypeEnum.ADMIN);
                    g.setStatus(GivesStatusEnum.TOBE_GIVEN);
                    g.setReceivedBy(GivesTypeEnum.ADMIN.name());
                    g.setReceivedDt(new Date());
                    g.setUser(praiser.email);
                    giveService.saveGive(g);
                }
            }else if(unspentCount>praiser.unspentCount){
                while(unspentCount>=praiser.unspentCount){
                    for(Give g: praiser.getGives()){
                        if(g.getReceivedBy().equals(GivesTypeEnum.ADMIN.name())){
                            if(g.getStatus().equals(GivesStatusEnum.TOBE_GIVEN)){
                                giveService.deleteSave(g.getId());
                                break;
                            }
                        }
                    }
                    unspentCount--;
                }
            }


        }
    }

    @Override
    public void deleteUser(String username) {
        jdbcTemplate.update("delete from users where username = ?", username);
        jdbcTemplate.update("delete from authorities where username = ?", username);
    }

    @Override
    public void saveUserCount(Employee praiser) {
        for(Give gives: praiser.getGives()){
            if(gives.getId() == null){
                giveService.saveGive(gives);
            }
        }
    }

    @Override
    public void decrementCount(String praiser) {
        //jdbcTemplate.update("update users set count = count-1 where username = ?", praiser);
    }
}
