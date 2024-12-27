package eu.telecomnancy.rpg;


public class AddTeamCommand implements Command {
    private Facade facade; // à faire
    private Team team;
    private boolean executed;

    public AddTeamCommand(Facade facade, Team team) {
        this.facade = facade;
        this.team = team;
        this.executed = true;
    }

    @Override
    public void execute() {
        facade.addTeam(team);
        executed = true;
    }


    @Override
    public void undo() {
        if (executed) {
            facade.removeTeam(team.getName());
            executed = false;
        }
    }
}
