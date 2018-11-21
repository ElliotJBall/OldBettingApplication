import { Result } from './Result';

export class Season {
    public seasonId: number;
    public seasonName: string;
    public seasonFullTimeResults: Array<Result>;
    // Average goals goals scored and conceded home and away.
    public averageHomeTeamGoalsAGame: number;
    public averageAwayTeamGoalsAGame: number;
    public averageHomeTeamGoalsConcededAGame: number;
    public averageAwayTeamGoalsConcededAGame: number;

    private averageHomeTeamCornersAGame: number;
    private averageAwayTeamCornersAGame: number;
    private totalHomeTeamCorners: number;
    private totalAwayTeamCorners: number;

    private averageHomeTeamFoulsCommittedAGame: number;
    private averageAwayTeamFoulsCommittedAGame: number;
    private totalHomeTeamFoulsCommitted: number;
    private totalAwayTeamFoulsCommitted: number;

    private averageHomeTeamShotsAGame: number;
    private averageAwayTeamShotsAGame: number;
    private averageHomeTeamShotsOnTargetAGame: number;
    private averageAwayTeamShotsOnTargetAGame: number;

    private averageHomeTeamYellowCardsAGame: number;
    private averageHomeTeamRedCardsAGame: number;
    private averageAwayTeamYellowCardsAGame: number;
    private averageAwayTeamRedCardsAGame: number;

    private totalHomeTeamShots: number;
    private totalAwayTeamShots: number;
    private totalHomeTeamShotsOnTarge: number;
    private totalAwayTeamShotsOnTarget: number;

    private totalHomeTeamYellowCardsGiven: number;
    private totalHomeTeamRedCardsGiven: number;
    private totalAwayTeamYellowCardsGiven: number;
    private totalAwayTeamRedCardsGiven: number;

    private totalHomeTeamGoals: number;
    private totalAwayTeamGoals: number;
    private totalGoalsInSeaon: number;

    private totalHomeTeamWins: number;
    private totalAwayTeamWins: number;
    private totalDraws: number;

    private totalGamesPlayedInSeason: number;

    private homeTeamWinPercentage: number;
    private awayTeamWinPercentage: number;
    private drawPercentage: number;

    constructor(seasonName: string) {
        this.seasonName = seasonName;
    }

}