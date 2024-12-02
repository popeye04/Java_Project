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

    public String getBranchNames() {
        StringBuilder sb = new StringBuilder("Branches in repository:\n");
        for (Branch branch : branches) {
            sb.append("- ").append(branch.getName()).append("\n");
        }
        return sb.toString();
    }

    public String getCommitsForActiveBranch() {
        if (activeBranch != null) {
            StringBuilder sb = new StringBuilder("Commits in branch " + activeBranch.getName() + ":\n");
            Commit current = activeBranch.getHeadCommit();
            while (current != null) {
                sb.append(current.getMessage()).append("\n");
                current = current.getPreviousCommit();
            }
            return sb.toString();
        }
        return "No active branch.";
    }

    public void saveRepository(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
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
