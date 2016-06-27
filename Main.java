package com.company;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        EightQueens board1 = new EightQueens(8);

        board1.addQueenAt(4,4);
        board1.addQueenAt(5,1);
        board1.addQueenAt(5,5);
        board1.addQueenAt(6,2);
        board1.addQueenAt(6,6);
        board1.addQueenAt(7,3);
        board1.addQueenAt(7,7);
        board1.addQueenAt(6,8);

        board1.printBoard();

        board1.hillCLimbingSolution();

	 board1.printBoard();


   }
}

