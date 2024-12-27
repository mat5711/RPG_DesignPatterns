package eu.telecomnancy.rpg;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GameInvoker {
    private Queue<Command> commandQueue; // FIFO
    private Stack<Command> commandHistory; // historique des commandes

    public GameInvoker() {
        this.commandQueue = new LinkedList<>();
        this.commandHistory = new Stack<>();
    }


    //ajoute une commande dans la file, sans l'exécuter immédiatement
    public void addCommand(Command command) {
        commandQueue.offer(command);
    }


    //traite toues les commandes en attente
    public void processCommands(){
        while (!commandQueue.isEmpty()){
            Command command = commandQueue.poll();
            command.execute();
            commandHistory.push(command);
        }
    }

    public void undoLastCommand(){
        if (!commandHistory.isEmpty()){
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
        }
        else{
            System.out.println("Il n'y a pas de commande à annuler");
        }
    }
}
