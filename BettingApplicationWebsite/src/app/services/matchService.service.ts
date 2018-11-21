import { Injectable, Inject } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';
import { headersToString } from 'selenium-webdriver/http';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

import { Season } from '../entity/Season';
import { Match } from '../entity/Match';
import { FootballTeam } from '../entity/FootballTeam';
import { validateConfig } from '@angular/router/src/config';

@Injectable()
export class MatchService {
  public homeTeam: FootballTeam = new FootballTeam();
  public awayTeam: FootballTeam = new FootballTeam();

  public selectedSeasonNames: Array<String> = [];
  private selectedSeasons: Array<Season> = [];

  private homeTeamExpectedGoals: number;
  private awayTeamExpectedGoals: number;

  private homeTeamPoissonGoalPercentage: number[] = [];
  private awayTeamPoissonGoalPercentage: number[] = [];

  public poissonDistribution: number[][] = [];
  private kellyCriterion: number[][] = [];

  //TODO: Add a hash map to store any previously searched FootballTeams, Search this hashmap first before making any calls to the database.
  //Will need to check if the selectedSeasons are stored with it otherwise that will require an additional API call (Still probably better than
  //doing it all over again).

  constructor(private http: Http) { }

  // Set the names of the home and away FootballTeam objects the user has input
  public getFootballStats(homeTeam, awayTeam) {
    this.selectedSeasons = [];

    const getSelectedFootballTeams = `http://localhost:8080/footballTeam/headToHeadForm?homeTeam=${homeTeam}&awayTeam=${awayTeam}&selectedSeasons=${this.selectedSeasonNames}`;
    const getSelectedSeasons = `http://localhost:8080/season/seasons?selectedSeasons=${this.selectedSeasonNames}`

    this.getFootballTeamData(getSelectedFootballTeams)
      .then(selectedTeams => {
        this.homeTeam = selectedTeams[0];
        this.awayTeam = selectedTeams[1];

        this.homeTeam.expectedGoalsToScore = this.calculateExpectedHomeTeamGoals(this.homeTeam.attackingStrengthHome, this.awayTeam.defensiveStrengthAway, 1);
        this.awayTeam.expectedGoalsToScore = this.calculateExpectedAwayTeamGoals(this.awayTeam.attackingStrengthAway, this.homeTeam.defensiveStrengthHome, 1);

        this.homeTeamPoissonGoalPercentage = this.calculatePoissonGoalPercentage(this.homeTeam.expectedGoalsToScore);
        this.awayTeamPoissonGoalPercentage = this.calculatePoissonGoalPercentage(this.awayTeam.expectedGoalsToScore);

        // Calculate the Poisson Distribution and Kelly criterion 
        this.poissonDistribution = this.calculatePoissonDistribution(this.homeTeamPoissonGoalPercentage, this.awayTeamPoissonGoalPercentage);
        this.kellyCriterion = this.calculateKellyCriterion(this.poissonDistribution, 10.00);
      });

    this.getSelectedSeasonsData(getSelectedSeasons)
      .then(selectedSeasons => {
          selectedSeasons.forEach(element => {
            this.selectedSeasons.push(element);
          });
      });

    // Empty the selectedSeasons so that the same seasons cannot be added to the list if the user comes back
    this.selectedSeasonNames = [];
  }

  public getFootballTeamData(apiUrl: string): Promise<FootballTeam[]> {
    return this.http.get(apiUrl)
      .toPromise()
      .then(this.extractData);
  }

  public getSelectedSeasonsData(apiUrl: string): Promise<Season[]> {
    return this.http.get(apiUrl)
    .toPromise()
    .then(this.extractData);
  }

  private extractData(res) {
    let body = res.json();
    return body || [];
  }

  private setFootballTeamsExpectedGoals(homeTeam: FootballTeam, awayTeam: FootballTeam) {
      // This is probably producing very inconsistent values - NEED to find an alternative way of getting these values
      // Alternatively we could round to 3 decimal places?
      const seasonAverageHomeGoalsAGame = this.getSeasonsAverageGoalsAGame(this.selectedSeasons, true);
      const seasonAverageAwayGoalsAGame = this.getSeasonsAverageGoalsAGame(this.selectedSeasons, false);
  }

  private async getSeasonsAverageGoalsAGame(seasonsToUse, isHomeTeam: boolean) {
    let averageGoalsAGame = 0;
    
    if (isHomeTeam) {
      seasonsToUse.forEach(element => {
        averageGoalsAGame += element.averageHomeTeamGoalsAGame;
      });
    } else {
      seasonsToUse.forEach(element => {
        averageGoalsAGame += element.averageAwayTeamGoalsAGame;
      });
    }
    return await (averageGoalsAGame / seasonsToUse.length);
  }

  private getApiCallData(apiData: string) {
    return this.http.get(apiData)
    .map(results => results.json());
  }

  // Function to add / remove a season from the array of selected seasons
  // Called everytime a season checkbox is selected / deselected
  public addCheckboxSeason(season:string) {
    if ((<HTMLInputElement>document.getElementById(season)).checked) {
      this.selectedSeasonNames.push(season);
    } else {
      const index = this.selectedSeasonNames.indexOf(season);
      this.selectedSeasonNames.splice(index, 1);
    }
  }

