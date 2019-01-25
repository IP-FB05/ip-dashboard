import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProcessesComponent } from './process/processes/processes.component';
import { MessagesComponent } from './messages/messages/messages.component';
import { DashboardComponent }   from './dashboard/dashboard.component';
import { ProcessDetailComponent }  from './process/process-detail/process-detail.component';
import { LoginComponent } from './login/login.component'; 
import { SystemsComponent } from './system/systems/systems.component';
import { MenuComponent } from './menu/menu.component';  
import { DocumentsComponent } from './document/documents/documents.component';
import { CategoryComponent } from './category/category/category.component';
import { ProfilComponent } from './profil/profil.component';
import { MyprocessesComponent } from './myprocesses/myprocesses.component';
//import { AuthGuard } from './login/auth/auth-guard';
import { Error401Component } from './helper/error/error401/error401.component';

/*const routes: Routes = [
 { path: '', redirectTo: '/dashboard', pathMatch: 'full', canActivate: [AuthGuard] },
 { path: 'dashboard', component: DashboardComponent },
 { path: 'detail/:id', component: ProcessDetailComponent },
 { path: 'login', component: LoginComponent },
 { path: 'processes', component: ProcessesComponent },
 { path: 'menu', component: MenuComponent },
 { path: 'systems', component: SystemsComponent },
 { path: 'documents', component: DocumentsComponent},
 { path: 'category', component: CategoryComponent},
 { path: 'profil', component: ProfilComponent},
 { path: 'myprocesses', component: MyprocessesComponent},

 // otherwise redirect to login
 { path: '**', redirectTo: '/login' }
];*/

const routes: Routes = [
  {
    path: '',
    //canActivateChild: [AuthGuard],
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent,
        data: {

        }
      },
      {
        path: 'messages',
        component: MessagesComponent,
        data: {

        }
      },
      {
        path: 'login',
        component: LoginComponent,
        data: {
        }
      },
      {
        path: 'processes',
        component: ProcessesComponent,
        data: {
        }
      },
      { 
        path: 'detail/:id', 
        component: ProcessDetailComponent,
        data: {
        }
      }, 
      {
        path: 'category',
        component: CategoryComponent,
        data: {
          allowedRoles: ['admin']
        }
      },
      {
        path: 'systems',
        component: SystemsComponent,
        data: {

        }
      },
      {
        path: 'profil',
        component: ProfilComponent,
        data: {

        }
      },
      {
        path: 'myprocesses',
        component: MyprocessesComponent,
        data: {

        }
      },
      {
        path: 'documents',
        component: DocumentsComponent,
        data: {

        }
      },
      {
        path: 'error401',
        component: Error401Component,
        data: {
          
        }
      },
      {
        path: '**',
        redirectTo: 'dashboard'
      }
    ]
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
