import java.util.Scanner;

public class Main {
    
     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filename = "repository.dat";

        Repository repo = Repository.loadRepository(filename);
        if (repo == null) {
            System.out.print("Enter repository name: ");
            String repoName = scanner.nextLine();
            repo = new Repository(repoName);
            System.out.print("Enter a branch name to create (e.g., main): ");
            String branchName = scanner.nextLine();
            repo.createBranch(branchName);
        }

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1) Commit");
            System.out.println("2) Switch Branch");
            System.out.println("3) Create Branch");
            System.out.println("4) Display Branches");
            System.out.println("5) Show Commits");
            System.out.println("6) Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter commit message: ");
                    String message = scanner.nextLine();
                    repo.commit(message);
                    break;
                case 2:
                    System.out.print("Enter branch name to switch to: ");
                    String switchBranchName = scanner.nextLine();
                    repo.switchBranch(switchBranchName);
                    break;
                case 3:
                    System.out.print("Enter new branch name: ");
                    String newBranchName = scanner.nextLine();
                    repo.createBranch(newBranchName);
                    break;
                case 4:
                    repo.displayBranches();
                    break;
                case 5:
                    repo.displayActiveBranchCommits();
                    break;
                case 6:
                    // Save the repository to a file before exiting
                    repo.saveRepository(filename);
                    System.out.println("Exiting and saving repository...");
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }
    
}
