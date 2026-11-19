import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProprietaireService } from '../../core/services/proprietaire.service';
import { DossierService } from '../../core/services/dossier.service';
import { AuthService } from '../../core/services/auth.service';
import { Proprietaire, DossierSoins } from '../../core/models/models';

@Component({
  selector: 'app-proprietaire-detail',
  templateUrl: './proprietaire-detail.component.html'
})
export class ProprietaireDetailComponent implements OnInit {
  proprietaire?: Proprietaire;
  dossiers: DossierSoins[] = [];
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: ProprietaireService,
    private dossierService: DossierService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.service.getById(id).subscribe({
      next: (p) => {
        this.proprietaire = p;
        this.dossierService.getByProprietaire(id).subscribe({
          next: (d) => { this.dossiers = d; this.loading = false; },
          error: () => { this.loading = false; }
        });
      },
      error: () => this.router.navigate(['/proprietaires'])
    });
  }
}
