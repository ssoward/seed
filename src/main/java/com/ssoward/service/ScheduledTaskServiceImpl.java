package com.ssoward.service;

import com.ssoward.model.Employee;
import com.ssoward.model.enums.GivesTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by ssoward on 3/8/14.
 */

@Service
public class ScheduledTaskServiceImpl implements ScheduledTaskService{
    final static int NUMBER_OF_POINTS_TO_GIVE_PER_MONTH = 3;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    GiveService giveService;

    @Autowired
    UserService userService;

    @Override
    //check if user has been given gifts for the month.
    public void loadUserTasks() {
        Employee employee = userService.getLoggedInUser();
        boolean alreadyReceivedMonthlyPoints = giveService.hasMonthlyGives(employee);

        if(!alreadyReceivedMonthlyPoints){
            for(int i = 0; i<NUMBER_OF_POINTS_TO_GIVE_PER_MONTH; i++){
                giveService.createGive(GivesTypeEnum.MONTHLY, employee.email);
            }
        }
    }
}
