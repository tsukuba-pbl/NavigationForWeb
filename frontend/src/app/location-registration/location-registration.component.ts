import { Component, OnInit, ViewChild } from '@angular/core'
import { ActivatedRoute } from '@angular/router'
import { FormGroup, FormControl, Validators } from '@angular/forms'
import { LocationService } from '../location.service'

@Component({
  selector: 'app-location',
  templateUrl: './location-registration.component.html',
  styleUrls: ['./location-registration.component.css'],
  providers: [LocationService]
})
export class LocationRegistrationComponent implements OnInit {
  locationForm = new FormGroup({
    locationName: new FormControl('', [Validators.required, Validators.pattern(".*\\S.*")]),
    locationDetail: new FormControl('', [Validators.required, Validators.pattern(".*\\S.*")]),
  })
  @ViewChild('f') form
  eventId: string = ""
  resultText: string = ""

  constructor(
    private activatedRoute: ActivatedRoute,
    private locationService: LocationService
  ) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.eventId = params["id"]
    })
  }



  onSubmit(formGroup: FormGroup) {
    if(!formGroup.valid || !this.eventId) {
      return
    }
    const requestParams = formGroup.value

    let requestBody: LocationType = {
      id: null,
      name: requestParams.locationName.trim(),
      detail: requestParams.locationDetail.trim(),
      eventId: this.eventId,
    }

    this.locationService.createLocation(this.eventId, requestBody)
    .subscribe(result => {
      switch(result) {
        case "200": 
          this.resultText = "目的地が登録できました"
          break;
        
        case "404": 
          this.resultText = "サーバ側に問題が起きたようです😥"
          break;
        
        default: 
          this.resultText = "問題が発生したようです．．．"
          break;
      }
    },
      error => this.resultText = <any>error
    )
  }
}
