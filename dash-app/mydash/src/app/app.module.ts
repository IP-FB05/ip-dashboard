import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { MatDialogModule } from '@angular/material';
import { MatCardModule} from '@angular/material';
import { MatButtonModule} from '@angular/material';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProcessesComponent } from './processes/processes.component';

import { MenuComponent } from './menu/menu.component';
import { LoginComponent } from './login/login.component';
import { ProcessDetailComponent } from './process-detail/process-detail.component';
import { MessagesComponent } from './messages/messages.component';
import { SystemsComponent } from './systems/systems.component';
import { DocumentsComponent } from './documents/documents.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; // <-- NgModel lives here
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DemoMaterialModule } from './material-module';

// import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
// import { InMemoryDataService } from './in-memory-data.service';
import { FilterComponent } from './filter/filter.component';
import { DocumentsDialogComponent } from './documents-dialog/documents-dialog.component';
import { SystemsDialogComponent } from './systems-dialog/systems-dialog.component';
import { ProcessesDialogComponent } from './processes-dialog/processes-dialog.component';
import { filterProcessesPipe } from './filter-processes.pipe';
import { filterSystemsPipe } from './filter-systems.pipe';
import { filterDocumentsPipe } from './filter-documents.pipe';
import { FormUploadComponent } from './upload/form-upload/form-upload.component';
import { ListUploadComponent } from './upload/list-upload/list-upload.component';
import { DetailsUploadComponent } from './upload/details-upload/details-upload.component';



@NgModule({
  declarations: [
    AppComponent,
    ProcessesComponent,
    MenuComponent,
    LoginComponent,
    ProcessDetailComponent,
    MessagesComponent,
    SystemsComponent,
    DashboardComponent,
    DocumentsComponent,
    FilterComponent,
    DocumentsDialogComponent,
    SystemsDialogComponent,
    ProcessesDialogComponent,
    filterProcessesPipe,
    filterSystemsPipe,
    filterDocumentsPipe,
    FormUploadComponent,
    ListUploadComponent,
    DetailsUploadComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    DemoMaterialModule,
    HttpClientModule,
    MatDialogModule,
    MatCardModule,
    MatButtonModule,

    // The HttpClientInMemoryWebApiModule module intercepts HTTP requests
    // and returns simulated server responses.
    // Remove it when a real server is ready to receive requests.
    // HttpClientInMemoryWebApiModule.forRoot(
    //   InMemoryDataService, { dataEncapsulation: false }
    // )

    
  ],
  entryComponents: [DocumentsDialogComponent, ProcessesDialogComponent, SystemsDialogComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
