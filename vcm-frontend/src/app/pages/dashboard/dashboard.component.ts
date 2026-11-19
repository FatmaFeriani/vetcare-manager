import { Component, OnInit } from '@angular/core';
import { DashboardService } from '../../core/services/dashboard.service';
import { DashboardStats } from '../../core/models/models';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  stats: DashboardStats = { totalProprietaires: 0, totalDossiers: 0, budgetTotal: 0 };
  loading = true;
  userName = '';
  isAdmin = false;

  constructor(
    private dashboardService: DashboardService,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    const user = this.auth.getCurrentUser();
    this.userName = user ? `${user.prenom} ${user.nom}` : '';
    this.isAdmin = this.auth.isAdmin();
    this.dashboardService.getStats().subscribe({
      next: (s) => {
        this.stats = s;
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }
}
