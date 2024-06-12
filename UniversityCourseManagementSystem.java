/*import all necessary libraries*/
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
/*
* main class that contains where we get input from user
*/
public class UniversityCourseManagementSystem {
    public static void main(String[] args) {
        fillInitialData();                          /*upload the initial data*/
        Scanner in = new Scanner(System.in);        /*creating scanner to get input*/
        try {
            while (in.hasNextLine()) {
                String command = in.nextLine();
                CommandController.controller(command, in);      /*address the method controller which checks type of command*/
            }
        } catch (Exception v) {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
    }
 
    /*functions that uploads all initial data*/
    public static void fillInitialData() {
        Course javaBeginner = new Course("java_beginner", CourseLevel.BACHELOR, true);          /*upload courses*/
        Course javaIntermediate = new Course("java_intermediate", CourseLevel.BACHELOR, true);
        Course pythonBasics = new Course("python_basics", CourseLevel.BACHELOR, true);
        Course algorithms = new Course("algorithms", CourseLevel.MASTER, true);
        Course advancedProgramming = new Course("advanced_programming", CourseLevel.MASTER, true);
        Course mathematicalAnalysis = new Course("mathematical_analysis", CourseLevel.MASTER, true);
        Course computerVision = new Course("computer_vision", CourseLevel.MASTER, true);
 
        Student alice = new Student(1, "Alice");            /*upload students*/
        alice.enroll(javaBeginner, true);
        alice.enroll(javaIntermediate, true);
        alice.enroll(pythonBasics, true);
        Student bob = new Student(2, "Bob");
        bob.enroll(javaBeginner, true);
        bob.enroll(algorithms, true);
        Student alex = new Student(2 + 1, "Alex");
        alex.enroll(advancedProgramming, true);
 
        Professor ali = new Professor(2 + 2, "Ali");        /*upload professors*/
        ali.teach(javaBeginner, true);
        ali.teach(javaIntermediate, true);
        Professor ahmed = new Professor(2 + 2 + 1, "Ahmed");
        ahmed.teach(pythonBasics, true);
        ahmed.teach(advancedProgramming, true);
        Professor andrei = new Professor(2 + 2 + 2, "Andrei");
        andrei.teach(mathematicalAnalysis, true);
 
    }
}
 
class CommandController {
    /*
    * method which gets input, check it's validation and prints the message
    */
    public static void controller(String command, Scanner in) {
        if (command.equals("course")) {                             /*command course*/
            String courseNAME = in.nextLine().toLowerCase();
            Course.checkCourseName(courseNAME);                     /*check course name's validity*/
            InputChecker.checkOnCommands(courseNAME);
            String courseLEVELString = in.nextLine().toUpperCase();
            InputChecker.checkOnCommands(courseLEVELString);        /*check course level's validity*/
            if (courseLEVELString.equals("BACHELOR")) {
                CourseLevel courseLEVEL = CourseLevel.BACHELOR;
                Course course = new Course(courseNAME, courseLEVEL);
            } else if (courseLEVELString.equals("MASTER")) {
                CourseLevel courseLEVEL = CourseLevel.MASTER;
                Course course = new Course(courseNAME, courseLEVEL);
            } else {
                System.out.println("Wrong inputs");
                System.exit(0);
            }
        } else if (command.equals("student")) {                 /*command student*/
            String memberNAME = in.nextLine();
            InputChecker.checkOnCommands(memberNAME);           /*check member name's validity*/
            Student student = new Student(memberNAME);
        } else if (command.equals("professor")) {               /*command professor*/
            String memberNAME = in.nextLine();
            InputChecker.checkOnCommands(memberNAME);           /*check professor's*/
            Professor prof = new Professor(memberNAME);
        } else if (command.equals("enroll")) {                  /*command enroll*/
            int memberID = RowValidator.convertToInt(in.nextLine());                    /*address the method which convert string to int and checks it's validity*/
            if (memberID > 0) {                                                         /*address the method which check the validation of the entered member id*/
                if (InputChecker.checkOnIdValidStudent(memberID)) {
                    int courseID = RowValidator.convertToInt(in.nextLine());            /*address the method which convert string to int and checks it's validity*/
                    if (courseID > 0) {
                        if (InputChecker.checkonValidCourse(courseID)) {                /*address the method which check the validation of the entered course id*/
                            Student student = Library.getStudentFromLibrary(memberID);
                            Course course = Library.getCourseFromLibrary(courseID);
                            student.enroll(course, false);                        /*address the method which enroll the student in a course*/
                        }
                    }
                }
            }
        } else if (command.equals("drop")) {                                            /*command drop*/
            int memberID = RowValidator.convertToInt(in.nextLine());                        /*address the method which convert string to int and checks it's validity*/
            if (memberID > 0) {                                                             /*address the method which check the validation of the entered member id*/
                if (InputChecker.checkOnIdValidStudent(memberID)) {
                    int courseID = RowValidator.convertToInt(in.nextLine());                /*address the method which convert string to int and checks it's validity*/
                    if (courseID > 0) {
                        if (InputChecker.checkonValidCourse(courseID)) {                    /*address the method which check the validation of the entered course id*/
                            Student student = Library.getStudentFromLibrary(memberID);
                            Course course = Library.getCourseFromLibrary(courseID);
                            student.drop(course, false);                              /*address the method which drop the student from a course*/
                        }
                    }
                }
            }
        } else if (command.equals("teach")) {                                       /*command teach*/
            int memberID = RowValidator.convertToInt(in.nextLine());                        /*address the method which convert string to int and checks it's validity*/
            if (memberID > 0) {                                                             /*address the method which check the validation of the entered member id*/
                if (InputChecker.checkOnIdValidProf(memberID)) {
                    int courseID = RowValidator.convertToInt(in.nextLine());                /*address the method which convert string to int and checks it's validity*/
                    if (courseID > 0) {
                        if (InputChecker.checkonValidCourse(courseID)) {                    /*address the method which check the validation of the entered course id*/
                            Professor professor = Library.getProfFromLibrary(memberID);
                            Course course = Library.getCourseFromLibrary(courseID);
                            professor.teach(course, false);                           /*address the method which assign the professor to teach a course*/
                        }
                    }
                }
            }
        } else if (command.equals("exempt")) {                                      /*command exempt*/
            int memberID = RowValidator.convertToInt(in.nextLine());                        /*address the method which convert string to int and checks it's validity*/
            if (memberID > 0) {                                                             /*address the method which check the validation of the entered member id*/
                if (InputChecker.checkOnIdValidProf(memberID)) {
                    int courseID = RowValidator.convertToInt(in.nextLine());                /*address the method which convert string to int and checks it's validity*/
                    if (courseID > 0) {
                        if (InputChecker.checkonValidCourse(courseID)) {                    /*address the method which check the validation of the entered course id*/
                            Professor professor = Library.getProfFromLibrary(memberID);
                            Course course = Library.getCourseFromLibrary(courseID);
                            professor.exempt(course, false);                          /*address the method which exempt the professor from teaching a course*/
                        }
                    }
                }
            }
        } else if (command.equals("")) {                /*absence of method*/
            System.exit(0);
        } else {
            System.out.println("Wrong inputs");         /*prints "Wrong inputs" and stops the further exec of the program*/
            System.exit(0);
        }
    }
}
 
class RowValidator {
    /*
    * method which convert entered string to int if it is possible
    * else it outputs "Wrong inputs" and stops further execution
    */
    public static int convertToInt(String line) {
        try {
            return Integer.parseInt(line);
        } catch (Error error) {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
        return 1;
    }
}
 
/*
* InputChecker contains all checks of the input
*/
class InputChecker {
    /*
    *method that checks that input does not contain layering of commands
    *if does outputs "Wrong inputs" and stop further execution
    */
    public static void checkOnCommands(String in) {
        if (in.equals("course") || in.equals("student") || in.equals("professor")) {
            System.out.println("Wrong inputs");
            System.exit(0);
        } else if (in.equals("enroll") || in.equals("drop") || in.equals("teach") || in.equals("exempt")) {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
    }
    /*
     *method that checks that input of student's id exists
     *if does not outputs "Wrong inputs" and stop further execution
     */
    public static boolean checkOnIdValidStudent(int id) {
        for (int i = 0; i < Library.getAllStudents().size(); i++) {
            if (Library.getAllStudents().get(i).memberId == id) {
                return true;
            }
        }
        System.out.println("Wrong inputs");
        System.exit(0);
        return false;
    }
    /*
     *method that checks that input of professor's id exists
     *if does not outputs "Wrong inputs" and stop further execution
     */
    public static boolean checkOnIdValidProf(int id) {
        for (int i = 0; i < Library.getAllProfessors().size(); i++) {
            if (Library.getAllProfessors().get(i).memberId == id) {
                return true;
            }
        }
        System.out.println("Wrong inputs");
        System.exit(0);
        return false;
    }
    /*
     *method that checks that input of course's id exists
     *if does not outputs "Wrong inputs" and stop further execution
     */
    public static boolean checkonValidCourse(int id) {
        for (int i = 0; i < Library.getAllCourses().size(); i++) {
            if (Library.getAllCourses().get(i).getCourseId() == id) {
                return true;
            }
        }
        System.out.println("Wrong inputs");
        System.exit(0);
        return false;
    }
}
 
//class where we store all courses, professors and students
class Library {
    private static List<Course> allCourses = new ArrayList<Course>();          /*list of all courses*/
    private static List<Student> allStudents = new ArrayList<Student>();        /*list of all students*/
    private static List<Professor> allProfessors = new ArrayList<Professor>();    /*list of all professors*/
    /*
    * below is declared all geters and setters
    */
    public static Course getCourseFromLibrary(int id) {
        return allCourses.get(id - 1);
    }
 
