//package com.ssoward.service;
//
//import com.ssoward.model.Praise;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Service;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//
///**
// * Created by ssoward on 3/1/14.
// */
//
//@Service
//public class PraiseServiceImpl implements PraiseService {
//
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    @Override
//    public List<Praise> getPraises() {
//        String sql = "select \n" +
//                "  z.first_name as perfirst,\n" +
//                "  z.last_name as perlast,\n" +
//                "  u.first_name as peefirst,\n" +
//                "  u.last_name as peelast, p.*, c.name\n" +
//                "  from praise p \n" +
//                "      join users u on u.username = p.praisee\n" +
//                "      join users z on z.username = p.praiser\n" +
//                "      join complements c on c.id = p.praise";
//        List<Praise> l = jdbcTemplate.query(sql, new RowMapper() {
//            @Override public Praise mapRow(ResultSet rs, int i) throws SQLException {
//                Praise prop = new Praise();
//                prop.setId(rs.getLong("id"));
//                prop.setPraiser(rs.getString("perfirst") +" "+rs.getString("perlast"));
//                prop.setPraisee(rs.getString("peefirst")+" "+rs.getString("peelast"));
//                prop.setComment(rs.getString("comment"));
//                prop.setPraise(rs.getString("name"));
//                prop.setPraiseDt(rs.getDate("praise_dt"));
//                return prop;
//            }
//        });
//        return l;
//    }
//
//    @Override
//    public Long savePraise(final Praise praise) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        final String sql = "insert into praise values (null,?,?,?,?, now())";
//
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                preparedStatement.setString(1, praise.getPraiser());
//                preparedStatement.setString(2, praise.getPraisee());
//                preparedStatement.setString(3, praise.getComment());
//                preparedStatement.setString(4, praise.getPraise());
//                return preparedStatement;
//            }
//        }, keyHolder);
//
//        Long id = keyHolder.getKey().longValue();
//        return id;
//    }
//
//    @Override
//    public void deletePraise(Long id) {
//        jdbcTemplate.update("delete from praise where id = ?", id);
//    }
//}
