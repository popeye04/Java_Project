import java.io.*;
import java.net.*;
import java.util.Scanner;

public class RepositoryClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nOptions:");
                System.out.println("1) Commit");
                System.out.println("2) Create Branch");
                System.out.println("3) Switch Branch");
                System.out.println("4) Show Branches");
                System.out.println("5) Show Commits");
                System.out.println("6) Exit");
                System.out.print("Select an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter commit message: ");
                        String message = scanner.nextLine();
                        sendCommand(out, in, "COMMIT", message);
                        break;
                    case 2:
                        System.out.print("Enter new branch name: ");
                        String branchName = scanner.nextLine();
                        sendCommand(out, in, "CREATE_BRANCH", branchName);
                        break;
                    case 3:
                        System.out.print("Enter branch name to switch to: ");
                        String branchToSwitch = scanner.nextLine();
                        sendCommand(out, in, "SWITCH_BRANCH", branchToSwitch);
                        break;
                    case 4:
                        sendCommand(out, in, "SHOW_BRANCHES", null);
                        break;
                    case 5:
                        sendCommand(out, in, "SHOW_COMMITS", null);
                        break;
                    case 6:
                        System.out.println("Exiting client.");
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

        private static void sendCommand(ObjectOutputStream out, ObjectInputStream in, String command, String data) throws IOException {
        try {
            out.writeObject(command);
            if (data != null) {
                out.writeObject(data);
            }
            out.flush();
            String response = (String) in.readObject();
            System.out.println(response);
        } catch (ClassNotFoundException e) {
            System.out.println("Error reading server response.");
        }
    }
}
