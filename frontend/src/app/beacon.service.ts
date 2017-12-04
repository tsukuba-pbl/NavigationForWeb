import { Injectable } from '@angular/core';
import { ServiceBase } from './service-base'
import { Http, Response } from '@angular/http'
import { Observable } from 'rxjs/Rx'
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class BeaconService extends ServiceBase {

  constructor(private http: Http) {
    super()
  }

  createBeacon(eventId: string, beacon: BeaconType): Observable<ResponseData> {
    return this.http.post('/api/events/' + eventId + '/beacons/new', beacon)
    .map(this.extractData)
    .catch(this.handleError);
  }

  getBeacons(eventId: string): Observable<any> {
    return this.http.get('/api/events/' + eventId + '/beacons').map(this.extractData).catch(this.handleError);
  }
}
