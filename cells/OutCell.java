package magicMaze.cells;

import magicMaze.Coords;

public class OutCell extends Cell {

	public OutCell(Coords coords) {
		super(coords);
	}

	@Override
	public boolean moveHere() {
		return false;
	}
	
}