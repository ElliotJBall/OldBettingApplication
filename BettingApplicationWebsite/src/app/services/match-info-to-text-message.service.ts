import { Injectable } from '@angular/core';

@Injectable()
export class MatchInfoToTextMessageService {

  public matchesTextMessageFormat: Array<MatchTextMessageObject> = [];
  public txtMessageId = 0;
  // Constant variable for the number of results to return
  public RESULTS_TO_RETURN: number = 3;

  constructor() { }

  /**
   * Instansiate a new MessageTextFormat object and then add it to the array to be displayed
   */
  public addMatchInfo(matchInfoToAdd: MatchTextMessageObject) { 
    this.matchesTextMessageFormat.push(matchInfoToAdd);
  }

  /**
   * Given the Id of a MatchTextFormat object, if it is found within the array remove it
   */
  public removeMatchInfo(id: number) {
    this.matchesTextMessageFormat.forEach(element => {
      if (element.id == id) {
        this.matchesTextMessageFormat.splice(element.id, 1);
      }
    });
  }
}

// Inner class to instansiate an object holding the information about an upcoming Fixture 
// And the top three most likely results
export class MatchTextMessageObject {
  public id: number;
  private homeTeamName: string;
  private awayTeamName: string;
  private topResults: Array<PoissonDistributionRow>;

  constructor(id: number, homeTeamName: string, awayTeamName: string, topResults: Array<PoissonDistributionRow>) { 
    this.id = id;
    this.homeTeamName = homeTeamName;
    this.awayTeamName = awayTeamName;
    this.topResults = topResults;
  }
}

export class PoissonDistributionRow {
  public homeTeamScore: number;
  public awayTeamScore: number;
  public probability: number;

  constructor(homeTeamScore, awayTeamScore, probability) { 
    this.homeTeamScore = homeTeamScore;
    this.awayTeamScore = awayTeamScore;
    this.probability = probability;
  }
}