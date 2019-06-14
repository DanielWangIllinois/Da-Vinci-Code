class Guess {
    String output;
    private Player playerGuessing;
    private Player playerGuessed;
    private boolean color;
    private boolean succeed;
    private String guessedNumber;
    private String position;
    private boolean lost;
    Guess(Player guessing, Player guessed, boolean colorBoolean,
          String number, String position, boolean successBoolean, boolean lost) {
        this.playerGuessing = guessing;
        this.playerGuessed = guessed;
        this.color = colorBoolean;
        this.guessedNumber = number;
        this.position = position;
        this.succeed = successBoolean;
        this.lost = lost;
    }
    void createGuessOutput(int turn) {
        // if this.color == true, then color = "W"
        String color = this.color?"W":"B";
        if (lost) {
            this.output = playerGuessing.name + "  is lost";
        } else {
            this.output = this.playerGuessing.name + " guessed " + this.playerGuessed.name + "'s card at "
                    + this.position + " position to be " + color + this.guessedNumber + " in turn #" + turn
                    + " " + (succeed?"and succeed":"but failed");
        }
    }
}
