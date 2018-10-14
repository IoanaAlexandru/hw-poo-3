package magicMaze.cells;

import magicMaze.Coords;
import magicMaze.exceptions.CannotMoveIntoWallsException;

public abstract class Cell {
	private final Coords coords;
	/*
	'I' - in
	'O' - out
	'#' - wall
	'.' - free cell
	*/
	private int visited;
	private char direction; // for TASK 1 - relative to the hero's orientation
	/*
	'R' - right
	'F' - front
	'L' - left
	'B' - back
	'N' - none
	*/
	private char status; // for TASK 2 - marks whether the cell was processed
	/*
	'R' - ready (default status)
	'P' - processed
	'W' - waiting
	*/

	public Cell(Coords coords) {
		this.coords = coords;
		visited = 0;
		direction = 'N';
		status = 'R';
	}

	public Coords getCoords() {
		return coords;
	}

	public int getVisited() {
		return visited;
	}

	public char getDirection() {
		return direction;
	}

	public char getStatus() {
		return status;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public void incVisited() {
		visited++;
	}

	/**
	* Move hero to this cell.
	*
	* @return true if the hero should keep moving and false otherwise
	*         (if they reached the destination).
	*/
	public abstract boolean moveHere() throws CannotMoveIntoWallsException;

	@Override
	public String toString() {
		return "(" + coords.toString() + ") " + visited + " " + direction; 
	}
}