package ru.innopolis.olympiads.dao;

import ru.innopolis.olympiads.domain.Contestant;

import java.util.List;

/**
 * Created by giylmi on 03.03.2015.
 */
public interface ContestantDao {

    List<Contestant> all();
}
