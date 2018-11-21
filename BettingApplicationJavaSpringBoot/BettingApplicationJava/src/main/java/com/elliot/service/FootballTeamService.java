package com.elliot.service;

import com.elliot.dao.FootballTeamDaoImpl;
import com.elliot.entity.FootballTeam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class FootballTeamService {

    @Autowired
    private FootballTeamDaoImpl footballTeamDaoImpl;

    public String getGivenTeamsStatistics(FootballTeam homeTeam, FootballTeam awayTeam, List<String> selectedSeasons) {
        return this.footballTeamDaoImpl.getGivenTeamsStatistics(homeTeam, awayTeam, selectedSeasons);
    }

    public float getAttackingStrengthAtHome(float averageHomeGoalsAGameOverSeason, FootballTeam footballTeam, List<String> selectedSeasons) throws SQLException {
        return this.footballTeamDaoImpl.calculateAttackingStrengthAtHome(averageHomeGoalsAGameOverSeason, footballTeam, selectedSeasons);
    }

    public float getDefensiveStrengthAtHome(float averageGoalsConcededAGameOverSeason, FootballTeam footballTeam, List<String> selectedSeasons) throws SQLException {
        return this.footballTeamDaoImpl.calculateDefensiveStrengthAtHome(averageGoalsConcededAGameOverSeason, footballTeam, selectedSeasons);
    }

    public float getAttackingStrengthAway(float averageAwayGoalsScoredAGameOverSeason, FootballTeam footballTeam, List<String> selectedSeasons) throws SQLException {
        return this.footballTeamDaoImpl.calculateAttackingStrengthAway(averageAwayGoalsScoredAGameOverSeason, footballTeam, selectedSeasons);
    }

    public float getDefensiveStrengthAway(float averageAwayGoalsConcededAGameOverSeason, FootballTeam footballTeam, List<String> selectedSeasons) throws SQLException {
        return this.footballTeamDaoImpl.calculateDefensiveStrengthAway(averageAwayGoalsConcededAGameOverSeason, footballTeam, selectedSeasons);
    }

    public String getAllFootballTeamNames() throws SQLException {
        return  this.footballTeamDaoImpl.getAllFootballTeamNames();
    }
}
