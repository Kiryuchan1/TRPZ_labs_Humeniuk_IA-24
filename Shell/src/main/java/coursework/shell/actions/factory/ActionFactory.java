package coursework.shell.actions.factory;

import coursework.shell.actions.FileAction;

public interface ActionFactory {
    FileAction createAction(String[] args);
}
