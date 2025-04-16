public class Puzzle {
    private int puzzleId;
    private String puzzleQuestion;
    private String puzzleAnswer;
    private String puzzlePassMsg;
    private String puzzleFailMsg;
    private int puzzleAttempts;
    private boolean puzzleSolved;

    public Puzzle(int puzzleId, String puzzleQuestion, String puzzleAnswer, String puzzlePassMsg, String puzzleFailMsg,
                  int PuzzleAttempts) {
        this.puzzleId = puzzleId;
        this.puzzleQuestion = puzzleQuestion;
        this.puzzleAnswer = puzzleAnswer;
        this.puzzlePassMsg = puzzlePassMsg;
        this.puzzleFailMsg = puzzleFailMsg;
        this.puzzleAttempts = PuzzleAttempts;
        this.puzzleSolved = false;
    }

    public int getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(int puzzleId) {
        this.puzzleId = puzzleId;
    }

    public String getPuzzleQuestion() {
        return puzzleQuestion;
    }

    public void setPuzzleQuestion(String puzzleQuestion) {
        this.puzzleQuestion = puzzleQuestion;
    }

    public String getPuzzleAnswer() {
        return puzzleAnswer;
    }

    public void setPuzzleAnswer(String puzzleAnswer) {
        this.puzzleAnswer = puzzleAnswer;
    }

    public String getPuzzlePassMsg() {
        return puzzlePassMsg;
    }

    public void setPuzzlePassMsg(String puzzlePassMsg) {
        this.puzzlePassMsg = puzzlePassMsg;
    }

    public String getPuzzleFailMsg() {
        return puzzleFailMsg;
    }

    public void setPuzzleFailMsg(String puzzleFailMsg) {
        this.puzzleFailMsg = puzzleFailMsg;
    }

    public int getPuzzleAttempts() {
        return puzzleAttempts;
    }

    public void setPuzzleAttempts(int puzzleAttempts) {
        this.puzzleAttempts = puzzleAttempts;
    }

    public boolean isPuzzleSolved() {
        return puzzleSolved;
    }

    public void setPuzzleSolved(boolean puzzleSolved) {
        this.puzzleSolved = puzzleSolved;
    }
}
