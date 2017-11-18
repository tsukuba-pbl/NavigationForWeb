import { async, ComponentFixture, TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { EventRegistrationComponent } from './event-registration.component'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'

import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { MatFormFieldModule, MatInputModule, MatDatepickerModule, MatNativeDateModule, MatTabsModule } from '@angular/material'

import { HttpModule } from '@angular/http';
import { Observable } from 'rxjs/Rx'
import { EventService } from '../event.service'

describe('EventRegistrationComponent', () => {
  let component: EventRegistrationComponent;
  let fixture: ComponentFixture<EventRegistrationComponent>;

  const eventServiceMock = {
    addEvent: () => Observable.create(new Object()).map(data =>JSON.stringify(data))
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventRegistrationComponent ],
      imports: [
        BrowserAnimationsModule,
        FormsModule,
        HttpModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatTabsModule,
        RouterTestingModule,
      ],
      providers: [
        {provide: EventService, useValue: eventServiceMock}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
