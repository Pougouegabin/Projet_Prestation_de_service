# Projet_Prestation_de_service

Ce projet permettra à n’importe quel utilisateur de demander des prestations de service à un prestataire de proximité depuis sa localité.

L’application est composée d’un frontend Angular et d’un backend Spring Boot.

## Prérequis

- Node.js et npm
- Java et Maven (ou le wrapper Maven fourni dans `backend/`)

## Démarrer le frontend

```bash
cd frontend/ProxiService
npm install
npm start
```

L’application Angular est disponible sur `http://localhost:4200`.

## Démarrer le backend

```bash
cd backend
./mvnw spring-boot:run
```

Sous Windows, utiliser `mvnw.cmd`.

## Travail en équipe

Clonez le dépôt, créez une branche pour chaque tâche, puis ouvrez une pull request vers `main` pour faire relire les changements.

Ne commitez pas de mots de passe, clés API ou fichiers `.env`. Ajoutez plutôt un fichier `.env.example` avec des valeurs fictives si la configuration en nécessite un.
