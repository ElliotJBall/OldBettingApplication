package com.elliot.dao;

import com.elliot.database.DatabaseConnectionManager;
import com.elliot.entity.*;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.ParseException;
import java.util.*;

/**
 * List of methods that are used to interact with the database
 * Implements from IFootballTeamDao
 *
 */
/**
 * @author Elliot
 *
 */
@Repository
public class FootballTeamDaoImpl implements IFootballTeamDao {

    private Gson gson = new Gson();

    public FootballTeamDaoImpl() {

    }

    /**
     * Builds JSON objects for the two given FootballTeam objects, this returns information about head to head,
     * recent form and attacking / defending strength
     * @param homeTeam FootballTeam object
     * @param awayTeam FootballTeam object
     */
    @Override
    public String getGivenTeamsStatistics(FootballTeam homeTeam, FootballTeam awayTeam, List<String> selectedSeasons) {
        try {
            getTeamsCurrentForm(homeTeam);
            getTeamsCurrentForm(awayTeam);
            getTeamsHeadToHeadForm(homeTeam, awayTeam);

            List<Object> objectsToJson = new ArrayList<>();
            objectsToJson.add(homeTeam);
            objectsToJson.add(awayTeam);

            int totalMatchesPlayed = 0;
            float averageHomeGoalsAGameOverSeason = 0.0f
                , averageHomeGoalsConcededAGameOverSeason = 0.0f
                , averageAwayGoalsScoredAGameOverSeason = 0.0f
                , averageAwayGoalsConcededAGameOverSeason = 0.0f;

            SeasonDaoImpl seasonDaoImpl = new SeasonDaoImpl();
            for (String seasonString: selectedSeasons) {
                Season season = new Season(seasonString);

                seasonDaoImpl.getSelectedSeasonMatches(season);
                season.calculateSeasonStatistics();

                averageHomeGoalsAGameOverSeason += season.getTotalHomeTeamGoals();
                averageHomeGoalsConcededAGameOverSeason += season.getTotalAwayTeamGoals();

                averageAwayGoalsScoredAGameOverSeason += season.getTotalAwayTeamGoals();
                averageAwayGoalsConcededAGameOverSeason += season.getTotalHomeTeamGoals();

                totalMatchesPlayed += season.getTotalGamesPlayedInSeason();
            }

            averageHomeGoalsAGameOverSeason = (averageHomeGoalsAGameOverSeason / (float) totalMatchesPlayed);
            averageHomeGoalsConcededAGameOverSeason = (averageHomeGoalsConcededAGameOverSeason / (float) totalMatchesPlayed);
            averageAwayGoalsScoredAGameOverSeason = (averageAwayGoalsScoredAGameOverSeason / (float) totalMatchesPlayed);
            averageAwayGoalsConcededAGameOverSeason = (averageAwayGoalsConcededAGameOverSeason / (float) totalMatchesPlayed);

            homeTeam.setAttackingStrengthHome(this.calculateAttackingStrengthAtHome(averageHomeGoalsAGameOverSeason, homeTeam, selectedSeasons));
            homeTeam.setDefensiveStrengthHome(this.calculateDefensiveStrengthAtHome(averageHomeGoalsConcededAGameOverSeason, homeTeam, selectedSeasons));

            awayTeam.setAttackingStrengthAway(this.calculateAttackingStrengthAway(averageAwayGoalsScoredAGameOverSeason, awayTeam, selectedSeasons));
            awayTeam.setDefensiveStrengthAway(this.calculateDefensiveStrengthAway(averageAwayGoalsConcededAGameOverSeason, awayTeam, selectedSeasons));

            return gson.toJson(objectsToJson);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Given a FootballTeam object it determines the recent form statistics for that FootballTeam
     * @param footballTeam The FootballTeam that you want to calculate the recent stats for
     */
    public void getTeamsCurrentForm(FootballTeam footballTeam) throws SQLException, ParseException {
        // List to hold the list of recent matches
        List<Match> recentMatches = new ArrayList<>();

        Connection connection = DatabaseConnectionManager.getInstance().getConnection();

        PreparedStatement query = connection.prepareStatement("SELECT * FROM betting_data.football_match " +
                "WHERE homeTeam = ? OR awayTeam = ? ORDER BY date DESC LIMIT 0, ?");

        query.setString(1, footballTeam.getTeamName());
        query.setString(2, footballTeam.getTeamName());
        query.setInt(3, FootballConstants.NUMBER_OF_RECENT_MATCHES);

        ResultSet resultSet = query.executeQuery();

        recentMatches = MatchDaoImpl.parseMatchFromResultSet(resultSet);

        calculateFootballTeamsRecentForm(recentMatches, footballTeam);

        connection.close();
    }

    /**
     * Given two FootballTeam objects it returns a JSON object holding the statistics for
     * head to head information of the given teams
     * @param homeTeam FootballTeam object
     * @param awayTeam FootballTeam object
     */
    public void getTeamsHeadToHeadForm(FootballTeam homeTeam, FootballTeam awayTeam) throws SQLException, ParseException{
        List<Match> headToHeadMatches = new ArrayList<Match>();

        Connection connection = DatabaseConnectionManager.getInstance().getConnection();

        PreparedStatement query = connection.prepareStatement("SELECT * FROM betting_data.football_match " +
                "WHERE homeTeam = ? AND awayTeam = ? OR homeTeam = ? AND awayTeam = ?");

        query.setString(1, homeTeam.getTeamName());
        query.setString(2, awayTeam.getTeamName());
        query.setString(3, awayTeam.getTeamName());
        query.setString(4, homeTeam.getTeamName());

        ResultSet resultSet = query.executeQuery();

        headToHeadMatches = MatchDaoImpl.parseMatchFromResultSet(resultSet);
        Match.sortMatchesByDate(headToHeadMatches);

        // Calculate the two Football team objects head to head stats
        calculateFootballTeamsHeadToHead(headToHeadMatches, homeTeam, awayTeam);

        connection.close();
    }

    /**
     * Calculate the attacking strength at home of the specified team to determine the strength of the team
     * (Based on ALL matches found in the database)
     */
    @Override
    public float calculateAttackingStrengthAtHome(float averageHomeGoalsAGameOverSeason, FootballTeam footballTeam, List<String> selectedSeasons) throws SQLException {
        Connection connection = DatabaseConnectionManager.getInstance().getConnection();

        try {
            int numberOfGoals = 0, numberOfGames = 0;

            // Loop over each selected season and grab the total number of goals scored at home for those seasons
            for (String season : selectedSeasons) {
                String startSeasonDate = season.substring(0, 4);
                String endSeasonDate = season.substring(5, 9);
                String query = String.format("SELECT SUM(fullTimeHomeTeamGoals) FROM betting_data.football_match WHERE homeTeam = '%s' AND date BETWEEN '%s' AND '%s'",
                        footballTeam.getTeamName(),
                        startSeasonDate + "-08-01",
                        endSeasonDate + "-06-01");

                Statement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    numberOfGoals += resultSet.getInt(1);
                }

                query = String.format("SELECT COUNT(*) FROM betting_data.football_match WHERE homeTeam = '%s' AND date BETWEEN '%s' AND '%s'",
                        footballTeam.getTeamName(),
                        startSeasonDate + "-08-01",
                        endSeasonDate + "-06-01");

                statement = connection.prepareStatement(query);
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    numberOfGames += resultSet.getInt(1);
                }
            }

            float attackingStrengthAtHome = 0.0f;
            if (numberOfGoals > 0 && numberOfGames > 0) {
                float averageGoalsAGame = (float) numberOfGoals / (float) numberOfGames;
                attackingStrengthAtHome = (averageGoalsAGame / averageHomeGoalsAGameOverSeason);
            }

            return attackingStrengthAtHome;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            connection.close();
        }

        return 0;
    }

