package com.ssoward.service;

import com.ssoward.model.Compliment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ssoward on 3/5/14.
 */

@Service
public class ComplimentsServiceImpl implements ComplimentsService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    @Override
    public List<Compliment> getCompliments() {
        List<Compliment> l = jdbcTemplate.query("select * from compliments", new RowMapper() {
            @Override public Compliment mapRow(ResultSet rs, int i) throws SQLException {
                Compliment prop = new Compliment();
                prop.setId(rs.getLong("id"));
                prop.setName(rs.getString("name"));
                prop.setCreatedDt(rs.getDate("createdDt"));
                prop.setCreatedBy(rs.getString("createdBy"));
                return prop;
            }
        });
        return l;
    }

    @Override
    public void saveCompliment(Compliment compliment) {
        jdbcTemplate.update("insert into compliments values (null,?, ?, now())",
                        compliment.getName(),
                        userService.getLoggedInUser().getEmail());
    }

    @Override
    public void deleteCompliment(Long id) {
        jdbcTemplate.update("delete from compliments where id = ?", id);
    }
}
