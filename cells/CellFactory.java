package magicMaze.cells;

import java.lang.IllegalArgumentException;

import magicMaze.Coords;

public class CellFactory {

	public static Cell getCell(Coords coords, char type) {
		switch(type) {
			case 'I':
				return new InCell(coords);
			case 'O':
				return new OutCell(coords);
			case '.':
				return new FreeCell(coords);
			case '#':
				return new WallCell(coords);
			default:
				throw new IllegalArgumentException();
		}
	}
	
}