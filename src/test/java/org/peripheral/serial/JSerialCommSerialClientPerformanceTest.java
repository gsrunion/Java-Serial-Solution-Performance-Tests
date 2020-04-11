package org.peripheral.serial;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.oio.OioByteStreamChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JSerialCommSerialClientPerformanceTest {
	private JSerialCommSerialClient server;
	private JSerialCommSerialClient client;
	private CompletableFuture<Void> finishedFuture = new CompletableFuture<>();

	@Before
	public void setup() {
		server = new JSerialCommSerialClient();
		client = new JSerialCommSerialClient();

		server.setConfiguration(SerialClientTestConstants.SERVER_CONFIG);
		client.setConfiguration(SerialClientTestConstants.CLIENT_CONFIG);

		server.setChannelInitializer(new ChannelInitializer<OioByteStreamChannel>() {
			@Override
			protected void initChannel(OioByteStreamChannel oioByteStreamChannel) throws Exception {
				oioByteStreamChannel.pipeline().addLast(new EchoServerHandler());
			}
		});

		client.setChannelInitializer(new ChannelInitializer<OioByteStreamChannel>() {
			@Override
			protected void initChannel(OioByteStreamChannel oioByteStreamChannel) throws Exception {
				oioByteStreamChannel.pipeline().addLast(new FixedLengthFrameDecoder(SerialClientTestConstants.BUFFER_SIZE));
				oioByteStreamChannel.pipeline().addLast(new LoggingHandler(LogLevel.ERROR));
				oioByteStreamChannel.pipeline().addLast(new EchoClientHandler());
			}
		});

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