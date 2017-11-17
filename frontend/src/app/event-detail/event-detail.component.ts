import { Component, OnInit } from '@angular/core'
import { ActivatedRoute } from '@angular/router'

import { EventService } from '../event.service'

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.css'],
  providers: [EventService]
})
export class EventDetailComponent implements OnInit {
  eventId: string = ""
  event: EventType = null
  constructor(
    private activatedRoute: ActivatedRoute,
    private eventService: EventService
  ) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.eventId = params["id"]
      this.eventService.getEvent(this.eventId).subscribe(event => {
        this.event = event
      })
    })
  }

}
