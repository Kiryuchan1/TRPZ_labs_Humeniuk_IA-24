package coursework.shell.states;

import coursework.shell.actions.FileAction;

public interface State {
    ActionStateType handle(FileAction fileAction);
}
