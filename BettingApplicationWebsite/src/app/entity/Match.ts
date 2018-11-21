import { Result } from './Result';

export class Match {

    homeTeam: string;
    awayTeam: string;
    dateOfMatch: string;

    private matchID: number;
    private division: String;
    private fullTimeResult: Result;
    private halfTimeResult: Result;
    private date: Date;
    // Information surrounding the home team stats
    private homeTeamCorners: number;
    private homeTeamShotsOnTarget: number;
    private homeTeamShots: number;
    private homeTeamFoulsCommitted: number;
    private homeTeamYellowCards: number;
    private homeTeamRedCards: number;
    private fullTimeHomeTeamGoals: number;
    // Information for the away teams stats
    private awayTeamCorners: number;
    private awayTeamShotsOnTarget: number;
    private awayTeamShots: number;
    private awayTeamFoulsCommitted: number;
    private awayTeamYellowCards: number;
    private awayTeamRedCards: number;
    private halfTimeHomeTeamGoals: number;
    private halfTimeAwayTeamGoals: number;
    private fullTimeAwayTeamGoals: number;

    constructor($matchID: number, $division: String, $fullTimeResult: Result, $halfTimeResult: Result, $date: Date
        , $homeTeamCorners: number, $homeTeamShotsOnTarget: number, $homeTeamShots: number, $homeTeamFoulsCommitted: number
        , $homeTeamYellowCards: number, $homeTeamRedCards: number, $fullTimeHomeTeamGoals: number, $awayTeamCorners: number
        , $awayTeamShotsOnTarget: number, $awayTeamShots: number, $awayTeamFoulsCommitted: number, $awayTeamYellowCards: number
        , $awayTeamRedCards: number, $halfTimeHomeTeamGoals: number, $halfTimeAwayTeamGoals: number, $fullTimeAwayTeamGoals: number) {
		this.matchID = $matchID;
		this.division = $division;
		this.fullTimeResult = $fullTimeResult;
		this.halfTimeResult = $halfTimeResult;
		this.date = $date;
		this.homeTeamCorners = $homeTeamCorners;
		this.homeTeamShotsOnTarget = $homeTeamShotsOnTarget;
		this.homeTeamShots = $homeTeamShots;
		this.homeTeamFoulsCommitted = $homeTeamFoulsCommitted;
		this.homeTeamYellowCards = $homeTeamYellowCards;
		this.homeTeamRedCards = $homeTeamRedCards;
		this.fullTimeHomeTeamGoals = $fullTimeHomeTeamGoals;
		this.awayTeamCorners = $awayTeamCorners;
		this.awayTeamShotsOnTarget = $awayTeamShotsOnTarget;
		this.awayTeamShots = $awayTeamShots;
		this.awayTeamFoulsCommitted = $awayTeamFoulsCommitted;
		this.awayTeamYellowCards = $awayTeamYellowCards;
		this.awayTeamRedCards = $awayTeamRedCards;
		this.halfTimeHomeTeamGoals = $halfTimeHomeTeamGoals;
		this.halfTimeAwayTeamGoals = $halfTimeAwayTeamGoals;
		this.fullTimeAwayTeamGoals = $fullTimeAwayTeamGoals;
	}
}