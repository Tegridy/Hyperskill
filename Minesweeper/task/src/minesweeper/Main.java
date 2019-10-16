package minesweeper;

/*
The game contain the following rules:

        - The game starts with an empty field.
        - The user can mark (flag) some cells as cells that potentially have a mine. Any empty cell can be flagged, not only cells that really contain a mine. In the example, this is done by typing the word "mine" after entering the coordinates. After that, the cell is marked with a '*' (a mine flag).
        - The user can also remove mine flags from cells. In the example, the user simply types 'mine' again after entering the same coordinates of the cell. After that, the cell should be marked as an empty cell.
        - The only way to get information about the field is to explore it. In the example, this is done by typing 'free' after entering the coordinates of the cell. This means that the user thinks that this cell is free of mines and thus can be explored. Make the generation of mines as in the original game - the first cell marked as "free" cannot be a mine, it should always be an empty field. You can achieve this in many ways, it's up to you.
          Obviously, if a cell has 0 mines around it, you can explore all the cells around it without any problems. Therefore this should be done automatically by the program. Also, if there is yet another cell next to it with 0 mines around it, the program should automatically check all the cells around that cell, and so on until no cells can be checked automatically. In the example, all cells with 0 mines around them are marked with a '/' symbol.
        - If the user wants to explore a cell with 1 to 8 mines around it, the program should only expose that cell, because mines could be anywhere.
        - If the user wants to explore a cell that contains a mine, the user loses. After that, you can show all the mines on the field.
        - If the user marks all the cells with potential mines, the user wins. Note that the user must mark all the mines, but no empty cells; if the user has extra mine flags that do not contain mines, the user should continue playing. After clearing all excess mine flags, he user wins.
        - There is another way to win. The user can simply explore all explorable cells, leaving only cells with mines. After that, the user wins.
*/

import java.util.*;

public class Main {

    private static char[][] board = new char[9][9];
    private static ArrayList<String> minesCoords = new ArrayList<>();
    // Map contains coords for cells that surround mine and value for each of them
    private static HashMap<String, Character> minesBordersCoords = new HashMap<>();

    private static boolean firstMove = true;
    private static int numOfMines = 0;
    private static int emptyCells = 9 * 9;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        fillBoard();

        System.out.print("How many mines do you want on the field? ");
        numOfMines = sc.nextInt();
        System.out.println();
        printBoard();

        int numOfFlags = 0;
        int fieldsWithMine = 0;

        while (true){
            try {
                if ( (numOfMines == fieldsWithMine && numOfMines == numOfFlags) || (numOfMines == emptyCells) ) {
                    System.out.println("Congratulations! You founded all mines!");
                    break;
                }

                System.out.print("Set/delete mines marks (row and y coordinates):");

                int row = sc.nextInt() - 1;
                int col = sc.nextInt() - 1;
                String type = sc.next();
                System.out.println();

                if (type.equals("free")){
                    if (minesCoords.contains(row + " " + col)){
                        System.out.println("You stepped on a mine and failed!");
                        replaceMineCoords('X');
                        printBoard();
                        break;
                    }

                    floodFill(row, col);

                } else if (type.equals("mine")){

                    if (board[row][col] == '.') {
                        board[row][col] = '*';
                        numOfFlags++;

                        if (minesCoords.contains(row + " " + col)) {
                            fieldsWithMine++;
                        }
                    } else if (board[row][col] == '*') {
                        if(minesBordersCoords.containsKey(row + " " + col)){
                            board[row][col] = minesBordersCoords.get(row + " " + col);
                            emptyCells--;
                        }

                        board[row][col] = '.';
                        numOfFlags--;

                        if (minesCoords.contains(row + " " + col)) {
                            fieldsWithMine--;
                        }
                    } else {
                        System.out.println("There is a number here!");
                        continue;
                    }

                }

                printBoard();

            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Wrong coordinates. Use range from 1-9!");
                printBoard();
            }
        }
    }

    private static void randomizeMines(int numOfMines, int safeZonePosX, int safeZonePosY){
        // There cant be mine near first move so we create a safe zone near user first cell

        ArrayList<String> safeZone = new ArrayList<>();

        for (int currRow = Math.max(safeZonePosX - 1, 0); currRow <= Math.min(safeZonePosX + 1, board.length - 1); currRow++) {
            for (int currCol = Math.max(safeZonePosY - 1, 0); currCol <= Math.min(safeZonePosY + 1, board.length - 1) ; currCol++) {
                safeZone.add(currRow + " " + currCol);
            }
        }

        Random random = new Random();

        // Placing mines in random places and setting values surrounding mine

        for (int i = 0; i < numOfMines; i++) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            if (board[row][col] != '/' && !minesCoords.contains(row + " " + col) && !safeZone.contains(row + " " + col)
            && board[row][col] != '*'){

                setMinesValues(row, col);

                // Adding mine coords to list
                minesCoords.add((row) + " " + (col));
            } else {
                // Incrementing when free place was not found
                numOfMines++;
            }
        }
        // Replacing all mines coords with '.'
        replaceMineCoords('.');

    }

    private static void replaceMineCoords(char s) {
        for (String mine : minesCoords) {
            int row = Integer.parseInt(mine.split(" ")[0]);
            int col = Integer.parseInt(mine.split(" ")[1]);
            board[row][col] = s;
        }
    }

    private static void setMinesValues(int row, int col){
        int rowStart  = Math.max( row - 1, 0 );
        int rowFinish = Math.min( row + 1, board.length - 1 );
        int colStart  = Math.max( col - 1, 0 );
        int colFinish = Math.min( col + 1, board.length - 1 );

        for ( int curRow = rowStart; curRow <= rowFinish; curRow++ ) {
            for ( int curCol = colStart; curCol <= colFinish; curCol++ ) {
                // Setting values surrounding mine
                if (row != curRow || col != curCol) {

                    if (minesBordersCoords.containsKey(curRow + " " + curCol)) {
                        char c = minesBordersCoords.get(curRow + " " + curCol);
                        c++;
                        minesBordersCoords.put(curRow + " " + curCol, c);
                    } else {
                        minesBordersCoords.put(curRow + " " + curCol, '1');
                    }
                }

            }
        }
    }

    private static void floodFill(int row, int col){

            char toReplace = '.';
            char replacement = '/';

            int wallPos_1 = -1;
            int wallPos_2 = 9;

            if (row == wallPos_1 || row == wallPos_2 || col == wallPos_1 || col == wallPos_2 || board[row][col] == replacement) return;

            if (board[row][col] == '*' && minesBordersCoords.containsKey(row + " " + col)) {
                board[row][col] = minesBordersCoords.get(row + " " + col);
                return;
            }
            if (board[row][col] == '*') {
                board[row][col] ='/';
            }
            if (board[row][col] != toReplace) return;


            if (minesBordersCoords.containsKey(row + " " + col)) {
                board[row][col] = minesBordersCoords.get(row + " " + col);
                emptyCells--;
                return;
            }

            board[row][col] = replacement;
            emptyCells--;


            // In first move we cant hit mine
            if (firstMove) {
                randomizeMines(numOfMines, row, col);
                firstMove = false;
            }

            floodFill(row + 1, col); // S
            floodFill(row + 1, col - 1); // SW
            floodFill(row + 1, col + 1); // SE
            floodFill(row - 1, col); // N
            floodFill(row - 1, col - 1); // NW
            floodFill(row - 1, col + 1); // NE
            floodFill(row, col - 1); // W
            floodFill(row, col + 1); // E

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