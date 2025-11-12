/**
 * This class has been completed for you.
 * You should not make any change on this class.
 */
public class Turtle {
    private final String color;
    private int row;
    private int col;

    public Turtle(String color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }
    public Turtle(String color) {
        this(color, 0, 0);
    }
    
    public String getColorSymbol() {
        return color.substring(0, 1).toUpperCase();
    }
    
    public String getColor() {
        return color;
    }
    
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String toString() {
        return "Turtle{" + color + " at (" + row + "," + col + ")}";
    }
}
