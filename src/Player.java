import java.util.ArrayList;

class Player {

    String name = "N/A";
    boolean win = false;
    boolean lose = false;
    // TODO Joker
    boolean needProactiveInsert = false;
    ArrayList<Card> hand = new ArrayList<>();
}
