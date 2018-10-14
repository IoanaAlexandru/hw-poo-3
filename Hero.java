package magicMaze;

import java.util.ArrayList;
import java.lang.IndexOutOfBoundsException;
import java.util.Collections;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

import magicMaze.exceptions.*;
import magicMaze.cells.*;

public class Hero {

	Coords coords;
	char orientation; // '^', '>', '<', 'v'
	Maze maze;

	public Hero(Maze maze) {
		int i, j;
		for (i = 0; i < maze.getHeight(); i++) {
			for (j = 0; j < maze.getWidth(); j++) {
				if (maze.get(i, j) instanceof InCell) {
					coords = new Coords(i, j);
					break;
				}
			} 
		}
		orientation = '^';
		maze.get(coords).incVisited();
		this.maze = maze;
	}

	public Coords getCoords() {
		return coords;
	}

	public char getOrientation() {
		return orientation;
	}

	public Maze getMaze() {
		return maze;
	}

	/**
	* Refreshes the direction (TASK 1) and status (TASK 2) of neighboring cell.
	*
	* @param o1, o2, o3, o4 - orientation order
	*        d - direction
	*        queue - queue of cells waiting to be processed
	*        origin - queue of origin cells (for backtracking)
	* @return coordinates of exit if it is next to the hero, or null otherwise.
	*/
	private Coords look(char o1, char o2, char o3, char o4, char d,
		                Queue<Cell> queue, Queue<Cell> origin) throws HeroOutOfGroundException {
		Coords pos = new Coords(coords.getX(), coords.getY()),
		       exit = null;
		
		try {
			if (orientation == o1)
				pos.incY();
			else if (orientation == o2)
				pos.incX();
			else if (orientation == o3)
				pos.decX();
			else if (orientation == o4)
				pos.decY();
			
			maze.get(pos).setDirection(d);

			if (maze.get(pos).getStatus() == 'R')
				maze.get(pos).setStatus('W');
		} catch(IndexOutOfBoundsException e) {
			throw new HeroOutOfGroundException();
		}

		if (maze.get(pos) instanceof OutCell)
			exit = pos;
		if (!(maze.get(pos) instanceof WallCell) && maze.get(pos).getStatus() == 'W') {
			queue.add(maze.get(pos));
			origin.add(maze.get(coords));
		}

		return exit;
	}

	/**
	* "Looks" in all directions.
	*
	* @param queue - queue of cells waiting to be processed
	*        origin - queue of origin cells (for backtracking)
	* @return coordinates of exit if it is next to the hero, or null otherwise.
	*/
	private Coords lookAround(Queue<Cell> queue, Queue<Cell> origin) {
		Coords exit = null;

		// Look right
		try {
			exit = look('^', '>', '<', 'v', 'R', queue, origin);
		} catch(HeroOutOfGroundException e) {
		}

		if (exit != null)
			return exit;

		// Look front
		try {
			exit = look('>', 'v', '^', '<', 'F', queue, origin);
		} catch(HeroOutOfGroundException e) {
		}

		if (exit != null)
			return exit;

		// Look left
		try {
			exit = look('v', '<', '>', '^', 'L', queue, origin);
		} catch(HeroOutOfGroundException e) {
		}

		if (exit != null)
			return exit;

		// Look back
		try {
			exit = look('<', '^', 'v', '>', 'B', queue, origin);
		} catch(HeroOutOfGroundException e) {
		}

		return exit;
	}

	/**
	* Moves hero one cell according to the given algorithm.
	*
	* @return true if the hero should keep moving, and false otherwise (if the exit was found)
	*/
	public boolean move() {
		Queue<Cell> possibleDirections = new LinkedList<Cell>(), origin = new LinkedList<Cell>();
		Coords pos = lookAround(possibleDirections, origin);

		// Checks if the exit was found
		if (pos != null) {
			coords = pos;
			return false;
		}
		
		Collections.sort((LinkedList<Cell>)possibleDirections, new CellComparator());
		Coords newCoords = possibleDirections.element().getCoords();
		
		if (newCoords.getX() > coords.getX())
			orientation = 'v';
		else if (newCoords.getX() < coords.getX())
			orientation = '^';
		else if (newCoords.getY() > coords.getY())
			orientation = '>';
		else if (newCoords.getY() < coords.getY())
			orientation = '<';

		coords = newCoords;

		try {
			return maze.get(coords).moveHere();
		} catch(CannotMoveIntoWallsException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	* Uses BFS and Backtracking to find the shortest path to the "O" cell.
	*
	* @return coordinates stack containing the shortest path to the exit.
	*/
	public Stack<Coords> findExit() {
		// Breadth-First-Search to reach the exit
		Queue<Cell> queue = new LinkedList<Cell>(), origin = new LinkedList<Cell>();
		Queue<Cell> queueRemoved = new LinkedList<Cell>(), originRemoved = new LinkedList<Cell>();
		
		lookAround(queue, origin);
		Cell cell = null;
		Coords startCoords = new Coords(coords.getX(), coords.getY());

		while(queue.peek() != null) {
			cell = queue.peek();
			queueRemoved.add(queue.poll());
			originRemoved.add(origin.poll());
			coords = cell.getCoords();
			if (cell instanceof OutCell)
				break;
			if (cell.getStatus() != 'P') { // if cell is already processed, skip it
				lookAround(queue, origin);
				cell.setStatus('P');
			}
		}

		if (queue.peek() == null)
			throw new ExitNotFoundException();

		// Backtracking to obtain the path
		Stack<Coords> path = new Stack<Coords>();
		int i;
		while (!(cell instanceof InCell)) {
			path.push(cell.getCoords());
			cell = (Cell)((LinkedList)originRemoved).get(((LinkedList)queueRemoved).indexOf(cell));
		}
		path.push(startCoords);

		return path;
	}

}