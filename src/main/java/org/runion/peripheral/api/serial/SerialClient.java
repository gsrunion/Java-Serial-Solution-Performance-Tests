package org.runion.peripheral.api.serial;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.oio.OioByteStreamChannel;

import java.util.function.Consumer;

public interface SerialClient {
	public SerialClient setConnectionLostHandler(Consumer<Void> callback);
	public SerialClient setConnectionAttemptedHandler(Consumer<Boolean> callback);
	public SerialClient setConfiguration(SerialDeviceConfiguration configuration);
	public SerialClient setChannelInitializer(ChannelInitializer<OioByteStreamChannel> initializer);
	public void open();
	public void close();
}
