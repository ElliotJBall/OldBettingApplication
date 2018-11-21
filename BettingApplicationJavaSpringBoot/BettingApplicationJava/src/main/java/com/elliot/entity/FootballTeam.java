package com.elliot.entity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Elliot
 *
 */
public class FootballTeam {

    private String teamName;
    // Information surrounding the recent form of a footballTeam
    private float recentFormAverageCorners;
    private float recentFormAverageYellowCards;
    private float recentFormAverageRedCards;
    private float recentFormAverageGoalsScored;
    private float recentFormAverageFoulsCommitted;
    private float recentFormAverageShots;
    private float recentFormAverageShotsOnTarget;
    // Information surrounding the headToHead form of a footballTeam
    private float headToHeadFormAverageCorners;
    private float headToHeadFormAverageYellowCards;
    private float headToHeadFormAverageRedCards;
    private float headToHeadFormAverageGoalsScored;
    private float headToHeadFormAverageFoulsCommitted;
    private float headToHeadFormAverageShots;
    private float headToHeadFormAverageShotsOnTarget;
    // Variables to store the attacking and defensive information about the desired footballTeam
    private float attackingStrengthHome = 0.00f;
    private float defensiveStrengthHome = 0.00f;
    private float attackingStrengthAway = 0.00f;
    private float defensiveStrengthAway = 0.00f;
    private float expectedGoalsToScore = 0.00f;
    // Lists to hold the recent form and head to head match results
    public List<Form> recentTeamsForm = new ArrayList<Form>();
    public List<Form> headToHeadTeamsForm = new ArrayList<Form>();


    public FootballTeam() {

    }

    public FootballTeam(String teamName) {
        this.teamName = teamName;
    }


    /**
     * Formula used to calculate the expected amount of goals the home team is meant to score
     * For use in the Poisson distribution
     * @param homeTeamAttackStrength
     * @param awayTeamDefensiveStrength
     * @param averageHomeTeamGoalsASeason
     */
    public void calculateExpectedHomeTeamGoals(float homeTeamAttackStrength, float awayTeamDefensiveStrength, float averageHomeTeamGoalsASeason) {
        this.setExpectedGoalsToScore(homeTeamAttackStrength * awayTeamDefensiveStrength *averageHomeTeamGoalsASeason);
    }

    /**
     * Formula used to calculate the expected amount of goals the home team is meant to score
     * For use in the Poisson distribution
     * @param awayTeamAttackStrength
     * @param homeTeamDefensiveStrength
     * @param averageAwayTeamGoalsASeason
     */
    public void calculateExpectedAwayTeamGoals(float awayTeamAttackStrength, float homeTeamDefensiveStrength, float averageAwayTeamGoalsASeason) {
        this.setExpectedGoalsToScore(awayTeamAttackStrength * homeTeamDefensiveStrength * averageAwayTeamGoalsASeason);
    }

    /**
     * GETTERS and SETTERS for the variables of the FootballTeam object
     *
     */
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public float getRecentFormAverageCorners() {
        return recentFormAverageCorners;
    }

    public void setRecentFormAverageCorners(float recentFormAverageCorners) {
        this.recentFormAverageCorners = recentFormAverageCorners;
    }

    public float getRecentFormAverageYellowCards() {
        return recentFormAverageYellowCards;
    }

    public void setRecentFormAverageYellowCards(float recentFormAverageYellowCards) {
        this.recentFormAverageYellowCards = recentFormAverageYellowCards;
    }

    public float getRecentFormAverageRedCards() {
        return recentFormAverageRedCards;
    }

    public void setRecentFormAverageRedCards(float recentFormAverageRedCards) {
        this.recentFormAverageRedCards = recentFormAverageRedCards;
    }

    public float getRecentFormAverageGoalsScored() {
        return recentFormAverageGoalsScored;
    }

    public void setRecentFormAverageGoalsScored(float recentFormAverageGoalsScored) {
        this.recentFormAverageGoalsScored = recentFormAverageGoalsScored;
    }

    public float getRecentFormAverageFoulsCommitted() {
        return recentFormAverageFoulsCommitted;
    }

    public void setRecentFormAverageFoulsCommitted(float recentFormAverageFoulsCommitted) {
        this.recentFormAverageFoulsCommitted = recentFormAverageFoulsCommitted;
    }

    public float getRecentFormAverageShots() {
        return recentFormAverageShots;
    }

