# Java-Serial-Solution-Performance-Tests

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
