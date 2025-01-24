package coursework.shell.actions.factory;

import coursework.shell.actions.FileAction;
import coursework.shell.actions.ViewFileAction;
import coursework.shell.utils.ArgumentValidator;
import coursework.shell.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component("view")
public class ViewFileActionFactory implements ActionFactory {

    private static final Logger logger = LogUtils.getLogger(ViewFileActionFactory.class);

    @Override
    public FileAction createAction(String[] args) {
        ViewFileAction action;
        if (!ArgumentValidator.validateArgsLength(args, 2)) {
            action = new ViewFileAction();
            logger.info("Initializing ViewFileAction with default directory");
        } else {
            String directoryPath = args[1];
            action = new ViewFileAction(directoryPath);
            logger.info("Initializing ViewFileAction with directory path: {}", directoryPath);
        }
        return action;
    }
}
