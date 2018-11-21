package com.elliot.controller;

import com.elliot.entity.Match;
import com.elliot.entity.Season;
import com.elliot.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/season")
public class SeasonController {

    @Autowired
    private SeasonService seasonService;

    @CrossOrigin
    @RequestMapping(value = "/seasons", method = RequestMethod.GET, produces = "application/json")
    public String getSelectedSeasonStats(@RequestParam("selectedSeasons") List<String> selectedSeasons) {
        return this.seasonService.getSelectedSeasonStats(selectedSeasons);
    }
}
