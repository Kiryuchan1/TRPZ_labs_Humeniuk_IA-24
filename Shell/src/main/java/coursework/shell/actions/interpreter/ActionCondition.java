package coursework.shell.actions.interpreter;

import coursework.shell.ActionFlowController;
import coursework.shell.actions.FileAction;
import coursework.shell.actions.factory.ActionFactory;
import coursework.shell.actions.factory.CopyFileActionFactory;
import coursework.shell.utils.LogUtils;
import org.slf4j.Logger;

public class ActionCondition implements Condition {
    private static final Logger logger = LogUtils.getLogger(CopyFileActionFactory.class);

    private final String[] args;
    private final ActionFlowController actionFlowController;
    private final ActionFactory actionFactory;

    public ActionCondition(String[] args, ActionFlowController actionFlowController, ActionFactory actionFactory) {
        this.args = args;
        this.actionFlowController = actionFlowController;
        this.actionFactory = actionFactory;
    }

    @Override
    public boolean interpret() {
        String userInput = args[0].toLowerCase();
        FileAction fileAction = actionFactory.createAction(args);
        if (fileAction != null) {
            logger.info("Executing action '{}'", userInput);
            actionFlowController.handleAction(fileAction);
            return true;
        } else {
            logger.warn("Action '{}' wasn`t executed", userInput);
            return false;
        }
    }
}
