package data;

import java.util.ArrayList;
import java.util.List;

public class SolitaireField {

    /**
     * Represents the solitaire field. True if pin is set, false if not.
     */
    private boolean pins[][];

    /**
     * Contains the possible moves on this solitaireField. A move is represented
     * by four integers, the row and column of the starting field as well as the
     * row and column of the ending field.
     */
    private List<int[]> moves;

    /**
     * Creates a solitaire field with the default representation, where the pin
     * in the middle is missing.
     */
    public SolitaireField() {
        this.pins = createCompleteField();
        this.removePin(3, 3);
    }

    /**
     * Creates a solitaire field with a missing pin at the provided position.
     * 
     * @param row
     *            The row of the missing pin.
     * @param column
     *            The column of the missing pin.
     */
    public SolitaireField(int column, int row) {
        this.pins = createCompleteField();
        this.removePin(column, row);
        calculateMoves();
    }

    /**
     * Creates a solitaire field with the same structure like the provided
     * array.
     * 
     * @param pins
     *            An array which represents the solitaire field.
     */
    public SolitaireField(boolean[][] pins) {
        this.setPins(pins);
        calculateMoves();
    }

    /**
     * Sets the pins similar to the provided array. The pins will only be set,
     * if the array has the correct shape, which means it has to be a 7x7 array
     * and the four fields in every corner have to be set to false.
     * 
     * @param pins
     *            An array which represents the solitaire field.
     */
    public void setPins(boolean[][] pins) {
        // check shape of array
        if (isShapeCorrect(pins)) {
            this.pins = pins;
        }
        calculateMoves();
    }

    /**
     * Returns the array which represents the pins.
     * 
     * @return The array of pins.
     */
    public boolean[][] getPins() {
        return pins;
    }

    /**
     * Sets a pin to true if it is accessible. The pin will not be changed if it
     * is already true.
     * 
     * @param row
     *            The row number of the pin to be set.
     * @param column
     *            The column number of the pin to be set.
     */
    public void setPin(int row, int column) {
        if (isAccessible(row, column)) {
            this.pins[row][column] = true;
        }
        calculateMoves();
    }

    /**
     * Sets a pin to false if it is accessible. The pin will not be changed if
     * it is already false.
     * 
     * @param row
     *            The row number of the pin to be removed.
     * @param column
     *            The column number of the pin to be removed.
     */
    public void removePin(int row, int column) {
        if (isAccessible(row, column)) {
            this.pins[row][column] = false;
        }
        calculateMoves();
    }

    /**
     * Overwrites the pin with the provided value if it is accessible.
     * 
     * @param row
     *            The row number of the pin to be overwritten.
     * @param column
     *            The column number of the pin to be overwritten.
     * @param value
     *            The value to overwrite the pin.
     */
    public void overwritePin(int row, int column, boolean value) {
        if (isAccessible(row, column)) {
            this.pins[row][column] = value;
        }
        calculateMoves();
    }

    /**
     * Returns the value of the pin in the provided row and column. If the pin
     * is not accessible, false is returned.
     * 
     * @param row
     *            The row of the pin to be returned.
     * @param column
     *            The column of the pin to be returned.
     *
     * @return The value of the pin in the provided row and column. False if the
     *         pin is not accessible.
     */
    public boolean getPin(int row, int column) {
        if (isAccessible(row, column)) {
            return this.pins[row][column];
        }
        return false;
    }

    /**
     * Returns true if the pin in the provided row and column is set and false
     * if it is not set or if the pin is not accessible.
     * 
     * @param row
     *            The row of the pin to be returned.
     * @param column
     *            The column of the pin to be returned.
     *
     * @return True if the pin in the provided row and column is set. False if
     *         the pin is not set or not accessible.
     */
    public boolean isPinSet(int row, int column) {
        if (isAccessible(row, column)) {
            return this.pins[row][column];
        } else {
            return false;
        }
    }

    /**
     * Returns the number of set pins.
     * 
     * @return The number of set pins.
     */
    public int countSetPins() {
        int setPins = 0;
        for (int i = 0; i < pins[0].length; i++) {
            for (int j = 0; j < pins[1].length; j++) {
                if (isPinSet(i, j)) {
                    setPins++;
                }
            }
        }
        return setPins;
    }

    /**
     * Calculates the currently possible moves on the solitaire field and stores
     * them in the private field {@link SolitaireField#moves}.
     */
    public void calculateMoves() {
        this.moves = new ArrayList<int[]>();
        // iterate through all pins
        for (int i = 0; i < pins[0].length; i++) {
            for (int j = 0; j < pins[1].length; j++) {
                // only existing pins can be moved
                if (isPinSet(i, j)) {
                    // check if pin can be moved up
                    if (isPinSet(i - 1, j)
                            && (!isPinSet(i - 2, j) && isAccessible(i - 2, j))) {
                        int[] m = { i, j, i - 2, j };
                        moves.add(m);
                    }
                    // check if pin can be moved down
                    if (isPinSet(i + 1, j)
                            && (!isPinSet(i + 2, j) && isAccessible(i + 2, j))) {
                        int[] m = { i, j, i + 2, j };
                        moves.add(m);
                    }
                    // check if pin can be moved left
                    if (isPinSet(i, j - 1)
                            && (!isPinSet(i, j - 2) && isAccessible(i, j - 2))) {
                        int[] m = { i, j, i, j - 2 };
                        moves.add(m);
                    }
                    // check if pin can be moved right
                    if (isPinSet(i, j + 1)
                            && (!isPinSet(i, j + 2) && isAccessible(i, j + 2))) {
                        int[] m = { i, j, i, j + 2 };
                        moves.add(m);
                    }
                }
            }
        }
    }

