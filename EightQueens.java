package com.company;

public class EightQueens {
    private int queensMatrix[][];
    private int minimum_costs[];
    private int safeCopyQueensBoard[][];
    private int MATRIX_SIZE = -1;
    private int indexI = -1, indexJ = -1;

    public EightQueens(){
        MATRIX_SIZE = -1;
    }
    public EightQueens(int boardSize){
        MATRIX_SIZE = boardSize;
        queensMatrix  = new int [boardSize][boardSize];
        minimum_costs = new int [boardSize];
        safeCopyQueensBoard = new int [boardSize][boardSize];

    }

    void addQueenAt(int Row, int Column){
        queensMatrix[Row-1][Column-1] = 1;
        safeCopyQueensBoard[Row-1][Column-1] = 1;
    }

    void printBoard(){
        for (int i = 0; i < MATRIX_SIZE ; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                String queen = (queensMatrix[i][j] == 0)?".":"W";
                System.out.print(queen + "  ");
            }
            System.out.println("");
        }
        System.out.println();
    }

    void hillCLimbingSolution(){

        boolean climb = true;
        int hCostInitial = heuristicSearch(queensMatrix);
        int iterations = 0;

        while(climb) {
            int comparisonCost = heuristicSearch(queensMatrix);
            System.out.println("Heuristic cost "+comparisonCost);

            // go column by column and find lowest cost for each column
            for (int columns = 0; columns < MATRIX_SIZE; columns++) {
                this.findLowestCostFor(queensMatrix, columns);
            }
            // find smallest from small costs
            int index = -1;
            index = (int) (Math.random()*MATRIX_SIZE);
            System.out.println("Take movement on column " + index);
            // flush column
            for (int i = 0; i < MATRIX_SIZE; i++) {
                queensMatrix[i][index] = 0;
            }
            // update latest values of row and column
            this.findLowestCostFor(queensMatrix, index);
            // move queen to next position
            queensMatrix[indexI][indexJ] = 1;
            printBoard();
            // flush minimum values
            for (int i = 0; i < MATRIX_SIZE ; i++) {
                minimum_costs[i] = 0;
            }

            if (heuristicSearch(queensMatrix) == 0){
                System.out.println("Stopped with cost "+ heuristicSearch(queensMatrix)+", and "+iterations+" steps.");
                printBoard();
                break;
            }
            // too much iterations reset things and go again
            if (iterations > 4){
                System.out.println("<<<< Start again >>>>");
                for (int i = 0; i < MATRIX_SIZE; i++) {
                    for (int j = 0; j < MATRIX_SIZE ; j++) {
                        queensMatrix[i][j] = safeCopyQueensBoard[i][j];
                    }
                }
                printBoard();
                iterations = 0;
            }
                iterations ++;
        }
    }

    /*
     column if totally cleared,
     find minimum cost of moving a queen in single column
     going from top to bottom, by moving each place herustic cost is
     calculated and found minimum for whole column
     */
    private void findLowestCostFor(int [][]inputMatrix, int column){
        int minimum = 10_000, cost = -1;
        int [][]copy_inputMatrix = new int [inputMatrix.length][inputMatrix.length];
        copy_inputMatrix = this.makeCopyOfArray(inputMatrix);

        // clean totally one column
        for (int i = 0; i < MATRIX_SIZE; i++) {
            copy_inputMatrix[i][column] = 0;
        }
        // move queen one by one place in column, compute heuristic and store minimum value
        for (int i = 0; i < MATRIX_SIZE; i++) {
            copy_inputMatrix[i][column] = 1;
            cost = this.heuristicSearch(copy_inputMatrix);
            if(cost <= minimum){
                minimum = cost;
                indexI = i;
                indexJ = column;
            }
            // return back original value since queen can be only at one position at a time
            // we do not want whole column to be full of queens
            copy_inputMatrix[i][column] = 0;
        }

        minimum_costs[column] = cost;

    }

    private int [][] makeCopyOfArray(int [][] originalMatrix){
        int [][]copyMatrix = new int [originalMatrix.length][originalMatrix.length];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                copyMatrix[i][j] = originalMatrix[i][j];
            }
        }
        return copyMatrix;
    }


    public int heuristicSearch(int [][]input){
        int attackingPairs = 0;
        // search for the row pairs
        for (int i = 0; i < MATRIX_SIZE; i++) {
            int counter = 0;
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if(input[i][j] == 1)
                    counter++;
            }
            // one row is checked, see whether there is pair or more
            if (counter >= 2)
                attackingPairs += (counter-1);
        }

        // search for right diagonal
        attackingPairs += this.findPairs(MATRIX_SIZE, MATRIX_SIZE, input);

        // find attacking on left diagonal,
        // flip the matrix and apply again right search
        int [][]copyOfArray = mirror(MATRIX_SIZE,MATRIX_SIZE, input);
        attackingPairs += this.findPairs(MATRIX_SIZE,MATRIX_SIZE, copyOfArray);

        return attackingPairs;

    }

    // right diagonal search for atackign pairs
    private int findPairs(int width, int height, int [][]in){
        int pairs = 0;
        for( int k = 0 ; k < MATRIX_SIZE * 2 ; k++ ) {
            int counter = 0;
            for( int j = 0 ; j <= k ; j++ ) {
                int i = k - j;
                if( i < MATRIX_SIZE && j < MATRIX_SIZE ) {
                    if (in[i][j] == 1)
                        counter++;
                }
            }
            if(counter >= 2)
                pairs += (counter-1);
        }
        return pairs;
    }

    // making mirror of array
    private int[][] mirror(int width, int height, int[][] in) {
        int[][] out = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                out[i][width - j - 1] = in[i][j];
            }
        }
        return out;
    }

}
