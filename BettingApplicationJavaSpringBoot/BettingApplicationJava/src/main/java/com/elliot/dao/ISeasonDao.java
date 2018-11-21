package com.elliot.dao;

import com.elliot.entity.Match;
import com.elliot.entity.Season;

import java.util.List;

/**
 * This Interface holds a list of methods that connect to the database and perform
 * CRUD like operations with any football seasons
 */
public interface ISeasonDao {
    public void getSelectedSeasonMatches(Season selectedSeason);

    public String getSelectedSeasonStats(List<String> selectedSeasonsStrings);
}
