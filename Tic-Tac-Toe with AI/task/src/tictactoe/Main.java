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
        do {
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
                            break;
                        case "user hard":
                            break;
                        case "easy user":
                            playerVSeasy(false);
                            break;
                        case "medium user":
                            break;
                        case "hard user":
                            break;
                        case "easy easy":
                            easyVSeasy();
                            break;
                        case "medium medium":
                            break;
                        case "hard hard":
                            break;
                        case "easy hard":
                            break;
                        case "hard easy":
                            break;
                        case "easy medium":
                            break;
                        case "medium easy":
                            break;
                        case "medium hard":
                            break;
                        case "hard medium":
                            break;
//                        default:
//                            System.out.println("Bad parameters!");
//                            break;
                    }
//                } else if (commands[0].equals("exit")) {
//                    System.exit(1);
                } else {
                    System.out.println("Bad parameters!");
                    break;
                }
            } catch (NoSuchElementException e){
               System.out.println("Bad parameters!");
               break;
           }
        } while (true);
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
            sc.nextLine();
            size = 9;
            getCommand();
            return true;
        }
        else if (checkScore('O')) {
            System.out.println("O wins");
            size = 9;
            return true;
        }
        else if (size == 0){
            System.out.println("Draw");
            size = 9;
            return true;
        }
//        else
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


    private static void easyMove(char symbol){
           int y = randNum.nextInt(3) + 1;
           int x = randNum.nextInt(3) + 1;
           x = setRow(x);
           if(board[x][y - 1] == ' ') {
            System.out.println("Making move level \"easy\"");
            board[x][y - 1] = symbol;
            printBoard();
            size--;
            } else {
               easyMove('O');
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
    getCommand();
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
                easyMove('O');
                if (checkWinner()) {
                    break;
                }
            } while (true);
            getCommand();
        } else {
            setCells();
            printBoard();
            do {
                easyMove('O');
                if (checkWinner()) {
                    break;
                }
                inputPos('X');
                if (checkWinner()) {
                    break;
                }
            } while (true);
            getCommand();
        }
    }

    private static void easyVSeasy(){
        setCells();
        printBoard();
        do {
            easyMove('O');
            if (checkWinner()) {
               break;
            }
            easyMove('X');
            if (checkWinner()) {
                break;
            }
        } while (true);
    }

    private static void playerVSmedium(boolean pStarts){
        if(pStarts) {
            setCells();
            printBoard();
            do {
                inputPos('X');
                if (checkWinner()) {
                    break;
                }
                easyMove('O');
                if (checkWinner()) {
                    break;
                }
            } while (true);
            getCommand();
        } else {
            setCells();
            printBoard();
            do {
                easyMove('O');
                if (checkWinner()) {
                    break;
                }
                inputPos('X');
                if (checkWinner()) {
                    break;
                }
            } while (true);
            getCommand();
        }
    }

    private static void mediumVSmedium(){
        setCells();
        printBoard();
        do {
            easyMove('O');
            if (checkWinner()) {
                break;
            }
            easyMove('X');
            if (checkWinner()) {
                break;
            }
        } while (true);
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
                easyMove('O');
                if (checkWinner()) {
                    break;
                }
            } while (true);
            getCommand();
        } else {
            setCells();
            printBoard();
            do {
                easyMove('O');
                if (checkWinner()) {
                    break;
                }
                inputPos('X');
                if (checkWinner()) {
                    break;
                }
            } while (true);
            getCommand();
        }
    }

    private static void hardVShard(){
        setCells();
        printBoard();
        do {
            easyMove('O');
            if (checkWinner()) {
                break;
            }
            easyMove('X');
            if (checkWinner()) {
                break;
            }
        } while (true);
    }
}











