import { Component, OnInit, ViewChild } from '@angular/core'
import { ActivatedRoute } from '@angular/router'
import { FormGroup, FormControl, Validators } from '@angular/forms'
import { BeaconService } from '../beacon.service'

@Component({
  selector: 'app-beacon',
  templateUrl: './beacon-registration.component.html',
  styleUrls: ['./beacon-registration.component.css'],
  providers: [BeaconService]
})
export class BeaconRegistrationComponent implements OnInit {
  beaconForm = new FormGroup({
    beaconId: new FormControl('', [Validators.required]),
  })
  @ViewChild('f') form
  eventId: string = ""
  resultText: string = ""
  beacons: BeaconType[] = []
  minorIdList: number[] = []
  
  constructor(
    private activatedRoute: ActivatedRoute,
    private beaconService: BeaconService
  ) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.eventId = params["id"]
      this.beaconService.getBeacons(this.eventId)
      .subscribe(result => {
        this.minorIdList = result.minorIdList
      })
    })
  }

  onSubmit(formGroup: FormGroup) {
    if(!formGroup.valid || !this.eventId) {
      return
    }
    const requestParam = formGroup.value

    let requestBody: BeaconType = {
      eventId: this.eventId,
      minorId: requestParam.beaconId,
    }

    this.beaconService.createBeacon(this.eventId, requestBody)
    .subscribe(result => {
      switch(result.status) {
        case 200:
          this.resultText = "ビーコンが登録できました"
          this.form.resetForm()
          this.beaconService.getBeacons(this.eventId)
          .subscribe(result => {
            this.minorIdList = result.minorIdList
          })
          break;
        case 300:
          this.resultText = "既に同じビーコンが登録されています"
          break;
        default:
          this.resultText = "問題が発生したようです😥．．．"
          break;
      }
    },
      error => this.resultText = <any>error
    )
  }
}