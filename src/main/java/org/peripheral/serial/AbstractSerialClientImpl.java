package org.peripheral.serial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioByteStreamChannel;
import io.netty.channel.oio.OioEventLoopGroup;
import org.runion.peripheral.api.serial.SerialClient;
import org.runion.peripheral.api.serial.SerialDeviceConfiguration;

import java.util.function.Consumer;

abstract class AbstractSerialClientImpl implements SerialClient {
	protected abstract void configureSerialPortParams(Bootstrap bootstrap, SerialDeviceConfiguration configuration);
	protected abstract ChannelFuture connect(Bootstrap bootstrap, String portName);

	private SerialDeviceConfiguration configuration;
	private ChannelInitializer<OioByteStreamChannel> initializer;
	private ChannelFuture channelFuture;
	private Consumer<Void> connectionLostHandler;
	private Consumer<Boolean> connectionAttemptedHandler;
	private EventLoopGroup eventLoopGroup;

	@Override
	public SerialClient setConnectionLostHandler(Consumer<Void> callback) {
		this.connectionLostHandler = callback;
		return this;
	}

	@Override
	public SerialClient setConnectionAttemptedHandler(Consumer<Boolean> callback) {
		this.connectionAttemptedHandler = callback;
		return this;
	}

	@Override
	public SerialClient setConfiguration(SerialDeviceConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}

	@Override
	public SerialClient setChannelInitializer(ChannelInitializer<OioByteStreamChannel> initializer) {
		this.initializer = initializer;
		return this;
	}

	@Override
	public void open() {
		if(!isConnected()) {
			eventLoopGroup = new OioEventLoopGroup();

			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup);
			bootstrap.handler(initializer);
			configureSerialPortParams(bootstrap, configuration);

			channelFuture = connect(bootstrap, configuration.getPortName());

			channelFuture.addListener(future -> {
				if(future.cause() != null) {
					future.cause().printStackTrace();
				}
				if(connectionAttemptedHandler != null) {
					connectionAttemptedHandler.accept(future.isSuccess());
				}
			});

			channelFuture.channel().closeFuture().addListener(future -> {
				if(connectionLostHandler != null) {
					connectionLostHandler.accept(null);
				}
			});
		}
	}

	@Override
	public void close() {
		if (channelFuture != null && isConnected()) {
			channelFuture.channel().disconnect();
		}

		if(channelFuture != null) {
			channelFuture.channel().close();
		}

		if(eventLoopGroup != null) {
			eventLoopGroup.shutdownGracefully();
		}
	}

	public boolean isConnected() {
		return (channelFuture != null) && (channelFuture.channel().isOpen());
	}
}
