package com.ssoward.service;

import com.ssoward.model.Employee;
import com.ssoward.model.Give;
import com.ssoward.model.enums.GivesStatusEnum;

/**
 * Created by ssoward on 3/8/14.
 */

//evaluate how many used points this employee has yet to give out.
public class SeedUtil {
    public static int getUnusedGives(Employee praiser) {
        int count = 0;
        if(praiser != null && praiser.getGives() != null){
            for(Give g: praiser.getGives()){
                if(g.getStatus().equals(GivesStatusEnum.TOBE_GIVEN)){
                    count++;
                }
            }
        }
        return count;
    }
}
