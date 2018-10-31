import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProcessesComponent } from './processes/processes.component';

import { FormsModule } from '@angular/forms';
import { MenuComponent } from './menu/menu.component';
import { LoginComponent } from './login/login.component';
import { ProcessDetailComponent } from './process-detail/process-detail.component';
import { MessagesComponent } from './messages/messages.component';
import { SystemsComponent } from './systems/systems.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; // <-- NgModel lives here

import {DemoMaterialModule} from './material-module';


@NgModule({
  declarations: [
    AppComponent,
    ProcessesComponent,
    MenuComponent,
    LoginComponent,
    ProcessDetailComponent,
    MessagesComponent,
    SystemsComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    DemoMaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
