import { Injectable } from '@angular/core';
import { ServiceBase } from './service-base'
import { Http, Response } from '@angular/http'
import { Observable } from 'rxjs/Rx'
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class LocationService extends ServiceBase {

  constructor(private http: Http) {
    super()
  }

  createLocation(eventId: string, location: LocationType): Observable<ResponseData> {
    return this.http.post('/api/events/' + eventId + '/locations/new', location)
    .map(this.extractData)
    .catch(this.handleError);
  }

  getLocations(eventId: string): Observable<any> {
    return this.http.get('/api/events/' + eventId + '/locations').map(this.extractData).catch(this.handleError)
  }
}
