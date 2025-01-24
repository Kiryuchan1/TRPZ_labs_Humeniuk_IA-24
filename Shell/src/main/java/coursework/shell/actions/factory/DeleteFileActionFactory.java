package coursework.shell.actions.factory;

import coursework.shell.actions.DeleteFileAction;
import coursework.shell.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component("delete")
public class DeleteFileActionFactory extends AbstractActionFactory<DeleteFileAction> {

    private static final Logger logger = LogUtils.getLogger(DeleteFileActionFactory.class);

    public DeleteFileActionFactory() {
        super(new DeleteFileAction(""));
    }

    @Override
    protected void setActionParameters(DeleteFileAction action, String[] args) {
        action.setPath(args[1]);
    }

    @Override
    protected int getRequiredArgsLength() {
        return 2;
    }

    @Override
    protected void logCreation(DeleteFileAction action, String[] args){
        logger.info("Delete action created for path '{}'", args[1]);
    }
}
