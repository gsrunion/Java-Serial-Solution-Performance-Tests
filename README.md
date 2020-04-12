# Java-Serial-Solution-Performance-Tests

## Background
Serial IO in Java is challenging. One would expect nothing less from a language and ecosystem striving towards a goal of 'write one run every'. Despite all that a handfull of serial IO solutions do exist and this project aims to shed light on some of those and collect metrics as to how they perform.

## Implementations
Java Serial IO libraries all require some amount of native code interop there appear to be 2 different strategies to this

(1) Interact with 3rd party, or standard, libraries that have to be independently installed.
- PureJavaComm, RxTx

(2) Interact with 3rd party libaries that are bundled with the implementation.
- JSerialComm




PureJavaComm
count: 500 max: 119mS min: 64mS, average: 78.584ms

JSerialComm
count: 500 max: 74mS min: 59mS, average: 60.548ms

RxTx
count: 500 max: 112mS min: 68mS, average: 83.928ms

Forthecoming:
- PureJavaComm Implementation without Netty
- RxTx Implementation without Netty
- JSerialComm Implementation without Netty
- JTermios Implementation with Netty Embedded Channel
- JTermios Implemenation with a custom Netty Transport ala PureJavaComm, RxTx, JSerialComm
