package tictactoe;

import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static char[][] board = new char[3][3];
    private static Random randNum = new Random();
//    private static int numOfX = 0;
//    private static int numOfO = 0;
    private static byte size = 9;

    public static void main(String[] args) {
        getCommand();
    }

    private static void getCommand(){
           try {
                System.out.print("Input command: ");
                String[] commands = sc.nextLine().split("\\s+");
                if (commands[0].equals("start")) {

                    switch (commands[1] + " " + commands[2]) {
                        case "user user":
                            playerVSplayer();
                            break;
                        case "user easy":
                            playerVSeasy(true);
                            break;
                        case "user medium":
                            playerVSmedium(true);
                            break;
                        case "user hard":
                            break;
                        case "easy user":
                            playerVSeasy(false);
                            break;
                        case "medium user":
                            playerVSmedium(false);
                            break;
                        case "hard user":
                            break;
                        case "easy easy":
                            easyVSeasy();
                            break;
                        case "medium medium":
                            mediumVSmedium();
                            break;
                        case "hard hard":
                            break;
                        case "easy hard":
                            break;
                        case "hard easy":
                            break;
                        case "easy medium":
                            mediumVSmedium();
                            break;
                        case "medium easy":
                            mediumVSmedium();
                            break;
                        case "medium hard":
                            break;
                        case "hard medium":
                            break;
                        default:
                                System.out.println("Bad parameters! in switch");
                            break;
                    }
//                } else if (commands[0].equals("exit")) {
//                    System.exit(1);
                } else {
                    System.out.println("Bad parameters! in else");
                }
            } catch (NoSuchElementException e){

            }
    }

    private static boolean checkScore (char val) {
        return (board[0][0] == val && board[0][1] == val && board[0][2] == val ||
                board[1][0] == val && board[1][1] == val && board[1][2] == val ||
                board[2][0] == val && board[2][1] == val && board[2][2] == val ||
                board[0][0] == val && board[1][0] == val && board[2][0] == val ||
                board[0][1] == val && board[1][1] == val && board[2][1] == val ||
                board[0][2] == val && board[1][2] == val && board[2][2] == val ||
                board[0][2] == val && board[1][1] == val && board[2][0] == val ||
                board[0][0] == val && board[1][1] == val && board[2][2] == val);
    }

