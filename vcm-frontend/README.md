# VetCare Manager – Frontend Angular

Application Angular (VetCare Manager / VCM) connectée à l'API Spring Boot.

## Prérequis

- Node.js 16+ (recommandé 18 LTS)
- npm 8+
- Backend Spring Boot démarré sur `http://localhost:8080`

## Installation

```bash
cd vcm-frontend
npm install
```

## Lancement en développement

```bash
npm start
# ou
ng serve
```

L'application est accessible sur **http://localhost:4200**

## Build de production

```bash
ng build --configuration production
```

Les fichiers générés se trouvent dans `dist/vcm-frontend/`.  
Pour le déploiement dans le JAR Spring Boot, copiez le contenu de ce dossier dans `src/main/resources/static/` du projet backend.

## Fonctionnalités

- Authentification JWT (login / register)
- AuthGuard + RoleGuard
- Interceptor HTTP (ajout automatique du token Bearer)
- Gestion des rôles ADMIN / COLLABORATEUR
- Dashboard (statistiques)
- CRUD Propriétaires (liste, détail, ajout, modification, suppression)
- CRUD Dossiers de soins
- Gestion des utilisateurs (ADMIN uniquement)
- Recherche et pagination
- Interface responsive Bootstrap 5

## Comptes de test (selon DataInitializer du backend)

| Email                 | Mot de passe | Rôle           |
|-----------------------|--------------|----------------|
| adminpethealth@gmail.com | admin123  | ADMIN          |
| collab@pethealth.com  | collab123    | COLLABORATEUR  |

*(Vérifiez les identifiants exacts dans `DataInitializer.java` du backend)*

## Structure

```
src/app/
├── core/
│   ├── guards/          # AuthGuard, RoleGuard
│   ├── interceptors/    # AuthInterceptor
│   ├── models/          # Interfaces TypeScript
│   └── services/        # Auth, Proprietaire, Dossier, User, Dashboard
├── layouts/             # Sidebar + Layout
└── pages/
    ├── auth/            # Login, Register
    ├── dashboard/
    ├── proprietaires/
    ├── dossiers/
    └── users/
```
