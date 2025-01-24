package coursework.shell.actions;

import coursework.shell.actions.factory.CopyFileActionFactory;
import coursework.shell.utils.LogUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
public class SwitchDriveAction extends AbstractFileAction {
    private static final Logger logger = LogUtils.getLogger(CopyFileActionFactory.class);

    private String driveName;

    public SwitchDriveAction(String driveName) {
        this.driveName = driveName;
    }

    @Override
    public void doRun() {
        logger.info("Switching to drive: '{}'", driveName);
        Path newPath = Paths.get(driveName + ":\\");
        if (newPath.toFile().exists()) {
            System.setProperty("user.dir", newPath.toAbsolutePath().toString());
            logger.info("Drive switched.");
        } else {
            logger.error("Missing drive '{}'.", driveName);
        }
    }

    @Override
    public FileAction clone() {
        logger.info("SwitchDriveAction object being cloned");
        return super.clone();
    }
}
