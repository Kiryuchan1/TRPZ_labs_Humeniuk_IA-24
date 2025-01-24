package coursework.shell.controllers;

import coursework.shell.actions.factory.ActionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/actions")
public class ActionController {

    private final Map<String, ActionFactory> actionFactories;

    @Autowired
    public ActionController(Map<String, ActionFactory> actionFactories) {
        this.actionFactories = actionFactories;
    }

    @GetMapping
    public ResponseEntity<Set<String>> getAvailableActions() {
        Set<String> actions = new HashSet<>(actionFactories.keySet());
        return ResponseEntity.ok(actions);
    }
}
