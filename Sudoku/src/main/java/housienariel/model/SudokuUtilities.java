package housienariel.model;

import java.util.Random;

public class SudokuUtilities {

    public enum SudokuLevel {EASY, MEDIUM, HARD}
    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;
    /**
    * Create a 3-dimensional matrix with initial values and solution in Sudoku.
    *
    * @param level The level, i.e. the difficulty, of the initial standing.
    * @return A 3-dimensional int matrix.
    * [row][col][0] represents the initial values, zero representing an empty
    cell.
    * [row][col][1] represents the solution.
    * @throws IllegalArgumentException if the length of stringRepresentation is
    not 2*81 characters and
    * for characters other than '0'-'9'.
    */
    public static int[][][] generateSudokuMatrix(SudokuLevel level) {
        String representationString;
        representationString = switch (level) {
            case EASY -> easy;
            case MEDIUM -> medium;
            case HARD -> hard;
            default -> medium;
        };

        int[][][] matrix = convertStringToIntMatrix(representationString);
        Random random = new Random();
        int variation = random.nextInt(3);

        switch (variation) {
            case 1 -> matrix = flipHorizontally(matrix);
            case 2 -> matrix = flipVertically(matrix);
        }
        return matrix;
    }
    /**
    * Create a 3-dimensional matrix with initial values and solution in Sudoku.
    *
    * @param stringRepresentation A string of 2*81 characters, 0-9. The first 81
    characters represents
    * the initial values, '0' representing an empty
    cell.
    * The following 81 characters represents the
    solution.
    * @return A 3-dimensional int matrix.
    * [row][col][0] represents the initial values, zero representing an empty
    cell.
    * [row][col][1] represents the solution.
    * @throws IllegalArgumentException if the length of stringRepresentation is
    not 2*81 characters and
    * for characters other than '0'-'9'.
    */
    /*package private*/
    static int[][][] convertStringToIntMatrix(String stringRepresentation) {
        if (stringRepresentation.length() != GRID_SIZE * GRID_SIZE * 2)
            throw new IllegalArgumentException("representation length " +
                    stringRepresentation.length());
        int[][][] values = new int[GRID_SIZE][GRID_SIZE][2];
        char[] charRepresentation = stringRepresentation.toCharArray();
        int charIndex = 0;
        // initial values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][0] =
                        convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }
        // solution values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][1] =
                        convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }
        return values;
    }

    /**
     * Convert a character to an integer in the range 0-9.
     * @param ch the character to convert
     * @return the integer value of the character
     */
    private static int convertCharToSudokuInt(char ch) {
        if (ch < '0' || ch > '9') throw new IllegalArgumentException("character " +
                ch);
        return ch - '0';
    }

    /**
     * Flip the matrix horizontally.
     * @param matrix the matrix to flip
     * @return the flipped matrix
     */
    private static int[][][] flipHorizontally(int[][][] matrix) {
        int[][][] flipped = new int[GRID_SIZE][GRID_SIZE][2];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                flipped[row][col][0] = matrix[row][GRID_SIZE - col - 1][0];
                flipped[row][col][1] = matrix[row][GRID_SIZE - col - 1][1];
            }
        }
        return flipped;
    }

    /**
     * Flip the matrix vertically.
     * @param matrix the matrix to flip
     * @return the flipped matrix
     */
    private static int[][][] flipVertically(int[][][] matrix) {
        int[][][] flipped = new int[GRID_SIZE][GRID_SIZE][2];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                flipped[row][col][0] = matrix[GRID_SIZE - row - 1][col][0];
                flipped[row][col][1] = matrix[GRID_SIZE - row - 1][col][1];
            }
        }
        return flipped;
    }

    private static final String easy =
                    "000914070" +
                    "010000054" +
                    "040002000" +
                    "007569001" +
                    "401000500" +
                    "300100000" +
                    "039000408" +
                    "650800030" +
                    "000403260" + // solution values after this substring
                    "583914672" +
                    "712386954" +
                    "946752183" +
                    "827569341" +
                    "461238597" +
                    "395147826" +
                    "239675418" +
                    "654821739" +
                    "178493265";
    private static final String medium =
                    "300000010" +
                    "000050906" +
                    "050401200" +
                    "030000080" +
                    "002069400" +
                    "000000002" +
                    "900610000" +
                    "200300058" +
                    "100800090" +
                    "324976815" +
                    "718253946" +
                    "659481273" +
                    "536142789" +
                    "872569431" +
                    "491738562" +
                    "985617324" +
                    "267394158" +
                    "143825697";
    private static final String hard =
                    "030600000" +
                    "000010070" +
                    "080000000" +
                    "000020000" +
                    "340000800" +
                    "500030094" +
                    "000400000" +
                    "150800200" +
                    "700006050" +
                    "931687542" +
                    "465219378" +
                    "287345916" +
                    "876924135" +
                    "349561827" +
                    "512738694" +
                    "693452781" +
                    "154873269" +
                    "728196453";
}
