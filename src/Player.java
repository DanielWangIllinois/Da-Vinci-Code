import java.util.ArrayList;

public class Player {
    String name = null;
    boolean win;
    boolean lose;
    ArrayList<Card> hand = new ArrayList<>();
    Player (boolean winornot, boolean lost) {
        this.win = winornot;
        this.lose = lost;
    }
}
