package org.peripheral.serial;

import gnu.io.*;
import org.runion.peripheral.api.serial.SerialDeviceConfiguration;
import org.runion.peripheral.api.serial.StreamingSerialClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RxTxStreamingSerialClient implements StreamingSerialClient {
	private SerialPort port;
	private SerialDeviceConfiguration configuration;

	@Override
	public boolean open() throws IllegalArgumentException, NoSuchPortException, UnsupportedCommOperationException, PortInUseException, IOException {
		CommPortIdentifier commPortIdentifier = CommPortIdentifier.getPortIdentifier(configuration.getPortName());
		port = (SerialPort) commPortIdentifier.open(getClass().getName(), 1000);

		int databits = 0;
		switch(configuration.getDatabits()) {
			case DATABITS_5:
				databits = SerialPort.DATABITS_5;
				break;
			case DATABITS_6:
				databits = SerialPort.DATABITS_6;
				break;
			case DATABITS_7:
				databits = SerialPort.DATABITS_7;
				break;
			case DATABITS_8:
				databits = SerialPort.DATABITS_8;
				break;
		}

		int stopbits = 0;
		switch (configuration.getStopbits()) {
			case STOPBITS_1:
				stopbits = SerialPort.STOPBITS_1;
				break;
			case STOPBITS_2:
				stopbits = SerialPort.STOPBITS_2;
				break;
			case STOPBITS_1_5:
				stopbits = SerialPort.STOPBITS_1_5;
				break;
		}

		int parity = 0;
		switch (configuration.getParitybit()) {
			case PARITY_NONE:
				parity = SerialPort.PARITY_NONE;
				break;
			case PARITY_ODD:
				parity = SerialPort.PARITY_ODD;
				break;
			case PARITY_EVEN:
				parity = SerialPort.PARITY_EVEN;
				break;
			case PARITY_MARK:
				parity = SerialPort.PARITY_MARK;
				break;
			case PARITY_SPACE:
				parity = SerialPort.PARITY_SPACE;
				break;
		}

		port.setSerialPortParams(configuration.getBaud(), databits, stopbits, parity);
		port.setDTR(false);
		port.setRTS(false);
		port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
		port.enableReceiveTimeout(5000);
		port.setInputBufferSize(256);
		port.setOutputBufferSize(256);
		return true;
	}

	@Override
	public boolean close() throws IOException {
		port.close();
		return true;

	}

	@Override
	public InputStream getInputStream() throws IOException {
		return port.getInputStream();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return port.getOutputStream();
	}

	@Override
	public StreamingSerialClient setConfiguration(SerialDeviceConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}

}