    public static void addCourseToLibrary(Course course) {
        allCourses.add(course);
    }
 
    public static List<Course> getAllCourses() {
        return allCourses;
    }
    public static Student getStudentFromLibrary(int id) {
        for (int i = 0; i < allStudents.size(); i++) {
            if (allStudents.get(i).memberId == id) {
                return allStudents.get(i);
            }
        }
        return null;
    }
 
    public static void addStudentToLibrary(Student student) {
        allStudents.add(student);
    }
    public static List<Student> getAllStudents() {
        return allStudents;
    }
    public static Professor getProfFromLibrary(int id) {
        for (int i = 0; i < allProfessors.size(); i++) {
            if (allProfessors.get(i).memberId == id) {
                return allProfessors.get(i);
            }
        }
        return null;
    }
 
    public static List<Professor> getAllProfessors() {
        return allProfessors;
    }
    public static void addProfToLibrary(Professor professor) {
        allProfessors.add(professor);
    }
}
/*
*abstract class for professors and students
*/
abstract class UniversityMember {
    private static int numberOfMembers = 0;
    protected int memberId = numberOfMembers + 1;
    protected String memberName;
/*
* initializer of the University member
*/
    public UniversityMember(int memberId, String memberName) {
        this.memberName = memberName;
        this.memberId = memberId;
    }
    /*
    * initializer of the University member
    */
    public UniversityMember() {
    }
    /*
    * getter of the amount of university members
    */
    public static int getNumberOfMembers() {
        return numberOfMembers;
    }
    /*
     * setter of the amount of university members
     */
    public static void setNumberOfMembers(int numberOfMember) {
        numberOfMembers = numberOfMember;
    }
}
/*
* interface for the student which contains methods of drop from course and enroll to course
*/
interface Enrollable {
    public boolean drop(Course course, boolean isInit);
 
