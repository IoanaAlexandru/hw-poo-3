package magicMaze.cells;

import java.util.Comparator;

public class CellComparator implements Comparator<Cell>{

	private int getPriority(char direction) {
		switch(direction) {
			case 'R':
				return 4;
			case 'F':
				return 3;
			case 'L':
				return 2;
			case 'B':
				return 1;
			default:
				return 0;
		}
	}

	@Override
	public int compare(Cell c1, Cell c2) {
		if (c2.getVisited() == c1.getVisited())
			return getPriority(c2.getDirection()) - getPriority(c1.getDirection());
		else
			return c1.getVisited() - c2.getVisited();
	}

}