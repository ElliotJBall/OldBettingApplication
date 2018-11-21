package com.elliot.controller;

import com.elliot.entity.FootballTeam;
import com.elliot.entity.Match;
import com.elliot.entity.Season;
import com.elliot.service.FootballTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/footballTeam")
public class FootballTeamController {

    @Autowired
    private FootballTeamService footballTeamService;

    @CrossOrigin
    @RequestMapping(value = "/headToHeadForm", method = RequestMethod.GET, produces = "application/json")
    public String getGivenTeamsStatistics(@RequestParam("homeTeam") FootballTeam homeTeam,
                                          @RequestParam("awayTeam") FootballTeam awayTeam,
                                          @RequestParam("selectedSeasons")List<String> selectedSeasons) {
        return this.footballTeamService.getGivenTeamsStatistics(homeTeam, awayTeam, selectedSeasons);
    }

    @CrossOrigin
    @RequestMapping(value = "/attackingStrengthHome", method = RequestMethod.GET, produces = "application/json")
    public float getAttackingStrengthAtHome(@RequestParam("averageHomeGoalsAGameOverSeason") float averageHomeGoalsAGameOverSeason,
                                            @RequestParam("footballTeam") FootballTeam footballTeam,
                                            @RequestParam("selectedSeasons")List<String> selectedSeasons) throws SQLException {
        return this.footballTeamService.getAttackingStrengthAtHome(averageHomeGoalsAGameOverSeason, footballTeam, selectedSeasons);
    }

    @CrossOrigin
    @RequestMapping(value = "/defensiveStrengthHome", method = RequestMethod.GET, produces = "application/json")
    public float getDefensiveStrengthAtHome(@RequestParam("averageGoalsConcededAGameOverSeason") float averageGoalsConcededAGameOverSeason,
                                            @RequestParam("footballTeam") FootballTeam footballTeam,
                                            @RequestParam("selectedSeasons")List<String> selectedSeasons) throws SQLException {
        return this.footballTeamService.getDefensiveStrengthAtHome(averageGoalsConcededAGameOverSeason, footballTeam, selectedSeasons);
    }

    @CrossOrigin
    @RequestMapping(value = "/attackingStrengthAway", method = RequestMethod.GET, produces = "application/json")
    public float getAttackingStrengthAway(@RequestParam("averageHomeGoalsAGameOverSeason") float averageAwayGoalsScoredAGameOverSeason,
                                          @RequestParam("footballTeam") FootballTeam footballTeam,
                                          @RequestParam("selectedSeasons")List<String> selectedSeasons) throws SQLException {
        return this.footballTeamService.getAttackingStrengthAway(averageAwayGoalsScoredAGameOverSeason, footballTeam, selectedSeasons);
    }

    @CrossOrigin
    @RequestMapping(value = "/defensiveStrengthAway", method = RequestMethod.GET, produces = "application/json")
    public float getDefensiveStrengthAway(@RequestParam("averageAwayGoalsConcededAGameOverSeason") float averageAwayGoalsConcededAGameOverSeason,
                                          @RequestParam("footballTeam") FootballTeam footballTeam,
                                          @RequestParam("selectedSeasons")List<String> selectedSeasons) throws SQLException {
        return this.footballTeamService.getDefensiveStrengthAway(averageAwayGoalsConcededAGameOverSeason, footballTeam, selectedSeasons);
    }

    @CrossOrigin
    @RequestMapping(value = "/getAllFootballTeamNames", method = RequestMethod.GET, produces = "application/json")
    public String getAllFootballTeamNames() throws SQLException {
        return this.footballTeamService.getAllFootballTeamNames();
    }
}
