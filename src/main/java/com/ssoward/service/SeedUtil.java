package com.ssoward.service;

import com.ssoward.model.Employee;
import com.ssoward.model.Give;
import com.ssoward.model.enums.GivesStatusEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
