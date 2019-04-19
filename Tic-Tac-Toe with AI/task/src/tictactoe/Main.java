package tictactoe;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter cells: ");
        String cells = sc.nextLine();
        int numOfX = 0;
        int numOfO = 0;

        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char temp = cells.charAt(i*3+j);
                if(temp == 'O') {
                    board[i][j] = temp;
                    numOfO++;
                }
                else if (temp == 'X') {
                    board[i][j] = temp;
                    numOfX++;
                }
                else if (temp == ' ') board[i][j] = temp;
            }
        }

        System.out.println("---------");
        for(char[] row : board){
            System.out.println(Arrays.toString(row).replaceAll("\\[","| ")
            .replaceAll("]", " |").replaceAll(",",""));
        }
        System.out.println("---------");

    }
}



