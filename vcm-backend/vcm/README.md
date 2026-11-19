# VetCare Manager (VCM) - Backend Spring Boot

Application de gestion de clinique vétérinaire – Partie Backend.

## Technologies

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Data JPA** + **MySQL**
- **Spring Security** + **JWT**
- **Spring Mail**
- **Maven**

## Prérequis

- JDK 17+
- Maven 3.8+
- MySQL 8.x (base `vcm_db` créée automatiquement)

## Configuration

Modifier `src/main/resources/application.properties` :

```properties
spring.datasource.username=root
spring.datasource.password=votre_mot_de_passe
spring.mail.username=votre.email@gmail.com
spring.mail.password=votre-app-password
```

## Lancement

```bash
cd vcm
mvn clean spring-boot:run
```

L'API démarre sur `http://localhost:8080`.

## Compte admin par défaut

- **Login** : `admin@pethealth.com`
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
