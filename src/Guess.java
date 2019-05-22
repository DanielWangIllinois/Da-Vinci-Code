public class Guess {
    String output;
    Player playerguessing;
    Player playerguessed;
    Card cardguessed;
    boolean succeed;
    int guessednumber;
    String position;
    Guess(Player guessing, Player guessed, Card inputcard, boolean inputboolean, int number, String position) {
        this.playerguessing = guessing;
        this.playerguessed = guessed;
        this.cardguessed = inputcard;
        this.succeed = inputboolean;
        this.guessednumber = number;
        this.position = position;
    }
    public void createguessoutput(int turn) {
        if (succeed == true) {
            this.output = this.playerguessing.name + " guessed " + this.playerguessed.name + "'s card at "
                    + this.position + " position to be " + this.guessednumber + " and succeed";
        } else {
            this.output = this.playerguessing.name + " guessed " + this.playerguessed.name + "'s card at "
                    + this.position + " position to be " + this.guessednumber + " but failed";
        }
    }
}
