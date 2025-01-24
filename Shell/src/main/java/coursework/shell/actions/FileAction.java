package coursework.shell.actions;

public interface FileAction extends Cloneable {
    void run();
    FileAction clone();
}
