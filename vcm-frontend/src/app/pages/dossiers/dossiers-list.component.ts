import { Component, OnInit } from '@angular/core';
import { DossierService } from '../../core/services/dossier.service';
import { AuthService } from '../../core/services/auth.service';
import { DossierSoins, Page } from '../../core/models/models';

@Component({
  selector: 'app-dossiers-list',
  templateUrl: './dossiers-list.component.html'
})
export class DossiersListComponent implements OnInit {
  page?: Page<DossierSoins>;
  loading = true;
  search = '';
  currentPage = 0;
  pageSize = 10;

  constructor(
    private service: DossierService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading = true;
    this.service.getAll(this.currentPage, this.pageSize, this.search || undefined).subscribe({
      next: (p) => { this.page = p; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  onSearch(): void {
    this.currentPage = 0;
    this.load();
  }

  goToPage(p: number): void {
    this.currentPage = p;
    this.load();
  }

  delete(id: number): void {
    if (!confirm('Supprimer ce dossier de soins ?')) return;
    this.service.delete(id).subscribe({
      next: () => this.load(),
      error: (err) => alert(err.error?.message || 'Erreur')
    });
  }
}
