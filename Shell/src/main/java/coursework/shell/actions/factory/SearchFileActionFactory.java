package coursework.shell.actions.factory;

import coursework.shell.actions.SearchFileAction;
import coursework.shell.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component("search")
public class SearchFileActionFactory extends AbstractActionFactory<SearchFileAction> {

    private static final Logger logger = LogUtils.getLogger(SearchFileActionFactory.class);

    public SearchFileActionFactory() {
        super(new SearchFileAction(""));
    }


    @Override
    protected void setActionParameters(SearchFileAction action, String[] args) {
        action.setQuery(args[1]);
    }

    @Override
    protected int getRequiredArgsLength() {
        return 2;
    }

    @Override
    protected void logCreation(SearchFileAction action, String[] args){
        logger.info("Search action created with query '{}'", args[1]);
    }
}
