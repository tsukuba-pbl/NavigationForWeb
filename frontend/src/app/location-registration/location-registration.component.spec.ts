import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { MatFormFieldModule, MatInputModule, MatDatepickerModule, MatNativeDateModule, MatTabsModule } from '@angular/material'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'

import { LocationRegistrationComponent } from './location-registration.component';
import { HttpModule } from '@angular/http';
import { Observable } from 'rxjs/Rx'

import { LocationService } from '../location.service'

describe('LocationRegistrationComponent', () => {
  let component: LocationRegistrationComponent;
  let fixture: ComponentFixture<LocationRegistrationComponent>;
  
  const locationServiceMock = {
    createLocation: (eventId: string, location: LocationType) => Observable.create(new Object()).map(data =>JSON.stringify(data)),
    getLocations: (eventId: string) => Observable.create(new Object()).map(data =>JSON.stringify(data))
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LocationRegistrationComponent ],
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
        {provide: LocationService, useValue: locationServiceMock},
      ]
    })
    .compileComponents();
    TestBed.overrideComponent(LocationRegistrationComponent, {
      set: {
        providers: [
          {provide: LocationService, useValue: locationServiceMock},
        ]
      }
    })
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LocationRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
