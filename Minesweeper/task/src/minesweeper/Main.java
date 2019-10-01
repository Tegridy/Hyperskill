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
            int posX = random.nextInt(9);
            int posY = random.nextInt(9);

            if ( board[posX][posY] == '.'){
                board[posX][posY] = 'X';
            } else {
                numOfMines++;
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
