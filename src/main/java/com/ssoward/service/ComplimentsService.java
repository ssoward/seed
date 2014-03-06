package com.ssoward.service;

import com.ssoward.model.Compliment;

import java.util.List;

/**
 * Created by ssoward on 3/5/14.
 */

public interface ComplimentsService {

    List<Compliment> getCompliments();

    void saveCompliment(Compliment compliment);

    void deleteCompliment(Long id);
}