    /**
     * Calculate the defensive strength at home of the specified team to determine the strength of the team
     * (Based on ALL matches found in the database)
     */
    @Override
    public float calculateDefensiveStrengthAtHome(float averageHomeGoalsConcededAGameOverSeason, FootballTeam footballTeam, List<String> selectedSeasons) throws SQLException {
        Connection connection = DatabaseConnectionManager.getInstance().getConnection();

        try {
            int numberOfGoals = 0, numberOfGames = 0;

            for (String season : selectedSeasons) {
                String startSeasonDate = season.substring(0, 4);
                String endSeasonDate = season.substring(5, 9);
                String query = String.format("SELECT SUM(fullTimeAwayTeamGoals) FROM betting_data.football_match WHERE homeTeam = '%s' AND date BETWEEN '%s' AND '%s'",
                        footballTeam.getTeamName(),
                        startSeasonDate + "-08-01",
                        endSeasonDate + "-06-01");

                Statement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    numberOfGoals += resultSet.getInt(1);
                }

                query = String.format("SELECT COUNT(*) FROM betting_data.football_match WHERE homeTeam = '%s' AND date BETWEEN '%s' AND '%s'",
                        footballTeam.getTeamName(),
                        startSeasonDate + "-08-01",
                        endSeasonDate + "-06-01");

                statement = connection.prepareStatement(query);
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    numberOfGames += resultSet.getInt(1);
                }
            }

