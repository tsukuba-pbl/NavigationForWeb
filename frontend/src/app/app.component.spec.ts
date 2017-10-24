import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { EventRegistrationComponent } from './event-registration/event-registration.component'
import { IndexComponent } from './index/index.component'
import { MatIconModule, MatButtonModule, MatToolbarModule } from '@angular/material'


class MockRouter { public navigate() {}; }

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        // EventRegistrationComponent,
        // IndexComponent
      ],
      imports: [
        MatIconModule,
        MatButtonModule,
        MatToolbarModule,
        RouterTestingModule,
      ]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
  it(`should have as title 'app'`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('app');
  }));
  it('should render title in a h1 tag', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Navient');
  }));
});
