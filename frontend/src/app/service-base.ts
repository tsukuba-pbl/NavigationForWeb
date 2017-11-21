import { Http, Response } from '@angular/http'
import { Observable } from 'rxjs/Observable'
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

export class ServiceBase {
  public extractData(res: Response) {
    let body = res.json()
    return body || {}
  }

  public handleError(error: any) {
    let errMsg = (error.message) ? error.message : error.status ? `${error.status} - ${error.statusText}` : 'Server error'
    return Observable.throw(errMsg)
  }
}
