package coursework.shell.actions;

import coursework.shell.actions.factory.CopyFileActionFactory;
import coursework.shell.utils.LogUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import java.nio.file.*;

@Getter
@Setter
public class DeleteFileAction extends AbstractFileAction {
    private static final Logger logger = LogUtils.getLogger(CopyFileActionFactory.class);

    private String path;

    public DeleteFileAction(String path) {
        this.path = path;
    }

    @Override
    protected void doRun() {
        logger.info("deleting '{}'", path);
        try {
            Files.deleteIfExists(Paths.get(path));
            logger.info("file '{}' deleted", path);
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }

    @Override
    public FileAction clone() {
        logger.info("DeleteFileAction object being cloned");
        return super.clone();

    }
}
