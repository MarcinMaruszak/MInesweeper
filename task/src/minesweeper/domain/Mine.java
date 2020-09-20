package minesweeper.domain;

import java.util.Objects;

public class Mine {
    private int x;
    private int y;
    private boolean marked;

    public Mine(int x, int y) {
        this.x = x;
        this.y = y;
        marked =false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mine mine = (Mine) o;
        return x == mine.x &&
                y == mine.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
