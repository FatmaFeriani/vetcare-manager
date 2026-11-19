import { Component } from '@angular/core';

@Component({
  selector: 'app-layout',
  template: `
    <app-sidebar></app-sidebar>
    <div class="main-content">
      <div class="topbar">
        <div>
          <span class="text-muted" style="font-size:0.9rem;">PetHealth Solutions</span>
        </div>
        <div class="d-flex align-items-center gap-3">
          <span class="badge bg-success-subtle text-success">
            <i class="bi bi-circle-fill me-1" style="font-size:0.5rem;"></i> En ligne
          </span>
        </div>
      </div>
      <div class="content-area">
        <router-outlet></router-outlet>
      </div>
    </div>
  `
})
export class LayoutComponent {}
