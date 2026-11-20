# VetCare Manager (VCM) - Backend Spring Boot

Application de gestion de clinique vétérinaire – Partie Backend.

## Technologies

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Data JPA** + **PostgreSQL**
- **Spring Security** + **JWT**
- **Spring Mail**
- **Maven**

## Prérequis

- JDK 17+
- Maven 3.8+
- PostgreSQL (base `vcm_db` créée au préalable)

## Configuration

La configuration locale utilise PostgreSQL sur `localhost:5432`, avec l'utilisateur `postgres` et le mot de passe `root` par défaut. Si besoin, définissez `DB_PASSWORD` avant de lancer l'application. `DB_USERNAME` et `JDBC_DATABASE_URL` peuvent aussi remplacer l'utilisateur et l'URL par défaut.

Lorsqu'un compte est créé via `/api/auth/register`, l'application envoie une notification à `adminpethealth@gmail.com`. Pour activer l'envoi, configurez un compte Gmail expéditeur et son mot de passe d'application (avec la validation en deux étapes activée) :

```powershell
$env:MAIL_USERNAME = "adminpethealth@gmail.com"
$env:MAIL_PASSWORD = "mot_de_passe_application_gmail"
$env:ADMIN_EMAIL = "adminpethealth@gmail.com"
```

Le mot de passe d'application est fourni par Google; le mot de passe normal du compte Gmail ne convient pas. Gardez-le dans une variable d'environnement et ne le commitez pas dans le dépôt.

Dans PowerShell :

```powershell
$env:DB_USERNAME = "postgres"
$env:DB_PASSWORD = "root"
mvn spring-boot:run
```

## Lancement

```bash
cd vcm
mvn clean spring-boot:run
```

L'API démarre sur `http://localhost:8080`.

## Compte admin par défaut

- **Login** : `adminpethealth@gmail.com`
- **Mot de passe** : `admin123`

## Endpoints principaux

### Authentification (public)
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/auth/register` | Inscription |
| POST | `/api/auth/login` | Connexion (retourne JWT) |

### Propriétaires
| Méthode | Endpoint | ADMIN | COLLAB |
|---------|----------|-------|--------|
| GET | `/api/proprietaires` | ✔ | ✔ |
| GET | `/api/proprietaires/{id}` | ✔ | ✔ |
| POST | `/api/proprietaires` | ✔ | ✔ |
| PUT | `/api/proprietaires/{id}` | ✔ | ✘ |
| DELETE | `/api/proprietaires/{id}` | ✔ | ✘ |

Paramètres optionnels : `?page=0&size=10&search=nom`

### Dossiers de soins
| Méthode | Endpoint | ADMIN | COLLAB |
|---------|----------|-------|--------|
| GET | `/api/dossiers` | ✔ | ✔ |
| GET | `/api/dossiers/{id}` | ✔ | ✔ |
| GET | `/api/proprietaires/{id}/dossiers` | ✔ | ✔ |
| POST | `/api/dossiers` | ✔ | ✔ |
| PUT | `/api/dossiers/{id}` | ✔ | ✘ |
| DELETE | `/api/dossiers/{id}` | ✔ | ✘ |

### Utilisateurs (ADMIN uniquement)
| Méthode | Endpoint |
|---------|----------|
| GET | `/api/users` |
| PUT | `/api/users/{id}/roles` |
| DELETE | `/api/users/{id}` |

### Dashboard
| Méthode | Endpoint |
|---------|----------|
| GET | `/api/dashboard/stats` |

## Build JAR

```bash
mvn clean package -DskipTests
java -jar target/vcm-0.0.1-SNAPSHOT.jar
```

## Structure du projet

```
com.sip.vcm
├── config          # SecurityConfig, DataInitializer
├── controllers     # REST Controllers
├── dto             # Data Transfer Objects
├── entities        # User, Role, Proprietaire, DossierSoins
├── exceptions      # Gestion centralisée des erreurs
├── repositories    # JPA Repositories
├── security        # JWT, UserDetails, Filters
└── services        # Logique métier
```
