
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
