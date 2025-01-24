package coursework.shell.actions;

import coursework.shell.actions.factory.CopyFileActionFactory;
import coursework.shell.utils.LogUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
public class SearchFileAction extends AbstractFileAction {
    private static final Logger logger = LogUtils.getLogger(CopyFileActionFactory.class);

    private String query;
    private String path = ".";
    private List<String> results = new ArrayList<>();

    public SearchFileAction(String query) {
        this.query = query;
    }

    @Override
    public void doRun() {
        logger.info("Searching for file: '{}'", query);
        try {
            Path startPath = Paths.get(path).toAbsolutePath(); // Преобразуем в абсолютный путь

            if (!Files.exists(startPath) || !Files.isDirectory(startPath)) {
                logger.error("Missing path '{}'", path);
                return;
            }

            results = performSearch(query, startPath);
        } catch (IOException e) {
            logger.error("Error", e);
        }
    }

    private List<String> performSearch(String query, Path startPath) throws IOException {
        List<String> foundItems = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(startPath)) {
            paths.filter(path -> path.getFileName().toString().contains(query))
                    .forEach(path -> foundItems.add(path.toString()));
        }

        return foundItems;
    }


    @Override
    public FileAction clone() {
        logger.info("SearchFileAction object being cloned");
        return super.clone();
    }
}
