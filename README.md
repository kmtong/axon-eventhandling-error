# HOW TO RUN

```
mvn compile exec:java
```

## Execution Steps

1. Use the Browser to point to http://localhost:8080
2. The request will trigger a command by random (either LeakyCommand or NormalCommand)
3. The LeadyCommandHandler will publish an event (ErrorThrowingEvent)
4. The ErrorThrowingEventHandler will throw an Error
5. The framework will bail out with exception, UnitOfWork state is somewhat strange (Could not clear this UnitOfWork. It is not the active one.)
6. Repeat the request a few more times
7. UnitOfWork state is totally messed up by LeakyCommand (Cannot register a listener for phase: CLEANUP because the Unit of Work is already in a later phase: CLOSED)

Noticed that NormalCommand will also be affected ultimately.


Or using a Controlled Version:

1. Use the Browser to point to http://localhost:8080/leaky
2. Repeat the request until it fails with (Cannot register a listener for phase: CLEANUP because the Unit of Work is already in a later phase: CLOSED)
3. Use the Browser to point to http://localhost:8080/normal
4. Normal request will fail as well
