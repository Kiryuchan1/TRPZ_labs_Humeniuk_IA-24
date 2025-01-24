package coursework.shell.actions.factory;

import coursework.shell.actions.CopyFileAction;
import coursework.shell.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component("copy")
public class CopyFileActionFactory extends AbstractActionFactory<CopyFileAction> {

    private static final Logger logger = LogUtils.getLogger(CopyFileActionFactory.class);

    public CopyFileActionFactory() {
        super(new CopyFileAction("", ""));
    }

    @Override
    protected void setActionParameters(CopyFileAction action, String[] args) {
        action.setSource(args[1]);
        action.setDestination(args[2]);
    }

    @Override
    protected int getRequiredArgsLength() {
        return 3;
    }

    @Override
    protected void logCreation(CopyFileAction action, String[] args){
        logger.info("Copy action created with source '{}' and destination '{}'", args[1], args[2]);
    }
}
