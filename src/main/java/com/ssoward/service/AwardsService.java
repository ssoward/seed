package com.ssoward.service;

import com.ssoward.model.Award;

import java.util.List;

/**
 * Created by ssoward on 3/17/14.
 */
public interface AwardsService {
    void saveAward(Award a);

    List<Award> getAwards();

    void deleteAward(Long id);

    Award getAward(Long id);
}
