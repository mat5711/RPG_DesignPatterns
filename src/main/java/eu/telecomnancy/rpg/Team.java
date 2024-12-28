package eu.telecomnancy.rpg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


//Cloneable est une interface qui existe déjà en java pour utiliser le pattern Prototype
public class Team implements Cloneable{

    private final String name;

    private List<GameCharacter> players;

    public Team(String name) {
        this.name = name;
        players=new ArrayList<GameCharacter>();
    }

    public String getName() {
        return name;
    }
    
    public List<GameCharacter> getPlayers() {
        return players;
    }

    public void addPlayer(GameCharacter player) {
        players.add(player);
    }

    public void removePlayer(GameCharacter player) {
        players.remove(player);
    }

    public void removePlayer(String name) {
        for (GameCharacter player : players) {
            if (player.getName().equals(name)) {
                players.remove(player);
                return;
            }
        }
    }

    public GameCharacter getPlayer(String name) {
        for (GameCharacter player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public boolean containsPlayer(String name) {
        for (GameCharacter player : players) {
            if (player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsPlayer(GameCharacter player) {
        return players.contains(player);
    }

    public int size() {
        return players.size();
    }


    //Le clonage en question
    @Override
    public Team clone(){
        try {
            Team clonedTeam = (Team) super.clone();
            clonedTeam.players = new ArrayList<>();
            for (GameCharacter player : this.players) {
                clonedTeam.players.add(player.clone());
            }
            return clonedTeam;
        } catch (CloneNotSupportedException e){
            throw new AssertionError();
        }
    }


}
