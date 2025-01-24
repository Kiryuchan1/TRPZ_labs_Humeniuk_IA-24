package coursework.shell.actions.factory;

import coursework.shell.actions.FileAction;
import coursework.shell.utils.ArgumentValidator;

public abstract class AbstractActionFactory<T extends FileAction> implements ActionFactory {

    private final T prototype;

    public AbstractActionFactory(T prototype) {
        this.prototype = prototype;
    }

    @Override
    public FileAction createAction(String[] args) {
        if (!ArgumentValidator.validateArgsLength(args, getRequiredArgsLength())) {
            return null;
        }
        T action = (T) prototype.clone();
        setActionParameters(action, args);
        logCreation(action, args);
        return action;
    }

    protected abstract void setActionParameters(T action, String[] args);
    protected abstract int getRequiredArgsLength();
    protected abstract void logCreation(T action, String[] args);
}
