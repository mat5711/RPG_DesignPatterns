package eu.telecomnancy.rpg;

public class RemoveTeamCommand implements Command {
    private Facade facade; // à faire
    private Team team;
    private boolean executed;
    private String name;

    public RemoveTeamCommand(Facade facade, String name) {
        this.facade = facade;
        this.name = name;
        this.executed = false;
    }

    @Override
    public void execute() {
        facade.removeCharacter(team);
        executed = true;
    }

    @Override
    public void undo() {
        facade.addCharacter(team);
        executed = false;
    }    
}
