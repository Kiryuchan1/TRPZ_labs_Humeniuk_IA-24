package coursework.shell.controllers;

import coursework.shell.ActionFlowController;
import coursework.shell.actions.factory.ActionFactory;
import coursework.shell.actions.interpreter.Condition;
import coursework.shell.actions.interpreter.ConditionAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/command-line")
public class UserOperationController {

    private final ConditionAnalyzer parser;

    @Autowired
    public UserOperationController(ActionFlowController actionFlowController, Map<String, ActionFactory> actionFactories) {
        this.parser = new ConditionAnalyzer(actionFlowController, actionFactories);
    }

    @PostMapping("/execute")
    public String executeCommandLine(@RequestBody String commandLine) {
        if (commandLine == null || commandLine.trim().isEmpty()) {
            return "Command is empty";
        }

        try {
            Condition condition = parser.analizeAction(commandLine);
            condition.interpret();
            return "Success";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}