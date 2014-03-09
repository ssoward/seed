package com.ssoward.service;

import com.ssoward.model.Give;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by ssoward on 3/5/14.
 */

@Service
public class GiveServiceImpl implements GiveService{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    @Override
    public void saveGive(Give give) {   //id user receivedDt givenDt receivedBy status giveType spentDt
        jdbcTemplate.update("insert into give values (null,?,?,?,?,?,?,?)",
                give.getUser(),
                give.getReceivedDt(),
                give.getGivenDt(),
                give.getReceivedBy(),
                give.getStatus().getLabel(),
                give.getType().getLabel(),
                give.getSpentDt()
        );
    }

    @Override
    public void deleteSave(Long id) {
        jdbcTemplate.update("delete from give where id = ?", id);
    }
}
