package coursework.shell.actions.factory;

import coursework.shell.actions.SwitchDriveAction;
import coursework.shell.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component("switch")
public class SwitchDriveActionFactory extends AbstractActionFactory<SwitchDriveAction> {

    private static final Logger logger = LogUtils.getLogger(SwitchDriveActionFactory.class);

    public SwitchDriveActionFactory() {
        super(new SwitchDriveAction(""));
    }

    @Override
    protected void setActionParameters(SwitchDriveAction action, String[] args) {
        action.setDriveName(args[1]);
    }

    @Override
    protected int getRequiredArgsLength() {
        return 2;
    }

    @Override
    protected void logCreation(SwitchDriveAction action, String[] args){
        logger.info("Switch drive action created for drive '{}'", args[1]);
    }
}
