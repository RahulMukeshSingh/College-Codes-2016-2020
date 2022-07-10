package aStarStatic;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import ch.aplu.util.Console;
import ch.aplu.util.Monitor;

class locCost {
	Location loc;
	double fn;

	public locCost(Location loc, double fn) {
		this.loc = loc;
		this.fn = fn;
	}

}

class robot extends Actor {
	public ArrayList<Location> unavailableLocation;
	public ArrayList<locCost> penalty;
	methods m = new methods();
	Location source;
	File f = new File("penalty.txt");
	BufferedWriter bw;
	BufferedReader br;

	public robot(ArrayList<Location> unavailableLocation) {
		super("sprites/nxtrobot.gif");
		this.unavailableLocation = unavailableLocation;
		source = new Location(0, 0);
		penalty = new ArrayList<locCost>();
		if (f.exists()) {
			try {
				br = new BufferedReader(new FileReader(f));
				String penaltyCost = br.readLine();
				String[] pCost = penaltyCost.split(" ");
				int costCount = 0;
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						penalty.add(new locCost(new Location(i, j), Double.parseDouble(pCost[costCount])));
						costCount++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					penalty.add(new locCost(new Location(i, j), 0));
				}
			}
		}

	}

	int count = 0, count1 = 0;
	Location temp;

	public void act() {

		if (!(source.x == 9 && source.y == 9)) {
			Location destination = m.nextLocation(source, unavailableLocation, penalty);
			if (destination == null) {
				try {
					throw new Exception("Unable to reach goal!!!");
				} catch (Exception e) {
					Console.print(e.getMessage());
				}

			}
			boolean flag = true;
			if (destination.equals(temp)) {
				m.addPenaltyCost(penalty, destination);
				flag = false;
			}
			
			if(flag || m.noOfLocations(source, unavailableLocation) == 1) {
				if (source.x > destination.x) {
					setDirection(180);
					setHorzMirror(true);
				} else if (source.x < destination.x) {
					setDirection(0);
					setHorzMirror(false);
				} else if (source.y > destination.y) {
					setDirection(270);
				} else if (source.y < destination.y) {
					setDirection(90);
				}
				move();
				temp = source;
				source = destination;
			}
		} else {
			if (count1 == 0) {
				try {
					bw = new BufferedWriter(new FileWriter(f));
					String penaltyCost = "";
					for (locCost lcc : penalty) {
						penaltyCost += lcc.fn + " ";
					}
					bw.write(penaltyCost.trim());
					bw.flush();

				} catch (Exception e) {

					e.printStackTrace();
				}
				Console.print("Success!!!");
				count1++;
			}
		}

	}
}

class methods {

	public double getHDist(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

	public boolean isAvailableTile(ArrayList<Location> unavailableLocation, int x, int y) {
		boolean flag = true;
		if (x > 9 || x < 0 || y > 9 || y < 0) {
			flag = false;
		} else {
			for (Location ual : unavailableLocation) {
				if (ual.x == x && ual.y == y) {
					flag = false;
					break;
				}
			}
		}

		return flag;
	}

	public Location getMin(ArrayList<locCost> lc) {
		Location minLoc = null;
		if (!lc.isEmpty()) {
			double min = lc.get(0).fn;
			minLoc = lc.get(0).loc;
			for (locCost lce : lc) {
				if (lce.fn < min) {
					min = lce.fn;
					minLoc = lce.loc;
				}
			}
		}
		return minLoc;
	}

	public void addPenaltyCost(ArrayList<locCost> penalty, Location l) {

		for (locCost lc : penalty) {
			if (lc.loc.equals(l)) {
				lc.fn = lc.fn + 10;
				break;
			}
		}
	}

	public double getPenaltyCost(ArrayList<locCost> penalty, Location l) {
		double pCost = 0;
		for (locCost lc : penalty) {
			if (lc.loc.equals(l)) {
				pCost = lc.fn;
				break;
			}
		}
		return pCost;
	}
	public int noOfLocations(Location loc, ArrayList<Location> unavailableLocation) {

		int count=0;
		for (int i = -1; i <= 1; i++) {
			if (i != 0) {
				int x = loc.x + i;
				int y = loc.y;

				if (isAvailableTile(unavailableLocation, x, y)) {
					count++;
				}

			}
		}
		for (int i = -1; i <= 1; i++) {

			if (i != 0) {
				int x = loc.x;
				int y = loc.y + i;
				if (isAvailableTile(unavailableLocation, x, y)) {
					count++;
				}

			}
		}

		return count;
	}

	public Location nextLocation(Location loc, ArrayList<Location> unavailableLocation, ArrayList<locCost> penalty) {

		ArrayList<locCost> lc = new ArrayList<locCost>();
		for (int i = -1; i <= 1; i++) {
			if (i != 0) {
				int x = loc.x + i;
				int y = loc.y;

				if (isAvailableTile(unavailableLocation, x, y)) {
					Location l = new Location(x, y);
					double fn = getHDist(x, y, 9, 9) + getPenaltyCost(penalty, l);
					lc.add(new locCost(l, fn));
				}

			}
		}
		for (int i = -1; i <= 1; i++) {

			if (i != 0) {
				int x = loc.x;
				int y = loc.y + i;
				if (isAvailableTile(unavailableLocation, x, y)) {
					Location l = new Location(x, y);
					double fn = getHDist(x, y, 9, 9) + getPenaltyCost(penalty, l);
					lc.add(new locCost(l, fn));
				}

			}
		}

		Location min = null;
		if (!lc.isEmpty()) {
			min = getMin(lc);
		}

		return min;
	}

}

public class AStarMazeSt extends GameGrid {
	private Color cellColor = Color.lightGray;
	private GGBackground ggbg;
	public ArrayList<Location> unavailableLocation = new ArrayList<Location>();

	public AStarMazeSt() {
		super(10, 10, 60, Color.lightGray, false);
		addActor(new robot(unavailableLocation), new Location(0, 0));
		setTitle("A Star Maze!!!");
		ggbg = getBg();
		ggbg.fillCell(new Location(9, 9), Color.red);

		for (int i = 3; i < 6; i++) {
			Location loc = new Location(0, i);
			ggbg.fillCell(loc, cellColor);
			addDistinctLocation(loc, unavailableLocation);
		}
		for (int i = 3; i < 10; i++) {
			Location loc = new Location(i, 1);
			ggbg.fillCell(loc, cellColor);
			addDistinctLocation(loc, unavailableLocation);
		}
		for (int i = 5; i < 7; i++) {
			Location loc = new Location(5, i);
			ggbg.fillCell(loc, cellColor);
			addDistinctLocation(loc, unavailableLocation);
		}
		for (int i = 4; i < 7; i++) {
			Location loc = new Location(i, 4);
			ggbg.fillCell(loc, cellColor);
			addDistinctLocation(loc, unavailableLocation);
		}
		for (int i = 1; i < 10; i++) {
			Location loc = new Location(i, 7);
			ggbg.fillCell(loc, cellColor);
			addDistinctLocation(loc, unavailableLocation);
		}

		doRun();
		show();
		Monitor.putSleep();
	}

	public void addDistinctLocation(Location loc, ArrayList<Location> unavailableLocation) {
		boolean flag = false;
		for (Location ul : unavailableLocation) {
			if (ul.equals(loc)) {
				flag = true;
			}
		}
		if (!flag) {
			unavailableLocation.add(loc);
		}
	}

	public static void main(String[] args) {
		new AStarMazeSt();

	}
}
