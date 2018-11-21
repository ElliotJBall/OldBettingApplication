package com.elliot.service;

import com.elliot.dao.MatchDaoImpl;
import com.elliot.entity.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchDaoImpl matchDaoImpl;


    public List<Match> getAllMatches() throws SQLException {
        return this.matchDaoImpl.getAllMatches();
    }
}
