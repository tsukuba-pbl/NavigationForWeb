import { Component, OnInit } from '@angular/core'
import { EventService } from '../event.service'

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css'],
  providers: [ EventService ]
})
export class IndexComponent implements OnInit {

  eventList: EventType[] = []

  constructor(private eventService: EventService) { }

  ngOnInit() {
    // イベント情報の取得
    this.eventService.getEventList()
    .subscribe(result => {
      this.eventList = result
      console.log(this.eventList)
    })
  }

}
