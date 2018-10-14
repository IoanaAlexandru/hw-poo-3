package magicMaze.cells;

import magicMaze.Coords;
import magicMaze.exceptions.CannotMoveIntoWallsException;

public class WallCell extends Cell {

	public WallCell(Coords coords) {
		super(coords);
	}

	@Override
	public boolean moveHere() throws CannotMoveIntoWallsException {
		throw new CannotMoveIntoWallsException();
	}
	
}