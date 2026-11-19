import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DossierService } from '../../core/services/dossier.service';
import { AuthService } from '../../core/services/auth.service';
import { DossierSoins } from '../../core/models/models';

@Component({
  selector: 'app-dossier-detail',
  templateUrl: './dossier-detail.component.html'
})
export class DossierDetailComponent implements OnInit {
  dossier?: DossierSoins;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: DossierService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.service.getById(id).subscribe({
      next: (d) => { this.dossier = d; this.loading = false; },
      error: () => this.router.navigate(['/dossiers'])
    });
  }
}
