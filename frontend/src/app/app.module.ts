import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes }   from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpModule } from '@angular/http';

import { 
  MatIconModule, 
  MatButtonModule, 
  MatToolbarModule, 
  MatFormFieldModule, 
  MatInputModule, 
  MatDatepickerModule,
  MatNativeDateModule,
} from '@angular/material';

import { AppComponent } from './app.component'
import { EventRegistrationComponent } from './event-registration/event-registration.component'
import { IndexComponent } from './index/index.component'
import { LocationRegistrationComponent } from './location-registration/location-registration.component'

const routes: Routes = [
  { path: '', component: IndexComponent },
  { path: 'event/new',  component: EventRegistrationComponent },
];


@NgModule({
  declarations: [
    AppComponent,
    EventRegistrationComponent,
    IndexComponent,
    LocationRegistrationComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    RouterModule.forRoot(routes),
  ],
  exports: [
    RouterModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
