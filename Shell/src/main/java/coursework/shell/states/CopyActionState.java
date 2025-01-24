package coursework.shell.states;

import coursework.shell.actions.FileAction;
import coursework.shell.actions.CopyFileAction;
import org.springframework.stereotype.Component;

@Component
public class CopyActionState implements State {

    @Override
    public ActionStateType handle(FileAction fileAction) {
        if (fileAction instanceof CopyFileAction) {
            fileAction.run();
            return ActionStateType.NORMAL;

        } else {
            return ActionStateType.COPY;
        }
    }
}
