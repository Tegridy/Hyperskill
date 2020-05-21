package maze;

public class Vertex {

    private int placeInRow;
    private int placeInColumn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex Vertex = (Vertex) o;
        return placeInRow == Vertex.placeInRow &&
                placeInColumn == Vertex.placeInColumn;
    }

    public int getPlaceInRow() {
        return placeInRow;
    }

    public void setPlaceInRow(int placeInRow) {
        this.placeInRow = placeInRow;
    }

    public int getPlaceInColumn() {
        return placeInColumn;
    }

    public void setPlaceInColumn(int placeInColumn) {
        this.placeInColumn = placeInColumn;
    }
}
