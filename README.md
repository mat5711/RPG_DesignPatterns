# RPG avec Design Patterns

## Description

TP réalisé dans le cadre du cours de MOCI à Télécom Nancy.
Il s’agit d’un prototype de jeu de rôle (RPG) conçu pour expérimenter et mettre en œuvre différents design patterns.

Les fonctionnalités principales incluent :

- Gestion des personnages et équipes (création, clonage, construction)
- Stratégies de combat dynamiques
- Système d’observation (level-up, mort)
- Décorateurs appliqués aux personnages (armure, invincibilité)
- Commandes centralisant les actions du jeu (attaque, soin, buff, etc.)
- États internes des personnages (normal, blessé, effrayé, mort)

## Structure du projet
  
- `src/main/java/eu/telecomnancy/rpg/` : code source principal (patterns, logique du jeu)

- `src/test/java/eu/telecomnancy/rpg/` : tests unitaires (JUnit) pour valider chaque pattern

## Prérequis

Avant de compiler et exécuter le projet, assurez-vous d’avoir installé :

- **Java 21** (ou version compatible)  
- **Apache Maven** (3.9 ou supérieur recommandé)  

Vérifiez vos versions avec :  

```bash
java -version
mvn -version
```

## Compilation et exécution

Pour utiliser Maven :

```bash
# Nettoyer et compiler le projet
mvn clean compile

# Lancer les tests unitaires (JUnit 5)
mvn test

# Compiler et générer le JAR dans target/
mvn package

# Lancer directement l'application (classe principale : Client)
mvn exec:java -Dexec.mainClass="eu.telecomnancy.rpg.Client"

# Lancer l'application via le JAR généré
java -cp target/rpg-1.0-SNAPSHOT.jar eu.telecomnancy.rpg.Client
```

## Patterns implémentés

- Singleton : configuration globale du jeu
- Factory Method : création de personnages (guerrier, mage, soigneur, etc.)
- Prototype : clonage d’équipes
- Builder : construction progressive d’équipes
- Visitor : opérations sur personnages/équipes (buff, dégâts, soin)
- Strategy : stratégies de combat (agressive, défensive, neutre)
- Observer : notification lors des changements d’état
- Decorator : ajout dynamique de capacités spéciales
- Command : encapsulation des actions (ajout/suppression d’équipe, attaque…)
- State : gestion des comportements selon l’état du personnage

## Auteur

Projet réalisé par Mathieu Breit dans le cadre du TP Design Patterns (MOCI, 2024).
