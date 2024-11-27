import java.io.Serializable;

public class Branch implements Serializable {
    
    private String name;
    private Commit headCommit;

    
    public Branch(String name) {
        this.name = name;
    }

    
    public String getName() {
        return name;
    }

    
    public Commit getHeadCommit() {
        return headCommit;
    }

    
    public void addCommit(String message) {
        headCommit = new Commit(message, headCommit);
    }

    
    public void displayCommits() {
        Commit current = headCommit;
        while (current != null) {
            current.displayCommit();
            current = current.getPreviousCommit();
        }
    }
}
