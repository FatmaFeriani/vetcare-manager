import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { DossierSoins, Page } from '../models/models';

@Injectable({ providedIn: 'root' })
export class DossierService {
  private base = `${environment.apiUrl}/dossiers`;

  constructor(private http: HttpClient) {}

  getAll(page = 0, size = 10, search?: string): Observable<Page<DossierSoins>> {
    let params = new HttpParams().set('page', page).set('size', size);
    if (search) params = params.set('search', search);
    return this.http.get<Page<DossierSoins>>(this.base, { params });
  }

  getById(id: number): Observable<DossierSoins> {
    return this.http.get<DossierSoins>(`${this.base}/${id}`);
  }

  getByProprietaire(proprietaireId: number): Observable<DossierSoins[]> {
    return this.http.get<DossierSoins[]>(`${environment.apiUrl}/proprietaires/${proprietaireId}/dossiers`);
  }

  create(data: any): Observable<DossierSoins> {
    return this.http.post<DossierSoins>(this.base, data);
  }

  update(id: number, data: any): Observable<DossierSoins> {
    return this.http.put<DossierSoins>(`${this.base}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
