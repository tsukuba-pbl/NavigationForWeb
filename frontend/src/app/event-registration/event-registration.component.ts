import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDatepicker, DateAdapter, NativeDateAdapter } from '@angular/material';

@Component({
  selector: 'app-event-registration',
  templateUrl: './event-registration.component.html',
  styleUrls: ['./event-registration.component.css']
})
export class EventRegistrationComponent implements OnInit {
  @ViewChild(MatDatepicker) datepicker: MatDatepicker<Date>;
  eventName: string = ""

  constructor(dateAdapter: DateAdapter<NativeDateAdapter>) {
    dateAdapter.setLocale('ja-JA')
  }

  ngOnInit() {
  }

  submit() {
    console.log(this.datepicker)
  }

  changeEventName(event) {
    console.log(this.eventName)
  }

}
