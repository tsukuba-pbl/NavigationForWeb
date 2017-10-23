import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-event-registration',
  templateUrl: './event-registration.component.html',
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

  constructor() {
  }

  ngOnInit() {
    
  }

  onSubmit(formGroup: FormGroup) {
    if(!formGroup.valid) {
      return;
    }
    const formValue: Object = formGroup.value
    // serviceを呼んで，登録
  }

}
