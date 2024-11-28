
import java.io.*;
import java.util.ArrayList; 

public class Repository implements Serializable {
    private String name;
    private ArrayList<Branch> branches;
    private Branch activeBranch; 

    public Repository(String name) {
        this.name = name;
        branches = new ArrayList<>();
    } 

    public void createBranch(String branchName) {
        Branch branch = new Branch(branchName);
        branches.add(branch);
        if (activeBranch == null) {
            activeBranch = branch;
        }
    } 

    public void switchBranch(String branchName) {
        for (Branch branch : branches) {
            if (branch.getName().equals(branchName)) {
                activeBranch = branch;
                System.out.println("Switched to branch: " + branchName);
                return;
            }
        }
        System.out.println("Branch not found.");
    }
    public void commit(String message) {
        if (activeBranch != null) {
            activeBranch.addCommit(message);
            System.out.println("Committed: " + message);
        } else {
            System.out.println("No active branch to commit.");
        }
    }

    public void displayBranches() {
        System.out.println("Branches in repository:");
        for (Branch branch : branches) {
            System.out.println("- " + branch.getName());
        }
    }

    public void displayActiveBranchCommits() {
        if (activeBranch != null) {
            System.out.println("Commits in branch " + activeBranch.getName() + ":");
            activeBranch.displayCommits();
        } else {
            System.out.println("No active branch.");
        }
    }
   
    public void saveRepository(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
            System.out.println("Repository saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving repository: " + e.getMessage());
        }
    }

    public static Repository loadRepository(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Repository) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading repository: " + e.getMessage());
            return null;
        }
    }
}
