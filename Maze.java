package magicMaze;

import java.util.ArrayList;

import magicMaze.cells.Cell;

public class Maze {

	int width, height;
	ArrayList<ArrayList<Cell>> map;

	public Maze(int width, int height, ArrayList<ArrayList<Cell>> map) {
		this.width = width;
		this.height = height;
		this.map = map;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Cell get(int x, int y) {
		return map.get(x).get(y);
	}

	public Cell get(Coords coords) {
		return map.get(coords.getX()).get(coords.getY());	
	}
	
}