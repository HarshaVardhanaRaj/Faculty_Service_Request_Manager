public class Action {
    private final int requestId;
    private final RequestStatus oldStatus;
    private final RequestStatus newStatus;

    public Action(int requestId, RequestStatus oldStatus, RequestStatus newStatus) {
        this.requestId = requestId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public int getRequestId() {
        return requestId;
    }

    public RequestStatus getOldStatus() {
        return oldStatus;
    }

    public RequestStatus getNewStatus() {
        return newStatus;
    }

    @Override
    public String toString() {
        return "Request #" + requestId + ": "
                + oldStatus + " -> " + newStatus;
    }
}
