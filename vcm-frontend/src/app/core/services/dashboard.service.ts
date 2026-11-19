import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, forkJoin, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { DashboardStats, Page, Proprietaire, DossierSoins } from '../models/models';
import { ProprietaireService } from './proprietaire.service';
import { DossierService } from './dossier.service';

@Injectable({ providedIn: 'root' })
export class DashboardService {
  constructor(
    private http: HttpClient,
    private proprietaireService: ProprietaireService,
    private dossierService: DossierService
  ) {}

  getStats(): Observable<DashboardStats> {
    return forkJoin({
      proprietaires: this.proprietaireService.getAll(0, 1),
      dossiers: this.dossierService.getAll(0, 1000)
    }).pipe(
      map(({ proprietaires, dossiers }) => {
        const budgetTotal = dossiers.content.reduce((sum, d) => sum + (d.budget || 0), 0);
        return {
          totalProprietaires: proprietaires.totalElements,
          totalDossiers: dossiers.totalElements,
          budgetTotal
        };
      })
    );
  }
}
