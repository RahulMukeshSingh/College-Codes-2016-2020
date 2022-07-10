package aStarJigsaw;

import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import ch.aplu.robotsim.Tools;
import ch.aplu.util.Console;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

class DistanceLocation {
	Location jumLoc, inLoc;
	double distance;
	ArrayList<PenaltyLocation> penalty;

	public DistanceLocation(Location jumLoc, Location inLoc) {
		this.jumLoc = jumLoc;
		this.inLoc = inLoc;
		this.distance = AStarJigsaw.getHDist(jumLoc.x, jumLoc.y, inLoc.x, inLoc.y);
	}

}

class PenaltyLocation {
	Location loc;
	double cost;
	double penaltyCost = 0;

	public PenaltyLocation(Location loc, double cost) {
		this.loc = loc;
		this.cost = cost;
	}

}

public class AStarJigsaw extends GameGrid {
	private ArrayList<Location> inputLoc;
	private ArrayList<Location> jumbledLoc;
	private ArrayList<Location> obstacleLoc;
	private ArrayList<DistanceLocation> minDistSrcDest;
	private String penaltyString = "";
	private String[] penaltyData;
	private GGBackground ggbg;
	private Color blankColour = Color.BLACK;
	private Color boxColour = Color.BLUE;
	private int aStarCount = 70;

	public AStarJigsaw() {
		super(30, 30, 20, Color.BLUE, false);
		while (true) {
			inputLoc = new ArrayList<Location>();
			jumbledLoc = new ArrayList<Location>();
			obstacleLoc = new ArrayList<Location>();
			minDistSrcDest = new ArrayList<DistanceLocation>();
			penaltyData = null;
			ggbg = getBg();
			readInFile();
			readInFilePenalty();
			show();
			String penalty = aStar_Jigsaw();
			writeInFile(penalty);
			// Console.print("Done!!!");
			aStarCount = aStarCount + 1;
			System.out.println("Done " + aStarCount + " times!!!");
			if(penaltyString.equals(penalty.trim())){
				System.out.println("Optimal State at "+(aStarCount - 1));
				break;
			}
			for (int i = 0; i < 30; i++) {
				for (int j = 0; j < 30; j++) {
					ggbg.fillCell(new Location(i, j), blankColour, false);
				}
			}
		}
	}

	public DistanceLocation distMin(ArrayList<DistanceLocation> distSrcDest) {
		DistanceLocation minDistLoc = distSrcDest.get(0);
		double minDistance = distSrcDest.get(0).distance;
		for (DistanceLocation dissrcdest : distSrcDest) {
			if (dissrcdest.distance < minDistance) {
				minDistLoc = dissrcdest;
				minDistance = dissrcdest.distance;
			}
		}
		return minDistLoc;
	}

	public void deleteDistLoc(ArrayList<DistanceLocation> distSrcDest, DistanceLocation minSrcDest) {
		for (int i = 0; i < distSrcDest.size();) {
			DistanceLocation disLocList = distSrcDest.get(i);
			if ((disLocList.inLoc.equals(minSrcDest.inLoc)) || (disLocList.inLoc.equals(minSrcDest.jumLoc))
					|| (disLocList.jumLoc.equals(minSrcDest.inLoc)) || (disLocList.jumLoc.equals(minSrcDest.jumLoc))) {
				distSrcDest.remove(disLocList);
			} else {
				i++;
			}
		}
	}

	public void readInFile() {
		ArrayList<DistanceLocation> distSrcDest = new ArrayList<DistanceLocation>();
		String[] inputLocations = null, jumbledLocations = null;
		try {
			File f = new File("input.txt");
			BufferedReader br;
			br = new BufferedReader(new FileReader(f));
			String[] inputs = br.readLine().split("///");
			inputLocations = inputs[0].split(" ");
			jumbledLocations = inputs[1].split(" ");
			br.close();
		} catch (Exception e) {
			System.out.println("input.txt not found!!!");
			e.printStackTrace();
		}
		for (String inLoc : inputLocations) {
			String[] coordinates = inLoc.split(",");
			int x = Integer.parseInt(coordinates[0]);
			int y = Integer.parseInt(coordinates[1]);
			inputLoc.add(new Location(x, y));
		}
		for (String jumLoc : jumbledLocations) {
			String[] coordinates = jumLoc.split(",");
			int x = Integer.parseInt(coordinates[0]);
			int y = Integer.parseInt(coordinates[1]);
			jumbledLoc.add(new Location(x, y));
			ggbg.fillCell(new Location(x, y), boxColour, false);
		}
		for (Location jumLoc : jumbledLoc) {
			for (Location inLoc : inputLoc) {
				distSrcDest.add(new DistanceLocation(jumLoc, inLoc));
			}
		}
		while (!distSrcDest.isEmpty()) {
			DistanceLocation minSrcDest = distMin(distSrcDest);
			minDistSrcDest.add(minSrcDest);
			deleteDistLoc(distSrcDest, minSrcDest);
		}
	}

