import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http'
import { Observable } from 'rxjs/Observable'
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class EventService {

  constructor(private http: Http) { }

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
  
  private extractData(res: Response) {
    let body = res.json()
    return body || {}
  }

  private handleError(error: any) {
    let errMsg = (error.message) ? error.message : error.status ? `${error.status} - ${error.statusText}` : 'Server error'
    return Observable.throw(errMsg)
  }
}
