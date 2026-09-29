import java.util.LinkedList;

public class Request {
    private final int requestId;
    private final Student student;
    private final String type;
    private final String description;
    private RequestStatus status;

    // Required Linked List: chronological history of this request.
    private final LinkedList<String> history;

    public Request(int requestId, Student student, String type, String description) {
        this.requestId = requestId;
        this.student = student;
        this.type = type;
        this.description = description;
        this.status = RequestStatus.PENDING;

        history = new LinkedList<>();
        history.add("Request submitted");
    }

    public int getRequestId() {
        return requestId;
    }

    public Student getStudent() {
        return student;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public LinkedList<String> getHistory() {
        return history;
    }

    public void addHistory(String event) {
        history.add(event);
    }

    @Override
    public String toString() {
        return "\nRequest ID: " + requestId
                + "\nStudent: " + student.getName()
                + " (" + student.getStudentId() + ")"
                + "\nDepartment: " + student.getDepartment()
                + "\nYear: " + student.getYear()
                + "\nType: " + type
                + "\nDescription: " + description
                + "\nStatus: " + status;
    }
}
