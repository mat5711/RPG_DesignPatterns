package eu.telecomnancy.rpg;

public class ScaredState implements State{
    
    @Override
    public void onEnterState(GameCharacter character){
        System.out.println("Le personnage " + character.getName() + " est effrayé");
    }

    @Override
    public void onUpdate(GameCharacter character){
        if (character.getHealth() <= 0){
            DeadState deadState = new DeadState();
            character.setState(deadState);
        }
        if (character.getHealth() >= 30){
            NormalState normalState = new NormalState();
            character.setState(normalState);
        }
        if (character.getHealth() > 10 && character.getHealth() <= 30){
            WoundedState woundedState = new WoundedState();
            character.setState(woundedState);
        }
    }

    @Override
    public void onTryToMove(GameCharacter character){
        // On effectue d'abord une possible transition vers le véritable état du personnage
        onUpdate(character);
        State currentState = character.getState();
        if (currentState instanceof ScaredState){
            System.out.println("Le personnage " + character.getName() + " est effrayé et s'enfuit");
        }
        else{
            currentState.onTryToMove(character);
        }
    }

    @Override
    public void onAttack(GameCharacter character, GameCharacter target){
        // On effectue d'abord une possible transition vers le véritable état du personnage
        onUpdate(character);
        State currentState = character.getState();
        if (currentState instanceof ScaredState){
            System.out.println("Le personnage " + character.getName() + " attaque faiblement, car effrayé");
            //Dans cet état, le personnage inflige 1 points de dégâts à la cible
            character.stateDamage = 1;
        }
        else{
            currentState.onAttack(character, target);
        }
    }
}
