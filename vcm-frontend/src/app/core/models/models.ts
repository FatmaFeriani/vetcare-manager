export interface User {
  id: number;
  login: string;
  nom: string;
  prenom: string;
  tel?: string;
  roles: string[];
}

export interface JwtResponse {
  token: string;
  type: string;
  id: number;
  login: string;
  nom: string;
  prenom: string;
  roles: string[];
}

export interface LoginRequest {
  login: string;
  password: string;
}

export interface RegisterRequest {
  login: string;
  password: string;
  nom: string;
  prenom: string;
  tel?: string;
  role?: string;
}

export interface Proprietaire {
  id?: number;
  nom: string;
  telephone: string;
  email: string;
  adresse?: string;
  photo?: string;
  activite?: string;
  gestionnaire?: User;
  dossiers?: DossierSoins[];
}

export interface DossierSoins {
  id?: number;
  libelle: string;
  duree: number;
  budget: number;
  description?: string;
  proprietaire?: Proprietaire;
  proprietaireId?: number;
  gestionnaire?: User;
}

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

export interface DashboardStats {
  totalProprietaires: number;
  totalDossiers: number;
  budgetTotal: number;
}
