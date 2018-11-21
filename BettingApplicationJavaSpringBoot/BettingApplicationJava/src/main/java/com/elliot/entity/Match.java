package com.elliot.entity;


import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Match {
    private int matchID;
    private String division;
    private Result fullTimeResult;
    private Result halfTimeResult;
    private Date date;
    // Information surrounding the home team stats
    private int homeTeamCorners;
    private int homeTeamShotsOnTarget;
    private int homeTeamShots;
    private int homeTeamFoulsCommitted;
    private int homeTeamYellowCards;
    private int homeTeamRedCards;
    private int fullTimeHomeTeamGoals;
    private String homeTeam;
    // Information for the away teams stats
    private int awayTeamCorners;
    private int awayTeamShotsOnTarget;
    private int awayTeamShots;
    private int awayTeamFoulsCommitted;
    private int awayTeamYellowCards;
    private int awayTeamRedCards;
    private int halfTimeHomeTeamGoals;
    private int halfTimeAwayTeamGoals;
    private int fullTimeAwayTeamGoals;
    private String awayTeam;

    private String referee;

    public Match(int matchID, String division, Result fullTimeResult, Result halfTimeResult, Date date, int homeTeamCorners,
                 int homeTeamShotsOnTarget, int awayTeamCorners, int homeTeamShots, int homeTeamFoulsCommitted,
                 int homeTeamYellowCards, int homeTeamRedCards, int fullTimeHomeTeamGoals, String homeTeam,
                 int awayTeamShotsOnTarget, int awayTeamShots, int awayTeamFoulsCommitted, int awayTeamYellowCards,
                 int awayTeamRedCards, int halfTimeHomeTeamGoals, int halfTimeAwayTeamGoals, int fullTimeAwayTeamGoals,
                 String awayTeam, String referee) {
        this.matchID = matchID;
        this.division = division;
        this.fullTimeResult = fullTimeResult;
        this.halfTimeResult = halfTimeResult;
        this.date = date;
        this.homeTeamCorners = homeTeamCorners;
        this.homeTeamShotsOnTarget = homeTeamShotsOnTarget;
        this.awayTeamCorners = awayTeamCorners;
        this.homeTeamShots = homeTeamShots;
        this.homeTeamFoulsCommitted = homeTeamFoulsCommitted;
        this.homeTeamYellowCards = homeTeamYellowCards;
        this.homeTeamRedCards = homeTeamRedCards;
        this.fullTimeHomeTeamGoals = fullTimeHomeTeamGoals;
        this.homeTeam = homeTeam;
        this.awayTeamShotsOnTarget = awayTeamShotsOnTarget;
        this.awayTeamShots = awayTeamShots;
        this.awayTeamFoulsCommitted = awayTeamFoulsCommitted;
        this.awayTeamYellowCards = awayTeamYellowCards;
        this.awayTeamRedCards = awayTeamRedCards;
        this.halfTimeHomeTeamGoals = halfTimeHomeTeamGoals;
        this.halfTimeAwayTeamGoals = halfTimeAwayTeamGoals;
        this.fullTimeAwayTeamGoals = fullTimeAwayTeamGoals;
        this.awayTeam = awayTeam;
        this.referee = referee;
    }

    /**
     * compareMatchesByDate method to compare two Match objects dates and to help sort a list of Matches from most recent
     * @param List<Match> allMatches
     */
    @SuppressWarnings("JavadocReference")
    public static void sortMatchesByDate(List<Match> allMatches) {
        Collections.sort(allMatches, (match1, match2) -> match2.getDate().compareTo(match1.getDate()));
    }

    // GETTERS and SETTERS for Match Variables
    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Result getFullTimeResult() {
        return fullTimeResult;
    }

    public void setFullTimeResult(Result fullTimeResult) {
        this.fullTimeResult = fullTimeResult;
    }

    public Result getHalfTimeResult() {
        return halfTimeResult;
    }

    public void setHalfTimeResult(Result halfTimeResult) {
        this.halfTimeResult = halfTimeResult;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHomeTeamCorners() {
        return homeTeamCorners;
    }

    public void setHomeTeamCorners(int homeTeamCorners) {
        this.homeTeamCorners = homeTeamCorners;
    }

    public int getHomeTeamShotsOnTarget() {
        return homeTeamShotsOnTarget;
    }

    public void setHomeTeamShotsOnTarget(int homeTeamShotsOnTarget) {
        this.homeTeamShotsOnTarget = homeTeamShotsOnTarget;
    }

    public int getAwayTeamCorners() {
        return awayTeamCorners;
    }

    public void setAwayTeamCorners(int awayTeamCorners) {
        this.awayTeamCorners = awayTeamCorners;
    }

    public int getHomeTeamShots() {
        return homeTeamShots;
    }

    public void setHomeTeamShots(int homeTeamShots) {
        this.homeTeamShots = homeTeamShots;
    }

    public int getHomeTeamFoulsCommitted() {
        return homeTeamFoulsCommitted;
    }

    public void setHomeTeamFoulsCommitted(int homeTeamFoulsCommitted) {
        this.homeTeamFoulsCommitted = homeTeamFoulsCommitted;
    }

    public int getHomeTeamYellowCards() {
        return homeTeamYellowCards;
    }

    public void setHomeTeamYellowCards(int homeTeamYellowCards) {
        this.homeTeamYellowCards = homeTeamYellowCards;
    }

    public int getHomeTeamRedCards() {
        return homeTeamRedCards;
    }

    public void setHomeTeamRedCards(int homeTeamRedCards) {
        this.homeTeamRedCards = homeTeamRedCards;
    }

    public int getFullTimeHomeTeamGoals() {
        return fullTimeHomeTeamGoals;
    }

    public void setFullTimeHomeTeamGoals(int fullTimeHomeTeamGoals) {
        this.fullTimeHomeTeamGoals = fullTimeHomeTeamGoals;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public int getAwayTeamShotsOnTarget() {
        return awayTeamShotsOnTarget;
    }

    public void setAwayTeamShotsOnTarget(int awayTeamShotsOnTarget) {
        this.awayTeamShotsOnTarget = awayTeamShotsOnTarget;
    }

    public int getAwayTeamShots() {
        return awayTeamShots;
    }

    public void setAwayTeamShots(int awayTeamShots) {
        this.awayTeamShots = awayTeamShots;
    }

    public int getAwayTeamFoulsCommitted() {
        return awayTeamFoulsCommitted;
    }

    public void setAwayTeamFoulsCommitted(int awayTeamFoulsCommitted) {
        this.awayTeamFoulsCommitted = awayTeamFoulsCommitted;
    }

    public int getAwayTeamYellowCards() {
        return awayTeamYellowCards;
    }

    public void setAwayTeamYellowCards(int awayTeamYellowCards) {
        this.awayTeamYellowCards = awayTeamYellowCards;
    }

    public int getAwayTeamRedCards() {
        return awayTeamRedCards;
    }

    public void setAwayTeamRedCards(int awayTeamRedCards) {
        this.awayTeamRedCards = awayTeamRedCards;
    }

    public int getHalfTimeHomeTeamGoals() {
        return halfTimeHomeTeamGoals;
    }

    public void setHalfTimeHomeTeamGoals(int halfTimeHomeTeamGoals) {
        this.halfTimeHomeTeamGoals = halfTimeHomeTeamGoals;
    }

    public int getHalfTimeAwayTeamGoals() {
        return halfTimeAwayTeamGoals;
    }

    public void setHalfTimeAwayTeamGoals(int halfTimeAwayTeamGoals) {
        this.halfTimeAwayTeamGoals = halfTimeAwayTeamGoals;
    }

    public int getFullTimeAwayTeamGoals() {
        return fullTimeAwayTeamGoals;
    }

    public void setFullTimeAwayTeamGoals(int fullTimeAwayTeamGoals) {
        this.fullTimeAwayTeamGoals = fullTimeAwayTeamGoals;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }
}
