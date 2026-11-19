import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AuthInterceptor } from './core/interceptors/auth.interceptor';

import { LayoutComponent } from './layouts/layout.component';
import { SidebarComponent } from './layouts/sidebar/sidebar.component';

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

@NgModule({
  declarations: [
    AppComponent,
    LayoutComponent,
    SidebarComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    ProprietairesListComponent,
    ProprietaireFormComponent,
    ProprietaireDetailComponent,
    DossiersListComponent,
    DossierFormComponent,
    DossierDetailComponent,
    UsersListComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CommonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
