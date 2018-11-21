package com.elliot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.elliot.database.DatabaseConnectionManager;
import com.elliot.entity.Season;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

@Repository
public class SeasonDaoImpl implements ISeasonDao {

    /* (non-Javadoc)
     * @see footballInformation.ISeasonDao#getSeasonStatistics(footballInformation.Season)
     */
    @Override
    public void getSelectedSeasonMatches(Season selectedSeason) {
        Connection connection = DatabaseConnectionManager.getInstance().getConnection();

        try {
            // Get the beginning and end year of the season object
            String startSeasonDate = selectedSeason.getSeasonName().substring(0, 4) + "/08/01", endSeasonDate = selectedSeason.getSeasonName().substring(5) + "/06/01";

            PreparedStatement query = connection.prepareStatement("SELECT * FROM betting_data.football_match WHERE football_match.date BETWEEN ? AND ?");

            query.setString(1, startSeasonDate);
            query.setString(2, endSeasonDate);

            ResultSet resultSet = query.executeQuery();

            selectedSeason.setMatchesPlayedInSeason(MatchDaoImpl.parseMatchFromResultSet(resultSet));

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            DatabaseConnectionManager.getInstance().closeConnection(connection);
        }
    }

    @Override
    public String getSelectedSeasonStats(List<String> selectedSeasonsStrings) {
        List<Season> selectedSeasons = new ArrayList<>();
        SeasonDaoImpl seasonDaoImpl = new SeasonDaoImpl();
        for (String seasonString: selectedSeasonsStrings) {
            Season season = new Season(seasonString);

            seasonDaoImpl.getSelectedSeasonMatches(season);
            season.calculateSeasonStatistics();

            selectedSeasons.add(season);
        }

        Gson gson = new Gson();

        // TODO: Consider setting the list of matches inside a season object to null to avoid passing 380 uneeded
        // messages back to the front end
        return gson.toJson(selectedSeasons);
    }
}
