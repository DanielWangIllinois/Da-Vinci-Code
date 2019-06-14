class Guess {
    String output;
    private Player playerGuessing;
    private Player playerGuessed;
    private boolean color;
    private boolean succeed;
    private String guessedNumber;
    private String position;
    private boolean lost;
    Guess(Player guessing, Player guessed, boolean colorboolean,
          String number, String position, boolean successboolean, boolean lost) {
        this.playerGuessing = guessing;
        this.playerGuessed = guessed;
        this.color = colorboolean;
        this.guessedNumber = number;
        this.position = position;
        this.succeed = successboolean;
        this.lost = lost;
    }
    public void createGuessOutput(int turn) {
        String color = "B";
        if (this.color == true) {
            color = "W";
        }
        if (lost == false) {
            if (succeed == true) {
                this.output = this.playerGuessing.name + " guessed " + this.playerGuessed.name + "'s card at "
                        + this.position + " position to be " + color + this.guessedNumber + " in turn #" + turn + " and succeed";
            } else {
                this.output = this.playerGuessing.name + " guessed " + this.playerGuessed.name + "'s card at "
                        + this.position + " position to be " + color + this.guessedNumber + " in turn #" + turn + " but failed";
            }
        } else {
            this.output = playerGuessing.name + "  is lost";
        }
    }
}
