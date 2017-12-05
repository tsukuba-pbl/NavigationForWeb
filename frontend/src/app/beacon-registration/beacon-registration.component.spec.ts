import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { MatFormFieldModule, MatInputModule, MatDatepickerModule, MatNativeDateModule, MatTabsModule } from '@angular/material'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'

import { BeaconRegistrationComponent } from './beacon-registration.component';
import { HttpModule } from '@angular/http';
import { Observable } from 'rxjs/Rx'

import { BeaconService } from '../beacon.service'

describe('BeaconRegistrationComponent', () => {
  let component: BeaconRegistrationComponent;
  let fixture: ComponentFixture<BeaconRegistrationComponent>;

  const locationServiceMock = {
    createLocation: (eventId: string, location: BeaconType) => Observable.create(new Object()).map(data =>JSON.stringify(data)),
    getLocations: (eventId: string) => Observable.create(new Object()).map(data =>JSON.stringify(data))
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BeaconRegistrationComponent ],
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
        {provide: BeaconService, useValue: locationServiceMock},
      ]
    })
    .compileComponents();
    TestBed.overrideComponent(BeaconRegistrationComponent, {
      set: {
        providers: [
          {provide: BeaconService, useValue: locationServiceMock},
        ]
      }
    })
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeaconRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  //it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
