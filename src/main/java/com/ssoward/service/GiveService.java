package com.ssoward.service;

import com.ssoward.model.Employee;
import com.ssoward.model.Give;
import com.ssoward.model.enums.GivesTypeEnum;

import java.util.List;

/**
 * Created by ssoward on 3/5/14.
 */

public interface GiveService {
    void saveGive(Give gives);
    void deleteSave(Long id);
    void createGive(GivesTypeEnum gtEnum, String user);
    List<Give> getGives(String userName);
    public List<Give> getGives();

    boolean hasMonthlyGives(Employee employee);
}
