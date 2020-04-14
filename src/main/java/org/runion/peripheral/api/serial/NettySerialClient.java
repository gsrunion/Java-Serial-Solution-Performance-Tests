package org.runion.peripheral.api.serial;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.oio.OioByteStreamChannel;

import java.util.function.Consumer;

public interface NettySerialClient {
	public NettySerialClient setConnectionLostHandler(Consumer<Void> callback);
	public NettySerialClient setConnectionAttemptedHandler(Consumer<Boolean> callback);
	public NettySerialClient setConfiguration(SerialDeviceConfiguration configuration);
	public NettySerialClient setChannelInitializer(ChannelInitializer<OioByteStreamChannel> initializer);
	public void open();
	public void close();
}
