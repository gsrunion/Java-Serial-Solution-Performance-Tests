# Java-Serial-Solution-Performance-Tests

## Background
Serial IO in Java is challenging. One would expect nothing less from a language and ecosystem striving towards a goal of 'write one run every'. Despite all that a handfull of serial IO solutions do exist and this project aims to shed light on some of those and collect metrics as to how they perform.

## Implementations
Java Serial IO libraries all require some amount of native code interop there appear to be 2 different strategies to this

(1) Interact with 3rd party, or standard, libraries that have to be independently installed.
- PureJavaComm, RxTx

(2) Interact with 3rd party libaries that are bundled with the implementation.
- JSerialComm

(3) Interact with serial port in platform specific ways that, without abstraction, can't be replicated from system to system.
- Posix file IO on /det/tty devices.

### Test Cases
I am a fan of the Netty project, it is a swiss army knife for all things asynch io and networking. This effort is starting with Netty abstractions build upon the 3 primary Java serial solutions: PureJavaComm, RxTx, and JSerialComm. Since there exist equivalent Netty abstractions for each of these, I am able to achieve a high degree of code reuse between test cases keeping non-implementation related variabls to a minimum. Since profiling those have moved along quickly, for good measure I hope to do like profiling of those 3 libaries without Netty in the mix. 

### Test
The tests includes serial communciations between a server and client application where each is connected to a distinct serial port. Each iteration measure is a 256 byte packet sent from the client to there server that is then echoed back to the client application who then verfies the content. So each timed interation includes a verification, a pair of transmissions, and any extra latencies. Since all the tests will use the same baud rate and perform the same verifications on the data, differences in performance will largely be a result of the latencies inherit to each implementation. We do 500 interations of this and capture the min, max, and average times for the round trip. Don't let the absolute times measured worry you much as I am makign every effort to use the libraries in the same manner and not doing any tuning. What is most important here is relative performance.

### Results
PureJavaComm -> max: 119mS min: 64mS, average: 78.584ms

JSerialComm. -> max: 74mS  min: 59mS, average: 60.548ms

RxTx         -> max: 112mS min: 68mS, average: 83.928ms


