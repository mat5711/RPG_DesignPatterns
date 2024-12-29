package eu.telecomnancy.rpg;

public class NormalState implements State {
    @Override
    public void onEnterState(GameCharacter character){
        System.out.println("Le personnage " + character.getName() + " est dans l'état normal");
    }

    @Override
    public void onUpdate(GameCharacter character){
        if (character.getHealth() <= 0){
            DeadState deadState = new DeadState();
            character.setState(deadState);
        }
        if (character.getHealth() <= 10 && character.getHealth() > 0){
            ScaredState scaredState = new ScaredState();
            character.setState(scaredState);
        }
        if (character.getHealth() < 30 && character.getHealth() > 10){
            WoundedState woundedState = new WoundedState();
            character.setState(woundedState);
        }
    }

    @Override
    public void onTryToMove(GameCharacter character){
        // On effectue d'abord une possible transition vers le véritable état du personnage
        onUpdate(character);
        State currentState = character.getState();
        if (currentState instanceof NormalState){
            System.out.println("Le personnage " + character.getName() + " se déplace normalement");
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
        if (currentState instanceof NormalState){
            System.out.println("Le personnage " + character.getName() + " attaque normalement");
            //On prend la valeur de son attribut principal pour les dégâts infligés 
            character.stateDamage = character.getMainAttribut();
        }
        else{
            currentState.onAttack(character, target);
        }
    }
}
