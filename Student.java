public class Student {
    private final int studentId;
    private final String name;
    private final String department;
    private final int year;

    public Student(int studentId, String name, String department, int year) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.year = year;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId
                + "\nName: " + name
                + "\nDepartment: " + department
                + "\nYear: " + year;
    }
}
