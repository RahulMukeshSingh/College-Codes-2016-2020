package geneticsTransportation;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class geneticsTransportationMethods {
	public ArrayList<String> getInitialRoute(ArrayList<String> places) {
		ArrayList<String> initialroutes = new ArrayList<String>();
		int i = 0;
		String routes = "";
		int noOfRoutes = places.size();
		if (places.size() % 2 != 0) {
			noOfRoutes--;
		}
		while (i < noOfRoutes) {
			routes = "";
			Collections.shuffle(places);
			for (String pl : places) {
				routes += pl + " ";
			}
			routes = routes.trim().replace(" ", "-");
			if (!initialroutes.contains(routes)) {
				initialroutes.add(routes);
				i++;
			}
		}
		return initialroutes;
	}

	public double getEachDistanceIndex(String A, String B, double[][] distance, ArrayList<String> places) {
		int AIndex = 0, BIndex = 0;
		for (int i = 0; i < places.size(); i++) {
			if (places.get(i).equals(A)) {
				AIndex = i;
			}
			if (places.get(i).equals(B)) {
				BIndex = i;
			}
		}

		return distance[AIndex][BIndex];
	}

	public double[] addRouteDistance(ArrayList<String> places, ArrayList<String> routes, double[][] distance) {
		double[] totalDistance = new double[routes.size()];
		double sum = 0;
		int j = 0;
		for (String route : routes) {
			String[] r = route.split("-");
			sum = 0;
			for (int i = 1; i < r.length; i++) {
				sum += getEachDistanceIndex(r[i - 1], r[i], distance, places);
			}
			totalDistance[j] = sum;
			j++;
		}
		return totalDistance;
	}

	public String getCrossOverPoints(String id, String mate, ArrayList<String> places,
			ArrayList<geneticsStructure> inputs) {
		String crossoverPoint = "";
		for (geneticsStructure rows : inputs) {
			if (rows.id.equals(mate) && rows.mate.equals(id)) {
				crossoverPoint = rows.crossover;
			}
		}
		if (crossoverPoint.isEmpty()) {
			Random r = new Random();
			int temp = places.size() / 2;
			if (places.size() == 3) {
				crossoverPoint = "1,2";
			} else {
				crossoverPoint = String.valueOf(r.nextInt(temp - 1) + 1) + ","
						+ String.valueOf(r.nextInt(places.size() - temp - 1) + temp + 1);
			}
		}
		return crossoverPoint;
	}

	public ArrayList<int[]> getInitialIdAndMate(ArrayList<String> routes) {
		ArrayList<int[]> idandmate = new ArrayList<int[]>();
		int[] id = new int[routes.size()];
		int[] mate = new int[routes.size()];
		for (int i = 0; i < routes.size(); i++) {
			id[i] = i + 1;
		}
		for (int i = 0; i < routes.size(); i++) {
			if (i % 2 == 0) {
				mate[i] = id[i + 1];
			} else {
				mate[i] = id[i - 1];
			}
		}
		idandmate.add(id);
		idandmate.add(mate);
		return idandmate;
	}

	public String getCrossOver(String A, String B, String crossoverpoint) {
		String newroute = "";
		int[] integercrossoverpoints = new int[crossoverpoint.split(",").length];
		int j = 0;
		for (String cp : crossoverpoint.split(",")) {
			integercrossoverpoints[j] = Integer.valueOf(cp);
			j++;
		}
		String[] A_Route = A.split("-");
		String[] B_Route = B.split("-");
		int midArrayLen = integercrossoverpoints[1] - integercrossoverpoints[0],
				leftArrayLen = integercrossoverpoints[0], rightArrayLen = A_Route.length - integercrossoverpoints[1];

		String[] new_Part_Mid = new String[midArrayLen];
		String[] other_Part_Mid = new String[midArrayLen];
		String[] new_Part_Left = new String[leftArrayLen];
		String[] new_Part_Right = new String[rightArrayLen];
		j = 0;
		for (int i = integercrossoverpoints[0]; i < integercrossoverpoints[1]; i++) {
			new_Part_Mid[j] = A_Route[i];
			other_Part_Mid[j] = B_Route[i];
			j++;
		}

		for (int i = 0; i < new_Part_Left.length; i++) {
			String part = B_Route[i];
			for (int k = 0; k < new_Part_Mid.length; k++) {
				if (new_Part_Mid[k].equals(part)) {
					part = other_Part_Mid[k];
					for (int l = 0; l < new_Part_Mid.length; l++) {
						if (new_Part_Mid[l].equals(part)) {
							k = -1;
							break;
						}
					}
				}
			}
			new_Part_Left[i] = part;
		}
		j = 0;
		for (int i = integercrossoverpoints[1]; i < B_Route.length; i++) {
			String part = B_Route[i];
			for (int k = 0; k < new_Part_Mid.length; k++) {
				if (new_Part_Mid[k].equals(part)) {
					part = other_Part_Mid[k];
					for (int l = 0; l < new_Part_Mid.length; l++) {
						if (new_Part_Mid[l].equals(part)) {
							k = -1;
							break;
						}
					}
				}
			}
			new_Part_Right[j] = part;
			j++;
		}
		for (String left : new_Part_Left) {
			newroute += left + " ";
		}
		for (String mid : new_Part_Mid) {
			newroute += mid + " ";
		}
		for (String right : new_Part_Right) {
			newroute += right + " ";
		}
		return newroute.trim().replace(" ", "-");
	}

	public String getMateRoute(String id, String mate, ArrayList<geneticsStructure> inputs) {
		String mateRoute = "";
		for (geneticsStructure rows : inputs) {
			if (rows.id.equals(mate) && rows.mate.equals(id)) {
				mateRoute = rows.route;
				break;
			}
		}
		return mateRoute;
	}

	public double generationValue(double[] distance) {
		double sum = 0;
		for (double ds : distance) {
			sum += ds;
		}
		return Math.round(sum / distance.length * 100D) / 100D;
	}

	public ArrayList<geneticsStructure> replaceById(ArrayList<geneticsStructure> inputs, ArrayList<String> newRoutes,
			double[] distance, ArrayList<String> places) {
		ArrayList<geneticsStructure> newInput = new ArrayList<geneticsStructure>();
		String[] maxminRouteAndIndexes = getMinAndMaxDistanceIndexAndId(inputs, distance).split(" ");
		String maxId = maxminRouteAndIndexes[0];
		String minId = maxminRouteAndIndexes[1];
		int minIndex = Integer.valueOf(maxminRouteAndIndexes[2]);
		String crossOver = "";
		int i = 0;
		for (geneticsStructure rows : inputs) {
			if (rows.id.equals(maxId) && rows.mate.equals(maxId)) {
				crossOver = getCrossOverPoints(minId, minId, places, newInput);
				newInput.add(new geneticsStructure(minId, newRoutes.get(minIndex), minId, crossOver));
			} else if (rows.id.equals(maxId)) {
				crossOver = getCrossOverPoints(minId, rows.mate, places, newInput);
				newInput.add(new geneticsStructure(minId, newRoutes.get(minIndex), rows.mate, crossOver));

			} else if (rows.mate.equals(maxId)) {
				crossOver = getCrossOverPoints(rows.id, minId, places, newInput);
				newInput.add(new geneticsStructure(rows.id, newRoutes.get(i), minId, crossOver));
			} else {
				crossOver = getCrossOverPoints(rows.id, rows.mate, places, newInput);
				newInput.add(new geneticsStructure(rows.id, newRoutes.get(i), rows.mate, crossOver));
			}
			i++;
		}
		return newInput;
	}

	public String getMinAndMaxDistanceIndexAndId(ArrayList<geneticsStructure> inputs, double[] distance) {
		int minIndex = 0, maxIndex = 0;
		double min = distance[0], max = 0;
		int j = 0;
		for (double d : distance) {
			if (d < min) {
				min = d;
				minIndex = j;

			}
			if (d > max) {
				max = d;
				maxIndex = j;
			}
			j++;
		}
		return inputs.get(maxIndex).id + " " + inputs.get(minIndex).id + " " + minIndex;
	}

	public ArrayList<ArrayList<String>> getMutation(ArrayList<String> newRoute, ArrayList<String> places) {

		ArrayList<String> mutationPoints = new ArrayList<String>();
		ArrayList<String> Mutation = new ArrayList<String>();
		int len = places.size();
		int[] probability = new int[] { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 };
		int pointA = 0, pointB = 0;
		ArrayList<ArrayList<String>> newRouteafterMutation = new ArrayList<ArrayList<String>>();
		Random r = new Random();
		for (String route : newRoute) {
			int chance = probability[r.nextInt(10)];
			if (chance == 0) {
				mutationPoints.add("No");
				Mutation.add(route);
			} else {
				pointA = r.nextInt((int) (len / 2));
				pointB = r.nextInt((int) (len / 2)) + (int) (len / 2);
				mutationPoints.add((pointA + 1) + "," + (pointB + 1));
				Mutation.add(swap(route.split("-"), pointA, pointB));
			}
		}
		newRouteafterMutation.add(mutationPoints);
		newRouteafterMutation.add(Mutation);
		return newRouteafterMutation;
	}

	public String swap(String[] in, int pointA, int pointB) {
		String temp = "";
		temp = in[pointA];
		in[pointA] = in[pointB];
		in[pointB] = temp;
		String swapped = "";
		for (String n : in) {
			swapped += n + " ";
		}
		return swapped.trim().replace(" ", "-");
	}

	public boolean allRouteSame(ArrayList<String> newRoute) {
		String route = newRoute.get(0);
		boolean flag = true;
		for (String r : newRoute) {
			if (!route.equals(r)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public void arrangement(int stopSameGeneration,String fileLocation) {
		double presentGeneration = 0;
		ArrayList<transportationStructure> out = (new csvread()).csvreadTransportation(fileLocation);
		ArrayList<String> places = out.get(0).places;
		double[][] values = out.get(0).values;
		geneticsTransportationMethods gtm = new geneticsTransportationMethods();
		ArrayList<String> tempplacesonlyforsort = new ArrayList<String>(places);
		ArrayList<String> InitialRoutes = gtm.getInitialRoute(tempplacesonlyforsort);
		double[] totalDistance = gtm.addRouteDistance(places, InitialRoutes, values);
		presentGeneration = gtm.generationValue(totalDistance);
		
		ArrayList<int[]> idandmate = gtm.getInitialIdAndMate(InitialRoutes);
		int[] id = idandmate.get(0);
		int[] mate = idandmate.get(1);
		ArrayList<geneticsStructure> inputs = new ArrayList<geneticsStructure>();
		for (int i = 0; i < InitialRoutes.size(); i++) {
			inputs.add(new geneticsStructure(String.valueOf(id[i]), InitialRoutes.get(i), String.valueOf(mate[i]),
					gtm.getCrossOverPoints(String.valueOf(id[i]), String.valueOf(mate[i]), places, inputs)));

		}
		System.out.println("ID" + "  \t  " + "INITIAL ROUTE" + " \t" + "MATE" + "\t" + "DISTANCE");
		System.out.println("----------------------------------------------------------------------------");
		int j = 0;
		ArrayList<String> newRoute = new ArrayList<String>();
		for (geneticsStructure rows : inputs) {
			System.out.println(rows.id + "\t" + rows.route + "\t\t" + rows.mate + "\t" + totalDistance[j]);
			newRoute.add(rows.route);
			j++;
		}
		System.out.println("Initial Generation Value : " + presentGeneration);
		System.out.println();
		int iter = 0;
		double min = presentGeneration;
		int sameMin = 0;
		double minAnswer=totalDistance[0];
		String minRouteAnswer=newRoute.get(0);
		for (int i = 1; i < totalDistance.length; i++) {
			if(totalDistance[i]<minAnswer)
			{
			minAnswer=totalDistance[i];
			minRouteAnswer=newRoute.get(i);
			}
		}
		inputs = gtm.replaceById(inputs, newRoute, totalDistance, places);
		do {

			newRoute = new ArrayList<String>();

			for (geneticsStructure rows : inputs) {
				newRoute.add(
						gtm.getCrossOver(rows.route, gtm.getMateRoute(rows.id, rows.mate, inputs), rows.crossover));
			}
			ArrayList<ArrayList<String>> mutationPointsAnsRoutes = gtm.getMutation(newRoute, places);
			ArrayList<String> points = mutationPointsAnsRoutes.get(0);
			newRoute = mutationPointsAnsRoutes.get(1);
			totalDistance = gtm.addRouteDistance(places, newRoute, values);
			int i = 0;
			System.out.println("ID" + "  \t  " + "ROUTE" + " \t  " + "MATE" + "\t" + "CROSSOVER" + "\t" + "MUTATION"
					+ "\t" + "NEW ROUTE" + "\t" + "DISTANCE");
			System.out.println("----------------------------------------------------------------------------");
			for (geneticsStructure rows : inputs) {
				System.out.println(rows.id + "\t" + rows.route + "\t  " + rows.mate + "\t" + rows.crossover + "\t      "
						+ points.get(i) + "\t" + newRoute.get(i) + "\t" + totalDistance[i]);
				i++;
			}
			presentGeneration = gtm.generationValue(totalDistance);
			System.out.println("Generation Value : " + presentGeneration);
			inputs = gtm.replaceById(inputs, newRoute, totalDistance, places);
			for (int u = 0; u < totalDistance.length; u++) {
				if(totalDistance[u]<minAnswer)
				{
				minAnswer=totalDistance[u];
				minRouteAnswer=newRoute.get(u);
				}
			}
			iter++;
			// System.out.println(presentGeneration+" "+pastGeneration+"
			// "+prePastGeneration);
			if (presentGeneration == min) {
				sameMin++;
			} else if (presentGeneration < min) {
				min = presentGeneration;
				sameMin = 0;
			} else {
				sameMin = 0;
			}
			// System.out.println(min+" "+presentGeneration+" "+sameMin);
		} while (sameMin < stopSameGeneration || !gtm.allRouteSame(newRoute));
		System.out.println("Total Rounds : " + iter);
		System.out.println("Minimum Distance : "+minAnswer);
		System.out.println("Minimum Route : "+minRouteAnswer);
	}
}
