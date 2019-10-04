package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static char[][] board = new char[9][9];
    private static ArrayList<String> minesCoords = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        fillBoard();

        System.out.print("How many mines do you want on the field? ");
        int numOfMines = sc.nextInt();
        randomizeMines(numOfMines);
        System.out.println();
        printBoard();
        System.out.println(minesCoords);

        int numOfFlags = 0;
        int fieldsWithMine = 0;

        while (true){

            try {

                if (numOfMines == fieldsWithMine && numOfMines == numOfFlags) {
                    System.out.println("Congratulations! You founded all mines!");
                    break;
                }

                System.out.print("Set/delete mines marks (x and y coordinates):");

                int col = sc.nextInt() - 1;
                int row = sc.nextInt() - 1;
                System.out.println();

                if (board[row][col] == '.') {
                    board[row][col] = '*';
                    numOfFlags++;

                    if (minesCoords.contains(row + " " + col)) {
                        fieldsWithMine++;
                    }
                } else if (board[row][col] == '*') {
                    board[row][col] = '.';
                    numOfFlags--;

                    if (minesCoords.contains(row + " " + col)) {
                        fieldsWithMine--;
                    }
                } else {
                    System.out.println("There is a number here!");
                    continue;
                }

                printBoard();

            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Wrong coordinates. Use range from 1-9!");
                printBoard();
            }
        }
    }

    private static void randomizeMines(int numOfMines){
        Random random = new Random();

        // Placing mines in random places and setting values surrounding mine

        for (int i = 0; i < numOfMines; i++) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            if (board[row][col] != 'X'){
                setMinesValues(row, col);

                // Adding mine coords to list
                minesCoords.add((row) + " " + (col));
            } else {
                // Incrementing when free place was not found
                numOfMines++;
            }
        }

        // Replacing all mines coords with '.'
        for (String mine : minesCoords) {
            int row = Integer.parseInt(mine.split(" ")[0]);
            int col = Integer.parseInt(mine.split(" ")[1]);
            board[row][col] = '.';
        }

    }

    private static void setMinesValues(int row, int col){
        int rowStart  = Math.max( row - 1, 0 );
        int rowFinish = Math.min( row + 1, board.length - 1 );
        int colStart  = Math.max( col - 1, 0 );
        int colFinish = Math.min( col + 1, board.length - 1 );

        for ( int curRow = rowStart; curRow <= rowFinish; curRow++ ) {
            for ( int curCol = colStart; curCol <= colFinish; curCol++ ) {

                // setting mine vales
                if (board[curRow][curCol] == '.'){
                    board[curRow][curCol] = '1';
                } else if (board[curRow][curCol] != '.') {
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
        System.out.println(" |123456789|");
        System.out.println("-|---------|");

        int i = 1;

        for(char[] row : board){
            System.out.print(i + "|");
            System.out.println(Arrays.toString(row).replaceAll(" ", "").replaceAll("\\[", "")
                    .replaceAll("]", "").replaceAll(",", "") + "|");

            i++;
         }

        System.out.println("-|---------|");
    }

}