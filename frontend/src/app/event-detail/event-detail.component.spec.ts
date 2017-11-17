import { async, ComponentFixture, TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'

import { EventDetailComponent } from './event-detail.component'

import { HttpModule } from '@angular/http'
import { EventService } from '../event.service'
import { Observable } from 'rxjs/Observable'

describe('EventDetailComponent', () => {
  let component: EventDetailComponent;
  let fixture: ComponentFixture<EventDetailComponent>;

  const eventServiceMock = {
    getEvent: (id: String) => Observable.create(observer => {
      observer.next({"id":"JDrs2","name":"hoge","description":"hoge","location":"hgoe","startDate":1510218540000,"endDate":1510920060000,"userId":"aiueo","createdAt":1510928133000,"updatedAt":1510928133000})
      observer.complete()
    }).map(data =>JSON.stringify(data)),
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventDetailComponent ],
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
    fixture = TestBed.createComponent(EventDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