    /**
     * Removes the move with the provided index from the list of moves if the
     * index is not greater than the highest index and greater or equal to 0.
     * 
     * @param index
     *            The index of the Move to be removed.
     */
    public void removeMove(int index) {
        if (index < this.moves.size() && index >= 0) {
            moves.remove(index);
        }
    }

    /**
     * Returns the List which contains the possible moves.
     * 
     * @return The List containing the possible moves.
     */
    public List<int[]> getMoves() {
        return this.moves;
    }

    /**
     * Returns a string which represents the solitaire field in ASCII art.
     * 
     * @return A string which represents the solitaire field in ASCII art.
     */
    public String toString() {
        StringBuilder field = new StringBuilder();
        field.append("    +-------+\n");
        field.append("    | ");
        for (int i = 2; i < 5; i++) {
            field.append(toChar(this.pins[0][i]));
            field.append(" ");
        }
        field.append("|\n");
        field.append("+---+ ");
        for (int i = 2; i < 5; i++) {
            field.append(toChar(this.pins[1][i]));
            field.append(" ");
        }
        field.append("+---+\n");
        for (int i = 2; i < 5; i++) {
            field.append("| ");
            for (int j = 0; j < 7; j++) {
                field.append(toChar(this.pins[i][j]));
                field.append(" ");
            }
            field.append("|\n");
        }
        field.append("+---+ ");
        for (int i = 2; i < 5; i++) {
            field.append(toChar(this.pins[5][i]));
            field.append(" ");
        }
        field.append("+---+\n");
        field.append("    | ");
        for (int i = 2; i < 5; i++) {
            field.append(toChar(this.pins[6][i]));
            field.append(" ");
        }
        field.append("|\n");
        field.append("    +-------+");
        return field.toString();
    }

    /**
     * Returns a character representation of the pin. If pin is set to true, the
     * character 'O' is returned, otherwise a blank.
     * 
     * @param pin
     *            The pin to be converted to a character.
     * @return A character representation of the pin.
     */
    private char toChar(boolean pin) {
        if (pin) {
            return 'O';
        } else {
            return ' ';
        }
    }

    /**
     * Checks if the provided array has the correct shape, i.e. a size of 7x7
     * and no pins at the corners.
     * 
     * @param pins
     *            The array to be checked.
     * @return True if shape is correct, false is shape is not correct.
     */
    private boolean isShapeCorrect(boolean[][] pins) {
        // several if clauses instead of a single one to increase readability
        // array has the wrong size
        if (pins[0].length != 7 || pins[1].length != 7) {
            return false;
        }
        // pin in the top left corner
        if (pins[0][0] == true || pins[0][1] == true || pins[1][0] == true
                || pins[1][1] == true) {
            return false;
        }
        // pin in the top right corner
        if (pins[0][5] == true || pins[0][6] == true || pins[1][5] == true
                || pins[1][6] == true) {
            return false;
        }
        // pin in the bottom left corner
        if (pins[5][0] == true || pins[5][1] == true || pins[6][0] == true
                || pins[6][1] == true) {
            return false;
        }
        // pin in the bottom right corner
        if (pins[5][5] == true || pins[6][6] == true || pins[6][5] == true
                || pins[6][6] == true) {
            return false;
        }
        return true;
    }

    /**
     * Creates an array with all possible fields, i.e. all fields except the
     * corners set to true.
     * 
     * @return The created array with all possible fields set to true.
     */
    private boolean[][] createCompleteField() {
        boolean[][] pins = new boolean[7][7];
        for (int i = 0; i < pins[0].length; i++) {
            for (int j = 0; j < pins[1].length; j++) {
                pins[i][j] = isAccessible(i, j);
            }
        }
        return pins;
    }

    /**
     * Checks if the field in the provided row and column can be set or removed.
     * 
     * @param column
     *            The column to be checked.
     * @param row
     *            The row to be checked.
     * @return True if the field can be set, false if it can not be set.
     */
    private boolean isAccessible(int row, int column) {
        if (row > 6 || row < 0 || column > 6 || column < 0) {
            return false;
        }
        // pin in the top left corner
        if ((row == 0 && column == 0) || (row == 0 && column == 1)
                || (row == 1 && column == 0) || (row == 1 && column == 1)) {
            return false;
        }
        // pin in the top left corner
        if ((row == 0 && column == 5) || (row == 0 && column == 6)
                || (row == 1 && column == 5) || (row == 1 && column == 6)) {
            return false;
        }
        // pin in the top left corner
        if ((row == 5 && column == 0) || (row == 5 && column == 1)
                || (row == 6 && column == 0) || (row == 6 && column == 1)) {
            return false;
        }
        // pin in the top left corner
        if ((row == 5 && column == 5) || (row == 5 && column == 6)
                || (row == 6 && column == 5) || (row == 6 && column == 6)) {
            return false;
        }
        return true;
    }
}
