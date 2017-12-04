import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BeaconRegistrationComponent } from './beacon-registration.component';

describe('BeaconRegistrationComponent', () => {
  let component: BeaconRegistrationComponent;
  let fixture: ComponentFixture<BeaconRegistrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BeaconRegistrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeaconRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
