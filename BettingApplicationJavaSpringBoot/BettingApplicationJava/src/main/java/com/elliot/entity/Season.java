package com.elliot.entity;

import java.util.ArrayList;
import java.util.List;

public class Season {
    private int seasonId;
    private String seasonName;
    private List<Result> seasonFullTimeResults = new ArrayList<Result>();

    // List of all the matches that were played in that season
    public List<Match> matchesPlayedInSeason = new ArrayList<Match>();

    // Average goals goals scored and conceded home and away.
    public float averageHomeTeamGoalsAGame;
    public float averageAwayTeamGoalsAGame;
    public float averageHomeTeamGoalsConcededAGame;
    public float averageAwayTeamGoalsConcededAGame;

    public float averageHomeTeamCornersAGame;
    public float averageAwayTeamCornersAGame;
    public int totalHomeTeamCorners;
    public int totalAwayTeamCorners;

    public float averageHomeTeamFoulsCommittedAGame;
    public float averageAwayTeamFoulsCommittedAGame;
    public int totalHomeTeamFoulsCommitted;
    public int totalAwayTeamFoulsCommitted;

    public float averageHomeTeamShotsAGame;
    public float averageAwayTeamShotsAGame;
    public float averageHomeTeamShotsOnTargetAGame;
    public float averageAwayTeamShotsOnTargetAGame;

    public float averageHomeTeamYellowCardsAGame;
    public float averageHomeTeamRedCardsAGame;
    public float averageAwayTeamYellowCardsAGame;
    public float averageAwayTeamRedCardsAGame;

    public int totalHomeTeamShots;
    public int totalAwayTeamShots;
    public int totalHomeTeamShotsOnTarget;
    public int totalAwayTeamShotsOnTarget;

    public int totalHomeTeamYellowCardsGiven;
    public int totalHomeTeamRedCardsGiven;
    public int totalAwayTeamYellowCardsGiven;
    public int totalAwayTeamRedCardsGiven;

    public int totalHomeTeamGoals;
    public int totalAwayTeamGoals;
    public int totalGoalsInSeason;

    public int totalHomeTeamWins;
    public int totalAwayTeamWins;
    public int totalDraws;

    public int totalGamesPlayedInSeason;

    public float homeTeamWinPercentage;
    public float awayTeamWinPercentage;
    public float drawPercentage;

    public Season() {

    }

    public Season(String seasonName) {
        this.seasonName = seasonName;
    }

