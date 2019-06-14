import java.util.ArrayList;

class Player {

    String name = "";
    boolean win = false;
    boolean lose = false;
    // TODO Joker
    boolean needProactiveInsert = false;
    ArrayList<Card> hand = new ArrayList<>();
}
