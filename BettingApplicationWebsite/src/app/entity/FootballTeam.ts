
import { Season } from './Season'
import { Http } from '@angular/http/src/http';
import { Form } from './Form';

export class FootballTeam {
    public teamName: string;
    // Information surrounding the recent form of a footballTeam
    public recentFormAverageCorners: number;
    public recentFormAverageYellowCards: number;
    public recentFormAverageRedCards: number;
    public recentFormAverageGoalsScored: number;
    public recentFormAverageFoulsCommitted: number;
    public recentFormAverageShots: number;
    public recentFormAverageShotsOnTarget: number;
    public recentTeamsForm: Form[] = [];
    // Information surrounding the headToHead form of a footballTeam
    public headToHeadFormAverageCorners: number;
    public headToHeadFormAverageYellowCards: number;
    public headToHeadFormAverageRedCards: number;
    public headToHeadFormAverageGoalsScored: number;
    public headToHeadFormAverageFoulsCommitted: number;
    public headToHeadFormAverageShots: number;
    public headToHeadFormAverageShotsOnTarget: number;
    public headToHeadTeamsForm: Form[] = [];
    // Variables to store the attacking and defensive information about the desired footballTeam
    public attackingStrengthHome: number;
    public defensiveStrengthHome: number;
    public attackingStrengthAway: number;
    public defensiveStrengthAway: number;
    public expectedGoalsToScore : number;

	// Constructors for a FootballTeam object
    constructor() {
        
    }
    
	// Methods to calculate the expected number of goals a FootballTeam is meant to score
	public calculateExpectedHomeTeamGoals(homeTeamAttackStrength: number
		, awayTeamDefensiveStrength: number
		, averageHomeTeamGoalsASeason: number): number {
		return this.expectedGoalsToScore = homeTeamAttackStrength * awayTeamDefensiveStrength *averageHomeTeamGoalsASeason;
	}

	public calculateExpectedAwayTeamGoals(awayTeamAttackStrength: number
		, homeTeamDefensiveStrength: number
		, averageAwayTeamGoalsASeason: number): number {
		return this.expectedGoalsToScore = (awayTeamAttackStrength * homeTeamDefensiveStrength * averageAwayTeamGoalsASeason);
    }
}