package coursework.shell.actions;
import coursework.shell.actions.factory.CopyFileActionFactory;
import coursework.shell.utils.LogUtils;
import org.slf4j.Logger;
import java.io.FileNotFoundException;

public abstract class AbstractFileAction implements FileAction {
    private static final Logger logger = LogUtils.getLogger(CopyFileActionFactory.class);

    @Override
    public final void run() {
        try {
            preRun();
            doRun();
            afterRun();
        } catch (Exception e) {
            handleError(e);
        }
    }

    protected void preRun() {
        logger.info("Start of action {}", this.getClass().getSimpleName());
    }

    protected abstract void doRun();

    protected void afterRun() {
        logger.info("End of action {}", this.getClass().getSimpleName());
    }

    protected void handleError(Exception e) {
        if (e instanceof IllegalArgumentException) {
            logger.error("Incorect arguments: {}", e.getMessage());
        } else if (e instanceof FileNotFoundException) {
            logger.error("File not found: {}", e.getMessage());
        } else {
            logger.error("Unexpected failure during the execution of {}", this.getClass().getSimpleName(), e);
        }
    }

    @Override
    public FileAction clone() {
        try {
            return (FileAction) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Not supported", e);
        }
    }
}
