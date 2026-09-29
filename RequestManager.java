import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class RequestManager {

    // HashMap: fast request lookup using request ID.
    private final HashMap<Integer, Request> requests;

    // Queue: requests waiting to be processed in FIFO order.
    private final Queue<Request> pendingRequests;

    // Stack: explicit status-changing actions for LIFO undo.
    private final Stack<Action> actionHistory;

    public RequestManager() {
        requests = new HashMap<>();
        pendingRequests = new LinkedList<>();
        actionHistory = new Stack<>();
    }

    public boolean addRequest(Request request) {
        if (requests.containsKey(request.getRequestId())) {
            return false;
        }

        requests.put(request.getRequestId(), request);
        pendingRequests.offer(request);
        return true;
    }

    public Request findRequest(int requestId) {
        return requests.get(requestId);
    }

    public boolean requestExists(int requestId) {
        return requests.containsKey(requestId);
    }

    public int getRequestCount() {
        return requests.size();
    }

    public Request processNextRequest() {
        if (pendingRequests.isEmpty()) {
            return null;
        }

        Request request = pendingRequests.poll();

        if (request.getStatus() == RequestStatus.PENDING) {
            request.setStatus(RequestStatus.PROCESSING);
            request.addHistory("Request moved to processing");
        }

        return request;
    }

    public boolean changeStatus(int requestId, RequestStatus newStatus) {
        Request request = requests.get(requestId);

        if (request == null) {
            return false;
        }

        RequestStatus oldStatus = request.getStatus();

        if (oldStatus == newStatus) {
            return false;
        }

        // Keep the original status-changing action on the Stack.
        actionHistory.push(new Action(requestId, oldStatus, newStatus));

        request.setStatus(newStatus);
        request.addHistory("Status changed from " + oldStatus + " to " + newStatus);

        return true;
    }

    public Action undoLastAction() {
        if (actionHistory.isEmpty()) {
            return null;
        }

        Action action = actionHistory.pop();
        Request request = requests.get(action.getRequestId());

        if (request == null) {
            return null;
        }

        request.setStatus(action.getOldStatus());
        request.addHistory(
                "Undo: status restored from "
                        + action.getNewStatus()
                        + " to "
                        + action.getOldStatus()
        );

        return action;
    }

    public void displayPendingRequests() {
        System.out.println("\n===== PENDING QUEUE =====");

        if (pendingRequests.isEmpty()) {
            System.out.println("No requests are waiting in the queue.");
            return;
        }

        for (Request request : pendingRequests) {
            System.out.println(
                    "#" + request.getRequestId()
                            + " | "
                            + request.getStudent().getName()
                            + " | "
                            + request.getType()
                            + " | "
                            + request.getStatus()
            );
        }
    }

    public void displayAllRequests() {
        System.out.println("\n===== ALL REQUESTS =====");

        if (requests.isEmpty()) {
            System.out.println("No requests found.");
            return;
        }

        for (Request request : requests.values()) {
            System.out.println(
                    "#" + request.getRequestId()
                            + " | "
                            + request.getStudent().getName()
                            + " | "
                            + request.getType()
                            + " | "
                            + request.getStatus()
            );
        }
    }

    public void displayHistory(int requestId) {
        Request request = requests.get(requestId);

        if (request == null) {
            System.out.println("Request not found.");
            return;
        }

        System.out.println("\n===== REQUEST HISTORY =====");
        System.out.println("Request #" + requestId);

        int number = 1;
        for (String event : request.getHistory()) {
            System.out.println(number + ". " + event);
            number++;
        }
    }
}
