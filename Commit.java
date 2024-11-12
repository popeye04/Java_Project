import java.io.Serializable;

public class Commit implements Serializable{
    
    private String message;
    private Commit previousCommit;

    public Commit(String message, Commit previousCommit) {
        this.message = message;
        this.previousCommit = previousCommit;
    }

    public String getMessage() { //getter method
        return message;
    }

    public Commit getPreviousCommit() {
        return previousCommit;
    }

    public void displayCommit() {
        System.out.println("Commit: " +message);
    }
    
    
}
