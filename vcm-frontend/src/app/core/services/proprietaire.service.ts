import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Page, Proprietaire } from '../models/models';

@Injectable({ providedIn: 'root' })
export class ProprietaireService {
  private base = `${environment.apiUrl}/proprietaires`;

  constructor(private http: HttpClient) {}

  getAll(page = 0, size = 10, search?: string): Observable<Page<Proprietaire>> {
    let params = new HttpParams().set('page', page).set('size', size);
    if (search) params = params.set('search', search);
    return this.http.get<Page<Proprietaire>>(this.base, { params });
  }

  getById(id: number): Observable<Proprietaire> {
    return this.http.get<Proprietaire>(`${this.base}/${id}`);
  }

  create(data: Proprietaire): Observable<Proprietaire> {
    return this.http.post<Proprietaire>(this.base, data);
  }

  update(id: number, data: Proprietaire): Observable<Proprietaire> {
    return this.http.put<Proprietaire>(`${this.base}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
