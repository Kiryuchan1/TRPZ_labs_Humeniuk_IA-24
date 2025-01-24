package coursework.shell.actions.factory;

import coursework.shell.actions.MoveFileAction;
import coursework.shell.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component("move")
public class MoveFileActionFactory extends AbstractActionFactory<MoveFileAction> {

    private static final Logger logger = LogUtils.getLogger(MoveFileActionFactory.class);

    public MoveFileActionFactory() {
        super(new MoveFileAction("", ""));
    }


    @Override
    protected void setActionParameters(MoveFileAction action, String[] args) {
        action.setSource(args[1]);
        action.setDestination(args[2]);
    }

    @Override
    protected int getRequiredArgsLength() {
        return 3;
    }

    @Override
    protected void logCreation(MoveFileAction action, String[] args){
        logger.info("Move action created from '{}' to '{}'", args[1], args[2]);
    }
}
