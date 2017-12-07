import { TestBed, inject } from '@angular/core/testing';
import { HttpModule } from '@angular/http';
import { BeaconService } from './beacon.service';

describe('BeaconService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule],
      providers: [BeaconService]
    });
  });

  it('should be created', inject([BeaconService], (service: BeaconService) => {
    expect(service).toBeTruthy();
  }));
});
