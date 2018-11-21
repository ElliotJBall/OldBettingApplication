package com.elliot.dao;

import com.elliot.entity.Match;

import java.sql.SQLException;
import java.util.List;

/**
 * This Interface holds a list of methods that connect to the database and perform
 * CRUD like operations with any football matches
 */
public interface IMatchDao {
      public List<Match> getAllMatches() throws SQLException;
}
