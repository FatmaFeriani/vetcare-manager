import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProprietaireService } from '../../core/services/proprietaire.service';

@Component({
  selector: 'app-proprietaire-form',
  templateUrl: './proprietaire-form.component.html'
})
export class ProprietaireFormComponent implements OnInit {
  form: FormGroup;
  loading = false;
  isEdit = false;
  id?: number;
  error = '';

  constructor(
    private fb: FormBuilder,
    private service: ProprietaireService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      nom: ['', Validators.required],
      telephone: ['', [Validators.required, Validators.pattern(/^[+]?[0-9\s\-]{8,20}$/)]],
      email: ['', [Validators.required, Validators.email]],
      adresse: [''],
      activite: [''],
      photo: ['']
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam && idParam !== 'nouveau') {
      this.isEdit = true;
      this.id = +idParam;
      this.service.getById(this.id).subscribe({
        next: (p) => this.form.patchValue(p),
        error: () => this.router.navigate(['/proprietaires'])
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    this.loading = true;
    this.error = '';
    const obs = this.isEdit
      ? this.service.update(this.id!, this.form.value)
      : this.service.create(this.form.value);

    obs.subscribe({
      next: () => this.router.navigate(['/proprietaires']),
      error: (err) => {
        this.loading = false;
        this.error = err.error?.message || 'Erreur lors de l\'enregistrement';
      }
    });
  }
}
