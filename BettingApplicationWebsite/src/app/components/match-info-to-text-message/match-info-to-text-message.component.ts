import { Component, OnInit } from '@angular/core';
import { MatchInfoToTextMessageService } from '../../services/match-info-to-text-message.service';

@Component({
  selector: 'app-match-info-to-text-message',
  templateUrl: './match-info-to-text-message.component.html',
  styleUrls: ['./match-info-to-text-message.component.css']
})
export class MatchInfoToTextMessageComponent implements OnInit {

  constructor(private _matchInfoToTextMessageService: MatchInfoToTextMessageService) { }

  ngOnInit() { }

}
