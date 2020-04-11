package org.runion.peripheral.api.serial;

/**
 * Properties that define the required hardware configuration for a serial port connected device.
 */
public class SerialDeviceConfiguration {
    private String portName;
    private int baud;
    private SerialDeviceParity paritybit;
    private SerialDeviceDataBits databits;
    private SerialDeviceStopBits stopbits;

    public String getPortName() {
        return portName;
    }

    public SerialDeviceConfiguration setPortName(String portName) {
        this.portName = portName;
        return this;
    }

    public int getBaud() {
        return baud;
    }

    public SerialDeviceConfiguration setBaud(int baud) {
        this.baud = baud;
        return this;
    }

    public SerialDeviceParity getParitybit() {
        return paritybit;
    }

    public SerialDeviceConfiguration setParitybit(SerialDeviceParity paritybit) {
        this.paritybit = paritybit;
        return this;
    }

    public SerialDeviceDataBits getDatabits() {
        return databits;
    }

    public SerialDeviceConfiguration setDatabits(SerialDeviceDataBits databits) {
        this.databits = databits;
        return this;
    }

    public SerialDeviceStopBits getStopbits() {
        return stopbits;
    }

    public SerialDeviceConfiguration setStopbits(SerialDeviceStopBits stopbits) {
        this.stopbits = stopbits;
        return this;
    }

    @Override
    public String toString() {
        return "SerialDeviceConfiguration{" +
                "portName='" + portName + '\'' +
                ", baud=" + baud +
                ", paritybit=" + paritybit +
                ", databits=" + databits +
                ", stopbits=" + stopbits +
                '}';
    }
}
