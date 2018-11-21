import { Component, OnInit, NgModule, Inject, Input, SimpleChanges } from '@angular/core';
import { Season } from '../../entity/Season';
import { FootballTeam } from '../../entity/FootballTeam';
import { FootballTeamService } from '../../services/footballTeam.service';
import { MatchService } from '../../services/matchService.service';
import { Router, ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-match',
  templateUrl: './match-search.component.html',
  styleUrls: ['./match-search.component.css']
})

export class MatchComponent implements OnInit {
  // A list of tall the current seasons that there are data for in the database
  private allSeasons: Array<string>;

  public allFootballTeams:String[] = new Array<String>();

  constructor(private _footballTeamService: FootballTeamService, private _matchService: MatchService,
              private router: Router, private route: ActivatedRoute) {
   }
  
  ngOnInit() {
    this.allSeasons  = ["2017/2018",  "2016/2017",  "2015/2016"
    , "2014/2015", "2013/2014",  "2012/2013"
    , "2011/2012", "2010/2011",  "2009/2010"
    , "2008/2009", "2007/2008",  "2006/2007"
    , "2005/2006", "2004/2005",  "2003/2004"
    , "2002/2003"];

    this.populateFootballTeamsDataList();
  }

  // This method needs to make a call to the database and return a list of all the FootballTeam names currently within the database
  // SELECT DISTINCT homeTeam FROM betting_data.football_match;
  private populateFootballTeamsDataList() {
    const getAllFootballTeamNames = `http://localhost:8080/footballTeam/getAllFootballTeamNames`;

    this._footballTeamService.getFootballTeamData(getAllFootballTeamNames)
    .then(allFootballTeams => {
      allFootballTeams.forEach(element => {
          this.allFootballTeams.push(element);
        });
    });
  }

  private validateUserInput(homeTeamEntered: string, awayTeamEntered: string) {
    var errorbox = <HTMLElement> (document.getElementById('userValidationErrorBox')); 

    // Capitialise the first letter of the string (The input tag looks like it capitalises it but it doesnt so do it here)
    homeTeamEntered = homeTeamEntered.charAt(0).toUpperCase() + homeTeamEntered.slice(1);
    awayTeamEntered = awayTeamEntered.charAt(0).toUpperCase() + awayTeamEntered.slice(1);

    if (this._matchService.selectedSeasonNames.length == 0) {
      errorbox.innerHTML += 'Please select atleast one season!';
      errorbox.style.display = "block";
      return;
    }

    if (!this.allFootballTeams.includes(homeTeamEntered) || !this.allFootballTeams.includes(awayTeamEntered)) {
      errorbox.innerHTML += 'Error! Please enter two valid Football team names and try again!';
      errorbox.style.display = "block";
      return;
    }

    this._matchService.getFootballStats(homeTeamEntered, awayTeamEntered);
    this.router.navigate([`displayMatch`], { relativeTo: this.route });
  }
}
