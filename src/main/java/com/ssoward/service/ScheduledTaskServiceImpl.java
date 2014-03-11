package com.ssoward.service;

import com.ssoward.model.Employee;
import com.ssoward.model.Praise;
import com.ssoward.model.enums.GivesStatusEnum;
import com.ssoward.model.enums.GivesTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Autowired
    PraiseService praiseService;

    @Override
    //check if user has been given gifts for the month.
    public void loadUserTasks() {
        Employee employee = userService.getLoggedInUser();

        //any points not this.year or this.month
        giveService.expireOldPoints(employee);

        //Give monthly points
        boolean alreadyReceivedMonthlyPoints = giveService.hasMonthlyGives(employee);
        if(!alreadyReceivedMonthlyPoints){
            for(int i = 0; i<NUMBER_OF_POINTS_TO_GIVE_PER_MONTH; i++){
                giveService.createGive(GivesTypeEnum.MONTHLY, employee.email);
            }
        }

        //award points for giving out all 3 monthly gives
        boolean completedParticipation = giveService.awardPointsForGiveParticipation(employee);
        if(completedParticipation){
            //update all monthly
            giveService.updateMonthlyForParticipation(employee);

            Praise praise = new Praise();
            praise.setPraiseDt(new Date());
            praise.setPraiser(GivesTypeEnum.PARTICIPATION.name());
            praise.setPraisee(employee.email);
            praise.setPraise(GivesTypeEnum.PARTICIPATION.name());
            Long id = praiseService.savePraise(praise);
            giveService.awardPoint(GivesTypeEnum.PARTICIPATION, employee.email, GivesStatusEnum.GIVEN, id);
        }
    }
}
