package magicMaze;

public class Coords {

	private int x, y;

	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int decX() {
		return --x;
	}

	public int incX() {
		return ++x;
	}

	public int decY() {
		return --y;
	}

	public int incY() {
		return ++y;
	}

	@Override
	public String toString() {
		return Integer.toString(x) + " " + Integer.toString(y);
	}
}