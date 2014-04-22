package com.ssoward.service;

import com.ssoward.model.Employee;
import com.ssoward.model.Give;
import com.ssoward.model.enums.GivesStatusEnum;
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
    List<Give> getBucks(String userName);
    public List<Give> getAllPraises();

    boolean hasMonthlyGives(Employee employee);

    void updateGive(Give g);

    void expireOldPoints(Employee employee);

    boolean awardPointsForGiveParticipation(Employee employee);

    void awardPoint(GivesTypeEnum type, String email, GivesStatusEnum given, Long praiseId);

    void updateMonthlyForParticipation(Employee employee);

    List<Give> fetchPurchasedLogs();

    Give getGive(Long id);
}
