package eu.telecomnancy.rpg;

import java.util.ArrayList;
import java.util.List;

import main.java.eu.telecomnancy.rpg.GameCharacter;

public class TeamBuilder implements Builder{
    private String name;
    private List<GameCharacter> players;


    public TeamBuilder(){
        this.players = new ArrayList<>();
    }

    @Override
    public void setName(String name){
        this.name = name;
    }

    @Override
    public void setCharacter(GameCharacter character){
        this.players.add(character);
    }


    //Principe du Builder, cette méthode sert à obtenir notre Team
    public Team getResult(){
        Team team = new Team(this.name);
        for (GameCharacter player : this.players){
            team.addPlayer(player);
        }
        return team;
    }

}