            float defensiveStrengthAtHome = 0.0f;
            if (numberOfGoals > 0 && numberOfGames > 0) {
                float averageGoalsConcededAGame = (float) numberOfGoals / (float) numberOfGames;
                defensiveStrengthAtHome = (averageGoalsConcededAGame / averageHomeGoalsConcededAGameOverSeason);
            }

            return defensiveStrengthAtHome;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return 0;
    }

    /**
     * Calculate the attacking strength away of the specified team to determine the strength of the team
     * (Based on ALL matches found in the database)x
     */
    @Override
    public float calculateAttackingStrengthAway(float averageAwayGoalsScoredAGameOverSeason, FootballTeam footballTeam, List<String> selectedSeasons) throws SQLException {
        Connection connection = DatabaseConnectionManager.getInstance().getConnection();

        try {
            int numberOfGoals = 0, numberOfGames = 0;

            for (String season : selectedSeasons) {
                String startSeasonDate = season.substring(0, 4);
                String endSeasonDate = season.substring(5, 9);

                String query = String.format("SELECT SUM(fullTimeAwayTeamGoals) FROM betting_data.football_match WHERE awayTeam = '%s' AND date BETWEEN '%s' AND '%s'"
                        , footballTeam.getTeamName(),
                        startSeasonDate + "-08-01",
                        endSeasonDate + "-06-01");

                Statement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    numberOfGoals += resultSet.getInt(1);
                }

                query = String.format("SELECT COUNT(*) FROM betting_data.football_match WHERE awayTeam = '%s' AND date BETWEEN '%s' AND '%s'",
                        footballTeam.getTeamName(),
                        startSeasonDate + "-08-01",
                        endSeasonDate + "-06-01");

                statement = connection.prepareStatement(query);
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    numberOfGames += resultSet.getInt(1);
                }
            }

            float attackingStrengthAway = 0.0f;
            if (numberOfGoals > 0 && numberOfGames > 0) {
                float averageGoalsAGame = (float) numberOfGoals / (float) numberOfGames;
                attackingStrengthAway = (averageGoalsAGame / averageAwayGoalsScoredAGameOverSeason);
            }

            return attackingStrengthAway;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return 0;
    }

    /**
     * Calculate the attacking strength away of the specified team to determine the strength of the team
     * (Based on ALL matches found in the database)
     */
    @Override
    public float calculateDefensiveStrengthAway(float averageAwayGoalsConcededAGameOverSeason, FootballTeam footballTeam, List<String> selectedSeasons) throws SQLException {
        Connection connection = DatabaseConnectionManager.getInstance().getConnection();

        try {
            int numberOfGoals = 0, numberOfGames = 0;

            for (String season : selectedSeasons) {
                String startSeasonDate = season.substring(0, 4);
                String endSeasonDate = season.substring(5, 9);

                String query = String.format("SELECT SUM(fullTimeHomeTeamGoals) FROM betting_data.football_match WHERE awayTeam = '%s' AND date BETWEEN '%s' AND '%s'",
                        footballTeam.getTeamName(),
                        startSeasonDate + "-08-01",
                        endSeasonDate + "-06-01");

                Statement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    numberOfGoals += resultSet.getInt(1);
                }

                query = String.format("SELECT COUNT(*) FROM betting_data.football_match WHERE awayTeam = '%s' AND date BETWEEN '%s' AND '%s'",
                        footballTeam.getTeamName(),
                        startSeasonDate + "-08-01",
                        endSeasonDate + "-06-01");

                statement = connection.prepareStatement(query);
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    numberOfGames += resultSet.getInt(1);
                }
            }

            float defensiveStrengthAway = 0.0f;
            if (numberOfGoals > 0 && numberOfGames > 0) {
                float averageGoalsConcededAGame = (float) numberOfGoals / (float) numberOfGames;
                defensiveStrengthAway = (averageGoalsConcededAGame / averageAwayGoalsConcededAGameOverSeason);
            }

            return defensiveStrengthAway;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return 0;
    }

    @Override
    public String getAllFootballTeamNames() throws SQLException {
        Connection connection = DatabaseConnectionManager.getInstance().getConnection();

        try {
            String query = "SELECT DISTINCT homeTeam FROM betting_data.football_match;";

            Statement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);

            Collection<String> footballTeams = new ArrayList<String>();
            while (resultSet.next()) {
                footballTeams.add(resultSet.getString(1));
            }

            return gson.toJson(footballTeams);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return "";
    }

    /**
     * Iterates over the list of head to head matches and sets the averages for the given FootballTeam objects head
     * to head stats
     */
    private void calculateFootballTeamsHeadToHead(List<Match> headToHeadMatches, FootballTeam homeTeam, FootballTeam awayTeam) {
        for (Match match : headToHeadMatches) {
            if (match.getHomeTeam().equals(homeTeam.getTeamName())) {
                homeTeam.setHeadToHeadFormAverageCorners(homeTeam.getHeadToHeadFormAverageCorners() + match.getHomeTeamCorners());
                homeTeam.setHeadToHeadFormAverageFoulsCommitted(homeTeam.getHeadToHeadFormAverageFoulsCommitted() + match.getHomeTeamFoulsCommitted());
                homeTeam.setHeadToHeadFormAverageGoalsScored(homeTeam.getHeadToHeadFormAverageGoalsScored() + match.getFullTimeHomeTeamGoals());
                homeTeam.setHeadToHeadFormAverageCorners(homeTeam.getHeadToHeadFormAverageCorners() + match.getHomeTeamCorners());
                homeTeam.setHeadToHeadFormAverageRedCards(homeTeam.getHeadToHeadFormAverageRedCards() + match.getHomeTeamRedCards());
                homeTeam.setHeadToHeadFormAverageYellowCards(homeTeam.getHeadToHeadFormAverageYellowCards() + match.getHomeTeamYellowCards());
                homeTeam.setHeadToHeadFormAverageShots(homeTeam.getHeadToHeadFormAverageShots() + match.getHomeTeamShots());
                homeTeam.setHeadToHeadFormAverageShotsOnTarget(homeTeam.getHeadToHeadFormAverageShotsOnTarget() + match.getHomeTeamShotsOnTarget());

                awayTeam.setHeadToHeadFormAverageCorners(awayTeam.getHeadToHeadFormAverageCorners() + match.getHomeTeamCorners());
                awayTeam.setHeadToHeadFormAverageFoulsCommitted(awayTeam.getHeadToHeadFormAverageFoulsCommitted() + match.getHomeTeamFoulsCommitted());
                awayTeam.setHeadToHeadFormAverageGoalsScored(awayTeam.getHeadToHeadFormAverageGoalsScored() + match.getFullTimeHomeTeamGoals());
                awayTeam.setHeadToHeadFormAverageCorners(awayTeam.getHeadToHeadFormAverageCorners() + match.getHomeTeamCorners());
                awayTeam.setHeadToHeadFormAverageRedCards(awayTeam.getHeadToHeadFormAverageRedCards() + match.getHomeTeamRedCards());
                awayTeam.setHeadToHeadFormAverageYellowCards(awayTeam.getHeadToHeadFormAverageYellowCards() + match.getHomeTeamYellowCards());
                awayTeam.setHeadToHeadFormAverageShots(awayTeam.getHeadToHeadFormAverageShots() + match.getHomeTeamShots());
                awayTeam.setHeadToHeadFormAverageShotsOnTarget(awayTeam.getHeadToHeadFormAverageShotsOnTarget() + match.getHomeTeamShotsOnTarget());
            } else {
                awayTeam.setHeadToHeadFormAverageCorners(homeTeam.getHeadToHeadFormAverageCorners() + match.getHomeTeamCorners());
                awayTeam.setHeadToHeadFormAverageFoulsCommitted(homeTeam.getHeadToHeadFormAverageFoulsCommitted() + match.getHomeTeamFoulsCommitted());
                awayTeam.setHeadToHeadFormAverageGoalsScored(homeTeam.getHeadToHeadFormAverageGoalsScored() + match.getFullTimeHomeTeamGoals());
                awayTeam.setHeadToHeadFormAverageCorners(homeTeam.getHeadToHeadFormAverageCorners() + match.getHomeTeamCorners());
                awayTeam.setHeadToHeadFormAverageRedCards(homeTeam.getHeadToHeadFormAverageRedCards() + match.getHomeTeamRedCards());
                awayTeam.setHeadToHeadFormAverageYellowCards(homeTeam.getHeadToHeadFormAverageYellowCards() + match.getHomeTeamYellowCards());
                awayTeam.setHeadToHeadFormAverageShots(homeTeam.getHeadToHeadFormAverageShots() + match.getHomeTeamShots());
                awayTeam.setHeadToHeadFormAverageShotsOnTarget(homeTeam.getHeadToHeadFormAverageShotsOnTarget() + match.getHomeTeamShotsOnTarget());

                homeTeam.setHeadToHeadFormAverageCorners(awayTeam.getHeadToHeadFormAverageCorners() + match.getHomeTeamCorners());
                homeTeam.setHeadToHeadFormAverageFoulsCommitted(awayTeam.getHeadToHeadFormAverageFoulsCommitted() + match.getHomeTeamFoulsCommitted());
                homeTeam.setHeadToHeadFormAverageGoalsScored(awayTeam.getHeadToHeadFormAverageGoalsScored() + match.getFullTimeHomeTeamGoals());
                homeTeam.setHeadToHeadFormAverageCorners(awayTeam.getHeadToHeadFormAverageCorners() + match.getHomeTeamCorners());
                homeTeam.setHeadToHeadFormAverageRedCards(awayTeam.getHeadToHeadFormAverageRedCards() + match.getHomeTeamRedCards());
                homeTeam.setHeadToHeadFormAverageYellowCards(awayTeam.getHeadToHeadFormAverageYellowCards() + match.getHomeTeamYellowCards());
                homeTeam.setHeadToHeadFormAverageShots(awayTeam.getHeadToHeadFormAverageShots() + match.getHomeTeamShots());
                homeTeam.setHeadToHeadFormAverageShotsOnTarget(awayTeam.getHeadToHeadFormAverageShotsOnTarget() + match.getHomeTeamShotsOnTarget());
            }

            // Add the correct Form result to the FootballTeam objects Head to Head form
            determineHeadToHeadFormMatchResult(match, homeTeam, awayTeam);
        }

        int totalNumberOfMatches = headToHeadMatches.size();

        homeTeam.setHeadToHeadFormAverageCorners(homeTeam.getHeadToHeadFormAverageCorners() / totalNumberOfMatches);
        homeTeam.setHeadToHeadFormAverageFoulsCommitted(homeTeam.getHeadToHeadFormAverageFoulsCommitted() / totalNumberOfMatches);
        homeTeam.setHeadToHeadFormAverageGoalsScored(homeTeam.getHeadToHeadFormAverageGoalsScored() / totalNumberOfMatches);
        homeTeam.setHeadToHeadFormAverageCorners(homeTeam.getHeadToHeadFormAverageCorners() / totalNumberOfMatches);
        homeTeam.setHeadToHeadFormAverageRedCards(homeTeam.getHeadToHeadFormAverageRedCards() / totalNumberOfMatches);
        homeTeam.setHeadToHeadFormAverageYellowCards(homeTeam.getHeadToHeadFormAverageYellowCards() / totalNumberOfMatches);
        homeTeam.setHeadToHeadFormAverageShots(homeTeam.getHeadToHeadFormAverageShots() / totalNumberOfMatches);
        homeTeam.setHeadToHeadFormAverageShotsOnTarget(homeTeam.getHeadToHeadFormAverageShotsOnTarget() / totalNumberOfMatches);

        awayTeam.setHeadToHeadFormAverageCorners(awayTeam.getHeadToHeadFormAverageCorners() / totalNumberOfMatches);
        awayTeam.setHeadToHeadFormAverageFoulsCommitted(awayTeam.getHeadToHeadFormAverageFoulsCommitted() / totalNumberOfMatches);
        awayTeam.setHeadToHeadFormAverageGoalsScored(awayTeam.getHeadToHeadFormAverageGoalsScored() / totalNumberOfMatches);
        awayTeam.setHeadToHeadFormAverageCorners(awayTeam.getHeadToHeadFormAverageCorners() / totalNumberOfMatches);
        awayTeam.setHeadToHeadFormAverageRedCards(awayTeam.getHeadToHeadFormAverageRedCards() / totalNumberOfMatches);
        awayTeam.setHeadToHeadFormAverageYellowCards(awayTeam.getHeadToHeadFormAverageYellowCards() / totalNumberOfMatches);
        awayTeam.setHeadToHeadFormAverageShots(awayTeam.getHeadToHeadFormAverageShots() / totalNumberOfMatches);
        awayTeam.setHeadToHeadFormAverageShotsOnTarget(awayTeam.getHeadToHeadFormAverageShotsOnTarget() / totalNumberOfMatches);
    }

    private void calculateFootballTeamsRecentForm(List<Match> recentMatches, FootballTeam footballTeam) {
        for (Match match : recentMatches) {
            if (match.getHomeTeam().equals(footballTeam.getTeamName())) {
                footballTeam.setRecentFormAverageCorners(footballTeam.getRecentFormAverageCorners() + match.getHomeTeamCorners());
                footballTeam.setRecentFormAverageFoulsCommitted(footballTeam.getRecentFormAverageFoulsCommitted() + match.getHomeTeamFoulsCommitted());
                footballTeam.setRecentFormAverageGoalsScored(footballTeam.getRecentFormAverageGoalsScored() + match.getFullTimeHomeTeamGoals());
                footballTeam.setRecentFormAverageCorners(footballTeam.getRecentFormAverageCorners() + match.getHomeTeamCorners());
                footballTeam.setRecentFormAverageRedCards(footballTeam.getRecentFormAverageRedCards() + match.getHomeTeamRedCards());
                footballTeam.setRecentFormAverageYellowCards(footballTeam.getRecentFormAverageYellowCards() + match.getHomeTeamYellowCards());
                footballTeam.setRecentFormAverageShots(footballTeam.getRecentFormAverageShots() + match.getHomeTeamShots());
                footballTeam.setRecentFormAverageShotsOnTarget(footballTeam.getRecentFormAverageShotsOnTarget() + match.getHomeTeamShotsOnTarget());
            } else {
                footballTeam.setRecentFormAverageCorners(footballTeam.getRecentFormAverageCorners() + match.getAwayTeamCorners());
                footballTeam.setRecentFormAverageFoulsCommitted(footballTeam.getRecentFormAverageFoulsCommitted() + match.getAwayTeamFoulsCommitted());
                footballTeam.setRecentFormAverageGoalsScored(footballTeam.getRecentFormAverageGoalsScored() + match.getFullTimeAwayTeamGoals());
                footballTeam.setRecentFormAverageCorners(footballTeam.getRecentFormAverageCorners() + match.getAwayTeamCorners());
                footballTeam.setRecentFormAverageRedCards(footballTeam.getRecentFormAverageRedCards() + match.getAwayTeamRedCards());
                footballTeam.setRecentFormAverageYellowCards(footballTeam.getRecentFormAverageYellowCards() + match.getAwayTeamYellowCards());
                footballTeam.setRecentFormAverageShots(footballTeam.getRecentFormAverageShots() + match.getAwayTeamShots());
                footballTeam.setRecentFormAverageShotsOnTarget(footballTeam.getRecentFormAverageShotsOnTarget() + match.getAwayTeamShotsOnTarget());
            }

            determineRecentFormMatchResult(match, footballTeam);
        }

        footballTeam.setRecentFormAverageCorners(footballTeam.getRecentFormAverageCorners() / FootballConstants.NUMBER_OF_RECENT_MATCHES);
        footballTeam.setRecentFormAverageFoulsCommitted(footballTeam.getRecentFormAverageFoulsCommitted() / FootballConstants.NUMBER_OF_RECENT_MATCHES);
        footballTeam.setRecentFormAverageGoalsScored(footballTeam.getRecentFormAverageGoalsScored() / FootballConstants.NUMBER_OF_RECENT_MATCHES);
        footballTeam.setRecentFormAverageCorners(footballTeam.getRecentFormAverageCorners() / FootballConstants.NUMBER_OF_RECENT_MATCHES);
        footballTeam.setRecentFormAverageRedCards(footballTeam.getRecentFormAverageRedCards() / FootballConstants.NUMBER_OF_RECENT_MATCHES);
        footballTeam.setRecentFormAverageYellowCards(footballTeam.getRecentFormAverageYellowCards() / FootballConstants.NUMBER_OF_RECENT_MATCHES);
        footballTeam.setRecentFormAverageShots(footballTeam.getRecentFormAverageShots() / FootballConstants.NUMBER_OF_RECENT_MATCHES);
        footballTeam.setRecentFormAverageShotsOnTarget(footballTeam.getRecentFormAverageShotsOnTarget() / FootballConstants.NUMBER_OF_RECENT_MATCHES);
    }

    /**
     * Adds the correct result (H | A | D) to the FootballTeam objects recent form Lsit
     * @param match
     * @param footballTeam
     */
    private void determineRecentFormMatchResult(Match match, FootballTeam footballTeam) {
        // Switch on the possible outcomes of a football match and to set a football teams form accordingly
        switch (match.getFullTimeResult()) {
            case H:
                if (match.getHomeTeam().equals(footballTeam.getTeamName())) {
                    if (match.getFullTimeResult() == Result.H) {
                        footballTeam.recentTeamsForm.add(Form.WIN);
                    } else {
                        footballTeam.recentTeamsForm.add(Form.LOSS);
                    }
                } else {
                    if (match.getFullTimeResult() == Result.A) {
                        footballTeam.recentTeamsForm.add(Form.WIN);
                    } else {
                        footballTeam.recentTeamsForm.add(Form.LOSS);
                    }
                }
                break;
            case A:
                if (match.getAwayTeam().equals(footballTeam.getTeamName())) {
                    if (match.getFullTimeResult() == Result.A) {
                        footballTeam.recentTeamsForm.add(Form.WIN);
                    } else {
                        footballTeam.recentTeamsForm.add(Form.LOSS);
                    }
                } else {
                    if (match.getFullTimeResult() == Result.H) {
                        footballTeam.recentTeamsForm.add(Form.WIN);
                    } else {
                        footballTeam.recentTeamsForm.add(Form.LOSS);
                    }
                }
                break;
            case D:
                footballTeam.recentTeamsForm.add(Form.DRAW);
                break;
        }
    }

    /**
     * Adds the correct result (H | A | D) to the Football Team objects based on the results of the given Match object
     * @param match
     * @param homeTeam
     * @param awayTeam
     */
    private void determineHeadToHeadFormMatchResult(Match match, FootballTeam homeTeam, FootballTeam awayTeam) {
        // Switch on the possible outcomes of a football match and to set a football teams form accordingly
        switch (match.getFullTimeResult()) {
            case H:
                if (match.getHomeTeam().equals(homeTeam.getTeamName())) {
                    homeTeam.headToHeadTeamsForm.add(Form.WIN);
                    awayTeam.headToHeadTeamsForm.add(Form.LOSS);
                } else {
                    homeTeam.headToHeadTeamsForm.add(Form.LOSS);
                    awayTeam.headToHeadTeamsForm.add(Form.WIN);
                }
                break;
            case A:
                if (match.getAwayTeam().equals(awayTeam.getTeamName())) {
                    homeTeam.headToHeadTeamsForm.add(Form.LOSS);
                    awayTeam.headToHeadTeamsForm.add(Form.WIN);
                } else {
                    homeTeam.headToHeadTeamsForm.add(Form.WIN);
                    awayTeam.headToHeadTeamsForm.add(Form.LOSS);
                }
                break;
            case D:
                homeTeam.headToHeadTeamsForm.add(Form.DRAW);
                awayTeam.headToHeadTeamsForm.add(Form.DRAW);
                break;
        }
    }
}