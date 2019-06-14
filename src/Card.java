class Card {
    boolean picked;
    double number;
    int realNumber;
    boolean colorWithWhiteTrueBlackFalse;
    boolean[] visible;
    String position = "";
    Card(int number, int realNumber, boolean white, boolean[] v, boolean p) {
        this.number = number;
        this.realNumber = realNumber;
        this.colorWithWhiteTrueBlackFalse = white;
        this.visible = v;
        this.picked = p;
    }
}
