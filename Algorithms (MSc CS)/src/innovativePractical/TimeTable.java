package innovativePractical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class SubjectDetailsDS {
	String subjectName, prof, course;

	public SubjectDetailsDS(String subjectName, String prof, String course) {
		this.subjectName = subjectName;
		this.prof = prof;
		this.course = course;
	}

}

class AllotedDS {
	String subjectName, prof, course, classRoom, time;

	public AllotedDS(String subjectName, String prof, String course, String classRoom, String time) {
		this.subjectName = subjectName;
		this.prof = prof;
		this.course = course;
		this.classRoom = classRoom;
		this.time = time;
	}

}

class TimeTableDS {
	String day;
	ArrayList<AllotedDS> alloted;

	public TimeTableDS(String day, ArrayList<AllotedDS> alloted) {
		this.day = day;
		this.alloted = alloted;
	}

}

class TimeTableFunctions {
	Scanner sc = new Scanner(System.in);

	public String[] inputCourses(int noOfCourses) {
		String[] courses = new String[noOfCourses];
		for (int i = 0; i < noOfCourses; i++) {
			System.out.println("Enter the Course" + (i+1) + " : ");
			courses[i] = sc.nextLine();
		}
		return courses;
	}

	public String[] inputLectureRooms(int noOfLectureRooms) {
		String[] lectureRooms = new String[noOfLectureRooms];
		for (int i = 0; i < noOfLectureRooms; i++) {
			System.out.println("Enter the Lecture Room" + (i+1) + " : ");
			lectureRooms[i] = sc.nextLine();
		}
		return lectureRooms;
	}

	public String[] inputLabRooms(int noOfLabRooms) {
		String[] labRooms = new String[noOfLabRooms];
		for (int i = 0; i < noOfLabRooms; i++) {
			System.out.println("Enter the Lab Room" + (i+1) + " : ");
			labRooms[i] = sc.nextLine();
		}
		return labRooms;
	}

	public ArrayList<SubjectDetailsDS> inputSubjectDetails(int noOfSubjects, int noOfLectures, int noOfPracticals) {
		ArrayList<SubjectDetailsDS> subjectDetails = new ArrayList<SubjectDetailsDS>();
		for (int i = 1; i <= noOfSubjects; i++) {
			System.out.println("Enter the Subject Name_" + i + " : ");
			String subjectName = sc.nextLine();
			System.out.println("Enter the Professor Name_" + i + " : ");
			String profName = sc.nextLine();
			System.out.println("Enter the Course Name_" + i + " : ");
			String course = sc.nextLine();
			for (int j = 0; j < noOfLectures; j++) {
				subjectDetails.add(new SubjectDetailsDS(subjectName, profName, course));
			}
			for (int j = 0; j < noOfPracticals; j++) {
				subjectDetails.add(new SubjectDetailsDS(subjectName + "-Practs", profName, course));
			}
		}
		Collections.shuffle(subjectDetails);
		return subjectDetails;
	}

	public double getTotalNoOfClasses(ArrayList<SubjectDetailsDS> subjectDetails, String course) {
		double totalNoOfClasses = 0;
		for (SubjectDetailsDS subjectDetail : subjectDetails) {
			if (subjectDetail.course.equalsIgnoreCase(course)) {
				totalNoOfClasses++;
			}
		}
		return totalNoOfClasses;
	}

	public int[] getAvgHrsPerCoursePerDay(int noOfCourses, int noOfDaysForClasses,
			ArrayList<SubjectDetailsDS> subjectDetails, String[] courses, int timeSlotDiff) {
		int[] avgHrsPerCoursePerDay = new int[noOfCourses];
		for (int i = 0; i < noOfCourses; i++) {
			double totalNoOfClasses = getTotalNoOfClasses(subjectDetails, courses[i]);
			avgHrsPerCoursePerDay[i] = (int) Math.ceil(totalNoOfClasses / noOfDaysForClasses);
			if (avgHrsPerCoursePerDay[i] < timeSlotDiff) {
				avgHrsPerCoursePerDay[i]++;
			}
		}

		return avgHrsPerCoursePerDay;
	}

	public String getAvailableRoom(String[] lectureRooms, String[] labRooms, String subjectName,
			ArrayList<AllotedDS> busy) {
		String availableRoom = null;
		if (subjectName.contains("-Practs")) {
			for (int i = 0; i < labRooms.length; i++) {
				boolean found = false;
				for (AllotedDS b : busy) {
					if (labRooms[i].equalsIgnoreCase(b.classRoom)) {
						found = true;
					}
				}
				if (!found) {
					availableRoom = labRooms[i];
					break;
				}
			}
		} else {
			for (int i = 0; i < lectureRooms.length; i++) {
				boolean found = false;
				for (AllotedDS b : busy) {
					if (lectureRooms[i].equalsIgnoreCase(b.classRoom)) {
						found = true;
					}
				}
				if (!found) {
					availableRoom = lectureRooms[i];
					break;
				}
			}
		}
		return availableRoom;
	}

	public SubjectDetailsDS getAvailableProf(ArrayList<SubjectDetailsDS> subjectDetails, ArrayList<AllotedDS> busy,
			String course) {
		SubjectDetailsDS availableProf = null;
		for (SubjectDetailsDS subjectDetail : subjectDetails) {
			boolean found = false;
			if (subjectDetail.course.equalsIgnoreCase(course)) {
				for (AllotedDS b : busy) {
					if (subjectDetail.prof.equalsIgnoreCase(b.prof)) {
						found = true;
					}
				}
				if (!found) {
					availableProf = subjectDetail;
					break;
				}
			}
		}

		return availableProf;
	}

