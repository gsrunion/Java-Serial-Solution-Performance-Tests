package org.peripheral.serial;

import org.runion.peripheral.api.serial.SerialDeviceConfiguration;
import org.runion.peripheral.api.serial.SerialDeviceDataBits;
import org.runion.peripheral.api.serial.SerialDeviceParity;
import org.runion.peripheral.api.serial.SerialDeviceStopBits;

class SerialClientTestConstants {
	public static final String SERVER_PORT = "/dev/tty.UC-232AC";
	public static final String CLIENT_PORT = "/dev/tty.usbserial-FTCASQQV";
	public static final int BAUD_RATE = 115200;
	public static final SerialDeviceDataBits DATA_BITS = SerialDeviceDataBits.DATABITS_8;
	public static final SerialDeviceStopBits STOP_BITS = SerialDeviceStopBits.STOPBITS_1;
	public static final SerialDeviceParity PARITY = SerialDeviceParity.PARITY_NONE;
	public static final int BUFFER_SIZE = 256;
	public static final int ITERATIONS = 500;

	public static final SerialDeviceConfiguration SERVER_CONFIG = new SerialDeviceConfiguration()
			.setPortName(SERVER_PORT)
			.setBaud(BAUD_RATE)
			.setDatabits(DATA_BITS)
			.setStopbits(STOP_BITS)
			.setParitybit(PARITY);

	public static final SerialDeviceConfiguration CLIENT_CONFIG = new SerialDeviceConfiguration()
			.setPortName(CLIENT_PORT)
			.setBaud(BAUD_RATE)
			.setDatabits(DATA_BITS)
			.setStopbits(STOP_BITS)
			.setParitybit(PARITY);

}
