import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static Random rand = new Random();
    static int numberOfPlayer;
    static boolean started = false;
    static boolean ended = false;
    static Player playerone = new Player (false, false);
    static Player playertwo = new Player (false, false);
    static Player playerthree = new Player (false, false);
    static Player playerfour = new Player (false, false);
    static Player[] playerarray = {playerone, playertwo, playerthree, playerfour};
    static Card[][] cardpool;
    static int cardpoolsize;
    static int whitecardpoolsize;
    static int blackcardpoolsize;
    static int numberofturn = 1;
    static ArrayList<Guess> history = new ArrayList<>();

    public static void main(String [] args) {
        cardpool = newcardpool();
        cardpoolsize = getpoolsize();
        whitecardpoolsize = countwhitepoolsize();
        blackcardpoolsize = countblackpoolsize();
        for (int i = 1; i <= 10; i++) {
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
        Card[][] cardpooltoreturn = new Card[2][13];
        for (int i = -1; i < 12; i++) {
            boolean[] visible = {false, false, false,false};
            Card card = new Card(i, i,true,visible,false);
            cardpooltoreturn[0][i + 1] = card;
        }
        for (int i = -1; i < 12; i++) {
            boolean[] visible = {false, false, false,false};
            Card card = new Card(i, i,false,visible,false);
            cardpooltoreturn[1][i + 1] = card;
        }
        return cardpooltoreturn;
    }

    public static int getpoolsize() {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 13; j++) {
                if (cardpool[i][j].picked == false) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int countwhitepoolsize() {
        int count = 0;
        for (int j = 0; j < 13; j++) {
            if (cardpool[0][j].picked == false) {
                count++;
            }
        }
        return count;
    }

    public static int countblackpoolsize() {
        int count = 0;
        for (int j = 0; j < 13; j++) {
            if (cardpool[1][j].picked == false) {
                count++;
            }
        }
        return count;
    }

    public static void shufflecardpool(Card[][] input) {
        Card[] whitecard = new Card[13];
        Card[] blackcard = new Card[13];
        for (int i = 0; i < 13; i++) {
            whitecard[i] = input[0][i];
            blackcard[i] = input[1][i];
        }
        for (int i = 13; i > 0; i--) {
            int randint = rand.nextInt(i);
            swap(whitecard, randint, i - 1);
        }
        for (int i = 13; i > 0; i--) {
            int randint = rand.nextInt(i);
            swap(blackcard, randint, i - 1);
        }
        for (int i = 0; i < 13; i++) {
            input[0][i] = whitecard[i];
            input[1][i] = blackcard[i];
        }
    }

    public static void swap (Card[] inputarray, int i, int j) {
        Card tmpcard = inputarray[i];
        inputarray[i] = inputarray[j];
        inputarray[j] = tmpcard;
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
            System.out.println("\n");
            System.out.println("Now welcome  " + playerarray[i].name + "  to pick his initial card");
            for (int j = 1; j <= 4; j++) {
                System.out.println("What color do you want for your #" + j + " card?");
                int color = getavailablecolor();
                if (color != -1) {
                    pickcardtoplayer(playerarray[i], color);
                }
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
                if (color > 1) {
                    color = 1;
                }
                if (color < 0) {
                    color = 0;
                }
                return color;
            }
        }
    }

    public static Card pickcardtoplayer(Player player, int color) {
        if (color <= 0) {
            Card toreturn = cardpool[0][13 - whitecardpoolsize];
            toreturn.picked = true;
            createpositionbeforepick(player, toreturn);
            if (toreturn.realnumber == -1) {
                player.needproactiveinsert = true;
                changecardnumber(player, toreturn);
            } else if (player.needproactiveinsert == true) {
                insertcard(player, toreturn);
            }
            puttohand(player, toreturn);
            cardpoolsize--;
            whitecardpoolsize--;
            if (checkcardpoolsize() == false) {
                System.out.println("Bug appeared");
                return null;
            }
            return toreturn;
        } else {
            Card toreturn = cardpool[1][13 - blackcardpoolsize];
            toreturn.picked = true;
            createpositionbeforepick(player, toreturn);
            if (toreturn.realnumber == -1) {
                player.needproactiveinsert = true;
                changecardnumber(player, toreturn);
            } else if (player.needproactiveinsert == true) {
                insertcard(player, toreturn);
            }
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

    public static void insertcard(Player player, Card card) {
        System.out.print("Been here!" + "\n");
        return;
    }

    public static void changecardnumber(Player player, Card card) {
        if (player.hand.size() == 0) {
            return;
        } else {
            String leftposition = null;
            String rightposition = null;
            double leftindex;
            double rightindex;
            boolean nextto = false;
            while (nextto == false) {
                showhand(player);
                System.out.println("Where do you want to put your hyphen?");
                System.out.println("Note that you could use L for the position left of the smallest card and R for the position right of the biggest card");
                System.out.println("Please enter the position of the card that you wish to be the left of the hyphen");
                leftposition = scan.next();
                System.out.println("Please enter the position of the card that you wish to be the right of the hyphen");
                rightposition = scan.next();
                nextto = checkifnextto(leftposition, rightposition, player);
            }
            if (leftposition.equals("L")) {
                leftindex = -1;
            } else {
                leftindex = findcardbyposition(leftposition).number;
            }
            if (rightposition.equals("R")) {
                rightindex = 12;
            } else {
                rightindex = findcardbyposition(rightposition).number;
            }
            card.number  = (leftindex + rightindex) / 2;
            if (leftindex == rightindex) {
                for (int i = 0; i < player.hand.size(); i++) {
                    if (player.hand.get(i).equals(card)) {
                        player.hand.get(i - 1).number = card.number - 0.1;
                        player.hand.get(i + 1).number = card.number + 0.1;
                    }
                }
            }
            return;
        }
    }

    public static boolean checkifnextto(String leftposition, String rightposition, Player player) {
        boolean leftisposition = false;
        boolean rightisposition = false;
        int leftpositionindex = -100;
        int rightpositionindex = -100;
        if (leftposition.equals("L")) {
            leftisposition = true;
        }
        if (rightposition.equals("R")) {
            rightisposition = true;
        }
        for (int i = 0; i < player.hand.size(); i++) {
            if (leftposition.equals(player.hand.get(i).position)) {
                leftisposition = true;
                leftpositionindex = i;
            }
            if (rightposition.equals(player.hand.get(i).position)) {
                rightisposition = true;
                rightpositionindex = i;
            }
        }
        if (leftisposition == false || rightisposition == false) {
            System.out.print("\n");
            System.out.println("The positions you entered are invalid, please try again");
            System.out.print("\n");
            return false;
        }
        boolean nextto = false;
        if (leftposition.equals("L") && rightposition.equals(player.hand.get(0).position)) {
            nextto = true;
        } else if (rightposition.equals("R") && leftposition.equals(player.hand.get(player.hand.size() - 1).position)) {
            nextto = true;
        } else if (leftpositionindex == rightpositionindex - 1 || leftpositionindex == rightpositionindex + 1) {
            nextto = true;
        }
        if (nextto == false) {
            System.out.print("\n");
            System.out.println("The two position you entered are not next to each other");
            System.out.println("In order to put the hyphen in your hand, you need to enter two position that are next to each other");
            System.out.println("So that the hyphen could be added in your hand between those two position");
            System.out.println("Please try again");
            System.out.print("\n");
            return false;
        }
        return true;
    }

    public static void createpositionbeforepick(Player player, Card card) {
        String[] stringarray = {"M", "N", "P", "Q"};
        String[] numberarray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
        card.position = stringarray[findplayerindex(player)] + numberarray[player.hand.size()];
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
                if (x.colorwithwhitetrueblackfalse == true) {
                    return -1;
                }
                return 1;
            }
        });
        card.visible[findplayerindex(player)] = true;
        return true;
    }

    public static boolean showhand(Player player) {
        System.out.println("Your cards are (with position string under them):");
        for (int i = 0; i < player.hand.size(); i++) {
            if (player.hand.get(i).colorwithwhitetrueblackfalse == true) {
                System.out.print("W");
            } else {
                System.out.print("B");
            }
            if (player.hand.get(i).realnumber == -1) {
                System.out.print("- ");
            } else {
                System.out.print(player.hand.get(i).realnumber + " ");
            }
        }
        System.out.print("\n");
        for (int k = 0; k < player.hand.size(); k++) {
            if (player.hand.get(k).realnumber >= 10) {
                System.out.print(player.hand.get(k).position + "  ");
            } else {
                System.out.print(player.hand.get(k).position + " ");
            }
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
        if (player.lose == true) {
            return false;
        }
        System.out.println("\n");
        System.out.println("#" + numberofturn + " turn:");
        Card currentcard = pickcardinturn(player);
        showboard(player);
        showhandfromotherplayer(player);
        showhand(player);
        if (currentcard != null) {
            String color = "B";
            if (currentcard.colorwithwhitetrueblackfalse == true) {
                color = "W";
            }
            if (currentcard.realnumber == -1) {
                System.out.println("Your current card is " + color + "- with position " + currentcard.position);
            } else {
                System.out.println("Your current card is " + color + currentcard.realnumber + " with position " + currentcard.position);
            }
            System.out.println("Note that if you make an incorrect guess, this card will be shown to everyone");
            System.out.print("\n");
        }
        boolean succeed = guess(player);
        if (succeed == false) {
            boolean[] visible = {true, true, true, true};
            currentcard.visible = visible;
        }
        if (ended == true) {
            return true;
        }
        System.out.println("This is the end of #" + numberofturn + " turn");
        numberofturn++;
        return true;
    }

    public static Card pickcardinturn(Player player) {
        System.out.println("\n");
        System.out.println("Welcome  " + playerarray[findplayerindex(player)].name + "  to pick a card");
        int color = getavailablecolor();
        if (color != -1) {
            Card cardpicked = pickcardtoplayer(player, color);
            return cardpicked;
        } else {
            return null;
        }
    }

    public static boolean showboard(Player player) {
        System.out.print("\n");
        for (int i = 0; i < numberOfPlayer; i++) {
            if (playerarray[i].equals(player)) {
                continue;
            } else {
                System.out.println(playerarray[i].name + "'s card (with position string under them):");
                for (int j = 0; j < playerarray[i].hand.size(); j++) {
                    if (playerarray[i].hand.get(j).colorwithwhitetrueblackfalse == true) {
                        System.out.print("W");
                    } else {
                        System.out.print("B");
                    }
                    if (playerarray[i].hand.get(j).visible[findplayerindex(player)] == true) {
                        if (playerarray[i].hand.get(j).realnumber == -1) {
                            System.out.print("- ");
                        } else {
                            System.out.print(playerarray[i].hand.get(j).realnumber + " ");
                        }
                    } else {
                        System.out.print("x ");
                    }
                }
                System.out.print("\n");
                for (int k = 0; k < playerarray[i].hand.size(); k++) {
                    if (playerarray[i].hand.get(k).realnumber >= 10
                            && playerarray[i].hand.get(k).visible[findplayerindex(player)] == true) {
                        System.out.print(playerarray[i].hand.get(k).position + "  ");
                    } else {
                        System.out.print(playerarray[i].hand.get(k).position + " ");
                    }
                }
                System.out.print("\n");
            }
        }
        return true;
    }

    public static boolean guess(Player player) {
        System.out.println("Now please make a guess on other player's card");
        System.out.print("\n");
        Player playerguessed = guessplayer(player);
        while (playerguessed == null) {
            playerguessed = guessplayer(player);
        }
        String position = guessposition(playerguessed);
        while (position == null) {
            position = guessposition(playerguessed);
        }
        int color = guesscolor();
        while (color == -1) {
            color = guesscolor();
        }
        boolean colorboolean = false;
        if (color <= 0) {
            colorboolean = true;
        }
        int number = guessnumber();
        while (number == -100) {
            number = guessnumber();
        }
        Card guessingcard = findcardbyposition(position);
        boolean guessboolean = testforguess(guessingcard, number, color);
        String numberstring = Integer.toString(number);
        if (numberstring.equals("-1")) {
            numberstring = "-";
        }
        Guess currentguess =  new Guess(player, playerguessed,
                guessingcard, colorboolean, numberstring, position, guessboolean, false);
        currentguess.createguessoutput(numberofturn);
        history.add(currentguess);
        playerguessed.needproactiveinsert = testforinsertion(playerguessed);
        if (guessboolean == true) {
            rightguess(guessingcard);
            System.out.println("Congratulations, this is a correct guess!");
            System.out.println("The new board is:");
            showboard(player);
            showhandfromotherplayer(player);
            showhand(player);
            if (testiflose(playerguessed) == true) {
                Guess lost = new Guess(playerguessed, null, null,
                        false, null,null, false, true);
                lost.createguessoutput(-1);
                history.add(lost);
                playerguessed.lose = true;
            }
            if (testifwin(player) == true) {
                changewinner(player);
                System.out.println("\n");
                System.out.println("This game is ended.");
                System.out.println("The winner is  " + player.name + "  !");
                System.out.println("Thank you for playing!");
                return true;
            } else {
                System.out.println("Do you want to keep guessing or to end this ture?");
                System.out.println("0 for keep guessing and 1 for end turn");
                int decision = scan.nextInt();
                if (decision <= 0) {
                    return guess(player);
                } else {
                    return true;
                }
            }
        } else {
            System.out.println("Sorry, you didn't guessed correctly");
            return false;
        }
    }

    public static Player guessplayer(Player player) {
        System.out.println("The name of the player you want to guess is:");
        System.out.println("If you want to see the guess history, please enter 'history'");
        String name = scan.next();
        for (int i = 0; i < numberOfPlayer; i++) {
            if (playerarray[i].equals(player)) {
                continue;
            }
            if (name.equals(playerarray[i].name)) {
                return playerarray[i];
            }
        }
        if (name.equals("history")) {
            printhistory();
            return null;
        }
        System.out.println("The name you entered did not match any of other player's name, please try again");
        return null;
    }

    public static String guessposition(Player guessingplayer) {
        System.out.println("The position you want to guess is:");
        String position = scan.next();
        for (int i = 0; i < guessingplayer.hand.size(); i++) {
            if (position.equals(guessingplayer.hand.get(i).position)) {
                boolean tmp = testforvisible(guessingplayer.hand.get(i));
                if (tmp == false) {
                    return position;
                } else {
                    System.out.println("The card correspond to the position you entered has already been guessed correctly, please try again");
                    return null;
                }
            }
        }
        System.out.println("The position you entered did not match any of the position, please try again");
        return null;
    }

    public static int guesscolor() {
        System.out.println("The color you want to guess is:");
        System.out.println("W for white and B for black");
        String color = scan.next();
        int intcolor = -1;
        if (color.equals("W")) {
            intcolor = 0;
            return intcolor;
        }
        if (color.equals("B")) {
            intcolor = 1;
            return intcolor;
        }
        System.out.println("The color you entered is invalid, please try again");
        return intcolor;
    }

    public static int guessnumber() {
        String[] stringarray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
        System.out.println("The number you want to guess is:");
        String numberstring = scan.next();
        if (numberstring.equals("-")) {
            return -1;
        }
        for (int i = 0; i < 12; i++) {
            if (numberstring.equals(stringarray[i])) {
                return i;
            }
        }
        System.out.println("The number you entered is not in the range of [0, 11] or '-', please try again");
        return -100;
    }

    public static boolean testforguess(Card card, int number, int color) {
        boolean colorboolean = false;
        if (color <= 0) {
            colorboolean = true;
        }
        if (card.realnumber == number) {
            if (card.colorwithwhitetrueblackfalse == colorboolean) {
                return true;
            }
        }
        return false;
    }

    public static boolean testifwin(Player player) {
        for (int i = 0; i < numberOfPlayer; i++) {
            if (player.equals(playerarray[i])) {
                continue;
            } else {
                for (int j = 0; j < playerarray[i].hand.size(); j++) {
                    if (testforvisible(playerarray[i].hand.get(j)) == false) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean testiflose(Player player) {
        for (int i = 0; i < player.hand.size(); i++) {
            if (testforvisible(player.hand.get(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static void printhistory() {
        System.out.println("\n");
        for (int i = 0; i < history.size(); i++) {
            System.out.println(history.get(i).output);
        }
        System.out.println("\n");
        return;
    }

    public static boolean rightguess(Card card) {
        boolean[] visible = {true, true, true, true};
        card.visible = visible;
        return true;
    }

    public static boolean showhandfromotherplayer(Player player) {
        System.out.println("Your cards in other player's view are (with position string under them):");
        for (int i = 0; i < player.hand.size(); i++) {
            if (player.hand.get(i).colorwithwhitetrueblackfalse == true) {
                System.out.print("W");
            } else {
                System.out.print("B");
            }
            boolean tmp = testforvisible(player.hand.get(i));
            if (tmp == true) {
                if (player.hand.get(i).realnumber == -1) {
                    System.out.print("- ");
                } else {
                    System.out.print(player.hand.get(i).realnumber + " ");
                }
            } else {
                System.out.print("x ");
            }
        }
        System.out.print("\n");
        for (int k = 0; k < player.hand.size(); k++) {
            boolean tmp = testforvisible(player.hand.get(k));
            if (tmp == true && player.hand.get(k).realnumber >= 10) {
                System.out.print(player.hand.get(k).position + "  ");
            } else {
                System.out.print(player.hand.get(k).position + " ");
            }
        }
        System.out.print("\n");
        return true;
    }

    public static Card findcardbyposition(String position) {
        for (int i = 0; i < numberOfPlayer; i++) {
            for (int j = 0; j < playerarray[i].hand.size(); j++) {
                if (playerarray[i].hand.get(j).position.equals(position)) {
                    return playerarray[i].hand.get(j);
                }
            }
        }
        return null;
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

    public static void changewinner (Player player) {
        player.win = true;
        ended = true;
    }

    public static boolean testforvisible (Card card) {
        for (int i  = 0; i < 4; i++) {
            if (card.visible[i] == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean testforinsertion (Player player) {
        if (player.needproactiveinsert == false) {
            return false;
        } else {
            for (int i = 0; i < player.hand.size(); i++) {
                if (player.hand.get(i).realnumber == -1 && testforvisible(player.hand.get(i)) == false) {
                    return true;
                }
            }
            return false;
        }
    }
}
