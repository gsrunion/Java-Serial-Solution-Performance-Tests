package org.peripheral.serial;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PureJavaCommSerialClientPerformanceTest {
	private PureJavaCommSerialClient server;
	private PureJavaCommSerialClient client;
	private CompletableFuture<Void> finishedFuture = new CompletableFuture<>();

	@Before
	public void setup() {
		server = new PureJavaCommSerialClient();
		client = new PureJavaCommSerialClient();
		server.setConfiguration(SerialClientTestConstants.SERVER_CONFIG);
		client.setConfiguration(SerialClientTestConstants.CLIENT_CONFIG);
		server.setChannelInitializer(SerialClientTestConstants.SERVER_INITIALIZER);
		client.setChannelInitializer(SerialClientTestConstants.CLIENT_INITIALIZER);
		client.setConnectionLostHandler(nil -> finishedFuture.complete(nil));
	}

	@Test
	public void testTiming() throws InterruptedException, TimeoutException, ExecutionException {
		server.open();
		TimeUnit.MILLISECONDS.sleep(1000);
		client.open();
		finishedFuture.get(120, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() {
		server.close();
		client.close();
	}
}