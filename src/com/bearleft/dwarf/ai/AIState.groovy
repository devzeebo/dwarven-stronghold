package com.bearleft.dwarf.ai
import com.bearleft.dwarf.config.CloneContainer
import com.bearleft.dwarf.config.IConfigurable
import com.bearleft.dwarf.config.IDelayedBuilder
import com.bearleft.dwarf.config.IBuilder
import com.bearleft.dwarf.config.MetaUnit
/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 4/14/13
 * Time: 6:38 PM
 * To change this template use File | Settings | File Templates.
 */
class AIState implements IConfigurable {

    protected String stateName

    protected Closure applyAIState

    private AIState() {}

    public IAState(String state) {
        CloneContainer.clone(IAState, state, this)
    }

    public Object getKey() {
        return stateName.toLowerCase()
    }

    public String getStateName() {
        return stateName
    }

    //////////////////////
    // DSL BUILDER CODE //
    //////////////////////

    static class AIStateBuilder implements IDelayedBuilder<AIState> {

        Map<String, Map<String, Object>> states = [:]
        String curType

        void newItem(String type) {
            state = new AIState() // <- create a reference pointer
            state.name = type
            curType = type
            // state, transitions, etc
            // 0 = AIState(Idle)
            // 1 = string names of transitions ['Walk', 'Sleep']
            states[type] = [
                    'state' : state,
                    'transitions' : []
            ]
        }

        void buildItem(String type) {
            AIState state = states[type]['state']
            // Associate states with each other
            state.transitions = states[type]['transitions'].collect {
                states[it]
            }
        }

        Map<String, AIState> getItems() {
            return states.collectEntries { [it.key, it.value['state']] }
        }

        Class<AIState> getType() {
            return AIState
        }

        public void transition(String transition) {
            states[curType]['transitions'] << transition
        }
    }
}
