package maze;

import java.util.Arrays;
import java.util.Random;

public class Main {

    private static int[][] maze = new int[10][10];

    public static void main(String[] args) {
        setBorders();
        generateMaze();

        printMaze();
    }

    private static void generateMaze(){
        Random r = new Random();

        // Generating entrance and exit
        int entrance = r.nextInt(9 - 1) + 1;
        int exit = r.nextInt(9 - 1) + 1;
        maze[entrance][0] = 0;
        maze[exit][9] = 0;

        //



    }


    private static void printMaze() {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if(maze[row][col] == 1){
                    System.out.print("\u2588\u2588");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    private static void setBorders() {
        // Filling rows
        Arrays.fill(maze[0], 1);
        Arrays.fill(maze[9], 1);

        // Filling columns
        for (int i = 0; i < maze[i].length - 1; i++) {
            maze[i][0] = 1;
            maze[i][9] = 1;
        }
    }
}
