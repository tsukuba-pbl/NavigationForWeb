import { TestBed, inject } from '@angular/core/testing';
import { HttpModule } from '@angular/http';
import { EventService } from './event.service';

describe('EventService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule],
      providers: [EventService]
    });
  });

  it('should be created', inject([EventService], (service: EventService) => {
    expect(service).toBeTruthy();
  }));
});
