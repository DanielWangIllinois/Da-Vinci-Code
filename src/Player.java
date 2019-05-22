import java.util.ArrayList;
import java.util.List;

public class Player {
    String name = null;
    boolean win;
    boolean lose;
    ArrayList<Card> hand = new ArrayList<>();
    Player (boolean winornot, boolean alive) {
        this.win = winornot;
        this.lose = alive;
    }
}
