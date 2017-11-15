import { BrowserModule } from '@angular/platform-browser'
import { NgModule } from '@angular/core'
import { RouterModule, Routes }   from '@angular/router'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { HttpModule } from '@angular/http'

import { 
  MatIconModule, 
  MatButtonModule, 
  MatToolbarModule, 
  MatFormFieldModule, 
  MatInputModule, 
  MatDatepickerModule,
  MatNativeDateModule,
  MatTabsModule,
} from '@angular/material';

import { AppComponent } from './app.component'
import { IndexComponent } from './index/index.component'
import { EventRegistrationComponent } from './event-registration/event-registration.component'
import { EventDetailComponent } from './event-detail/event-detail.component';

const routes: Routes = [
  { path: '', component: IndexComponent },
  { path: 'event/new',  component: EventRegistrationComponent },
  { path: 'event/:id', component: EventDetailComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    EventRegistrationComponent,
    IndexComponent,
    EventDetailComponent
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
    MatTabsModule,
    RouterModule.forRoot(routes),
  ],
  exports: [
    RouterModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
