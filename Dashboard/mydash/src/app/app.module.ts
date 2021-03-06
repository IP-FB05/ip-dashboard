import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatDialogModule } from '@angular/material';
import { MatCardModule } from '@angular/material';
import { MatButtonModule } from '@angular/material';
import { MatSnackBarModule } from '@angular/material'


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProcessesComponent } from './process/processes/processes.component';

import { MenuComponent } from './menu/menu.component';
import { LoginComponent } from './login/login.component';
import { ProcessDetailComponent } from './process/process-detail/process-detail.component';
import { MessagesComponent } from './messages/messages/messages.component';
import { SystemsComponent } from './system/systems/systems.component';
import { DocumentsComponent } from './document/documents/documents.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; // <-- NgModel lives here
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DiagramModule } from './process/process-detail/diagram/diagram.module'

import { DemoMaterialModule } from './material-module';

// import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
// import { InMemoryDataService } from './in-memory-data.service';
import { FilterComponent } from './filter/filter.component';
import { DocumentsDialogComponent } from './document/documents-dialog/documents-dialog.component';
import { SystemsDialogComponent } from './system/systems-dialog/systems-dialog.component';
import { ProcessesDialogComponent } from './process/processes-dialog/processes-dialog.component';
import { filterProcessesPipe } from './filter/filter-processes.pipe';
import { filterSystemsPipe } from './filter/filter-systems.pipe';
import { filterDocumentsPipe } from './filter/filter-documents.pipe';
import { CategoryComponent } from './category/category/category.component';
import { CategoryAddComponent } from './category/category-add/category-add.component';
import { ProfilComponent } from './profil/profil.component';
import { ListUploadComponent } from './upload/list-upload/list-upload.component';
import { DetailsUploadComponent } from './upload/details-upload/details-upload.component';
import { FormUploadComponent } from './upload/form-upload/form-upload.component';
import { SubsComponent } from './subs/subs/subs.component';
import { MyprocessesComponent } from './myprocesses/myprocesses.component';
import { ProcessStartComponent } from './process/process-start/process-start.component';

// Cookie
import { CookieService } from 'ngx-cookie-service';

//Interceptors
import { BasicAuthInterceptor } from './login/auth/basic-auth-interceptor';
import { ErrorInterceptor } from './login/auth/error-interceptor';

// Angular-JWT
import { AuthorizationService } from './login/auth/authorization.service';
import { Error401Component } from './helper/error/error401/error401.component';
import { UsergroupComponent } from './usergroup/usergroup/usergroup.component';
import { DoTaskComponent } from './myprocesses/do-task/do-task.component';
import { DocumentsDeleteDialogComponent } from './document/documents-delete-dialog/documents-delete-dialog.component';
import { ProcessesDeleteDialogComponent } from './process/processes-delete-dialog/processes-delete-dialog.component';
import { SystemsDeleteDialogComponent } from './system/systems-delete-dialog/systems-delete-dialog.component';
import { FaqComponent } from './helper/faq/faq/faq.component';

// PdfViewer
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';




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
    CategoryComponent,
    CategoryAddComponent,
    ProfilComponent,
    ListUploadComponent,
    DetailsUploadComponent,
    FormUploadComponent,
    SubsComponent,
    MyprocessesComponent,
    Error401Component,
    ProcessStartComponent,
    UsergroupComponent,
    DoTaskComponent,
    DocumentsDeleteDialogComponent,
    ProcessesDeleteDialogComponent,
    SystemsDeleteDialogComponent,
    FaqComponent
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
    MatSnackBarModule,
    DiagramModule,
    PdfViewerModule,

    // The HttpClientInMemoryWebApiModule module intercepts HTTP requests
    // and returns simulated server responses.
    // Remove it when a real server is ready to receive requests.
    // HttpClientInMemoryWebApiModule.forRoot(
    //   InMemoryDataService, { dataEncapsulation: false }
    // )

    
  ],
  entryComponents: [DocumentsDialogComponent, DocumentsDeleteDialogComponent, ProcessesDialogComponent, ProcessesDeleteDialogComponent, SystemsDialogComponent, SystemsDeleteDialogComponent, ProcessStartComponent, DoTaskComponent],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    FilterComponent,
    ProcessesComponent,
    SystemsComponent,
    DocumentsComponent,
    CookieService,
    AuthorizationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
