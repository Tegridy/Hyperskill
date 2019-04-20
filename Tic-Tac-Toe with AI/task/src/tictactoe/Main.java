package tictactoe;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter cells: ");
        String cells = sc.nextLine().replaceAll("\"", "");
        int numOfX = 0;
        int numOfO = 0;

        char[][] board = new char[3][3];
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

        printBoard(board);

        System.out.print("Enter the coordinates: ");
        int coordY = sc.nextInt()-1;
        int coordX = sc.nextInt();
        coordX = setRow(coordX);

        board[coordX][coordY] = 'X';

        printBoard(board);
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
        private static void printBoard ( char[][] board){
            System.out.println("---------");
            for (char[] row : board) {
                System.out.println(Arrays.toString(row).replaceAll("\\[", "| ")
                        .replaceAll("]", " |").replaceAll(",", ""));
            }
            System.out.println("---------");
        }

        private static int setRow(int coordX){
            switch (coordX){
                case 1:
                    coordX = 2;
                    break;
                case 2:
                    coordX = 1;
                    break;
                case 3:
                    coordX = 0;
                    break;
            }
            return coordX;
        }

    }




