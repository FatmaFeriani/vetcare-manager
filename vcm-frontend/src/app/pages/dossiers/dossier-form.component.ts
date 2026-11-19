import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DossierService } from '../../core/services/dossier.service';
import { ProprietaireService } from '../../core/services/proprietaire.service';
import { Proprietaire } from '../../core/models/models';

@Component({
  selector: 'app-dossier-form',
  templateUrl: './dossier-form.component.html'
})
export class DossierFormComponent implements OnInit {
  form: FormGroup;
  loading = false;
  isEdit = false;
  id?: number;
  error = '';
  proprietaires: Proprietaire[] = [];

  constructor(
    private fb: FormBuilder,
    private service: DossierService,
    private proprietaireService: ProprietaireService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      libelle: ['', Validators.required],
      duree: [1, [Validators.required, Validators.min(1)]],
      budget: [0, [Validators.required, Validators.min(0.01)]],
      description: [''],
      proprietaireId: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.proprietaireService.getAll(0, 200).subscribe({
      next: (p) => this.proprietaires = p.content
    });

    const preselected = this.route.snapshot.queryParamMap.get('proprietaireId');
    if (preselected) {
      this.form.patchValue({ proprietaireId: +preselected });
    }

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam && idParam !== 'nouveau') {
      this.isEdit = true;
      this.id = +idParam;
      this.service.getById(this.id).subscribe({
        next: (d) => {
          this.form.patchValue({
            libelle: d.libelle,
            duree: d.duree,
            budget: d.budget,
            description: d.description,
            proprietaireId: d.proprietaire?.id
          });
        },
        error: () => this.router.navigate(['/dossiers'])
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    this.loading = true;
    this.error = '';
    const data = { ...this.form.value };
    const obs = this.isEdit
      ? this.service.update(this.id!, data)
      : this.service.create(data);

    obs.subscribe({
      next: () => this.router.navigate(['/dossiers']),
      error: (err) => {
        this.loading = false;
        this.error = err.error?.message || 'Erreur lors de l\'enregistrement';
      }
    });
  }
}
