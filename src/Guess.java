public class Guess {
    String output;
    Player playerguessing;
    Player playerguessed;
    Card cardguessed;
    boolean color;
    boolean succeed;
    int guessednumber;
    String position;
    boolean lost;
    Guess(Player guessing, Player guessed, Card inputcard,
          boolean colorboolean, int number, String position, boolean successboolean, boolean lost) {
        this.playerguessing = guessing;
        this.playerguessed = guessed;
        this.cardguessed = inputcard;
        this.color = colorboolean;
        this.guessednumber = number;
        this.position = position;
        this.succeed = successboolean;
        this.lost = lost;
    }
    public void createguessoutput(int turn) {
        String color = "B";
        if (this.color == true) {
            color = "W";
        }
        if (lost == false) {
            if (succeed == true) {
                this.output = this.playerguessing.name + " guessed " + this.playerguessed.name + "'s card at "
                        + this.position + " position to be " + color + this.guessednumber + " in turn #" + turn + " and succeed";
            } else {
                this.output = this.playerguessing.name + " guessed " + this.playerguessed.name + "'s card at "
                        + this.position + " position to be " + color + this.guessednumber + " in turn #" + turn + " but failed";
            }
        } else {
            this.output = playerguessing.name + "  is lost";
        }
    }
}