//    private static boolean isImpossible (){
//        return (numOfO - numOfX >=2 || numOfX - numOfO >= 2 ||
//                board[0][0] == 'X' && board[0][1] == 'X' && board[0][2] == 'X' &&
//                board[1][0] == 'O' && board[1][1] == 'O' && board[2][2] == 'O' ||
//                board[0][0] == 'X' && board[1][0] == 'X' && board[2][0] == 'X' &&
//                board[0][1] == 'O' && board[1][1] == 'O' && board[2][1] == 'O');
//    }

    private static boolean checkWinner(){
//        if (isImpossible()) {
//            System.out.println("Impossible");
//            return true;
//        }
        if(checkScore('X')) {
            System.out.println("X wins");
            size = 9;
            //sc.nextLine();
            getCommand();
            return true;
        }
        else if (checkScore('O')) {
            System.out.println("O wins");
            size = 9;
            //sc.nextLine();
            getCommand();
            return true;
        }
        else if (size == 0){
            System.out.println("Draw");
            //sc.nextLine();
            size = 9;
            getCommand();
            return true;
        }
        else
            return false;
//            System.out.println("Game not finished");
    }

    private static void setCells(){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = ' ';
            }
        }
    }

    private static void printBoard() {
        System.out.println("---------");
        for (char[] row : board) {
            System.out.println(Arrays.toString(row).replaceAll("\\[", "| ")
                    .replaceAll("]", " |").replaceAll(",", ""));
        }
        System.out.println("---------");
    }

    private static int setRow(int posX) {
        switch (posX) {
            case 1:
                posX = 2;
                break;
            case 2:
                posX = 1;
                break;
            case 3:
                posX = 0;
                break;
        }
        return posX;
    }

    private static void inputPos(char symbol) {
           do {
               try {
                   System.out.print("Enter the coordinates: ");
                   int posY = sc.nextInt() - 1;
                   int posX = sc.nextInt();
                   posX = setRow(posX);
                   if (posX > 2 || posX < 0 || posY > 2 || posY < 0) {
                       System.out.println("Coordinates should be from 1 to 3!");
                   } else if (board[posX][posY] != ' ') {
                       System.out.println("This cell is occupied! Choose another one!");
                   } else {
                       board[posX][posY] = symbol;
                       printBoard();
                       size--;
                       break;
                   }
               } catch (InputMismatchException e) {
                   System.out.println("You should enter numbers!");
                   sc.nextLine();
               } catch (NoSuchElementException e){
                   break;
               }
           } while (true);
       }


    private static void easy_mediumMove(char symbol, String bot){
           int y = randNum.nextInt(3) + 1;
           int x = randNum.nextInt(3) + 1;
           x = setRow(x);
           if(board[x][y - 1] == ' ') {
            System.out.println("Making move level \""+bot+"\"");
            board[x][y - 1] = symbol;
            printBoard();
            size--;
            } else {
               easy_mediumMove('O', bot);
            }
    }

    private static void playerVSplayer(){
        setCells();
        printBoard();
    do {
        inputPos('X');
        if (checkWinner()){
            break;
        }
        inputPos('O');
    } while (true);
    }

    private static void playerVSeasy(boolean pStarts){
        if(pStarts) {
            setCells();
            printBoard();
            do {
                inputPos('X');
                if (checkWinner()) {
                    break;
                }
                easy_mediumMove('O', "easy");
                if (checkWinner()) {
                    break;
                }
            } while (true);

        } else {
            setCells();
            printBoard();
            do {
                easy_mediumMove('O', "easy");
                if (checkWinner()) {
                    break;
                }
                inputPos('X');
                if (checkWinner()) {
                    break;
                }
            } while (true);

        }
    }

    private static void easyVSeasy(){
        setCells();
        printBoard();
        do {
            easy_mediumMove('O', "easy");
            if (checkWinner()) {
               break;
            }
            easy_mediumMove('X', "easy");
            if (checkWinner()) {
                break;
            }
        } while (true);
    }

    private static void playerVSmedium(boolean pStarts){
        setCells();
        printBoard();

        do {
            if (pStarts) {
                inputPos('X');
                if (checkWinner()) break;
                // Checking if X can win and tries to block otherwise trying to win
                if (findImmediateWin('X') != null) {
                    int[] xy = findImmediateWin('X');
                    System.out.println("Making move level \"medium\"");
                    board[xy[0]][xy[1]] = 'O';
                    size--;
                    printBoard();
                    if (checkWinner()) break;
                } else if (findImmediateWin('O') != null) {
                    int[] xy = findImmediateWin('O');
                    System.out.println("Making move level \"medium\"");
                    board[xy[0]][xy[1]] = 'O';
                    size--;
                    printBoard();
                    if (checkWinner()) break;
                } else {
                    easy_mediumMove('O', "medium");
                    if (checkWinner()) break;
                }
            } else {
                if (findImmediateWin('X') != null) {
                    int[] xy = findImmediateWin('X');
                    System.out.println("Making move level \"medium\"");
                    board[xy[0]][xy[1]] = 'O';
                    size--;
                    printBoard();
                    if (checkWinner()) break;
                } else if (findImmediateWin('O') != null) {
                    int[] xy = findImmediateWin('O');
                    System.out.println("Making move level \"medium\"");
                    board[xy[0]][xy[1]] = 'O';
                    size--;
                    printBoard();
                    if (checkWinner()) break;
                } else {
                    easy_mediumMove('O', "medium");
                    if (checkWinner()) break;
                }
                inputPos('X');
                if (checkWinner()) break;
            }
        } while (true);
    }

    private static void mediumVSmedium(){
        setCells();
        printBoard();
        char symbol = 'X';
        do {
            if (findImmediateWin('X') != null) {
                int[] xy = findImmediateWin('X');
                System.out.println("Making move level \"medium\"");
                board[xy[0]][xy[1]] = symbol;
                size--;
                printBoard();
                if (checkWinner()) break;
            } else if (findImmediateWin('O') != null) {
                int[] xy = findImmediateWin('O');
                System.out.println("Making move level \"medium\"");
                board[xy[0]][xy[1]] = symbol;
                size--;
                printBoard();
                if (checkWinner()) break;
            } else {
                easy_mediumMove(symbol, "medium");
                if (checkWinner()) break;
            }
            if(symbol == 'X') symbol = 'O';
            else symbol = 'X';
        } while (true);
    }

    private static int[] findImmediateWin(char symbol) {
        int counterOfSymbol;
        int counterOfEmptyCells;
        int posX = 0;
        int posY = 0;

        // Searching for rows
        for (int i = 0; i < 3; i++) {
            counterOfSymbol = 0;
            counterOfEmptyCells = 0;
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == symbol) {
                    counterOfSymbol++;
                } else if (board[i][j] == ' ') {
                    counterOfEmptyCells++;
                    posX = i;
                    posY = j;
                }
            }
            if (counterOfSymbol == 2 && counterOfEmptyCells == 1) {
                return new int[]{posX, posY};
            }
        }

        // Searching for columns
        for (int i = 0; i < 3; i++) {
            counterOfSymbol = 0;
            counterOfEmptyCells = 0;
            for (int j = 0; j < 3; j++) {
                if (board[j][i] == symbol) {
                    counterOfSymbol++;
                } else if (board[j][i] == ' ') {
                    counterOfEmptyCells++;
                    posX = j;
                    posY = i;
                }
            }
            if (counterOfSymbol == 2 && counterOfEmptyCells == 1) {
                return new int[]{posX, posY};
            }
        }

        // Searching for main diagonal
        counterOfSymbol = 0;
        counterOfEmptyCells = 0;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] == symbol) {
                counterOfSymbol++;
            } else if (board[i][i] == ' ') {
                counterOfEmptyCells++;
                posX = i;
                posY = i;
            }
        }
        if (counterOfSymbol == 2 && counterOfEmptyCells == 1) {
            return new int[]{posX, posY};
        }

        // Searching for side diagonal
        counterOfSymbol = 0;
        counterOfEmptyCells = 0;
        for (int i = 0; i < 3; i++) {
            if (board[i][2-i] == symbol) {
                counterOfSymbol++;
            } else if (board[i][2-i] == ' ') {
                counterOfEmptyCells++;
                posX = i;
                posY = 2-i;
            }
        }
        if (counterOfSymbol == 2 && counterOfEmptyCells == 1) {
            return new int[]{posX, posY};
        }

        // If nothing was found
        return null;
    }



    private static void playerVShard(boolean pStarts){
        if(pStarts) {
            setCells();
            printBoard();
            do {
                inputPos('X');
                if (checkWinner()) {
                    break;
                }
                easy_mediumMove('O', "medium");
                if (checkWinner()) {
                    break;
                }
            } while (true);

        } else {
            setCells();
            printBoard();
            do {
                easy_mediumMove('O', "");
                if (checkWinner()) {
                    break;
                }
                inputPos('X');
                if (checkWinner()) {
                    break;
                }
            } while (true);
        }
    }

    private static void hardVShard(){
        setCells();
        printBoard();
        do {
            easy_mediumMove('O', "medium");
            if (checkWinner()) {
                break;
            }
            easy_mediumMove('X', "medium");
            if (checkWinner()) {
                break;
            }
        } while (true);
    }
}