    public void setRecentFormAverageShots(float recentFormAverageShots) {
        this.recentFormAverageShots = recentFormAverageShots;
    }

    public float getRecentFormAverageShotsOnTarget() {
        return recentFormAverageShotsOnTarget;
    }

    public void setRecentFormAverageShotsOnTarget(float recentFormAverageShotsOnTarget) {
        this.recentFormAverageShotsOnTarget = recentFormAverageShotsOnTarget;
    }

    public float getHeadToHeadFormAverageCorners() {
        return headToHeadFormAverageCorners;
    }

    public void setHeadToHeadFormAverageCorners(float headToHeadFormAverageCorners) {
        this.headToHeadFormAverageCorners = headToHeadFormAverageCorners;
    }

    public float getHeadToHeadFormAverageYellowCards() {
        return headToHeadFormAverageYellowCards;
    }

    public void setHeadToHeadFormAverageYellowCards(float headToHeadFormAverageYellowCards) {
        this.headToHeadFormAverageYellowCards = headToHeadFormAverageYellowCards;
    }

    public float getHeadToHeadFormAverageRedCards() {
        return headToHeadFormAverageRedCards;
    }

    public void setHeadToHeadFormAverageRedCards(float headToHeadFormAverageRedCards) {
        this.headToHeadFormAverageRedCards = headToHeadFormAverageRedCards;
    }

    public float getHeadToHeadFormAverageGoalsScored() {
        return headToHeadFormAverageGoalsScored;
    }

    public void setHeadToHeadFormAverageGoalsScored(float headToHeadFormAverageGoalsScored) {
        this.headToHeadFormAverageGoalsScored = headToHeadFormAverageGoalsScored;
    }

    public float getHeadToHeadFormAverageFoulsCommitted() {
        return headToHeadFormAverageFoulsCommitted;
    }

    public void setHeadToHeadFormAverageFoulsCommitted(float headToHeadFormAverageFoulsCommitted) {
        this.headToHeadFormAverageFoulsCommitted = headToHeadFormAverageFoulsCommitted;
    }

    public float getHeadToHeadFormAverageShots() {
        return headToHeadFormAverageShots;
    }

    public void setHeadToHeadFormAverageShots(float headToHeadFormAverageShots) {
        this.headToHeadFormAverageShots = headToHeadFormAverageShots;
    }

    public float getHeadToHeadFormAverageShotsOnTarget() {
        return headToHeadFormAverageShotsOnTarget;
    }

    public void setHeadToHeadFormAverageShotsOnTarget(float headToHeadFormAverageShotsOnTarget) {
        this.headToHeadFormAverageShotsOnTarget = headToHeadFormAverageShotsOnTarget;
    }

    public float getAttackingStrengthHome() {
        return attackingStrengthHome;
    }

    public void setAttackingStrengthHome(float attackingStrengthHome) {
        this.attackingStrengthHome = attackingStrengthHome;
    }

    public float getDefensiveStrengthHome() {
        return defensiveStrengthHome;
    }

    public void setDefensiveStrengthHome(float defensiveStrengthHome) {
        this.defensiveStrengthHome = defensiveStrengthHome;
    }

    public float getAttackingStrengthAway() {
        return attackingStrengthAway;
    }

    public void setAttackingStrengthAway(float attackingStrengthAway) {
        this.attackingStrengthAway = attackingStrengthAway;
    }

    public float getDefensiveStrengthAway() {
        return defensiveStrengthAway;
    }

    public void setDefensiveStrengthAway(float defensiveStrengthAway) {
        this.defensiveStrengthAway = defensiveStrengthAway;
    }

    public float getExpectedGoalsToScore() {
        return expectedGoalsToScore;
    }

    public void setExpectedGoalsToScore(float expectedGoalsToScore) {
        this.expectedGoalsToScore = expectedGoalsToScore;
    }

    public List<Form> getRecentTeamsForm() {
        return recentTeamsForm;
    }

    public void setRecentTeamsForm(List<Form> recentTeamsForm) {
        this.recentTeamsForm = recentTeamsForm;
    }

    public List<Form> getHeadToHeadTeamsForm() {
        return headToHeadTeamsForm;
    }

    public void setHeadToHeadTeamsForm(List<Form> headToHeadTeamsForm) {
        this.headToHeadTeamsForm = headToHeadTeamsForm;
    }
}