	public void readInFilePenalty() {
		File f = new File("penaltyJigsaw.txt");
		if (f.exists()) {
			try {
				BufferedReader br;
				br = new BufferedReader(new FileReader(f));
				penaltyString = br.readLine(); 
				penaltyData = penaltyString.split("///");
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<PenaltyLocation> getPenaltyList(int index) {
		ArrayList<PenaltyLocation> pl = new ArrayList<PenaltyLocation>();
		String[] penaltyPerLocation = null;
		if (penaltyData != null) {
			penaltyPerLocation = penaltyData[index].split(" ");
		}

		int count = 0;
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				Location path = new Location(i, j);
				if ((!jumbledLoc.contains(path)) && (!obstacleLoc.contains(path))) {
					Location to = minDistSrcDest.get(index).inLoc;
					double distance = getHDist(path.x, path.y, to.x, to.y);
					if (penaltyPerLocation == null) {
						pl.add(new PenaltyLocation(path, distance));
					} else {
						pl.add(new PenaltyLocation(path, distance + Double.parseDouble(penaltyPerLocation[count])));
						count++;
					}
				}
			}
		}
		return pl;
	}

	public Location getValidNextLocation(Location curLoc, ArrayList<PenaltyLocation> penaltyLoc) {
		Location nextLoc = null;
		ArrayList<Location> nextMoves = new ArrayList<Location>();
		int x = curLoc.x;
		int y = curLoc.y;
		nextMoves.add(new Location(x - 1, y));
		nextMoves.add(new Location(x + 1, y));
		nextMoves.add(new Location(x, y - 1));
		nextMoves.add(new Location(x, y + 1));
		double minPenDistPath = 0;
		int count = 0;
		for (PenaltyLocation possibleLoc : penaltyLoc) {
			if ((nextMoves.contains(possibleLoc.loc))) {
				if (count == 0) {
					minPenDistPath = possibleLoc.cost;
					nextLoc = possibleLoc.loc;
				}
				if ((possibleLoc.cost <= minPenDistPath)) {
					minPenDistPath = possibleLoc.cost;
					nextLoc = possibleLoc.loc;
				}
				count++;
			}
		}
		return nextLoc;
	}

	public void addPenalty(double penalty, int index, Location penaltyLoc) {
		ArrayList<PenaltyLocation> penalty_Locs = minDistSrcDest.get(index).penalty;
		for (PenaltyLocation penalty_Loc : penalty_Locs) {
			if (penalty_Loc.loc.equals(penaltyLoc)) {
				penalty_Loc.cost += penalty;
				penalty_Loc.penaltyCost += penalty;
			}
		}
	}

	public void moveColour(Location from, Location to) {
		try {
			ggbg.fillCell(from, blankColour, false);
			refresh();
			ggbg.fillCell(to, boxColour, false);
			refresh();
			Tools.delay(40);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String aStar_Jigsaw() {
		int index = 0;
		for (DistanceLocation mdsd : minDistSrcDest) {
			mdsd.penalty = getPenaltyList(index);
			Location curLoc = mdsd.jumLoc;
			Location pastLoc = null;
			Location nextLoc = null;
			while (true) {
				if (curLoc.equals(mdsd.inLoc)) {
					obstacleLoc.add(mdsd.inLoc);
					jumbledLoc.remove(mdsd.jumLoc);
					inputLoc.remove(mdsd.inLoc);
					break;
				}
				nextLoc = getValidNextLocation(curLoc, mdsd.penalty);
				moveColour(curLoc, nextLoc);
				if (nextLoc.equals(pastLoc)) {
					addPenalty(10, index, nextLoc);
				}
				pastLoc = curLoc;
				curLoc = nextLoc;
			}
			index++;
		}
		String penalty = "";
		int count = 0;
		for (DistanceLocation mdsd : minDistSrcDest) {
			ArrayList<PenaltyLocation> penaltyLoc = mdsd.penalty;
			if (count != 0) {
				penalty = penalty + "///";
			}
			for (PenaltyLocation penalty_Loc : penaltyLoc) {
				penalty = penalty + penalty_Loc.penaltyCost + " ";
			}
			penalty = penalty.trim();
			count++;
		}
		return penalty;
	}

	public static double getHDist(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

	public void writeInFile(String penalty) {
		try {
			File f = new File("penaltyJigsaw.txt");
			BufferedWriter bw;
			bw = new BufferedWriter(new FileWriter(f));
			bw.write(penalty.trim());
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new AStarJigsaw();
	}
}
