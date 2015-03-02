package ru.innopolis.olympiads.dao.adapter;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by giylmi on 27.12.14.
 */
public class DataSourceAdapter extends JdbcDaoSupport {

    public DataSourceAdapter() {
    }

    public void execute(String sql) {
        getJdbcTemplate().execute(sql);
    }

    public KeyHolder update(final String sql, KeyHolder holder){
        getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                return statement;
            }
        }, holder);
        return holder;
    }

    public <T> T query(String sql, ResultSetExtractor<T> rse) throws DataAccessException {
        return getJdbcTemplate().query(sql, rse);
    }

    public void query(String sql, RowCallbackHandler rch) throws DataAccessException {
        getJdbcTemplate().query(sql, rch);
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        return getJdbcTemplate().query(sql, rowMapper);
    }

}
