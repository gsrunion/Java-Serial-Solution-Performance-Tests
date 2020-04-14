package org.peripheral.serial;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.oio.OioByteStreamChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.ietf.jgss.Oid;
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

	public static final ChannelInitializer<OioByteStreamChannel> SERVER_INITIALIZER = new ChannelInitializer<OioByteStreamChannel>() {
		@Override
		protected void initChannel(OioByteStreamChannel oioByteStreamChannel) throws Exception {
			oioByteStreamChannel.pipeline().addLast(new FixedLengthFrameDecoder(SerialClientTestConstants.BUFFER_SIZE));
			//oioByteStreamChannel.pipeline().addLast(new LoggingHandler(LogLevel.ERROR));
			oioByteStreamChannel.pipeline().addLast(new ByteArrayDecoder());
			oioByteStreamChannel.pipeline().addLast(new ByteArrayEncoder());
			oioByteStreamChannel.pipeline().addLast(new EchoServerHandler());
		}
	};

	public static final ChannelInitializer<OioByteStreamChannel> CLIENT_INITIALIZER = new ChannelInitializer<OioByteStreamChannel>() {
		@Override
		protected void initChannel(OioByteStreamChannel oioByteStreamChannel) throws Exception {
			oioByteStreamChannel.pipeline().addLast(new FixedLengthFrameDecoder(SerialClientTestConstants.BUFFER_SIZE));
			//oioByteStreamChannel.pipeline().addLast(new LoggingHandler(LogLevel.ERROR));
			oioByteStreamChannel.pipeline().addLast(new ByteArrayDecoder());
			oioByteStreamChannel.pipeline().addLast(new ByteArrayEncoder());
			oioByteStreamChannel.pipeline().addLast(new EchoClientHandler());
		}
	};


}
