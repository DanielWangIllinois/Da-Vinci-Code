class Card {
    boolean picked;
    double number;
    int realNumber;
    boolean colorWithWhiteTrueBlackFalse;
    boolean[] visible;
    String position = null;
    Card(int number, int realNumber, boolean inputColor, boolean[] v, boolean p) {
        this.number = number;
        this.realNumber = realNumber;
        this.colorWithWhiteTrueBlackFalse = inputColor;
        this.visible = v;
        this.picked = p;
    }
}
