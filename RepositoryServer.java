import java.io.*;
import java.net.*;
import java.util.concurrent.Executors;

public class RepositoryServer {
    private static final int PORT = 12345;
    private static final String REPO_FILE = "repository.dat";
    private Repository repository;

    public RepositoryServer() {
        repository = Repository.loadRepository(REPO_FILE);
        if (repository == null) {
            repository = new Repository("DefaultRepo");
            repository.createBranch("main");
        }
    }

    public void start() {
        System.out.println("Starting Repository Server...");
        var pool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");
                pool.execute(new ClientHandler(clientSocket, repository));
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        } finally {
            repository.saveRepository(REPO_FILE);
        }
    }

    public static void main(String[] args) {
        new RepositoryServer().start();
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private Repository repository;

    public ClientHandler(Socket socket, Repository repository) {
        this.socket = socket;
        this.repository = repository;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            while (true) {
                String command = (String) in.readObject();
                String response = handleCommand(command, in);
                out.writeObject(response);
                out.flush();
            }
        } catch (Exception e) {
            System.out.println("Client disconnected.");
        }
    }

    private String handleCommand(String command, ObjectInputStream in) throws IOException, ClassNotFoundException {
        switch (command) {
            case "COMMIT":
                String message = (String) in.readObject();
                repository.commit(message);
                return "Commit added: " + message;
            case "CREATE_BRANCH":
                String branchName = (String) in.readObject();
                repository.createBranch(branchName);
                return "Branch created: " + branchName;
            case "SWITCH_BRANCH":
                String branchToSwitch = (String) in.readObject();
                repository.switchBranch(branchToSwitch);
                return "Switched to branch: " + branchToSwitch;
            case "SHOW_BRANCHES":
                return repository.getBranchNames();
            case "SHOW_COMMITS":
                return repository.getCommitsForActiveBranch();
            default:
                return "Unknown command.";
        }
    }
}

