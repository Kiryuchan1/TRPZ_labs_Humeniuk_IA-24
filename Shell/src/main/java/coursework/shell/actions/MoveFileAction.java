package coursework.shell.actions;

import coursework.shell.actions.factory.CopyFileActionFactory;
import coursework.shell.utils.LogUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import java.io.IOException;
import java.nio.file.*;

@Getter
@Setter
public class MoveFileAction extends AbstractFileAction {
    private static final Logger logger = LogUtils.getLogger(CopyFileActionFactory.class);

    private String source;
    private String destination;

    public MoveFileAction(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public void doRun() {
        logger.info("Moving from '{}' to '{}'", source, destination);
        try {
            Files.move(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
            logger.info("Success.");
        } catch (IOException e) {
            logger.error("Error", e);
        }
    }

    @Override
    public FileAction clone() {
        logger.info("MoveFileAction object being cloned");
        return super.clone();
    }
}
