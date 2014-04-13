package com.ssoward.service;

import com.ssoward.model.Award;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ssoward on 3/17/14.
 */

@Service
public class AwardsServiceImpl implements AwardsService{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    @Override
    public void saveAward(Award a) {
        jdbcTemplate.update("insert into awards (id, name, createdDt, createdBy) values(null, ?, now(), ?)",
                a.getName(),
                userService.getLoggedInUser().getEmail());
    }

    @Override
    public List<Award> getAwards() {
        List<Award> l = jdbcTemplate.query("select * from awards", new RowMapper() {
            @Override public Award mapRow(ResultSet rs, int i) throws SQLException {
                Award prop = new Award();
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
    public void deleteAward(Long id) {
        jdbcTemplate.update("delete from awards where id = ?", id);
    }

    @Override
    public Award getAward(Long id) {
        Award l = jdbcTemplate.queryForObject("select * from awards where id = ?", new RowMapper<Award>() {
            @Override public Award mapRow(ResultSet rs, int i) throws SQLException {
                Award prop = new Award();
                prop.setId(rs.getLong("id"));
                prop.setName(rs.getString("name"));
                prop.setCreatedDt(rs.getDate("createdDt"));
                prop.setCreatedBy(rs.getString("createdBy"));
                return prop;
            }
        }, id);
        return l;
    }

}
