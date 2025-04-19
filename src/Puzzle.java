public class Puzzle {
    private String area;
    private String puzzleId;
    private String puzzleRoomId;
    private String description;
    private String solution;
    private String completionMessage;
    private String failureMessage;
    private String requiredItem; //tbd
    private int damageOnFailure; //tbd
    private int attempts;
    private boolean puzzleSolved;

    public Puzzle(String area, String puzzleId, String puzzleRoomId, String description, String solution,
                  String completionMessage, String failureMessage, String requiredItem, int damageOnFailure, int attempts) {
        this.area = area;
        this.puzzleId = puzzleId;
        this.puzzleRoomId = puzzleRoomId;
        this.description = description;
        this.solution = solution;
        this.completionMessage = completionMessage;
        this.failureMessage = failureMessage;
        this.requiredItem = requiredItem;
        this.damageOnFailure = damageOnFailure;
        this.attempts = attempts;
        this.puzzleSolved = false;
    }

    public String getArea() {
        return area;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getPuzzleRoomId() {
        return puzzleRoomId;
    }

    public String getDescription() {
        return description;
    }

    public String getCompletionMessage() {
        return completionMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public String getRequiredItem() {
        return requiredItem;
    }

    public int getDamageOnFailure() {
        return damageOnFailure;
    }

    public boolean checkSolution(String input) {
        return input.equalsIgnoreCase(solution);
    }

    public boolean requiresItem() {
        return requiredItem != null && !requiredItem.isEmpty();
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public boolean isPuzzleSolved() {
        return puzzleSolved;
    }

    public void setPuzzleSolved(boolean puzzleSolved) {
        this.puzzleSolved = puzzleSolved;
    }
}