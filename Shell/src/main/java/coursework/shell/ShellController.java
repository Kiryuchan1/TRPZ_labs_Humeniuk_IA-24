package coursework.shell;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class ShellController {

    private final RestTemplate restTemplate = new RestTemplate();
    private Set<String> availableActions;

    public void startShell() {
        getAvailableActions();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Input the action to perform or 'exit' to stop the program: ");
            String commandLine = scanner.nextLine();
            processAction(commandLine);
        }
    }

    private void getAvailableActions() {
        try {
            ResponseEntity<Set> response = restTemplate.getForEntity("http://localhost:8080/actions", Set.class);
            this.availableActions = new HashSet<>((Collection<String>) response.getBody());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            this.availableActions = new HashSet<>(Arrays.asList("copy", "move", "delete", "search", "switch", "view"));
        }
        this.availableActions.add("exit");
    }

    private void processAction(String commandLine) {
        if (!commandLine.trim().isEmpty()) {
            String[] parts = commandLine.trim().split("\\s+");
            String userInput = parts[0].toLowerCase();

            if ("exit".equals(userInput)) {
                System.out.println("Program stopped");
                System.exit(0);
            }

            else if (!availableActions.contains(userInput)) {
                System.out.println("Unknown action: " + userInput);
            } else {
                try {
                    if (!"search".equals(userInput) && !"view".equals(userInput)) {
                        String response = restTemplate.postForObject("http://localhost:8080/command-line/execute", commandLine, String.class);
                        System.out.println(response);
                    } else {
                        handleDataReturning(userInput, parts);
                    }
                } catch (Exception e) {
                    System.out.println("Unexpected error: " + e.getMessage());
                }
            }
        }
    }

    private void handleDataReturning(String userInput, String[] parts) {
        try {
            String url;
            ResponseEntity<List> response;
            List<String> fileList;
            if ("search".equals(userInput)) {
                if (parts.length < 2) {
                    System.out.println("not enough arguments 'search'.");
                    return;
                }
                String query = parts[1];
                url = "http://localhost:8080/actions/search?query=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
                response = restTemplate.postForEntity(url, null, List.class);
                fileList = response.getBody();
                if (fileList != null && !fileList.isEmpty()) {
                    System.out.println("Searching results:");
                    for (String fileName : fileList) {
                        System.out.println(" - " + fileName);
                    }
                } else {
                    System.out.println("No mathes.");
                }
            } else if ("view".equals(userInput)) {
                url = "http://localhost:8080/actions/view";
                if (parts.length >= 2) {
                    String path = parts[1];
                    url = url + "?path=" + URLEncoder.encode(path, StandardCharsets.UTF_8);
                }

                response = restTemplate.postForEntity(url, null, List.class);
                fileList = response.getBody();
                if (fileList != null && !fileList.isEmpty()) {
                    System.out.println("Current directory:");
                    for (String fileName : fileList) {
                        System.out.println(" - " + fileName);
                    }
                } else {
                    System.out.println("Current directory is empty.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error '" + userInput + "': " + e.getMessage());
        }
    }
}