import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes }   from '@angular/router';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { EventRegistrationComponent } from './event-registration/event-registration.component';
import { IndexComponent } from './index/index.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { 
  MatIconModule, 
  MatButtonModule, 
  MatToolbarModule, 
  MatFormFieldModule, 
  MatInputModule, 
  MatDatepickerModule,
  MatNativeDateModule } from '@angular/material';

const routes: Routes = [
  { path: '', component: IndexComponent },
  { path: 'event/new',  component: EventRegistrationComponent },
];


@NgModule({
  declarations: [
    AppComponent,
    EventRegistrationComponent,
    IndexComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
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
