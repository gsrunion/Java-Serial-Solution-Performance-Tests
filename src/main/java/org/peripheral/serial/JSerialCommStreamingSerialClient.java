package org.peripheral.serial;

import com.fazecast.jSerialComm.SerialPort;
import org.runion.peripheral.api.serial.NettySerialClient;
import org.runion.peripheral.api.serial.SerialDeviceConfiguration;
import org.runion.peripheral.api.serial.StreamingSerialClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JSerialCommStreamingSerialClient implements StreamingSerialClient {
	private SerialPort port;

	private InputStream inputStream;
	private OutputStream outputStream;

	@Override
	public boolean open() throws IllegalArgumentException {
		if (port.openPort()) {
			port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
			inputStream = port.getInputStream();
			outputStream = port.getOutputStream();
			return true;
		}
		return false;
	}

	@Override
	public boolean close() throws IOException {
		if (port.closePort()) {
			inputStream.close();
			inputStream = null;

			outputStream.close();
			outputStream = null;

			return true;
		}
		return false;
	}

	@Override
	public InputStream getInputStream() {
		return inputStream;
	}

	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}

	@Override
	public StreamingSerialClient setConfiguration(SerialDeviceConfiguration configuration) {
		port = SerialPort.getCommPort(configuration.getPortName());
		port.setBaudRate(configuration.getBaud());

		switch(configuration.getDatabits()) {
			case DATABITS_5:
				port.setNumDataBits(5);
			case DATABITS_6:
				port.setNumDataBits(6);
				break;
			case DATABITS_7:
				port.setNumDataBits(7);
				break;
			case DATABITS_8:
				port.setNumDataBits(8);
				break;
		}

		switch (configuration.getStopbits()) {
			case STOPBITS_1:
				port.setNumStopBits(SerialPort.ONE_STOP_BIT);
				break;
			case STOPBITS_2:
				port.setNumStopBits(SerialPort.TWO_STOP_BITS);
				break;
			case STOPBITS_1_5:
				port.setNumStopBits(SerialPort.ONE_POINT_FIVE_STOP_BITS);
				break;
		}

		switch (configuration.getParitybit()) {
			case PARITY_NONE:
				port.setParity(SerialPort.NO_PARITY);
				break;
			case PARITY_ODD:
				port.setParity(SerialPort.ODD_PARITY);
				break;
			case PARITY_EVEN:
				port.setParity(SerialPort.EVEN_PARITY);
				break;
			case PARITY_MARK:
				port.setParity(SerialPort.MARK_PARITY);
				break;
			case PARITY_SPACE:
				port.setParity(SerialPort.SPACE_PARITY);
				break;
		}

		port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 5000, 0);

		return this;
	}

}
