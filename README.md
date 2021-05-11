# HOW TO RUN

```
mvn exec:java
```

## Execution Steps

1. Use the Browser to point to http://localhost:8080
2. The request will trigger a command (LeakyCommand)
3. The command handler will publish an event (ErrorThrowingEvent)
4. The event handler will throw an Error
5. The framework will bail out with exception, UnitOfWork state is somewhat strange (Error: Could not clear this UnitOfWork. It is not the active one.)
6. Repeat the request a few more times
7. UnitOfWork state is totally messed up (Error: Cannot register a listener for phase: CLEANUP because the Unit of Work is already in a later phase: CLOSED)
