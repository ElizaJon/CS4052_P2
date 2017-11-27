package model;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * A model is consist of states and transitions
 */
public class Model {
    State[] states;
    Transition[] transitions;

    public static Model parseModel(String filePath) throws IOException {
        Gson gson = new Gson();
        Model model = gson.fromJson(new FileReader(filePath), Model.class);
        for (Transition t : model.transitions) {
            System.out.println(t);
        }
        return model;
    }

    /**
     * Returns the list of the states
     * 
     * @return list of state for the given model
     */
    public State[] getStates() {
        return states;
    }

    /**
     * Returns the list of transitions
     * 
     * @return list of transition for the given model
     */
    public Transition[] getTransitions() {
        return transitions;
    }

    public Transition[] getTransitions(Set<String> actions){
        Transition[] allTransitions = transitions;
        if(actions.size() == 0){
            return allTransitions;
        }
        String[] transitionActions;
        boolean check;
        ArrayList<Transition> newTransitions = new ArrayList<>();
        for(int i = 0; i < allTransitions.length; i++){
            transitionActions = allTransitions[i].getActions();
            check = false;
            for(int k = 0; k < transitionActions.length; k++){
                for(int j = 0; j < actions.size(); j++){
                    if(actions.contains(transitionActions[k])) {
                        newTransitions.add(allTransitions[i]);
                        check = true;
                        break;
                    }
                }
                if(check){
                    break;
                }
            }
        }
        return newTransitions.toArray(new Transition[newTransitions.size()]);
    }

}
