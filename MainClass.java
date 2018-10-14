package magicMaze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.Queue;

import magicMaze.cells.Cell;
import magicMaze.cells.CellFactory;

public class MainClass {

	public static void main(String[] args) {
		ArrayList<ArrayList<Cell>> map = new ArrayList<ArrayList<Cell>>();
		int width = 0, height = 0;
		
		// Reading data and initialising hero

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(new File(args[1])));
			
			String line = reader.readLine(); int i = 0, j;
			String[] strings = line.trim().split("\\s+");
			height = Integer.parseInt(strings[0]);
			width = Integer.parseInt(strings[1]);

			for (i = 0; i < height; i++) {
				line = reader.readLine();
				map.add(i, new ArrayList<Cell>());
				for (j = 0; j < width; j++)
					map.get(i).add(j, CellFactory.getCell(new Coords(i, j), line.charAt(j)));
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		Hero hero = new Hero(new Maze(width, height, map));

		// Computing and writing the path

		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(new File(args[2])));

			if (args[0].equals("1")) {
				Queue<Coords> path = new LinkedList<Coords>();

				do
					path.add(hero.getCoords());
				while (hero.move());

				writer.write(Integer.toString(path.size() + 1));
				writer.newLine();
			
				while(path.peek() != null) {
					writer.write(path.poll().toString());
					writer.newLine();
				}

				writer.write(hero.getCoords().toString());
				writer.newLine();
			} else if (args[0].equals("2")) {
				Stack<Coords> path = hero.findExit();

				writer.write(Integer.toString(path.size()));
				writer.newLine();

				while (!path.empty()) {
					writer.write(path.pop().toString());
					writer.newLine();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

	}

}