package minesweeper;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static char[][] board = new char[9][9];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        System.out.print("How many mines do you want on the field? ");
        int numOfMines = sc.nextInt();
        System.out.println();

        fillBoard();
        randomizeMines(numOfMines);
        printBoard();
    }

    private static void randomizeMines(int numOfMines){
        Random random = new Random();
        for (int i = 0; i < numOfMines; i++) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            if (board[row][col] != 'X'){
                mines(row, col);
                board[row][col] = 'X';
            } else {
                numOfMines++;
            }
        }

    }

    private static void mines(int row, int col){
        int rowStart  = Math.max( row - 1, 0 );
        int rowFinish = Math.min( row + 1, board.length - 1 );
        int colStart  = Math.max( col - 1, 0 );
        int colFinish = Math.min( col + 1, board.length - 1 );

        for ( int curRow = rowStart; curRow <= rowFinish; curRow++ ) {
            for ( int curCol = colStart; curCol <= colFinish; curCol++ ) {

                // setting mines info
                if (board[curRow][curCol] == '.'){
                    board[curRow][curCol] = '1';
                } else if (board[curRow][curCol] != 'X') {
                    board[curRow][curCol] += 1;
                }
            }
        }
    }

    private static void fillBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length ; j++) {
                board[i][j] = '.';
            }
        }
    }

    private static void printBoard() {
        for(char[] row : board){
            System.out.println(Arrays.toString(row).replaceAll(" ", "").replaceAll("\\[", "")
                    .replaceAll("]", "").replaceAll(",", ""));
         }
    }

}