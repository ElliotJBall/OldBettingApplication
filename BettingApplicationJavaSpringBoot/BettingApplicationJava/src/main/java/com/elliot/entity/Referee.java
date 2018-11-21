package com.elliot.entity;


public class Referee {
    private String refereeName;
    private float averageYellowCardsPerGame;
    private float averageRedCardsPerGame;
    private float averageHomeTeamYellowCardsPerGame;
    private float averageAwayTeamYellowCardsPerGame;
    private float averageHomeTeamRedCardsPerGame;
    private float averageAwayTeamRedCardsPerGame;

    public Referee() {

    }

    public Referee(String refereeName, float averageYellowCardsPerGame, float averageRedCardsPerGame,
                   float averageHomeTeamYellowCardsPerGame, float averageAwayTeamYellowCardsPerGame,
                   float averageHomeTeamRedCardsPerGame, float averageAwayTeamRedCardsPerGame) {
        super();
        this.refereeName = refereeName;
        this.averageYellowCardsPerGame = averageYellowCardsPerGame;
        this.averageRedCardsPerGame = averageRedCardsPerGame;
        this.averageHomeTeamYellowCardsPerGame = averageHomeTeamYellowCardsPerGame;
        this.averageAwayTeamYellowCardsPerGame = averageAwayTeamYellowCardsPerGame;
        this.averageHomeTeamRedCardsPerGame = averageHomeTeamRedCardsPerGame;
        this.averageAwayTeamRedCardsPerGame = averageAwayTeamRedCardsPerGame;
    }

    // GETTERS and SETTERS for referee variables
    public String getRefereeName() {
        return refereeName;
    }

    public void setRefereeName(String refereeName) {
        this.refereeName = refereeName;
    }

    public float getAverageYellowCardsPerGame() {
        return averageYellowCardsPerGame;
    }

    public void setAverageYellowCardsPerGame(float averageYellowCardsPerGame) {
        this.averageYellowCardsPerGame = averageYellowCardsPerGame;
    }

    public float getAverageRedCardsPerGame() {
        return averageRedCardsPerGame;
    }

    public void setAverageRedCardsPerGame(float averageRedCardsPerGame) {
        this.averageRedCardsPerGame = averageRedCardsPerGame;
    }

    public float getAverageHomeTeamYellowCardsPerGame() {
        return averageHomeTeamYellowCardsPerGame;
    }

    public void setAverageHomeTeamYellowCardsPerGame(float averageHomeTeamYellowCardsPerGame) {
        this.averageHomeTeamYellowCardsPerGame = averageHomeTeamYellowCardsPerGame;
    }

    public float getAverageAwayTeamYellowCardsPerGame() {
        return averageAwayTeamYellowCardsPerGame;
    }

    public void setAverageAwayTeamYellowCardsPerGame(float averageAwayTeamYellowCardsPerGame) {
        this.averageAwayTeamYellowCardsPerGame = averageAwayTeamYellowCardsPerGame;
    }

    public float getAverageHomeTeamRedCardsPerGame() {
        return averageHomeTeamRedCardsPerGame;
    }

    public void setAverageHomeTeamRedCardsPerGame(float averageHomeTeamRedCardsPerGame) {
        this.averageHomeTeamRedCardsPerGame = averageHomeTeamRedCardsPerGame;
    }

    public float getAverageAwayTeamRedCardsPerGame() {
        return averageAwayTeamRedCardsPerGame;
    }

    public void setAverageAwayTeamRedCardsPerGame(float averageAwayTeamRedCardsPerGame) {
        this.averageAwayTeamRedCardsPerGame = averageAwayTeamRedCardsPerGame;
    }
}
