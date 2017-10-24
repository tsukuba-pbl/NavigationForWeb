import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { EventService } from '../event.service'

@Component({
  selector: 'app-event-registration',
  templateUrl: './event-registration.component.html',
  providers: [ EventService ],
  styleUrls: ['./event-registration.component.css']
})
export class EventRegistrationComponent implements OnInit {
  eventForm = new FormGroup({
    eventName: new FormControl('', Validators.required),
    eventDetail: new FormControl('', Validators.required),
    eventLocation: new FormControl('', Validators.required),
    eventStartDate: new FormControl('', Validators.required),
    eventEndDate: new FormControl('', [Validators.required])
  })
  errorMessage: string;
  resultText: string = "";

  constructor(private eventService: EventService) {

  }

  ngOnInit() {
    
  }

  onSubmit(formGroup: FormGroup) {
    if(!formGroup.valid) {
      return;
    }
    const requestParams = formGroup.value
    let requestBody: EventType = {
      id: "",
      name: requestParams.eventName,
      description: requestParams.eventDetail,
      location: requestParams.eventLocation,
      startDate: requestParams.eventStartDate,
      endDate: requestParams.eventEndDate,
      userId: "aiueo"
    }

    this.eventService.createEvent(requestBody)
    .subscribe(
      result => this.resultText = "イベントを登録できました",
      error => this.resultText = <any>error
    );
  }

}
