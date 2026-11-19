import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { RoleGuard } from './core/guards/role.guard';
import { LayoutComponent } from './layouts/layout.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ProprietairesListComponent } from './pages/proprietaires/proprietaires-list.component';
import { ProprietaireFormComponent } from './pages/proprietaires/proprietaire-form.component';
import { ProprietaireDetailComponent } from './pages/proprietaires/proprietaire-detail.component';
import { DossiersListComponent } from './pages/dossiers/dossiers-list.component';
import { DossierFormComponent } from './pages/dossiers/dossier-form.component';
import { DossierDetailComponent } from './pages/dossiers/dossier-detail.component';
import { UsersListComponent } from './pages/users/users-list.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'proprietaires', component: ProprietairesListComponent },
      { path: 'proprietaires/nouveau', component: ProprietaireFormComponent },
      { path: 'proprietaires/:id', component: ProprietaireDetailComponent },
      { path: 'proprietaires/:id/edit', component: ProprietaireFormComponent },
      { path: 'dossiers', component: DossiersListComponent },
      { path: 'dossiers/nouveau', component: DossierFormComponent },
      { path: 'dossiers/:id', component: DossierDetailComponent },
      { path: 'dossiers/:id/edit', component: DossierFormComponent },
      { path: 'users', component: UsersListComponent, canActivate: [RoleGuard] }
    ]
  },
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
