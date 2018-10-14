package magicMaze.cells;

import magicMaze.Coords;

public class InCell extends Cell {

	public InCell(Coords coords) {
		super(coords);
	}

	@Override
	public boolean moveHere() {
		incVisited();
		return true;
	}

}