    public void calculateSeasonStatistics() {
        int matchesPlayed = 0;

        for (Match match : this.getMatchesPlayedInSeason()) {
            matchesPlayed++;

            // Goals
            this.setTotalHomeTeamGoals(this.getTotalHomeTeamGoals() + match.getFullTimeHomeTeamGoals());
            this.setTotalAwayTeamGoals(this.getTotalAwayTeamGoals() + match.getFullTimeAwayTeamGoals());

            // Call Match method to determine the matches full time result (Potentially move to a utility class?)
            this.seasonFullTimeResults.add(match.getFullTimeResult());

            // Yellow + Red Cards
            this.setTotalHomeTeamYellowCardsGiven(this.getTotalHomeTeamYellowCardsGiven() + match.getHomeTeamYellowCards());
            this.setTotalAwayTeamYellowCardsGiven(this.getTotalAwayTeamYellowCardsGiven() + match.getAwayTeamYellowCards());

            this.setTotalHomeTeamRedCardsGiven(this.getTotalHomeTeamRedCardsGiven() + match.getHomeTeamRedCards());
            this.setTotalAwayTeamRedCardsGiven(this.getTotalAwayTeamRedCardsGiven() + match.getAwayTeamRedCards());

            // Fouls
            this.setTotalHomeTeamFoulsCommitted(this.getTotalHomeTeamFoulsCommitted() + match.getHomeTeamFoulsCommitted());
            this.setTotalAwayTeamFoulsCommitted(this.getTotalAwayTeamFoulsCommitted() + match.getAwayTeamFoulsCommitted());

            // Corners
            this.setTotalHomeTeamCorners(this.getTotalHomeTeamCorners() + match.getHomeTeamCorners());
            this.setTotalAwayTeamCorners(this.getTotalAwayTeamCorners() + match.getAwayTeamCorners());

            // Shots + shots on target
            this.setTotalHomeTeamShotsOnTarget(this.getTotalHomeTeamShotsOnTarget() + match.getHomeTeamShotsOnTarget());
            this.setTotalAwayTeamShotsOnTarget(this.getTotalAwayTeamShotsOnTarget() + match.getAwayTeamShotsOnTarget());

            this.setTotalHomeTeamShots(this.getTotalHomeTeamShots() + match.getHomeTeamShots());
            this.setTotalAwayTeamShots(this.getTotalAwayTeamShots() + match.getAwayTeamShots());

            switch (match.getFullTimeResult()) {
                case H:
                    this.setTotalHomeTeamWins(this.getTotalHomeTeamWins() + 1);
                    break;
                case A:
                    this.setTotalAwayTeamWins(this.getTotalAwayTeamWins() + 1);
                    break;
                case D:
                    this.setTotalDraws(this.getTotalDraws() + 1);
                    break;
            }
        }

        this.setTotalGamesPlayedInSeason(matchesPlayed);

        // Total Goals in season
        this.setTotalGoalsInSeaon(this.getTotalHomeTeamGoals() + this.getTotalAwayTeamGoals());

        // Average Home + Away goals a game
        this.setAverageHomeTeamGoalsAGame((float)this.getTotalHomeTeamGoals() / (float)this.getTotalGamesPlayedInSeason());
        this.setAverageAwayTeamGoalsAGame((float)this.getTotalAwayTeamGoals() / (float)this.getTotalGamesPlayedInSeason());

        // Average Home + Away goals conceded a game
        this.setAverageHomeTeamGoalsConcededAGame(this.getAverageAwayTeamGoalsAGame());
        this.setAverageAwayTeamGoalsConcededAGame(this.getAverageHomeTeamGoalsAGame());

        // Average Red + Yellow cards a game
        this.setAverageHomeTeamYellowCardsAGame((float)this.getTotalHomeTeamYellowCardsGiven() / (float)this.getTotalGamesPlayedInSeason());
        this.setAverageHomeTeamRedCardsAGame((float)this.getTotalHomeTeamRedCardsGiven() / (float)this.getTotalGamesPlayedInSeason());
        this.setAverageAwayTeamYellowCardsAGame((float)this.getTotalAwayTeamYellowCardsGiven() / (float)this.getTotalGamesPlayedInSeason());
        this.setAverageAwayTeamRedCardsAGame((float)this.getTotalAwayTeamRedCardsGiven() / (float)this.getTotalGamesPlayedInSeason());

        // Average Fouls committed a game
        this.setAverageHomeTeamFoulsCommittedAGame((float)this.getTotalHomeTeamFoulsCommitted() / (float)this.getTotalGamesPlayedInSeason());
        this.setAverageAwayTeamFoulsCommittedAGame((float)this.getTotalAwayTeamFoulsCommitted() / (float)this.getTotalGamesPlayedInSeason());

        // Average Corners a game
        this.setAverageHomeTeamCornersAGame((float)this.getTotalHomeTeamCorners() / (float)this.getTotalGamesPlayedInSeason());
        this.setAverageAwayTeamCornersAGame((float)this.getTotalAwayTeamCorners() / (float)this.getTotalGamesPlayedInSeason());

        // Average Shots and Shots on target a game
        this.setAverageHomeTeamShotsAGame((float)this.getTotalHomeTeamShots() / (float)this.getTotalGamesPlayedInSeason());
        this.setAverageAwayTeamShotsAGame((float)this.getTotalAwayTeamShots() / (float)this.getTotalGamesPlayedInSeason());
        this.setAverageHomeTeamShotsOnTargetAGame((float)this.getTotalHomeTeamShotsOnTarget() / (float)this.getTotalGamesPlayedInSeason());
        this.setAverageAwayTeamShotsOnTargetAGame((float)this.getTotalAwayTeamShotsOnTarget() / (float)this.getTotalGamesPlayedInSeason());

        // Average Home | Away | Draw percentage
        this.setHomeTeamWinPercentage(this.getTotalHomeTeamWins() / (float) this.getTotalGamesPlayedInSeason());
        this.setAwayTeamWinPercentage(this.getTotalAwayTeamWins() / (float) this.getTotalGamesPlayedInSeason());
        this.setDrawPercentage(this.getTotalDraws() / (float) this.getTotalGamesPlayedInSeason());

    }

