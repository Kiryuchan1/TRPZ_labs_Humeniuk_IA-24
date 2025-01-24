package coursework.shell;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class ShellApplication implements CommandLineRunner {

    private final ShellController shellController;

    @Autowired
    public ShellApplication(ShellController shellController) {
        this.shellController = shellController;
    }

    public static void main(String[] args) {
        SpringApplication.run(ShellApplication.class, args);
    }

    @Override
    public void run(String... args) {
        shellController.startShell();
    }
}