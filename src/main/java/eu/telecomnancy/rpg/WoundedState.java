package eu.telecomnancy.rpg;

public class WoundedState implements State {
    
    @Override
    public void onEnterState(GameCharacter character){
        System.out.println("Le personnage " + character.getName() + " est blessé");
    }

    @Override
    public void onUpdate(GameCharacter character){
        // On modifie l'état du personnage en fonction de ses HP
        if (character.getHealth() <= 0){
            DeadState deadState = new DeadState(); // On appelle le constructeur par défaut de DeadState
            character.setState(deadState);
        }
        if (character.getHealth() >= 30){
            //Si le personnage a au minimum autant de HP qu'au départ
            NormalState normalState = new NormalState(); // Pareil, on appelle le constructeur par défaut
            character.setState(normalState);
        }
        if (character.getHealth() <= 10 && character.getHealth() > 0){
            //Si le personnage n'est pas mort, mais n'a plus beaucoup de HP
            ScaredState scaredState = new ScaredState(); //Pareil, on appelle le constructeur par défaut
            character.setState(scaredState);
        }
    }

    @Override
    public void onTryToMove(GameCharacter character){
        // On effectue d'abord une possible transition vers le véritable état du personnage
        onUpdate(character);
        State currentState = character.getState();
        if (currentState instanceof WoundedState){
            System.out.println("Le personnage " + character.getName() + " se déplace difficilement,car blessé");
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
        if (currentState instanceof WoundedState){
            System.out.println("Le personnage " + character.getName() + " attaque difficilement,car blessé");
            //On prend la valeur de son attribut principal divisé par 2 pour les dégâts infligés 
            character.stateDamage = character.getMainAttribut()/2;
        }
        else{
            currentState.onAttack(character, target);
        }
    }
}
