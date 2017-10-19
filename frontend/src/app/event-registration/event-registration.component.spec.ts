import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventRegistrationComponent } from './event-registration.component';

import { MatFormFieldModule, MatInputModule, MatDatepickerModule, MatNativeDateModule } from '@angular/material';

describe('EventRegistrationComponent', () => {
  let component: EventRegistrationComponent;
  let fixture: ComponentFixture<EventRegistrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventRegistrationComponent ],
      imports: [
        MatFormFieldModule,
        MatInputModule,
        MatDatepickerModule,
        MatNativeDateModule,
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
