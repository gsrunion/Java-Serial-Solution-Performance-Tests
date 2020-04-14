package org.runion.peripheral.api.serial;

import purejavacomm.NoSuchPortException;
import purejavacomm.PortInUseException;
import purejavacomm.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamingSerialClient {
	public boolean open() throws NoSuchPortException, UnsupportedCommOperationException, PortInUseException, IOException, gnu.io.NoSuchPortException, gnu.io.UnsupportedCommOperationException, gnu.io.PortInUseException;
	public boolean close() throws IOException;
	public InputStream getInputStream() throws IOException;
	public OutputStream getOutputStream() throws IOException;
	public StreamingSerialClient setConfiguration(SerialDeviceConfiguration configuration);
}
