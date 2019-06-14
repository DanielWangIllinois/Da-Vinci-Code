import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static Random rand = new Random();
    private static int numberOfPlayer;
    private static boolean started = false;
    private static boolean ended = false;
    private static Player playerOne = new Player (false, false);
    private static Player playerTwo = new Player (false, false);
    private static Player playerThree = new Player (false, false);
    private static Player playerFour = new Player (false, false);
    private static Player[] playerArray = {playerOne, playerTwo, playerThree, playerFour};
    private static Card[][] cardpool;
    private static int cardpoolSize;
    private static int whiteCardpoolSize;
    private static int blackCardpoolSize;
    private static int numberOfTurn = 1;
    private static ArrayList<Guess> history = new ArrayList<>();

    public static void main(String [] args) {
        cardpool = newCardpool();
        cardpoolSize = getPoolSize();
        whiteCardpoolSize = countWhitePoolSize();
        blackCardpoolSize = countBlackPoolSize();
        for (int i = 1; i <= 10; i++) {
            shuffleCardpool(cardpool);
        }
        while (!started) {
            start();
        }
        getInitialCard();
        while (!ended) {
            for (int i = 0; i < numberOfPlayer; i++) {
                turn(playerArray[i]);
                if (ended) {
                    break;
                }
            }
        }
    }

    private static Card[][] newCardpool() {
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

    private static int getPoolSize() {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 13; j++) {
                if (!cardpool[i][j].picked) {
                    count++;
                }
            }
        }
        return count;
    }

    private static int countWhitePoolSize() {
        int count = 0;
        for (int j = 0; j < 13; j++) {
            if (!cardpool[0][j].picked) {
                count++;
            }
        }
        return count;
    }

    private static int countBlackPoolSize() {
        int count = 0;
        for (int j = 0; j < 13; j++) {
            if (!cardpool[1][j].picked) {
                count++;
            }
        }
        return count;
    }

    private static void shuffleCardpool(Card[][] input) {
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

    private static void swap (Card[] inputarray, int i, int j) {
        Card tmpcard = inputarray[i];
        inputarray[i] = inputarray[j];
        inputarray[j] = tmpcard;
    }

    private static boolean start() {
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
            playerOne.name = playerTwo.name;
            playerTwo.name = playerThree.name;
            playerThree.name = playerFour.name;
            playerFour.name = tmpStringForNameInput;
        }
        while (playerOne.name == null) {
            String tmpStringForNameSwap = playerOne.name;
            playerOne.name = playerTwo.name;
            playerTwo.name = playerThree.name;
            playerThree.name = playerFour.name;
            playerFour.name = tmpStringForNameSwap;
        }
        for (int i = numberOfPlayer; i < 4; i++) {
            playerArray[i].lose = true;
        }
        started = true;
        return true;
    }

    private static void getInitialCard() {
        System.out.println("\n");
        for (int i = 0; i < numberOfPlayer; i++) {
            System.out.println("\n");
            System.out.println("Now welcome  " + playerArray[i].name + "  to pick his initial card");
            for (int j = 1; j <= 4; j++) {
                System.out.println("What color do you want for your #" + j + " card?");
                int color = getAvailableColor();
                if (color != -1) {
                    pickCardToPlayer(playerArray[i], color);
                }
            }
            showHand(playerArray[i]);
        }
    }

    private static int getAvailableColor() {
        //TODO 调换 if else 位置
        if (!pickWhite()) {
            if (!pickBlack()) {
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
            if (!pickBlack()) {
                System.out.println("There's only white card left in the cardpool");
                System.out.println("Automatically picked white card");
                System.out.println("Please type c and enter to continue");
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

    private static Card pickCardToPlayer(Player player, int color) {
        Card toReturn;
        if (color <= 0) {
            toReturn = cardpool[0][13 - whiteCardpoolSize];
        } else {
            toReturn = cardpool[1][13 - blackCardpoolSize];
        }
        toReturn.picked = true;
        createPositionBeforePick(player, toReturn);
        if (toReturn.realNumber == -1) {
            player.needProactiveInsert = true;
            changeCardNumber(player, toReturn);
        } else if (player.needProactiveInsert) {
            insertCard(player, toReturn);
        }
        putToHand(player, toReturn);
        cardpoolSize--;
        if (color <= 0) {
            whiteCardpoolSize--;
        } else {
            blackCardpoolSize--;
        }
        if (!checkCardpoolSize()) {
            System.out.println("Bug appeared");
            return null;
        }
        return toReturn;
    }

    private static void insertCard(Player player, Card card) {
        // TODO balabala
        //在将新的非连字符插入手牌的时候，我们需要考虑以下这几种情况：
        //1、手牌里只有连字符
        //2、手牌里只有连字符和连字符的一边有数字牌
        //3、手牌里连字符的两边都有数字牌
        //之后我们就要判断在放完牌之后连字符的两侧是否有暗着的牌、亮着的牌或根本没有牌
        System.out.print("Been here!" + "\n");
    }

    private static void changeCardNumber(Player player, Card card) {
        if (player.hand.size() != 0) {
            String leftposition = null;
            String rightposition = null;
            double leftindex;
            double rightindex;
            boolean nextto = false;
            while (!nextto) {
                showHand(player);
                System.out.println("Where do you want to put your hyphen?");
                System.out.println("Note that you could use L for the position left of the smallest card and R for the position right of the biggest card");
                System.out.println("Please enter the position of the card that you wish to be the left of the hyphen");
                leftposition = scan.next();
                System.out.println("Please enter the position of the card that you wish to be the right of the hyphen");
                rightposition = scan.next();
                nextto = checkIfNextto(leftposition, rightposition, player);
            }
            if (leftposition.equals("L")) {
                leftindex = -1;
            } else {
                leftindex = findCardByPosition(leftposition).number;
            }
            if (rightposition.equals("R")) {
                rightindex = 12;
            } else {
                rightindex = findCardByPosition(rightposition).number;
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
        }
    }

    private static boolean checkIfNextto(String leftposition, String rightposition, Player player) {
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
        if (!leftisposition || !rightisposition) {
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
        if (!nextto) {
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

    private static void createPositionBeforePick(Player player, Card card) {
        String[] stringarray = {"M", "N", "P", "Q"};
        String[] numberarray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
        card.position = stringarray[findPlayerIndex(player)] + numberarray[player.hand.size()];
    }

    private static void putToHand(Player player, Card card) {
        player.hand.add(card);
        Collections.sort(player.hand, new Comparator<Card>(){
            public int compare(Card x, Card y) {
                if(x.number > y.number){
                    return 1;
                }
                if(x.number < y.number){
                    return -1;
                }
                if (x.colorWithWhiteTrueBlackFalse) {
                    return -1;
                }
                return 1;
            }
        });
        card.visible[findPlayerIndex(player)] = true;
    }

    private static void showHand(Player player) {
        System.out.println("Your cards are (with position string under them):");
        for (int i = 0; i < player.hand.size(); i++) {
            if (player.hand.get(i).colorWithWhiteTrueBlackFalse) {
                System.out.print("W");
            } else {
                System.out.print("B");
            }
            if (player.hand.get(i).realNumber == -1) {
                System.out.print("- ");
            } else {
                System.out.print(player.hand.get(i).realNumber + " ");
            }
        }
        System.out.print("\n");
        for (int k = 0; k < player.hand.size(); k++) {
            if (player.hand.get(k).realNumber >= 10) {
                System.out.print(player.hand.get(k).position + "  ");
            } else {
                System.out.print(player.hand.get(k).position + " ");
            }
        }
        System.out.println("\n");
    }

    private static boolean checkCardpoolSize() {
        return getPoolSize() == cardpoolSize && countWhitePoolSize() == whiteCardpoolSize &&
                countBlackPoolSize() == blackCardpoolSize;
    }

    private static int findPlayerIndex(Player player) {
        int playerindex = -1;
        for (int i = 0; i < numberOfPlayer; i++) {
            if (player.equals(playerArray[i])) {
                playerindex = i;
            }
        }
        return playerindex;
    }

    private static boolean turn(Player player) {
        if (player.lose) {
            return false;
        }
        System.out.println("\n");
        System.out.println("#" + numberOfTurn + " turn:");
        Card currentcard = pickCardInTurn(player);
        showBoard(player);
        showHandFromOtherPlayer(player);
        showHand(player);
        if (currentcard != null) {
            String color = "B";
            if (currentcard.colorWithWhiteTrueBlackFalse) {
                color = "W";
            }
            if (currentcard.realNumber == -1) {
                System.out.println("Your current card is " + color + "- with position " + currentcard.position);
            } else {
                System.out.println("Your current card is " + color + currentcard.realNumber + " with position " + currentcard.position);
            }
            System.out.println("Note that if you make an incorrect guess, this card will be shown to everyone");
            System.out.print("\n");
        }
        boolean succeed = guess(player);
        if (!succeed) {
            currentcard.visible = new boolean[] {true, true, true, true};
        }
        if (ended) {
            return true;
        }
        System.out.println("This is the end of #" + numberOfTurn + " turn");
        numberOfTurn++;
        return true;
    }

    private static Card pickCardInTurn(Player player) {
        System.out.println("\n");
        System.out.println("Welcome  " + playerArray[findPlayerIndex(player)].name + "  to pick a card");
        int color = getAvailableColor();
        if (color != -1) {
            return pickCardToPlayer(player, color);
        } else {
            return null;
        }
    }

    private static void showBoard(Player player) {
        System.out.print("\n");
        for (int i = 0; i < numberOfPlayer; i++) {
            if (!playerArray[i].equals(player)) {
                System.out.println(playerArray[i].name + "'s card (with position string under them):");
                for (int j = 0; j < playerArray[i].hand.size(); j++) {
                    if (playerArray[i].hand.get(j).colorWithWhiteTrueBlackFalse) {
                        System.out.print("W");
                    } else {
                        System.out.print("B");
                    }
                    if (playerArray[i].hand.get(j).visible[findPlayerIndex(player)]) {
                        if (playerArray[i].hand.get(j).realNumber == -1) {
                            System.out.print("- ");
                        } else {
                            System.out.print(playerArray[i].hand.get(j).realNumber + " ");
                        }
                    } else {
                        System.out.print("x ");
                    }
                }
                System.out.print("\n");
                for (int k = 0; k < playerArray[i].hand.size(); k++) {
                    if (playerArray[i].hand.get(k).realNumber >= 10
                            && playerArray[i].hand.get(k).visible[findPlayerIndex(player)]) {
                        System.out.print(playerArray[i].hand.get(k).position + "  ");
                    } else {
                        System.out.print(playerArray[i].hand.get(k).position + " ");
                    }
                }
                System.out.print("\n");
            }
        }
    }

    private static boolean guess(Player player) {
        System.out.println("Now please make a guess on other player's card");
        System.out.print("\n");
        Player playerguessed = guessPlayer(player);
        while (playerguessed == null) {
            playerguessed = guessPlayer(player);
        }
        String position = guessPosition(playerguessed);
        while (position == null) {
            position = guessPosition(playerguessed);
        }
        int color = guessColor();
        while (color == -1) {
            color = guessColor();
        }
        boolean colorboolean = false;
        if (color <= 0) {
            colorboolean = true;
        }
        int number = guessNumber();
        while (number == -100) {
            number = guessNumber();
        }
        Card guessingcard = findCardByPosition(position);
        boolean guessboolean = testForGuess(guessingcard, number, color);
        String numberstring = Integer.toString(number);
        if (numberstring.equals("-1")) {
            numberstring = "-";
        }
        Guess currentguess =  new Guess(player, playerguessed,
                colorboolean, numberstring, position, guessboolean, false);
        currentguess.createGuessOutput(numberOfTurn);
        history.add(currentguess);
        playerguessed.needProactiveInsert = testForInsertion(playerguessed);
        if (guessboolean) {
            rightGuess(guessingcard);
            System.out.println("Congratulations, this is a correct guess!");
            System.out.println("The new board is:");
            showBoard(player);
            showHandFromOtherPlayer(player);
            showHand(player);
            if (testIfLose(playerguessed)) {
                Guess lost = new Guess(playerguessed, null,
                        false, null,null, false, true);
                lost.createGuessOutput(-1);
                history.add(lost);
                playerguessed.lose = true;
            }
            if (testIfWin(player)) {
                changeWinner(player);
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

    private static Player guessPlayer(Player player) {
        System.out.println("The name of the player you want to guess is:");
        System.out.println("If you want to see the guess history, please enter 'history'");
        String name = scan.next();
        for (int i = 0; i < numberOfPlayer; i++) {
            if (playerArray[i].equals(player)) {
                continue;
            }
            if (name.equals(playerArray[i].name)) {
                return playerArray[i];
            }
        }
        if (name.equals("history")) {
            printHistory();
            return null;
        }
        System.out.println("The name you entered did not match any of other player's name, please try again");
        return null;
    }

    private static String guessPosition(Player guessingplayer) {
        System.out.println("The position you want to guess is:");
        String position = scan.next();
        for (int i = 0; i < guessingplayer.hand.size(); i++) {
            if (position.equals(guessingplayer.hand.get(i).position)) {
                boolean tmp = testForVisible(guessingplayer.hand.get(i));
                if (!tmp) {
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

    private static int guessColor() {
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

    private static int guessNumber() {
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

    private static boolean testForGuess(Card card, int number, int color) {
//        boolean colorboolean = false;
//        if (color <= 0) {
//            colorboolean = true;
//        }
//        if (card.realNumber == number) {
//            if (card.colorWithWhiteTrueBlackFalse == colorboolean) {
//                return true;
//            }
//        }
//        return false;
        return card.realNumber == number && card.colorWithWhiteTrueBlackFalse == (color <= 0);
    }

    private static boolean testIfWin(Player player) {
        for (int i = 0; i < numberOfPlayer; i++) {
            if (!player.equals(playerArray[i])) {
                for (int j = 0; j < playerArray[i].hand.size(); j++) {
                    if (!testForVisible(playerArray[i].hand.get(j))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean testIfLose(Player player) {
        for (int i = 0; i < player.hand.size(); i++) {
            if (!testForVisible(player.hand.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static void printHistory() {
        System.out.println("\n");
        for (int i = 0; i < history.size(); i++) {
            System.out.println(history.get(i).output);
        }
        System.out.println("\n");
    }

    private static void rightGuess(Card card) {
        card.visible = new boolean[] {true, true, true, true};
    }

    private static void showHandFromOtherPlayer(Player player) {
        System.out.println("Your cards in other player's view are (with position string under them):");
        for (int i = 0; i < player.hand.size(); i++) {
            if (player.hand.get(i).colorWithWhiteTrueBlackFalse) {
                System.out.print("W");
            } else {
                System.out.print("B");
            }
            boolean tmp = testForVisible(player.hand.get(i));
            if (tmp) {
                if (player.hand.get(i).realNumber == -1) {
                    System.out.print("- ");
                } else {
                    System.out.print(player.hand.get(i).realNumber + " ");
                }
            } else {
                System.out.print("x ");
            }
        }
        System.out.print("\n");
        for (int k = 0; k < player.hand.size(); k++) {
            boolean tmp = testForVisible(player.hand.get(k));
            if (tmp && player.hand.get(k).realNumber >= 10) {
                System.out.print(player.hand.get(k).position + "  ");
            } else {
                System.out.print(player.hand.get(k).position + " ");
            }
        }
        System.out.print("\n");
    }

    private static Card findCardByPosition(String position) {
        for (int i = 0; i < numberOfPlayer; i++) {
            for (int j = 0; j < playerArray[i].hand.size(); j++) {
                if (playerArray[i].hand.get(j).position.equals(position)) {
                    return playerArray[i].hand.get(j);
                }
            }
        }
        return null;
    }

    private static boolean pickWhite() {
        return countWhitePoolSize() > 0;
    }

    private static boolean pickBlack() {
        return countBlackPoolSize() > 0;
    }

    private static void changeWinner(Player player) {
        player.win = true;
        ended = true;
    }

    private static boolean testForVisible(Card card) {
        for (int i  = 0; i < 4; i++) {
            if (!card.visible[i]) {
                return false;
            }
        }
        return true;
    }

    private static boolean testForInsertion(Player player) {
        if (!player.needProactiveInsert) {
            return false;
        } else {
            for (int i = 0; i < player.hand.size(); i++) {
                if (player.hand.get(i).realNumber == -1 && !testForVisible(player.hand.get(i))) {
                    return true;
                }
            }
            return false;
        }
    }
}
