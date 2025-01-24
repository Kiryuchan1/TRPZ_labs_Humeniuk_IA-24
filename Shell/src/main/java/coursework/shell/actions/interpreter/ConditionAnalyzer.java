package coursework.shell.actions.interpreter;

import coursework.shell.ActionFlowController;
import coursework.shell.actions.factory.ActionFactory;
import coursework.shell.utils.LogUtils;
import org.slf4j.Logger;

import java.util.Map;

public class ConditionAnalyzer {
    private static final Logger logger = LogUtils.getLogger(ConditionAnalyzer.class);

    private final ActionFlowController actionFlowController;
    private final Map<String, ActionFactory> actionFactories;

    public ConditionAnalyzer(ActionFlowController actionFlowController, Map<String, ActionFactory> actionFactories) {
        this.actionFlowController = actionFlowController;
        this.actionFactories = actionFactories;
    }

    public Condition analizeAction(String commandLine) {
        logger.info("Commencing analysis of command line input: '{}'", commandLine);

        try {
            String[] tokens = commandLine.trim().split("\\s+");

            if (tokens.length == 0) {
                throw new IllegalArgumentException("Empty command");
            }

            Condition result = getNextExpression(tokens);

            logger.info("Action analysis completed with success");
            return result;
        } catch (Exception e) {
            logger.error("Issue encountered while analyzing action: {}", e.getMessage());
            throw e;
        }
    }

    private Condition getNextExpression(String[] tokens) {
        String userInput = tokens[0].toLowerCase();
        logger.debug("Recognized user input as action type: '{}'", userInput);
        ActionFactory factory = actionFactories.get(userInput);

        if (factory == null) {
            logger.warn("Unrecognized action specified: '{}'", userInput);
            throw new IllegalArgumentException("Unknown command: " + userInput);
        }

        logger.debug("Generated command condition for action: '{}'", userInput);
        return new ActionCondition(tokens, actionFlowController, factory);
    }
}
