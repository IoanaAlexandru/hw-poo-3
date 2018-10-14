package magicMaze.cells;

import magicMaze.Coords;

public class FreeCell extends Cell {

	public FreeCell(Coords coords) {
		super(coords);
	}

	@Override
	public boolean moveHere() {
		incVisited();
		return true;
	}

}