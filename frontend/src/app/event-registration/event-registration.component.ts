import { Component, OnInit, ViewChild } from '@angular/core'
import { FormGroup, FormControl, Validators } from '@angular/forms'
import { Router } from '@angular/router'
import { EventService } from '../event.service'

@Component({
  selector: 'app-event-registration',
  templateUrl: './event-registration.component.html',
  providers: [ EventService ],
  styleUrls: ['./event-registration.component.css']
})
export class EventRegistrationComponent implements OnInit {
  eventForm = new FormGroup({
    eventName: new FormControl('', [Validators.required, Validators.pattern(".*\\S.*")]),
    eventDescription: new FormControl('', [Validators.required, Validators.pattern("[^ \\t]+[ \\t]*[^ \\t]+|.*[^ \\t]+|[^ \\t]+[ \\t]*")]),
    eventLocation: new FormControl('', [Validators.required, Validators.pattern(".*\\S.*")]),
    eventStartDate: new FormControl('', Validators.required),
    eventEndDate: new FormControl('', Validators.required),
  })
  @ViewChild('f') form
  errorMessage: string = ""
  resultText: string = ""

  constructor(private eventService: EventService, private router: Router) {

  }

  ngOnInit() {
  }

  onSubmit(formGroup: FormGroup) {
    if(!formGroup.valid) {
      return
    }
    const requestParams = formGroup.value

    let datetimeValid = this.compareDateTime(new Date(requestParams.eventStartDate), new Date(requestParams.eventEndDate))
    if (datetimeValid == 0) {
      this.errorMessage = "開始日時と終了日時が同じです．イベントの期間を正確に入力してください．"
      return
    } else if (datetimeValid == -1) {
      this.errorMessage = "終了日時が開始日時より過去です．正しい日時を入力してください．"
      return
    }

    let requestBody: EventType = {
      id: "",
      name: requestParams.eventName.trim(),
      description: requestParams.eventDescription.trim(),
      location: requestParams.eventLocation.trim(),
      startDate: requestParams.eventStartDate,
      endDate: requestParams.eventEndDate,
      userId: "aiueo",
      createdAt: null,
      updatedAt: null,
    }

    this.eventService.createEvent(requestBody)
    .subscribe(result => {
      switch(result.status) {
        case 200: 
          this.resultText = "イベントを登録できました"
          this.form.resetForm()
          setTimeout(() => {this.router.navigate(['/events/'+result.data.eventId])}, 1000)
          console.log(result.data)
          console.log(result.data.eventId)
          break;
        case 300: 
          this.resultText = "既に同じ目的地が登録されています"
          break;
        default: 
          this.resultText = "問題が発生したようです😥．．．"
          break;
      }
    },
      error => this.errorMessage = <any>error
    );
  }

  /**
   * 日付の比較
   * 
   * @param {Date} startDate 
   * @param {Date} endDate 
   * @returns startDateよりendDateが未来であれば 1, 等しければ 0, 時系列がおかしければ -1
   * @memberof EventRegistrationComponent
   */
  compareDateTime(startDate: Date, endDate: Date) {
    if ((startDate.getTime() / 1000) < (endDate.getTime() / 1000)) {
      return 1
    } else if ((startDate.getTime() / 1000) > (endDate.getTime() / 1000)) {
      return -1
    } else {
      return 0
    }
  }
}
