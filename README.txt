# Ground Zero — Faculty Service Request System

A small Java console prototype for the DSA mini-project.

## Required data structures

- Linked List: request history in `Request.java`
- HashMap: request ID -> Request in `RequestManager.java`
- Queue: pending requests in `RequestManager.java`
- Stack: explicit status-change actions for undo in `RequestManager.java`

## Files

- Main.java
- Student.java
- Request.java
- Action.java
- RequestManager.java
- RequestStatus.java

## Run

From this folder:

```text
javac *.java
java Main
```

The application starts with three demo requests so the prototype can be demonstrated immediately.

## Suggested demo

1. View All Requests
2. View Pending Queue
3. Process Next Request
4. Update Request #1001 to APPROVED
5. View Request History for #1001
6. Undo the approval
7. View Request #1001 again
8. Submit a new request
9. Try a duplicate request ID to demonstrate validation
10. Try a missing request ID

## Important design choice

Processing a request removes it from the pending Queue and changes it from PENDING to PROCESSING.

The Stack is reserved for explicit faculty status changes such as APPROVED/REJECTED, so the undo operation has a clear LIFO meaning.