  /** Produces an array of the percentage chance of a football team scoring that number of goals
   Uses the poisson distributuion formula to claculate the likelihood of a football team scoring a goal coming true
   <param name="expectedGoalsToScore"> The exepected amount of goals the football is meant to score </param>
   returns Returns an array with 6 entries, each entry represents the likelihood of a football team scoring a goal as a percentage */
  public calculatePoissonGoalPercentage(expectedGoalsToScore: number): number[] {
    const totalGoals =6;
    let resultsProbability: number[] = [];
    let goalExpectancy: number = 0.00; 
    let factorial: number = 0.00; 
    const poissonConstant: number = 2.718281828459;
    let e: number = 0.00;
    
    for (let index = 0; index < totalGoals; index++) {
      goalExpectancy = 0.00;
      factorial = 0;
      e = poissonConstant;

      goalExpectancy = Math.pow(expectedGoalsToScore, index);
      e = Math.pow(e, -expectedGoalsToScore);

      factorial = this.factorialRecursion(index);

      resultsProbability[index] = (goalExpectancy * e) / factorial;
    }

    return resultsProbability;
  }

  /**
  Once the percentage of both teams scoring x number of goals has been completed a table representing the chances of a 
  football match's final score can be produced.Multiplying the percentages from both the home and away teams poisson 
  distribution can provide a 2D array showing the likelihood of the possible results of a football match.
  <param name="homeTeamPoisson"> An array of percentages representing the likelihood of the home football team scoring a goal </param>
  <param name="awayTeamPoisson"> An array of percentages representing the likelihood of the away football team scoring a goal </param> */
  public calculatePoissonDistribution(homeTeamPoisson: number[], awayTeamPoisson: number[]): number[][] {
    let poissonDistribution: number[][] = [ [homeTeamPoisson.length] , [awayTeamPoisson.length], 
                                            [homeTeamPoisson.length] , [awayTeamPoisson.length],
                                            [homeTeamPoisson.length] , [awayTeamPoisson.length] ];
    let scorePercentage: number = 0.00;

    for (let i = 0; i < homeTeamPoisson.length; i++) {
      for (let j = 0; j < awayTeamPoisson.length; j++) {
        scorePercentage = 0.00;
        scorePercentage = homeTeamPoisson[i] * awayTeamPoisson[j];

        scorePercentage = scorePercentage * 100;
        poissonDistribution[i][j] = Math.ceil(scorePercentage * 100)/100;;
      }
    }
    return poissonDistribution;
  }

  private factorialRecursion(number: number): number {
    if (number == 1 || number == 0) {
      return 1;
    } else {
      return number * this.factorialRecursion(number - 1);
    }
  }

  // TODO: Get Odds for each score from an API (BetFair?) and then use that as part of the calculations to build a correct kelly criterion grid
  // CURRENTLY DOESN'T PRODUCE THE CORRECT RESULTS
  /** 
  Kelly Criterion Formula = (BP - Q) / B
  This formula is used to determine how much of your original stake you should bet
  bettingBalance: The users current betting account's balance
  oddsDecimal: The odds given by the betting company in decimal format 
  probabilityOfSuccess: The probability of the bet being true (0 - 1)
  probabilityOfFailure: The probability for the bet to fail (0 -1) 
  Returns a double value which represents the total amount you should stake on a bet based on the inputs */
  public calculateKellyCriterion(poissonDistributionTable: number[][], bettingBalance: number, ): number[][] {
      let kellyCriterion: number[][]  = [ [poissonDistributionTable.length] , [poissonDistributionTable.length], 
                                          [poissonDistributionTable.length] , [poissonDistributionTable.length],
                                          [poissonDistributionTable.length] , [poissonDistributionTable.length] ];
      let oddsDecimal = 8.00;

      let B = oddsDecimal - 1, P, Q;

      for (let i = 0; i < poissonDistributionTable.length; i++) {
        for (let j = 0; j < poissonDistributionTable[i].length; j++) {
          P = poissonDistributionTable[j][i];
          Q = (100 - P);

          let stakePercentage = ((B * P) - Q) / B;
          kellyCriterion[j][i] =  Math.ceil(stakePercentage * 100) / 100; 
        }
      }
      return kellyCriterion;
  }

  // Methods to calculate the expected number of goals a FootballTeam is meant to score
	private calculateExpectedHomeTeamGoals(homeTeamAttackStrength: number
		, awayTeamDefensiveStrength: number
		, averageHomeTeamGoalsASeason: number): number {
		return this.homeTeamExpectedGoals = homeTeamAttackStrength * awayTeamDefensiveStrength *averageHomeTeamGoalsASeason;
  }
  
  private calculateExpectedAwayTeamGoals(awayTeamAttackStrength: number
		, homeTeamDefensiveStrength: number
		, averageAwayTeamGoalsASeason: number): number {
		return this.awayTeamExpectedGoals = (awayTeamAttackStrength * homeTeamDefensiveStrength * averageAwayTeamGoalsASeason);
  }
}
