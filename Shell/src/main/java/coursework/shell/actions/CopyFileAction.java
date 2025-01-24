package coursework.shell.actions;

import coursework.shell.actions.factory.CopyFileActionFactory;
import coursework.shell.utils.LogUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import java.nio.file.*;

@Getter
@Setter
public class CopyFileAction extends AbstractFileAction {
    private static final Logger logger = LogUtils.getLogger(CopyFileActionFactory.class);

    private String source;
    private String destination;

    public CopyFileAction(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    protected void doRun() {
        logger.info("Executing copyAction");
        try {
            Files.copy(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
            logger.info("copyAction success.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error ocured", e);
        }
    }

    @Override
    public FileAction clone() {
        logger.info("CopyFileAction object being cloned");
        return super.clone();
    }
}
