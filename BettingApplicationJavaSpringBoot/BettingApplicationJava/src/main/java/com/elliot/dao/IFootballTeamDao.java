package com.elliot.dao;

import com.elliot.entity.FootballTeam;
import com.elliot.entity.Match;
import com.elliot.entity.Season;

import java.sql.SQLException;
import java.util.List;

/**
 * The following list of methods are used to calculate the statistical information and populate the variables of a
 * FootballTeam object.
 */
public interface IFootballTeamDao {

    /**
     * Builds JSON objects for the two given FootballTeam objects, this returns information about head to head,
     * information about two Football Teams
     */
    public String getGivenTeamsStatistics(FootballTeam homeTeam, FootballTeam awayTeam, List<String> selectedSeasons);

    /**
     * The following methods are used to calculate the attacking / defensive strength at home / away of a given
     * FootballTeam object.
     */
    public float calculateAttackingStrengthAtHome(float averageHomeGoalsAGameOverSeason, FootballTeam footballTeam, List<String> selectedSeasons) throws SQLException;

    public float calculateDefensiveStrengthAtHome(float averageGoalsConcededAGameOverSeason, FootballTeam footballTeam, List<String> selectedSeasons) throws SQLException;

    public float calculateAttackingStrengthAway(float averageAwayGoalsScoredAGameOverSeason, FootballTeam footballTeam, List<String> selectedSeasons) throws SQLException;

    public float calculateDefensiveStrengthAway(float averageAwayGoalsConcededAGameOverSeason, FootballTeam footballTeam, List<String> selectedSeasons) throws SQLException;

    /**
     * Return a JSON array of all the distinct FootballTeam names currently held in the database
     */
    public String getAllFootballTeamNames() throws SQLException;
}
