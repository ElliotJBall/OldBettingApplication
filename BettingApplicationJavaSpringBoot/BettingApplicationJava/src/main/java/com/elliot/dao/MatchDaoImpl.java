package com.elliot.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.elliot.database.DatabaseConnectionManager;
import com.elliot.entity.Match;
import com.elliot.entity.Result;
import org.springframework.stereotype.Repository;

@Repository
public class MatchDaoImpl implements IMatchDao{

    @Override
    public List<Match> getAllMatches() throws SQLException {
        Connection connection = DatabaseConnectionManager.getInstance().getConnection();

        try {
            String query = "SELECT * FROM football_match";

            Statement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);

            return parseMatchFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            connection.close();
        }

        throw new SQLException("Error gathering football matches from the database. Please check the connection and try again");
    }

    public static List<Match> parseMatchFromResultSet(ResultSet resultSet) throws SQLException, ParseException {
        List<Match> allMatches = new ArrayList<Match>();

        while (resultSet.next()) {
            DateFormat dateFormat = new SimpleDateFormat("YYYY-mm-dd");

            allMatches.add(new Match(resultSet.getInt("matchId"), resultSet.getString("division"), calculateMatchResult(resultSet.getString("fullTimeResult")),
                    calculateMatchResult(resultSet.getString("halfTimeResult")), dateFormat.parse(resultSet.getString("date")), resultSet.getInt("homeTeamCorners"),
                    resultSet.getInt("homeTeamShotsOnTarget"), resultSet.getInt("awayTeamCorners"), resultSet.getInt("homeTeamShots"), resultSet.getInt("homeTeamFoulsCommitted"),
                    resultSet.getInt("homeTeamYellowCards"), resultSet.getInt("homeTeamRedCards"), resultSet.getInt("fullTimeHomeTeamGoals"),
                    resultSet.getString("homeTeam"), resultSet.getInt("awayTeamShotsOnTarget"), resultSet.getInt("awayTeamShots"),
                    resultSet.getInt("awayTeamFoulsCommitted"), resultSet.getInt("awayTeamYellowCards"), resultSet.getInt("awayTeamRedCards"),
                    resultSet.getInt("halfTimeHomeTeamGoals"), resultSet.getInt("halfTimeAwayTeamGoals"),
                    resultSet.getInt("fullTimeAwayTeamGoals"), resultSet.getString("awayTeam"), resultSet.getString("referee")));
        }


        return allMatches;
    }

    private static Result calculateMatchResult(String result) {
        switch (result) {
            case "H":
                return Result.H;
            case "A":
                return Result.A;
            case "D":
                return Result.D;
            default:
                return Result.D;
        }
    }

}
