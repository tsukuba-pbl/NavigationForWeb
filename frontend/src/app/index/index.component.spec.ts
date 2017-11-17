import { async, ComponentFixture, TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'

import { IndexComponent } from './index.component'

import { HttpModule } from '@angular/http'
import { EventService } from '../event.service'
import { Observable } from 'rxjs/Observable'

describe('IndexComponent', () => {
  let component: IndexComponent;
  let fixture: ComponentFixture<IndexComponent>;

  const eventServiceMock = {
    getEventList: () => Observable.create([{"id":"JDrs2","name":"hoge","description":"hoge","location":"hgoe","startDate":1510218540000,"endDate":1510920060000,"userId":"aiueo","createdAt":1510928133000,"updatedAt":1510928133000}])
      .map(data => JSON.stringify(data))
      .catch(error => {
        let errMsg = (error.message) ? error.message : error.status ? `${error.status} - ${error.statusText}` : 'Server error'
        return Observable.throw(errMsg)
      })
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IndexComponent ],
      imports: [
        RouterTestingModule,
        HttpModule,
      ],
      providers: [
        {provide: EventService, useValue: eventServiceMock}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
