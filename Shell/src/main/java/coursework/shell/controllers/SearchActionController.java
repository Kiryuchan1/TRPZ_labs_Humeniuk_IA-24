package coursework.shell.controllers;

import coursework.shell.ActionFlowController;
import coursework.shell.actions.FileAction;
import coursework.shell.actions.SearchFileAction;
import coursework.shell.actions.factory.ActionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actions")
public class SearchActionController {

    private final ActionFlowController actionFlowController;
    private final ActionFactory searchActionFactory;

    @Autowired
    public SearchActionController(ActionFlowController actionFlowController, Map<String, ActionFactory> actionFactories) {
        this.actionFlowController = actionFlowController;
        this.searchActionFactory = actionFactories.get("search");
    }

    @PostMapping("/search")
    public ResponseEntity<?> executeSearchCommand(@RequestParam String query, @RequestParam String path) {
        String[] args = {"search", query, path};
        FileAction fileAction = searchActionFactory.createAction(args);

        if (fileAction == null) {
            return ResponseEntity.badRequest().body("Search error.");
        }

        if (fileAction instanceof SearchFileAction searchFileAction) {
            searchFileAction.setPath(path);
            actionFlowController.handleAction(fileAction);
            List<String> results = searchFileAction.getResults();
            return ResponseEntity.ok(results);
        } else {
            return ResponseEntity.status(500).body("Server error.");
        }
    }
}
