package eu.telecomnancy.rpg;

import java.util.HashMap;
import java.util.Map;

public class Facade {
    private Map<String, Team> teams;

    public Facade(){
        this.teams = new HashMap<>();
    }

    //On ajoute une équipe au jeu
    public void addTeam(Team team) {
        if (teams.containsKey(team.getName())) {
            System.out.println("Une équipe avec ce nom existe déjà.");
            return;
        }
        teams.put(team.getName(), team);
        System.out.println("Équipe " + team.getName() + " ajoutée.");
    }

    //On retire une équipe du jeu
    public void removeTeam(String teamName) {
        if (teams.remove(teamName) != null) {
            System.out.println("Équipe " + teamName + " supprimée.");
        } else {
            System.out.println("Aucune équipe nommée " + teamName + " n'existe.");
        }
    }


    // Autres méthodes à implémenter plus tard
    
    
    
    
    public Map<String, Team> getTeams() {
        return teams;
    }
}
