package com.elliot.entity;

public class Fixture {

    private String dateOfMatch;
    private String homeTeam;
    private String awayTeam;

    public Fixture() {

    }

    public Fixture(String dateOfMatch, String homeTeam, String awayTeam) {
        this.dateOfMatch = dateOfMatch;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    // GETTERS and SETTERS for Fixture Variables
    public String getDateOfMatch() {
        return dateOfMatch;
    }

    public void setDateOfMatch(String dateOfMatch) {
        this.dateOfMatch = dateOfMatch;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }
}
