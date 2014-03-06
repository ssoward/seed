package com.ssoward.service;

import com.ssoward.model.Praise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ssoward on 3/1/14.
 */

@Service
public class PraiseServiceImpl implements PraiseService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Praise> getPraises() {
        String sql = "select \n" +
                "  z.first_name as perfirst,\n" +
                "  z.last_name as perlast,\n" +
                "  u.first_name as peefirst,\n" +
                "  u.last_name as peelast, p.*\n" +
                "  from praise p \n" +
                "      join users u on u.username = p.praisee\n" +
                "      join users z on u.username = p.praiser";
        List<Praise> l = jdbcTemplate.query(sql, new RowMapper() {
            @Override public Praise mapRow(ResultSet rs, int i) throws SQLException {
                Praise prop = new Praise();
                prop.setId(rs.getLong("id"));
                prop.setPraiser(rs.getString("perfirst") +" "+rs.getString("perlast"));
                prop.setPraisee(rs.getString("peefirst")+" "+rs.getString("peelast"));
                prop.setComment(rs.getString("comment"));
                prop.setPraise(rs.getString("praise"));
                prop.setPraiseDt(rs.getDate("praise_dt"));
                return prop;
            }
        });
        return l;
    }

    @Override
    public void savePraise(Praise praise) {
        jdbcTemplate.update("insert into praise values (null,?,?,?,?, now())",
                praise.getPraiser(),
                praise.getPraisee(),
                praise.getComment(),
                praise.getPraise()
        );
    }

    @Override
    public void deletePraise(Long id) {
        jdbcTemplate.update("delete from praise where id = ?", id);
    }
}
