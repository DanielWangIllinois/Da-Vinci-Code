public class Card {
    boolean picked;
    int number;
    boolean colorwithwhitetrueblackfalse;
    boolean[] visible;
    Card(int inputint, boolean inputcolor, boolean[] v, boolean p) {
        this.number = inputint;
        this.colorwithwhitetrueblackfalse = inputcolor;
        this.visible = v;
        this.picked = p;
    }
}
