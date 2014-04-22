package com.ssoward.service;

import com.ssoward.model.Award;
import com.ssoward.model.Employee;
import com.ssoward.model.Give;
import com.ssoward.model.enums.GivesStatusEnum;
import com.ssoward.model.enums.GivesTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;
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
//    @Cacheable(value = "userDetailsCache")
    public Employee getLoggedInUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getUser(user.getUsername());
    }

    @Override
    public List<Employee> getUsers() {
        List<Employee> uList = new ArrayList<Employee>();
        List<Map<String, Object>> l = jdbcTemplate.queryForList("select * from users u join authorities a on a.username = u.username");
        buildUser(l, uList);
        List<Give> gives = giveService.getGives(null);
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
            e.setRemainingGives(SeedUtil.getUnusedGives(e));
        }
        return uList;
    }

    @Override
    public Employee getUser(String userName) {
        List<Employee> uList = new ArrayList<Employee>();
        List<Map<String, Object>> l = jdbcTemplate.queryForList("select * from users u join authorities a on a.username = u.username where a.username = ?", userName);
        buildUser(l, uList);
        Employee emp = uList != null?uList.get(0):null;
        if(emp != null){
            emp.setGives(giveService.getGives(userName));
            emp.setBucks(giveService.getBucks(userName));
            emp.setRemainingGives(SeedUtil.getUnusedGives(emp));
            emp.setUnspentBucks(getUnspentPoints(emp));
        }
        return emp;
    }

    private Integer getUnspentPoints(Employee emp) {
        String sql = "select count(*) from give g where g.status = ? and g.givenTo = ?";
        Integer i = jdbcTemplate.queryForObject(sql, new Object[] {GivesStatusEnum.GIVEN.name(), emp.getEmail()}, Integer.class);
        return i;
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
        if(praiser.remainingGives != null){
            int remainingGives = SeedUtil.getUnusedGives(praiser);

            //check if unspent count is greater or less than
            if(remainingGives<praiser.remainingGives){
                while(remainingGives<praiser.remainingGives){
                    remainingGives++;
                    giveService.createGive(GivesTypeEnum.ADMIN, praiser.email);
                }
            }else if(remainingGives>praiser.remainingGives){
                while(remainingGives>=praiser.remainingGives){
                    for(Give g: praiser.getGives()){
                        if(g.getReceivedBy().equals(GivesTypeEnum.ADMIN.name())){
                            if(g.getStatus().equals(GivesStatusEnum.TOBE_GIVEN)){
                                giveService.deleteSave(g.getId());
                                break;
                            }
                        }
                    }
                    remainingGives--;
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
    //find the first NON-GIVEN give and 'expire' it ie. set status to GIVEN.
    public void distributeGive(Give give) throws InsufficientResourcesException {
        Employee emp = getLoggedInUser();
        if(emp.getRemainingGives() == null || emp.getRemainingGives()<1){
            throw new InsufficientResourcesException("No Gives To Give");
        }
        for(Give g: emp.getGives()){
            if(g.getStatus().equals(GivesStatusEnum.TOBE_GIVEN)){
                g.setStatus(GivesStatusEnum.GIVEN);
                g.setGivenDt(new Date());
                g.setComplement(give.complement);
                g.setGivenTo(give.getGivenTo());
                g.setComment(give.getComment());
                giveService.updateGive(g);
                break;
            }
        }
    }

}
