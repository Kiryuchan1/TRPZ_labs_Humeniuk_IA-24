package coursework.shell;

import coursework.shell.actions.FileAction;
import coursework.shell.states.CopyActionState;
import coursework.shell.states.NormalState;
import coursework.shell.states.State;
import coursework.shell.states.ActionStateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.EnumMap;
import java.util.Map;

@Component
public class ActionFlowController {
    private final Map<ActionStateType, State> states = new EnumMap<>(ActionStateType.class);
    private State currentState;

    @Autowired
    public ActionFlowController(NormalState normalState, CopyActionState copyActionState) {
        states.put(ActionStateType.NORMAL, normalState);
        states.put(ActionStateType.COPY, copyActionState);
        currentState = normalState;
    }

    public void handleAction(FileAction fileAction) {
        ActionStateType nextActionStateType = currentState.handle(fileAction);
        State nextState = states.get(nextActionStateType);

        if (nextState != null && nextState != currentState) {
            currentState = nextState;
            nextActionStateType = currentState.handle(fileAction);
            nextState = states.get(nextActionStateType);
            if (nextState != null && nextState != currentState) {
                currentState = nextState;
            }
        }
    }
}
