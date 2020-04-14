package org.peripheral.serial;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import purejavacomm.NoSuchPortException;
import purejavacomm.PortInUseException;
import purejavacomm.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertArrayEquals;

public class RxTxStreamingSerialClientPerformanceTest {
	private RxTxStreamingSerialClient server;
	private RxTxStreamingSerialClient client;
	private List<Long> times = new ArrayList<>();

	@Before
	public void setup() {
		server = new RxTxStreamingSerialClient();
		client = new RxTxStreamingSerialClient();

		server.setConfiguration(SerialClientTestConstants.SERVER_CONFIG);
		client.setConfiguration(SerialClientTestConstants.CLIENT_CONFIG);
	}

	@Test
	public void testTiming() throws InterruptedException, TimeoutException, ExecutionException, IOException, PortInUseException, NoSuchPortException, UnsupportedCommOperationException, gnu.io.PortInUseException, gnu.io.NoSuchPortException, gnu.io.UnsupportedCommOperationException {
		if(server.open()) {
			TimeUnit.MILLISECONDS.sleep(1000);
			if(client.open()) {
				TimeUnit.MILLISECONDS.sleep(2000);
				byte[] bytes = new byte[SerialClientTestConstants.BUFFER_SIZE];

				for(int x = 0; x < bytes.length; x++) {
					bytes[x] = (byte)x;
				}
				for(int x = 0; x < SerialClientTestConstants.ITERATIONS; x++) {
					long startTime = System.nanoTime();
					client.getOutputStream().write(bytes);
					server.getOutputStream().write(readAll(server.getInputStream()));
					byte[] received = readAll(client.getInputStream());
					long endTime = System.nanoTime();
					times.add((endTime - startTime) / 1000 / 1000);
					assertArrayEquals(bytes, received);
				}
				System.out.printf("count: %s max: %smS min: %smS, average: %sms\n", times.size(), Collections.max(times), Collections.min(times), times.stream().mapToLong(s -> s).average().getAsDouble());
			}
		}
	}

	private byte[] readAll(InputStream stream) throws IOException {
		byte[] received = new byte[SerialClientTestConstants.BUFFER_SIZE];
		for(int x = 0; x < SerialClientTestConstants.BUFFER_SIZE; x++) {
			received[x] = (byte)stream.read();
		}
		return received;
	}

						   @After
	public void tearDown() throws IOException {
		server.close();
		client.close();
	}
}