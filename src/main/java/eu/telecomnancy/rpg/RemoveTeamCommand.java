package eu.telecomnancy.rpg;

public class RemoveTeamCommand implements Command {
    
    private Facade facade; 
    private Team removedTeam;
    private boolean executed;
    private String teamName;

    public RemoveTeamCommand(Facade facade, String teamName) {
        this.facade = facade;
        this.teamName = teamName;
        this.executed = false;
    }

    @Override
    public void execute() {
        removedTeam = facade.getTeams().get(teamName); //on sauvegarde la team qu'on veut supprimer pour potentiellement undo
        facade.removeTeam(teamName);
        executed = true;
    }

    @Override
    public void undo() { // on annule notre action, càd on réajoute la team qu'on vient de supprimer
        if (executed && removedTeam != null){
            facade.addTeam(removedTeam);
            executed = false;
        }
    }    

}
