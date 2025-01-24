package coursework.shell.controllers;


import coursework.shell.ActionFlowController;
import coursework.shell.actions.FileAction;
import coursework.shell.actions.ViewFileAction;
import coursework.shell.actions.factory.ActionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actions")
public class ViewActionController {

    private final ActionFlowController actionFlowController;
    private final ActionFactory viewActionFactory;

    @Autowired
    public ViewActionController(ActionFlowController actionFlowController, Map<String, ActionFactory> actionFactories) {
        this.actionFlowController = actionFlowController;
        this.viewActionFactory = actionFactories.get("view");
    }

    @PostMapping("/view")
    public ResponseEntity<?> executeViewAction(@RequestParam(value = "path", required = false) String path) {
        String[] args;
        if (path == null || path.trim().isEmpty()) {
            args = new String[]{"view"};
        } else {
            args = new String[]{"view", path};
        }
        FileAction fileAction = viewActionFactory.createAction(args);


        if (fileAction instanceof ViewFileAction viewAction) {
            actionFlowController.handleAction(fileAction);
            List<String> fileList = viewAction.getFileList();
            return ResponseEntity.ok(fileList);
        } else {
            return ResponseEntity.status(500).body("Server error");
        }
    }
}
