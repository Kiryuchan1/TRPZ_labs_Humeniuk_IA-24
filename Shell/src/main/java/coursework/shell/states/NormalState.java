package coursework.shell.states;

import coursework.shell.actions.*;
import org.springframework.stereotype.Component;


@Component
public class NormalState implements State {

    @Override
    public ActionStateType handle(FileAction fileAction) {
        if (fileAction instanceof ViewFileAction || fileAction instanceof SearchFileAction ||
                fileAction instanceof DeleteFileAction || fileAction instanceof MoveFileAction ||
                fileAction instanceof SwitchDriveAction) {

            fileAction.run();
            return ActionStateType.NORMAL;

        } else if (fileAction instanceof CopyFileAction) {
            return ActionStateType.COPY;

        } else {
            return ActionStateType.NORMAL;
        }
    }
}