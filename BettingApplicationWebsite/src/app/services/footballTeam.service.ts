import { Injectable, Inject } from '@angular/core';
import { Http } from '@angular/http';
import { Subject } from 'rxjs/Subject';
import { Season } from '../entity/Season';
import { Match } from '../entity/Match';
import { FootballTeam } from '../entity/FootballTeam';
import { Observable } from "rxjs/Rx"

@Injectable()
export class FootballTeamService {

  constructor(private http: Http) { }

  public getFootballTeamData(apiUrl: string): Promise<String[]> {
    return this.http.get(apiUrl)
      .toPromise()
      .then(this.extractData);
  }

  private extractData(res) {
    let body = res.json();
    return body || [];
  }
}
