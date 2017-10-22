import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDatepicker, DateAdapter, NativeDateAdapter } from '@angular/material';

@Component({
  selector: 'app-event-registration',
  templateUrl: './event-registration.component.html',
  styleUrls: ['./event-registration.component.css']
})
export class EventRegistrationComponent implements OnInit {
  @ViewChild(MatDatepicker) datepicker: MatDatepicker<Date>;
  eventName: string = ""
  eventNameFormControl: FormControl = new FormControl('', Validators.required)
  eventDetailFormControl: FormControl = new FormControl('', Validators.required)
  eventLocationFormControl: FormControl = new FormControl('', Validators.required)
  eventStartDateFormControl: FormControl = new FormControl('', Validators.required)
  eventEndDateFormControl: FormControl = new FormControl('', [Validators.required])

  constructor(dateAdapter: DateAdapter<NativeDateAdapter>) {
    dateAdapter.setLocale('ja-JA')
  }

  ngOnInit() {
    
  }

  submit() {
    console.log(this.datepicker)
  }

}
