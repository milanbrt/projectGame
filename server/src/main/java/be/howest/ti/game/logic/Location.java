package be.howest.ti.game.logic;

public class Location {

    private final int row;
    private final int col;

    public Location() {
        this.row = 0;
        this.col = 0;
    }

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String toString() {
        return "Location{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
