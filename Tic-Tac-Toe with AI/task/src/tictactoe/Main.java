package tictactoe;

import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static char[][] board = new char[3][3];
    private static Random randNum = new Random();

    public static void main(String[] args) {

        inputCells();
        printBoard();

           // inputPos();
            easyMove();

    }

    /*
    if (isImpossible(board, numOfO, numOfX))
        System.out.println("Impossible");
    else if(checkScore(board, 'X') )
        System.out.println("X wins");
    else if (checkScore(board, 'O'))
        System.out.println("O wins");
    else if (numOfO == 4 && numOfX == 5 || numOfO == 5 && numOfX == 4)
        System.out.println("Draw");
    else
        System.out.println("Game not finished");
    }

    private static boolean checkScore (char[][] board, char val){
        return (board[0][0] == val && board[0][1] == val && board[0][2] == val ||
                board[1][0] == val && board[1][1] == val && board[1][2] == val ||
                board[2][0] == val && board[2][1] == val && board[2][2] == val ||
                board[0][0] == val && board[1][0] == val && board[2][0] == val ||
                board[0][1] == val && board[1][1] == val && board[2][1] == val ||
                board[0][2] == val && board[1][2] == val && board[2][2] == val ||
                board[0][2] == val && board[1][1] == val && board[2][0] == val ||
                board[0][0] == val && board[1][1] == val && board[2][2] == val);
    }

    private static boolean isImpossible (char[][] board, int numOfO, int numOfX){
        return (numOfO - numOfX >=2 || numOfX - numOfO >= 2 ||
                board[0][0] == 'X' && board[0][1] == 'X' && board[0][2] == 'X' &&
                board[1][0] == 'O' && board[1][1] == 'O' && board[2][2] == 'O' ||
                board[0][0] == 'X' && board[1][0] == 'X' && board[2][0] == 'X' &&
                board[0][1] == 'O' && board[1][1] == 'O' && board[2][1] == 'O');
    }
    */
    private static void inputCells(){
        System.out.print("Enter cells: ");
        String cells = sc.nextLine().replaceAll("\"", "");
        if(cells.length()<1) {
            inputCells();
        }
        else {
            int numOfX = 0;
            int numOfO = 0;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    char temp = cells.charAt(i * 3 + j);
                    if (temp == 'O') {
                        board[i][j] = temp;
                        numOfO++;
                    } else if (temp == 'X') {
                        board[i][j] = temp;
                        numOfX++;
                    } else if (temp == ' ') board[i][j] = temp;
                }
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

    private static void inputPos() {
           do {
               //System.out.print("Enter the coordinates: ");
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
                       board[posX][posY] = 'X';
                       printBoard();
                       break;
                   }
               } catch (InputMismatchException e) {
                   System.out.println("You should enter numbers!");
                   sc.nextLine();
               }
           } while (true);
       }


       private static void easyMove(){
           int y = randNum.nextInt(3) + 1;
           int x = randNum.nextInt(3) + 1;
           x = setRow(x);
           System.out.println("Making move level \"easy\"");
           board[x][y] = 'O';

           printBoard();
    }
}











