package com.ssoward.service;

import com.ssoward.model.Award;
import com.ssoward.model.Give;

import javax.naming.InsufficientResourcesException;
import java.util.List;

/**
 * Created by ssoward on 3/17/14.
 */
public interface AwardsService {
    Long saveAward(Award a);

    List<Award> getAwards();

    void deleteAward(Long id);

    Award getAward(Long id);

    void updateAward(Award award);

    void decrementBucks(Award a) throws InsufficientResourcesException;

    void awardDistributed(Give give);
}
