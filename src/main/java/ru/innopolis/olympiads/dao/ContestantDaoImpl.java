package ru.innopolis.olympiads.dao;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.innopolis.olympiads.dao.adapter.DataSourceAdapter;
import ru.innopolis.olympiads.dao.adapter.QueryManager;
import ru.innopolis.olympiads.domain.Contestant;
import ru.innopolis.olympiads.domain.Form;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by giylmi on 03.03.2015.
 */
@Repository
public class ContestantDaoImpl implements ContestantDao {

    @Autowired
    DataSourceAdapter ds;

    @Autowired
    Form form;

    @Override
    public List<Contestant> all() {
        return ds.query(QueryManager.getQuery("sql/all.ftl", ImmutableMap.<String, Object>builder().put("tableName", form.getTableName()).build()), new RowMapper<Contestant>() {
            @Override
            public Contestant mapRow(ResultSet resultSet, int i) throws SQLException {
                Contestant contestant = new Contestant();
                contestant.setFirstName(resultSet.getString("firstName"));
                contestant.setMiddleName(resultSet.getString("middleName"));
                contestant.setLastName(resultSet.getString("lastName"));
                contestant.setStatus(resultSet.getBoolean("status"));
                return contestant;
            }
        });
    }
}