    // GETTERS and SETTERS for Season Variables
    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public List<Result> getSeasonFullTimeResults() {
        return seasonFullTimeResults;
    }

    public void setSeasonFullTimeResults(List<Result> seasonFullTimeResults) {
        this.seasonFullTimeResults = seasonFullTimeResults;
    }

    public float getAverageHomeTeamGoalsAGame() {
        return averageHomeTeamGoalsAGame;
    }

    public void setAverageHomeTeamGoalsAGame(float averageHomeTeamGoalsAGame) {
        this.averageHomeTeamGoalsAGame = averageHomeTeamGoalsAGame;
    }

    public float getAverageAwayTeamGoalsAGame() {
        return averageAwayTeamGoalsAGame;
    }

    public void setAverageAwayTeamGoalsAGame(float averageAwayTeamGoalsAGame) {
        this.averageAwayTeamGoalsAGame = averageAwayTeamGoalsAGame;
    }

    public float getAverageHomeTeamGoalsConcededAGame() {
        return averageHomeTeamGoalsConcededAGame;
    }

    public void setAverageHomeTeamGoalsConcededAGame(float averageHomeTeamGoalsConcededAGame) {
        this.averageHomeTeamGoalsConcededAGame = averageHomeTeamGoalsConcededAGame;
    }

    public float getAverageAwayTeamGoalsConcededAGame() {
        return averageAwayTeamGoalsConcededAGame;
    }

    public void setAverageAwayTeamGoalsConcededAGame(float averageAwayTeamGoalsConcededAGame) {
        this.averageAwayTeamGoalsConcededAGame = averageAwayTeamGoalsConcededAGame;
    }

    public float getAverageHomeTeamCornersAGame() {
        return averageHomeTeamCornersAGame;
    }

    public void setAverageHomeTeamCornersAGame(float averageHomeTeamCornersAGame) {
        this.averageHomeTeamCornersAGame = averageHomeTeamCornersAGame;
    }

    public float getAverageAwayTeamCornersAGame() {
        return averageAwayTeamCornersAGame;
    }

    public void setAverageAwayTeamCornersAGame(float averageAwayTeamCornersAGame) {
        this.averageAwayTeamCornersAGame = averageAwayTeamCornersAGame;
    }

    public int getTotalHomeTeamCorners() {
        return totalHomeTeamCorners;
    }

    public void setTotalHomeTeamCorners(int totalHomeTeamCorners) {
        this.totalHomeTeamCorners = totalHomeTeamCorners;
    }

    public int getTotalAwayTeamCorners() {
        return totalAwayTeamCorners;
    }

    public void setTotalAwayTeamCorners(int totalAwayTeamCorners) {
        this.totalAwayTeamCorners = totalAwayTeamCorners;
    }

    public float getAverageHomeTeamFoulsCommittedAGame() {
        return averageHomeTeamFoulsCommittedAGame;
    }

    public void setAverageHomeTeamFoulsCommittedAGame(float averageHomeTeamFoulsCommittedAGame) {
        this.averageHomeTeamFoulsCommittedAGame = averageHomeTeamFoulsCommittedAGame;
    }

    public float getAverageAwayTeamFoulsCommittedAGame() {
        return averageAwayTeamFoulsCommittedAGame;
    }

    public void setAverageAwayTeamFoulsCommittedAGame(float averageAwayTeamFoulsCommittedAGame) {
        this.averageAwayTeamFoulsCommittedAGame = averageAwayTeamFoulsCommittedAGame;
    }

    public int getTotalHomeTeamFoulsCommitted() {
        return totalHomeTeamFoulsCommitted;
    }

    public void setTotalHomeTeamFoulsCommitted(int totalHomeTeamFoulsCommitted) {
        this.totalHomeTeamFoulsCommitted = totalHomeTeamFoulsCommitted;
    }

