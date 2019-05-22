import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    static Scanner scan = new Scanner(System.in);
    public static Random rand = new Random();
    static int numberOfPlayer;
    static boolean started = false;
    static boolean ended = false;
    static Player playerone = new Player (false, true);
    static Player playertwo = new Player (false, true);
    static Player playerthree = new Player (false, true);
    static Player playerfour = new Player (false, true);
    static Player[] playerarray = {playerone, playertwo, playerthree, playerfour};
    static Card[][] cardpool;
    static int cardpoolsize;
    static int whitecardpoolsize;
    static int blackcardpoolsize;
    static int numberofturn = 1;

    public static void main(String [] args) {
        cardpool = newcardpool();
        cardpoolsize = getpoolsize();
        whitecardpoolsize = countwhitepoolsize();
        blackcardpoolsize = countblackpoolsize();
        for (int i = 1; i <= 100; i++) {
            shufflecardpool(cardpool);
        }
        while (started == false) {
            start();
        }
        getinitialcard();
        while (ended == false) {
            for (int i = 0; i < numberOfPlayer; i++) {
                turn(playerarray[i]);
                if (ended == true) {
                    break;
                }
            }
        }
    }

    public static Card[][] newcardpool() {
        Card[][] cardpooltoreturn = new Card[2][12];
        for (int i = 0; i < 12; i++) {
            boolean[] visible = {false, false, false,false};
            Card card = new Card(i,true,visible,false);
            cardpooltoreturn[0][i] = card;
        }
        for (int i = 0; i < 12; i++) {
            boolean[] visible = {false, false, false,false};
            Card card = new Card(i,false,visible,false);
            cardpooltoreturn[1][i] = card;
        }
        return cardpooltoreturn;
    }

    public static int getpoolsize() {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 12; j++) {
                if (cardpool[i][j].picked == false) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int countwhitepoolsize() {
        int count = 0;
        for (int j = 0; j < 12; j++) {
            if (cardpool[0][j].picked == false) {
                count++;
            }
        }
        return count;
    }

    public static int countblackpoolsize() {
        int count = 0;
        for (int j = 0; j < 12; j++) {
            if (cardpool[1][j].picked == false) {
                count++;
            }
        }
        return count;
    }

    public static void shufflecardpool(Card[][] input) {
        Card[] whitecard = new Card[12];
        Card[] blackcard = new Card[12];
        for (int i = 0; i < 12; i++) {
            whitecard[i] = input[0][i];
            blackcard[i] = input[1][i];
        }
        for (int i = 12; i > 0; i--) {
            int randint = rand.nextInt(i);
            swap(whitecard, randint, i - 1);
        }
        for (int i = 12; i > 0; i--) {
            int randint = rand.nextInt(i);
            swap(blackcard, randint, i - 1);
        }
        for (int i = 0; i < 12; i++) {
            input[0][i] = whitecard[i];
            input[1][i] = blackcard[i];
        }
    }

    public static void swap (Card[] inputarray, int i, int j) {
        Card tmpcard = inputarray[i];
        inputarray[i] = inputarray[j];
        inputarray[j] = tmpcard;
    }

    public void Changewinner (Player player) {
        player.win = true;
        ended = true;
    }

    public static boolean start() {
        System.out.println("Welcome to The Da Vinci Code" + "\n");
        System.out.println("How many people is playing this game?");
        numberOfPlayer = scan.nextInt();
        if (numberOfPlayer >= 5) {
            System.out.println("Sorry, this game could not have more than 4 players" + "\n");
            return false;
        } else if (numberOfPlayer <= 0) {
            System.out.println("Sorry, this game could not have less than 1 player" + "\n");
            return false;
        }
        System.out.println("Warmest welcome for all of our " + numberOfPlayer + " players");
        for (int i = 1; i <= numberOfPlayer; i++) {
            System.out.println("What's the name of #" + i + " player?");
            String tmpStringForNameInput = scan.next();
            playerone.name = playertwo.name;
            playertwo.name = playerthree.name;
            playerthree.name = playerfour.name;
            playerfour.name = tmpStringForNameInput;
        }
        while (playerone.name == null) {
            String tmpStringForNameSwap = playerone.name;
            playerone.name = playertwo.name;
            playertwo.name = playerthree.name;
            playerthree.name = playerfour.name;
            playerfour.name = tmpStringForNameSwap;
        }
        for (int i = numberOfPlayer; i < 4; i++) {
            playerarray[i].lose = true;
        }
        started = true;
        return true;
    }

    public static boolean getinitialcard() {
        System.out.println("\n");
        for (int i = 0; i < numberOfPlayer; i++) {
            System.out.println("Now welcome  " + playerarray[i].name + "  to pick his initial card");
            for (int j = 1; j <= 4; j++) {
                System.out.println("What color do you want for your #" + j + " card?");
                int color = getavailablecolor();
                pickcardtoplayer(playerarray[i], color);
            }
            showhand(playerarray[i]);
        }
        return true;
    }

    public static int getavailablecolor() {
        if (pickwhite() == false) {
            if (pickblack() == false) {
                System.out.println("The cardpool is out of card");
                System.out.println("Please type c and enter to continue");
                String useless = scan.next();
                return -1;
            } else {
                System.out.println("There's only black card left in the cardpool");
                System.out.println("Automatically picked black card");
                System.out.println("Please type c and enter to continue");
                String useless = scan.next();
                return 1;
            }
        } else {
            if (pickblack() == false) {
                System.out.println("There's only white card left in the cardpool");
                System.out.println("Automatically picked white card");
                System.out.println("Please type c and enter continue");
                String useless = scan.next();
                return 0;
            } else {
                System.out.println("0 for white and 1 for black");
                int color = scan.nextInt();
                return color;
            }
        }
    }

    public static Card pickcardtoplayer(Player player, int color) {
        if (color <= 0) {
            Card toreturn = cardpool[0][12 - whitecardpoolsize];
            toreturn.picked = true;
            puttohand(player, toreturn);
            cardpoolsize--;
            whitecardpoolsize--;
            if (checkcardpoolsize() == false) {
                System.out.println("Bug appeared");
                return null;
            }
            return toreturn;
        } else {
            Card toreturn = cardpool[1][12 - blackcardpoolsize];
            toreturn.picked = true;
            puttohand(player, toreturn);
            cardpoolsize--;
            blackcardpoolsize--;
            if (checkcardpoolsize() == false) {
                System.out.println("Bug appeared");
                return null;
            }
            return toreturn;
        }
    }

    public static boolean puttohand(Player player, Card card) {
        player.hand.add(card);
        Collections.sort(player.hand, new Comparator<Card>(){
            public int compare(Card x, Card y) {
                if(x.number > y.number){
                    return 1;
                }
                if(x.number < y.number){
                    return -1;
                }
                if(x.colorwithwhitetrueblackfalse == false) {
                    return 1;
                }
                return -1;
            }
        });
        card.visible[findplayerindex(player)] = true;
        return true;
    }

    public static boolean showhand(Player player) {
        System.out.println("Your cards are:");
        for (int i = 0; i < player.hand.size(); i++) {
            if (player.hand.get(i).colorwithwhitetrueblackfalse == true) {
                System.out.print("W");
            } else {
                System.out.print("B");
            }
            System.out.print(player.hand.get(i).number + " ");
        }
        System.out.println("\n");
        return true;
    }

    public static boolean checkcardpoolsize() {
        if (getpoolsize() == cardpoolsize) {
            if (countwhitepoolsize() == whitecardpoolsize && countblackpoolsize() == blackcardpoolsize) {
                return true;
            }
        }
        return false;
    }

    public static int findplayerindex(Player player) {
        int playerindex = -1;
        for (int i = 0; i < numberOfPlayer; i++) {
            if (player.equals(playerarray[i])) {
                playerindex = i;
            }
        }
        return playerindex;
    }

    public static boolean turn(Player player) {
        System.out.println("\n");
        System.out.println("#" + numberofturn + " turn:");
        Card currentcard = pickcardinturn(player);
        showboard(player);
        showhand(player);
        guess(player);
        numberofturn++;
        return true;
    }

    public static Card pickcardinturn(Player player) {
        System.out.println("\n");
        System.out.println("Welcome  " + playerarray[findplayerindex(player)].name + "  to pick a card");
        int color = getavailablecolor();
        if (color >= 0) {
            Card cardpicked = pickcardtoplayer(player, color);
            return cardpicked;
        } else {
            return null;
        }
    }

    public static boolean showboard(Player player) {
        System.out.println("\n");
        for (int i = 0; i < numberOfPlayer; i++) {
            if (playerarray[i].equals(player)) {
                continue;
            } else {
                System.out.println(playerarray[i].name + "'s card:");
                for (int j = 0; j < playerarray[i].hand.size(); j++) {
                    if (playerarray[i].hand.get(j).colorwithwhitetrueblackfalse == true) {
                        System.out.print("W");
                    } else {
                        System.out.print("B");
                    }
                    if (playerarray[i].hand.get(j).visible[findplayerindex(player)] == true) {
                        System.out.print(playerarray[i].hand.get(j).number + " ");
                    } else {
                        System.out.print("x ");
                    }
                }
                System.out.print("\n");
            }
        }
        return true;
    }

    public static boolean guess(Player player) {
        return true;
    }

    public static boolean pickwhite() {
        if (countwhitepoolsize() <= 0) {
            return false;
        }
        return true;
    }

    public static boolean pickblack() {
        if (countblackpoolsize() <= 0) {
            return false;
        }
        return true;
    }
}
