package com.ssoward.service;

import com.ssoward.model.Praise;

import java.util.List;

/**
 * Created by ssoward on 3/1/14.
 */
public interface PraiseService {

    List<Praise> getPraises();

    void savePraise(Praise praise);
}
