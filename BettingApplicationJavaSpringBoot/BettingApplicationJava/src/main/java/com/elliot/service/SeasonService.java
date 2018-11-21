package com.elliot.service;

import com.elliot.dao.SeasonDaoImpl;
import com.elliot.entity.Match;
import com.elliot.entity.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService {

    @Autowired
    private SeasonDaoImpl seasonDaoImpl;

    public String getSelectedSeasonStats(List<String> selectedSeasons) {
        return this.seasonDaoImpl.getSelectedSeasonStats(selectedSeasons);
    }
}
