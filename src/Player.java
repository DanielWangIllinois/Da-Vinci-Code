import java.util.ArrayList;

class Player {
    String name = null;
    boolean win;
    boolean lose;
    boolean needProactiveInsert = false;
    ArrayList<Card> hand = new ArrayList<>();
    Player (boolean winOrNot, boolean lost) {
        this.win = winOrNot;
        this.lose = lost;
    }
}
