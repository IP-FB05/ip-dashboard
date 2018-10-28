import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProcessesComponent } from './processes/processes.component';

import { FormsModule } from '@angular/forms';
import { MenuComponent } from './menu/menu.component'; // <-- NgModel lives here

@NgModule({
  declarations: [
    AppComponent,
    ProcessesComponent,
    MenuComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
