import { Component, OnInit, Input } from '@angular/core';
import { MatchService } from '../../services/matchService.service';
import { Form } from '../../entity/Form';
//import { Chart } from '../../../../node_modules/chart.js';
import { FootballTeam } from '../../entity/FootballTeam';
import { MatchInfoToTextMessageService, MatchTextMessageObject, PoissonDistributionRow } from '../../services/match-info-to-text-message.service';

@Component({
  selector: 'app-match-display',
  templateUrl: './match-display.component.html',
  styleUrls: ['./match-display.component.css']
})

export class MatchDisplayComponent implements OnInit {


  constructor(private _matchService: MatchService, private _matchInfoToTextMessageService: MatchInfoToTextMessageService) { }

  ngOnInit() { }

  private addToTextMessageList() {
    const matchToAdd = new MatchTextMessageObject(this._matchInfoToTextMessageService.txtMessageId++, this._matchService.homeTeam.teamName,
                                                  this._matchService.awayTeam.teamName, this.getMostLikelyPoissonDistributionResults());
    this._matchInfoToTextMessageService.addMatchInfo(matchToAdd);
  }

  private getMostLikelyPoissonDistributionResults() {
    // Array to hold the most likely results along with the home and away team scores
    var mostLikelyResults: PoissonDistributionRow [] = [];
    var homeTeamScore = 0;
    var awayTeamScore = 0;

    for (var i = 0; i < this._matchService.poissonDistribution.length; i++) {
      var arrays = this._matchService.poissonDistribution[i];

      for (var j = 0; j < arrays.length; j++) {
        mostLikelyResults.push(new PoissonDistributionRow(i, j, arrays[j]));
      }
    }

    // TODO: Abstract this out into its own personal method within the PossionDistributionRow inner class
    mostLikelyResults.sort(function(a,b) {return (a.probability > b.probability) ? -1 : ((b.probability > a.probability) ? 1 : 0);} );
  
    return mostLikelyResults.splice(0, this._matchInfoToTextMessageService.RESULTS_TO_RETURN);
  }

  private mostLikelyCompare(a: PoissonDistributionRow, b: PoissonDistributionRow) {
    if (a.probability < b.probability) {
      return -1;
    }
    if (a.probability > b.probability) {
      return 1;
    }
    return 0;
  }

  /* private createHeadToHeadChart(homeTeamDataToUse: number, awayTeamDataToUse: number, labelToUse: string) {
    var ctx = document.getElementById("headToHeadChart");
    var myChart = new Chart(ctx, {
    type: 'horizontalBar',
    data: {
        labels: [this._matchService.homeTeam.teamName, this._matchService.awayTeam.teamName],
        datasets: [{
            label: labelToUse,
            data: [homeTeamDataToUse, awayTeamDataToUse, 0],
            backgroundColor: [
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 99, 132, 0.2)'
            ],
            borderColor: [
              'rgba(54, 162, 235, 0.2)',
              'rgba(255, 99, 132, 0.2)'
            ],
            borderWidth: 1
        }]
    },
    options: { }
    });
  }

  private getHeadToHeadChartDataToUse(nameOfData: string) {
    switch (nameOfData) {
      case 'headToHeadFormAverageGoalsScored':
        this.createHeadToHeadChart(this._matchService.homeTeam.headToHeadFormAverageGoalsScored, this._matchService.awayTeam.headToHeadFormAverageGoalsScored
          , 'Head to head average goals scored');
        break;
      case 'headToHeadShotsOnTarget':
      this.createHeadToHeadChart(this._matchService.homeTeam.headToHeadFormAverageShotsOnTarget, this._matchService.awayTeam.headToHeadFormAverageShotsOnTarget
        , 'Head to head average shots on target');
          break;
      case 'headToHeadTotalShots':
      this.createHeadToHeadChart(this._matchService.homeTeam.headToHeadFormAverageShots, this._matchService.awayTeam.headToHeadFormAverageShots
        , 'Head to head average number of shots');
          break;
      case 'headToHeadAverageYellowCards':
      this.createHeadToHeadChart(this._matchService.homeTeam.headToHeadFormAverageYellowCards, this._matchService.awayTeam.headToHeadFormAverageYellowCards
        , 'Head to head average yellow cards');
          break;
      case 'headToHeadAverageRedCards':
      this.createHeadToHeadChart(this._matchService.homeTeam.headToHeadFormAverageRedCards, this._matchService.awayTeam.headToHeadFormAverageRedCards
        , 'Head to head average red cards');
          break;
      case 'headToHeadAverageCorners':
      this.createHeadToHeadChart(this._matchService.homeTeam.headToHeadFormAverageCorners, this._matchService.awayTeam.headToHeadFormAverageCorners
        , 'Head to head average corners');
          break;
      default: 
        break;
    }
  }

  private enableFormulaNav(divToEnable: string) {
    if (divToEnable == 'poissonDistributionFormula') {
      document.getElementById('poissonDistributionFormula').style.display = "block";
      document.getElementById('kellyCriterionFormula').style.display = "none";
    } else {
      document.getElementById('kellyCriterionFormula').style.display = "block";
      document.getElementById('poissonDistributionFormula').style.display = "none";
    }
  } */
}
