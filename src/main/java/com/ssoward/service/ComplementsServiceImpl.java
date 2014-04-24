package com.ssoward.service;

import com.ssoward.model.Complement;
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
public class ComplementsServiceImpl implements ComplementsService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    @Override
    public List<Complement> getComplements() {
        List<Complement> l = jdbcTemplate.query("select * from complements", new RowMapper() {
            @Override public Complement mapRow(ResultSet rs, int i) throws SQLException {
                Complement prop = new Complement();
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
    public void saveComplement(Complement complement) {
        jdbcTemplate.update("insert into complements values (null,?, ?, now())",
                        complement.getName(),
                        userService.getLoggedInUser().getEmail());
    }

    @Override
    public void deleteComplement(Long id) {
        jdbcTemplate.update("delete from complements where id = ?", id);
    }
}