    public boolean enroll(Course course, boolean isInit);
}
 
class Student extends UniversityMember implements Enrollable {
    private static final int MAX_ENROLLMENT = 3;
    private List<Course> enrolledCourses = new ArrayList<Course>();         /*list of all courses on which student is enrolled*/
/*
* initializer of student from input
*/
    public Student(String memberName) {
        if (memberName.matches("[a-zA-Z]+")) {                  /*check that student's name contains only english letters*/
            this.memberName = memberName;
            Library.addStudentToLibrary(this);
            int numberOfMember = UniversityMember.getNumberOfMembers() + 1;
            UniversityMember.setNumberOfMembers(numberOfMember);
            System.out.println("Added successfully");
        } else {
            System.out.println("Wrong inputs");               /*if we have incorrect students name prints "Wrong inputs" and stops the further exec of the program*/
            System.exit(0);
        }
    }
    /*
     * initializer of student from initial data
     */
    public Student(int memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
        Library.addStudentToLibrary(this);
        int numberOfMember = UniversityMember.getNumberOfMembers() + 1;
        UniversityMember.setNumberOfMembers(numberOfMember);
    }
/*
*realization of dropping student from course
*/
    @Override
    public boolean drop(Course course, boolean isInit) {
        if (this.enrolledCourses.contains(course)) {                /*check if student is enrolled to the course*/
            if (!isInit) {
                System.out.println("Dropped successfully");
            }
            this.enrolledCourses.remove(course);
            course.getEnrolledStudents().remove(this);
            return true;
        }
        System.out.println("Student is not enrolled in this course");
        System.exit(0);
        return false;
    }
/*
*realization of enrolling student to course
*/
    @Override
    public boolean enroll(Course course, boolean isInit) {                      /*check if student is not already enrolled to this course*/
        if (this.enrolledCourses.contains(course)) {
            System.out.println("Student is already enrolled in this course");
            System.exit(0);
            return false;
        }
        if (this.enrolledCourses.size() >= MAX_ENROLLMENT) {                        /*checks that student did not reach the maximum number of enrollments*/
            System.out.println("Maximum enrollment is reached for the student");
            System.exit(0);
            return false;
        }
        if (course.isFull()) {                                      /*check that course is not full of students*/
            System.out.println("Course is full");
            System.exit(0);
            return false;
        }
        this.enrolledCourses.add(course);
        course.getEnrolledStudents().add(this);
        if (!isInit) {
            System.out.println("Enrolled successfully");
        }
        return true;
    }
}
 
class Professor extends UniversityMember {
    private static final int MAX_LOAD = 2;
    private List<Course> assignedCourses = new ArrayList<Course>();         /*list of all courses which this professor is teaching*/
    /*
     * initializer of professor from input
     */
    public Professor(String memberName) {
        if (memberName.matches("[a-zA-Z]+")) {                      /*check that professor's name contains only english letters*/
            this.memberName = memberName;
            Library.addProfToLibrary(this);
            int numberOfMember = UniversityMember.getNumberOfMembers() + 1;
            UniversityMember.setNumberOfMembers(numberOfMember);
            System.out.println("Added successfully");
        } else {
            System.out.println("Wrong inputs");                         /*else output "Wrong inputs" and stop further execution*/
            System.exit(0);
        }
    }
    /*
     * initializer of professor from initial data
     */
    public Professor(int memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
        Library.addProfToLibrary(this);
        int numberOfMember = UniversityMember.getNumberOfMembers() + 1;
        UniversityMember.setNumberOfMembers(numberOfMember);
    }
/*
* realization of assigning professor to teach the course
*/
    public boolean teach(Course course, boolean isInit) {
        if (this.assignedCourses.size() >= MAX_LOAD) {              /*check that professor has not reached his/her course load limit*/
            System.out.println("Professor's load is complete");
            System.exit(0);
            return false;
        }
        if (this.assignedCourses.contains(course)) {                        /*check that professor isn't already teaching this course*/
            System.out.println("Professor is already teaching this course");
            System.exit(0);
            return false;
        }
        if (!isInit) {
            System.out.println("Professor is successfully assigned to teach this course");
        }
        this.assignedCourses.add(course);
        return true;
    }
    /*
     * realization of exempting professor from teaching the course
     */
    public boolean exempt(Course course, boolean isInit) {
        if (this.assignedCourses.contains(course)) {                /*checking that professor teaching this course*/
            if (!isInit) {
                System.out.println("Professor is exempted");
            }
            this.assignedCourses.remove(course);
            return true;
        }
        System.out.println("Professor is not teaching this course");    /*if not output "Wrong inputs" stop further execution*/
        System.exit(0);
        return false;
    }
}
/*
*enumerator that contains all possible course levels
*/
enum CourseLevel {
    BACHELOR,
    MASTER
}
 
class Course {
    private static final int CAPACITY = 3;
    private static int numberOfCourses = 0; /*определяет идишник*/
    private int courseId = numberOfCourses + 1;
    private String courseName;
    private List<Student> enrolledStudents = new ArrayList<Student>();      /*list that contains all students which are enrolled to this course*/
    private CourseLevel courseLevel;
/*
* method that checks that entered course name exists
* else output "Wrong input" stop execution
*/
    public static void checkCourseName(String courseName) {
        for (int i = 1; i < Library.getAllCourses().size() + 1; i++) {
            String nameToCheck = Library.getCourseFromLibrary(i).getCourseName();
            if (nameToCheck.equals(courseName)) {
                System.out.println("Course exists");
                System.exit(0);
            }
        }
        if (!courseName.matches("[a-zA-Z_]+") || courseName.charAt(0) == '_') {
            System.out.println("Wrong inputs");
            System.exit(0);
        } else if (courseName.charAt(courseName.length() - 1) == '_' || courseName.contains("__")) {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
    }
/*
*initializer course from input
*/
    public Course(String courseName, CourseLevel courseLevel) {
        if (courseLevel == CourseLevel.BACHELOR || courseLevel == CourseLevel.MASTER) {             /*checks that course level is correct*/
            this.courseName = courseName;
            this.courseLevel = courseLevel;
            numberOfCourses++;
            Library.addCourseToLibrary(this);
            System.out.println("Added successfully");
        } else {
            System.out.println("Wrong inputs");                 /*if not output "Wrong inputs" stop further execution*/
            System.exit(0);
        }
    }
    /*
     *initializer course from initial data
     */
    public Course(String courseName, CourseLevel courseLevel, boolean isInitial) {
        this.courseName = courseName;
        this.courseLevel = courseLevel;
        numberOfCourses++;
        Library.addCourseToLibrary(this);
    }
/*
*method that checks if our is not full of students
*/
    public boolean isFull() {
        return enrolledStudents.size() == CAPACITY;
    }
/*
*getter of course id
*/
    public int getCourseId() {
        return this.courseId;
    }
/*
*getter of course name
*/
    public String getCourseName() {
        return this.courseName;
    }
/*
*getter the list of all students which are enrolled to this course
*/
    public List<Student> getEnrolledStudents() {
        return this.enrolledStudents;
    }
}
