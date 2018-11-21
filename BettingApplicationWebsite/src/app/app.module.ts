import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { AppComponent } from './app.component';
import { MatchComponent } from './components/match-search/match-search.component';
import { MatchService } from './services/matchService.service';
import { RouterModule, Routes } from '@angular/router';
import { AddNewMatchesComponent } from './components/add-new-matches/add-new-matches.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { MatchDisplayComponent } from './components/match-display/match-display.component';
import { FormsModule } from '@angular/forms';
import { FootballTeamService } from './services/footballTeam.service';
import { MatchInfoToTextMessageComponent } from './components/match-info-to-text-message/match-info-to-text-message.component';
import { MatchInfoToTextMessageService } from './services/match-info-to-text-message.service';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

const appRoutes: Routes = [
  {
    path: '',
    component: MatchComponent
  },
  {
    path: 'displayMatch',
    component: MatchDisplayComponent
  },
  {
    path: 'addNewMatches',
    component: AddNewMatchesComponent,
  },
  {
    path: 'matchesTextMessage',
    component: MatchInfoToTextMessageComponent,
  },
  {
    path: '**',
    component: PageNotFoundComponent,
  }
];

@NgModule({
  declarations: [
    AppComponent, MatchComponent, AddNewMatchesComponent, PageNotFoundComponent, MatchDisplayComponent, MatchInfoToTextMessageComponent
  ],
  imports: [
    BrowserModule, HttpModule, RouterModule.forRoot(appRoutes), FormsModule, NgbModule.forRoot()
  ],
  providers: [MatchService, FootballTeamService, MatchInfoToTextMessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