    public int getTotalAwayTeamFoulsCommitted() {
        return totalAwayTeamFoulsCommitted;
    }

    public void setTotalAwayTeamFoulsCommitted(int totalAwayTeamFoulsCommitted) {
        this.totalAwayTeamFoulsCommitted = totalAwayTeamFoulsCommitted;
    }

    public float getAverageHomeTeamShotsAGame() {
        return averageHomeTeamShotsAGame;
    }

    public void setAverageHomeTeamShotsAGame(float averageHomeTeamShotsAGame) {
        this.averageHomeTeamShotsAGame = averageHomeTeamShotsAGame;
    }

    public float getAverageAwayTeamShotsAGame() {
        return averageAwayTeamShotsAGame;
    }

    public void setAverageAwayTeamShotsAGame(float averageAwayTeamShotsAGame) {
        this.averageAwayTeamShotsAGame = averageAwayTeamShotsAGame;
    }

    public float getAverageHomeTeamShotsOnTargetAGame() {
        return averageHomeTeamShotsOnTargetAGame;
    }

    public void setAverageHomeTeamShotsOnTargetAGame(float averageHomeTeamShotsOnTargetAGame) {
        this.averageHomeTeamShotsOnTargetAGame = averageHomeTeamShotsOnTargetAGame;
    }

    public float getAverageAwayTeamShotsOnTargetAGame() {
        return averageAwayTeamShotsOnTargetAGame;
    }

    public void setAverageAwayTeamShotsOnTargetAGame(float averageAwayTeamShotsOnTargetAGame) {
        this.averageAwayTeamShotsOnTargetAGame = averageAwayTeamShotsOnTargetAGame;
    }

    public float getAverageHomeTeamYellowCardsAGame() {
        return averageHomeTeamYellowCardsAGame;
    }

    public void setAverageHomeTeamYellowCardsAGame(float averageHomeTeamYellowCardsAGame) {
        this.averageHomeTeamYellowCardsAGame = averageHomeTeamYellowCardsAGame;
    }

    public float getAverageHomeTeamRedCardsAGame() {
        return averageHomeTeamRedCardsAGame;
    }

    public void setAverageHomeTeamRedCardsAGame(float averageHomeTeamRedCardsAGame) {
        this.averageHomeTeamRedCardsAGame = averageHomeTeamRedCardsAGame;
    }

    public float getAverageAwayTeamYellowCardsAGame() {
        return averageAwayTeamYellowCardsAGame;
    }

    public void setAverageAwayTeamYellowCardsAGame(float averageAwayTeamYellowCardsAGame) {
        this.averageAwayTeamYellowCardsAGame = averageAwayTeamYellowCardsAGame;
    }

    public float getAverageAwayTeamRedCardsAGame() {
        return averageAwayTeamRedCardsAGame;
    }

    public void setAverageAwayTeamRedCardsAGame(float averageAwayTeamRedCardsAGame) {
        this.averageAwayTeamRedCardsAGame = averageAwayTeamRedCardsAGame;
    }

    public int getTotalHomeTeamShots() {
        return totalHomeTeamShots;
    }

    public void setTotalHomeTeamShots(int totalHomeTeamShots) {
        this.totalHomeTeamShots = totalHomeTeamShots;
    }

    public int getTotalAwayTeamShots() {
        return totalAwayTeamShots;
    }

    public void setTotalAwayTeamShots(int totalAwayTeamShots) {
        this.totalAwayTeamShots = totalAwayTeamShots;
    }

    public int getTotalHomeTeamShotsOnTarget() {
        return totalHomeTeamShotsOnTarget;
    }

    public void setTotalHomeTeamShotsOnTarget(int totalHomeTeamShotsOnTarget) {
        this.totalHomeTeamShotsOnTarget = totalHomeTeamShotsOnTarget;
    }

    public int getTotalAwayTeamShotsOnTarget() {
        return totalAwayTeamShotsOnTarget;
    }

    public void setTotalAwayTeamShotsOnTarget(int totalAwayTeamShotsOnTarget) {
        this.totalAwayTeamShotsOnTarget = totalAwayTeamShotsOnTarget;
    }

