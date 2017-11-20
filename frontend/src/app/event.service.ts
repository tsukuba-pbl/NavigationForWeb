import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http'
import { Observable } from 'rxjs/Observable'
import { ServiceBase } from './service-base'
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class EventService extends ServiceBase {

  constructor(private http: Http) {
    super()
  }

  createEvent(event: EventType): Observable<any> {
    return this.http.post('/api/events/new', event)
    .map(this.extractData)
    .catch(this.handleError);
  }

  getEventList(): Observable<EventType[]> {
    return this.http.get('/api/events').map(this.extractData).catch(this.handleError)
  }

  getEvent(eventId: string): Observable<EventType> {
    return this.http.get('/api/events/'+eventId).map(this.extractData).catch(this.handleError)
  }
}
