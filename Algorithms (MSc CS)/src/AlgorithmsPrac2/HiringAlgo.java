package AlgorithmsPrac2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

class HiringDataStructure {
	int id;
	String name;
	int age;
	long phoneNo;
	String address, qualification;
	double percentage;

	public HiringDataStructure(int id, String name, int age, long phoneNo, String address, String qualification,
			double percentage) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.phoneNo = phoneNo;
		this.address = address;
		this.qualification = qualification;
		this.percentage = percentage;
	}
}

class HiringRequiredMethods {
	public int getRandomNumber(int limit) {
		return (int) (System.nanoTime() % limit);
	}

	public void insertIntoTable(ArrayList<HiringDataStructure> candidates) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hiringAlgo", "MScCS", "root");
			String insertCandidate = "insert into hiring values(?,?,?,?,?,?,?);";
			PreparedStatement ps = con.prepareStatement(insertCandidate);
			for (HiringDataStructure candidate : candidates) {
				ps.setInt(1, candidate.id);
				ps.setString(2, candidate.name);
				ps.setInt(3, candidate.age);
				ps.setLong(4, candidate.phoneNo);
				ps.setString(5, candidate.address);
				ps.setString(6, candidate.qualification);
				ps.setDouble(7, candidate.percentage);
				ps.executeUpdate();
			}
			System.out.println("Data Inserted");
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}
	}

	public ArrayList<HiringDataStructure> inputCandidate() {
		ArrayList<HiringDataStructure> candidates = new ArrayList<HiringDataStructure>();
		Scanner sc = new Scanner(System.in);
		System.out.print(" Enter the number of candidate : ");
		int noOfCandidate = sc.nextInt();
		sc.nextLine();
		for (int i = 1; i <= noOfCandidate; i++) {
			System.out.println(" Enter the Candidate No." + i + " : ");
			System.out.print(" Name : ");
			String name = sc.nextLine();
			System.out.print(" Age : ");
			int age = sc.nextInt();
			sc.nextLine();
			System.out.print(" Phone No. : ");
			long phoneNo = sc.nextLong();
			sc.nextLine();
			System.out.print(" Address : ");
			String address = sc.nextLine();
			System.out.print(" Qualification : ");
			String qualification = sc.nextLine();
			System.out.print(" Percentage : ");
			double percentage = sc.nextDouble();
			sc.nextLine();
			if ((age >= 18 && age < 60) && (qualification.equalsIgnoreCase("msc")) && (percentage >= 60)) {
				candidates.add(new HiringDataStructure(i, name, age, phoneNo, address, qualification, percentage));
			}
		}
		return candidates;
	}

	public void showCandidates(ArrayList<HiringDataStructure> candidates) {
		System.out.println("| ID \t | Name");
		for (HiringDataStructure candidate : candidates) {
			System.out.println("| " + candidate.id + " \t | " + candidate.name);
		}
	}

	public ArrayList<HiringDataStructure> shuffleCandidates(ArrayList<HiringDataStructure> eligibleCandidates) {
		ArrayList<HiringDataStructure> shuffledEligibleCandidates = new ArrayList<HiringDataStructure>();
		while (true) {
			int randomCandidateIndex = getRandomNumber(eligibleCandidates.size());
			shuffledEligibleCandidates.add(eligibleCandidates.get(randomCandidateIndex));
			eligibleCandidates.remove(randomCandidateIndex);
			if (eligibleCandidates.size() == 0) {
				break;
			}
		}
		return shuffledEligibleCandidates;
	}

	public ArrayList<HiringDataStructure> retrieveCandidates() {
		ArrayList<HiringDataStructure> retrievedEligibleCandidates = new ArrayList<HiringDataStructure>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hiringAlgo", "MScCS", "root");
			String retrieveCandidate = "select * from hiring;";
			PreparedStatement ps = con.prepareStatement(retrieveCandidate);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				int age = rs.getInt(3);
				long phoneNo = rs.getLong(4);
				String address = rs.getString(5);
				String qualification = rs.getString(6);
				double percentage = rs.getDouble(7);
				retrievedEligibleCandidates
						.add(new HiringDataStructure(id, name, age, phoneNo, address, qualification, percentage));
			}
			System.out.println("Data Retrieved");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retrievedEligibleCandidates;
	}

}

public class HiringAlgo {
	public HiringAlgo(ArrayList<HiringDataStructure> shuffledEligibleCandidates) {
		HiringRequiredMethods hrm = new HiringRequiredMethods();
		HiringDataStructure hiredCandidate = shuffledEligibleCandidates.get(0);
		int hiringCost = 50, totalHiringCost = 0;
		System.out.println("Each Hiring Cost is " + hiringCost);
		System.out.println("-------------------------------------------------");
		System.out.println("Hired : " + hiredCandidate.name + "( id : " + hiredCandidate.id + ")");
		totalHiringCost += hiringCost;
		int count = 0;
		for (HiringDataStructure candidate : shuffledEligibleCandidates) {
			if (count != 0) {
				int selected = hrm.getRandomNumber(100) % 2;
				if (selected == 1) {
					System.out.print("Hired : " + candidate.name + "( id : " + candidate.id + ")");
					System.out.print(" and Fired : " + hiredCandidate.name + "( id : " + hiredCandidate.id + ")");
					System.out.println();
					totalHiringCost += hiringCost;
					hiredCandidate = candidate;
				} else {
					System.out.println("Not Hired : " + candidate.name + "( id : " + candidate.id + ")");
				}
			}
			count++;
		}
		System.out.println("-------------------------------------------------");
		if (hiredCandidate != null) {
			System.out.println("Last Hired Person : " + hiredCandidate.name + "( id : " + hiredCandidate.id + ")");
		}
		System.out.println("Total Hiring Cost : " + totalHiringCost);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HiringRequiredMethods hrm = new HiringRequiredMethods();
		ArrayList<HiringDataStructure> candidates = hrm.inputCandidate();
		System.out.println("-------------------------------------------------");
		System.out.println("Eligible Candidates : ");
		hrm.showCandidates(candidates);
		System.out.println("-------------------------------------------------");
		hrm.insertIntoTable(candidates);
		System.out.println("-------------------------------------------------");
		System.out.println("Shuffled Candidates : ");
		ArrayList<HiringDataStructure> eligibleCandidates = hrm.retrieveCandidates();
		ArrayList<HiringDataStructure> shuffledEligibleCandidates = hrm.shuffleCandidates(eligibleCandidates);
		hrm.showCandidates(shuffledEligibleCandidates);
		System.out.println("-------------------------------------------------");
		HiringAlgo ha = new HiringAlgo(shuffledEligibleCandidates);
		System.out.println("-------------------------------------------------");
	}
}
