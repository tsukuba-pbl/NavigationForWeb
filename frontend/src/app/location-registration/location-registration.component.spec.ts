import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { MatFormFieldModule, MatInputModule, MatDatepickerModule, MatNativeDateModule, MatTabsModule } from '@angular/material'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'

import { LocationRegistrationComponent } from './location-registration.component';
import { HttpModule } from '@angular/http';
import { Observable } from 'rxjs/Rx'

import { LocationService } from '../location.service'

describe('LocationComponent', () => {
  let component: LocationRegistrationComponent;
  let fixture: ComponentFixture<LocationRegistrationComponent>;
  
  const locationServiceMock = {
    addLocation: () => Observable.create(new Object()).map(data =>JSON.stringify(data))
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
      ],
      providers: [
        {provide: LocationService, useValue: locationServiceMock}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LocationRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