    public int getTotalHomeTeamYellowCardsGiven() {
        return totalHomeTeamYellowCardsGiven;
    }

    public void setTotalHomeTeamYellowCardsGiven(int totalHomeTeamYellowCardsGiven) {
        this.totalHomeTeamYellowCardsGiven = totalHomeTeamYellowCardsGiven;
    }

    public int getTotalHomeTeamRedCardsGiven() {
        return totalHomeTeamRedCardsGiven;
    }

    public void setTotalHomeTeamRedCardsGiven(int totalHomeTeamRedCardsGiven) {
        this.totalHomeTeamRedCardsGiven = totalHomeTeamRedCardsGiven;
    }

    public int getTotalAwayTeamYellowCardsGiven() {
        return totalAwayTeamYellowCardsGiven;
    }

    public void setTotalAwayTeamYellowCardsGiven(int totalAwayTeamYellowCardsGiven) {
        this.totalAwayTeamYellowCardsGiven = totalAwayTeamYellowCardsGiven;
    }

    public int getTotalAwayTeamRedCardsGiven() {
        return totalAwayTeamRedCardsGiven;
    }

    public void setTotalAwayTeamRedCardsGiven(int totalAwayTeamRedCardsGiven) {
        this.totalAwayTeamRedCardsGiven = totalAwayTeamRedCardsGiven;
    }

    public int getTotalHomeTeamGoals() {
        return totalHomeTeamGoals;
    }

    public void setTotalHomeTeamGoals(int totalHomeTeamGoals) {
        this.totalHomeTeamGoals = totalHomeTeamGoals;
    }

    public int getTotalAwayTeamGoals() {
        return totalAwayTeamGoals;
    }

    public void setTotalAwayTeamGoals(int totalAwayTeamGoals) {
        this.totalAwayTeamGoals = totalAwayTeamGoals;
    }

    public int getTotalGoalsInSeaon() {
        return totalGoalsInSeason;
    }

    public void setTotalGoalsInSeaon(int totalGoalsInSeason) {
        this.totalGoalsInSeason = totalGoalsInSeason;
    }

    public int getTotalHomeTeamWins() {
        return totalHomeTeamWins;
    }

    public void setTotalHomeTeamWins(int totalHomeTeamWins) {
        this.totalHomeTeamWins = totalHomeTeamWins;
    }

    public int getTotalAwayTeamWins() {
        return totalAwayTeamWins;
    }

    public void setTotalAwayTeamWins(int totalAwayTeamWins) {
        this.totalAwayTeamWins = totalAwayTeamWins;
    }

    public int getTotalDraws() {
        return totalDraws;
    }

    public void setTotalDraws(int totalDraws) {
        this.totalDraws = totalDraws;
    }

    public int getTotalGamesPlayedInSeason() {
        return totalGamesPlayedInSeason;
    }

    public void setTotalGamesPlayedInSeason(int totalGamesPlayedInSeason) {
        this.totalGamesPlayedInSeason = totalGamesPlayedInSeason;
    }

    public float getHomeTeamWinPercentage() {
        return homeTeamWinPercentage;
    }

    public void setHomeTeamWinPercentage(float homeTeamWinPercentage) {
        this.homeTeamWinPercentage = homeTeamWinPercentage;
    }

    public float getAwayTeamWinPercentage() {
        return awayTeamWinPercentage;
    }

    public void setAwayTeamWinPercentage(float awayTeamWinPercentage) {
        this.awayTeamWinPercentage = awayTeamWinPercentage;
    }

    public float getDrawPercentage() {
        return drawPercentage;
    }

    public void setDrawPercentage(float drawPercentage) {
        this.drawPercentage = drawPercentage;
    }

    public List<Match> getMatchesPlayedInSeason () {
        return matchesPlayedInSeason;
    }

    public void setMatchesPlayedInSeason (List<Match> matchesInSeason) {
        this.matchesPlayedInSeason  = matchesInSeason;
    }

    public int getTotalGoalsInSeason() {
        return totalGoalsInSeason;
    }

    public void setTotalGoalsInSeason(int totalGoalsInSeason) {
        this.totalGoalsInSeason = totalGoalsInSeason;
    }
}
