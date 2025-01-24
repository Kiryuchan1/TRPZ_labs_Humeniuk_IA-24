package coursework.shell.actions;

import lombok.Getter;
import lombok.Setter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ViewFileAction extends AbstractFileAction {
    private String directoryPath;
    private List<String> fileList;

    public ViewFileAction() {
        this.directoryPath = Paths.get("").toAbsolutePath().toString();
    }

    public ViewFileAction(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    protected void doRun() {
        Path toDirPath = Paths.get(directoryPath);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(toDirPath)) {
            fileList = new ArrayList<>();
            for (Path path : stream) {
                fileList.add(path.getFileName().toString());
            }
        } catch (IOException e) {
            try {
                throw new Exception("Issue encountered while listing directory contents: " + e.getMessage(), e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
