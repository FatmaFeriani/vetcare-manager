import { Component, OnInit } from '@angular/core';
import { UserService } from '../../core/services/user.service';
import { AuthService } from '../../core/services/auth.service';
import { User } from '../../core/models/models';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html'
})
export class UsersListComponent implements OnInit {
  users: User[] = [];
  loading = true;

  constructor(
    private service: UserService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading = true;
    this.service.getAll().subscribe({
      next: (u) => { this.users = u; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  delete(id: number): void {
    if (!confirm('Supprimer cet utilisateur ?')) return;
    this.service.delete(id).subscribe({
      next: () => this.load(),
      error: (err) => alert(err.error?.message || 'Erreur')
    });
  }
}
