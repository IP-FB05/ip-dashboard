import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProcessesComponent } from './processes/processes.component';
import { DashboardComponent }   from './dashboard/dashboard.component';
import { ProcessDetailComponent }  from './process-detail/process-detail.component';
import { LoginComponent } from './login/login.component'; 
import { SystemsComponent } from './systems/systems.component';
import { MenuComponent } from './menu/menu.component';  
import { DokumenteComponent } from './dokumente/dokumente.component';

const routes: Routes = [
 { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
 { path: 'dashboard', component: DashboardComponent },
 { path: 'detail/:id', component: ProcessDetailComponent },
 { path: 'login', component: LoginComponent },
 { path: 'processes', component: ProcessesComponent },
 { path: 'menu', component: MenuComponent },
 { path: 'systems', component: SystemsComponent },
 { path: 'dokumente', component: DokumenteComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