	public SubjectDetailsDS getAvailableDifferentProf(ArrayList<SubjectDetailsDS> subjectDetails,
			ArrayList<AllotedDS> busy, String course, boolean isPracts) {
		SubjectDetailsDS availableProf = null;
		for (SubjectDetailsDS subjectDetail : subjectDetails) {
			boolean found = false;
			if (subjectDetail.course.equalsIgnoreCase(course)
					&& (subjectDetail.subjectName.contains("-Practs") && isPracts)) {
				for (AllotedDS b : busy) {
					if (subjectDetail.prof.equalsIgnoreCase(b.prof)) {
						found = true;
					}
				}
				if (!found) {
					availableProf = subjectDetail;
					break;
				}
			}
		}

		return availableProf;
	}

	public AllotedDS getAvailableProfAndRoom(ArrayList<SubjectDetailsDS> subjectDetails, ArrayList<AllotedDS> busy,
			String course, String[] lectureRooms, String[] labRooms, int time) {
		AllotedDS available;
		SubjectDetailsDS availableProf = getAvailableProf(subjectDetails, busy, course);
		if (availableProf == null) {
			available = new AllotedDS("-", "-", "-", "-", "-");
		} else {
			String availableRoom = getAvailableRoom(lectureRooms, labRooms, availableProf.subjectName, busy);
			if (availableRoom == null) {
				boolean isPracts;
				if (availableProf.subjectName.contains("-Practs")) {
					isPracts = false;
				} else {
					isPracts = true;
				}
				availableProf = getAvailableDifferentProf(subjectDetails, busy, course, isPracts);
				if (availableProf == null) {
					available = new AllotedDS("-", "-", "-", "-", "-");
				} else {
					availableRoom = getAvailableRoom(lectureRooms, labRooms, availableProf.subjectName, busy);
					if (availableRoom == null) {
						available = new AllotedDS("-", "-", "-", "-", "-");
					} else {
						available = new AllotedDS(availableProf.subjectName, availableProf.prof, availableProf.course,
								availableRoom, "" + time);
						subjectDetails.remove(subjectDetails.indexOf(availableProf));
					}
				}
			} else {
				available = new AllotedDS(availableProf.subjectName, availableProf.prof, availableProf.course,
						availableRoom, "" + time);
				subjectDetails.remove(subjectDetails.indexOf(availableProf));
			}
		}

		return available;
	}

	public void display(ArrayList<TimeTableDS> allotedTimeTableWeek, String[] courses) {
		for (TimeTableDS tt : allotedTimeTableWeek) {
			ArrayList<AllotedDS> alloted = tt.alloted;
			for (int i = 0; i < courses.length; i++) {
				int count = 0;
				for (AllotedDS a : alloted) {
					if (courses[i].equalsIgnoreCase(a.course)) {
						if (count == 0) {
							System.out.println("-------------------------------------------------------------------");
							System.out.println(tt.day + " " + a.course);
							System.out.println("Time | Subject | Professor | ClassRoom");
						}
						System.out.println(a.time + ":00 | " + a.subjectName + " | " + a.prof + " | " + a.classRoom);
						count++;
					}
				}
			}
		}
	}
}

public class TimeTable {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the No. Of Professors : ");
		int noOfTeachers = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the No. Of Courses : ");
		int noOfCourses = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the No. Of Lecture Rooms : ");
		int noOfLectureRooms = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the No. Of Lab Rooms : ");
		int noOfLabRooms = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the No. Of Subjects : ");
		int noOfSubjects = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the No. Of Lectures Per Week For Each Subject : ");
		int noOfLectures = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the No. Of Practicals Per Week For Each Subject : ");
		int noOfPracticals = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the No. Of Days : ");
		int noOfDaysForClasses = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the Start Time (24 hour format) : ");
		int timeSlotStart = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the End Time (24 hour format) : ");
		int timeSlotEnd = sc.nextInt();
		sc.nextLine();
		String[] days = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		TimeTableFunctions ttf = new TimeTableFunctions();
		String[] courses = ttf.inputCourses(noOfCourses);
		String[] lectureRooms = ttf.inputLectureRooms(noOfLectureRooms);
		String[] labRooms = ttf.inputLabRooms(noOfLabRooms);
		ArrayList<SubjectDetailsDS> subjectDetails = ttf.inputSubjectDetails(noOfSubjects, noOfLectures,
				noOfPracticals);
		int[] avgHrsPerCoursePerDay = ttf.getAvgHrsPerCoursePerDay(noOfCourses, noOfDaysForClasses, subjectDetails,
				courses, (timeSlotEnd - timeSlotStart));
		ArrayList<TimeTableDS> allotedTimeTableWeek = new ArrayList<TimeTableDS>();
		for (int i = 0; i < noOfDaysForClasses; i++) {
			ArrayList<AllotedDS> allotedTimeTableDay = new ArrayList<AllotedDS>();
			int[] countAvg = new int[noOfCourses];
			for (int j = timeSlotStart; j < timeSlotEnd; j++) {
				ArrayList<AllotedDS> busy = new ArrayList<AllotedDS>();
				for (int k = 0; k < noOfCourses; k++) {
					if (countAvg[k] < avgHrsPerCoursePerDay[k]) {
						AllotedDS availableProfAndRoom = ttf.getAvailableProfAndRoom(subjectDetails, busy, courses[k],
								lectureRooms, labRooms, j);
						allotedTimeTableDay.add(availableProfAndRoom);
						busy.add(availableProfAndRoom);
						countAvg[k]++;
					}
				}
			}
			allotedTimeTableWeek.add(new TimeTableDS(days[i], allotedTimeTableDay));
		}
		ttf.display(allotedTimeTableWeek, courses);
	}

}
