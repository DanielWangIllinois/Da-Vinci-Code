public class Card {
    boolean picked;
    double number;
    int realnumber;
    boolean colorwithwhitetrueblackfalse;
    boolean[] visible;
    String position = null;
    Card(int number, int realnumber, boolean inputcolor, boolean[] v, boolean p) {
        this.number = number;
        this.realnumber = realnumber;
        this.colorwithwhitetrueblackfalse = inputcolor;
        this.visible = v;
        this.picked = p;
    }
}
