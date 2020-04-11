package org.peripheral.serial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.jsc.JSerialCommChannel;
import io.netty.channel.jsc.JSerialCommChannelConfig;
import io.netty.channel.jsc.JSerialCommChannelOption;
import io.netty.channel.jsc.JSerialCommDeviceAddress;
import org.runion.peripheral.api.serial.SerialDeviceConfiguration;

public class JSerialCommSerialClient extends AbstractSerialClientImpl {
	@Override
	protected void configureSerialPortParams(Bootstrap bootstrap, SerialDeviceConfiguration configuration) {
		bootstrap.channel(JSerialCommChannel.class);
		bootstrap.option(JSerialCommChannelOption.BAUD_RATE, configuration.getBaud());
		bootstrap.option(JSerialCommChannelOption.READ_TIMEOUT, 5000);
		bootstrap.option(JSerialCommChannelOption.WAIT_TIME, 0);

		switch(configuration.getDatabits()) {
			case DATABITS_5:
				bootstrap.option(JSerialCommChannelOption.DATA_BITS, 5);
				break;
			case DATABITS_6:
				bootstrap.option(JSerialCommChannelOption.DATA_BITS, 6);
				break;
			case DATABITS_7:
				bootstrap.option(JSerialCommChannelOption.DATA_BITS, 7);
				break;
			case DATABITS_8:
				bootstrap.option(JSerialCommChannelOption.DATA_BITS, 8);
				break;
		}

		switch (configuration.getStopbits()) {
			case STOPBITS_1:
				bootstrap.option(JSerialCommChannelOption.STOP_BITS, JSerialCommChannelConfig.Stopbits.STOPBITS_1);
				break;
			case STOPBITS_2:
				bootstrap.option(JSerialCommChannelOption.STOP_BITS, JSerialCommChannelConfig.Stopbits.STOPBITS_2);
				break;
			case STOPBITS_1_5:
				bootstrap.option(JSerialCommChannelOption.STOP_BITS, JSerialCommChannelConfig.Stopbits.STOPBITS_1_5);
				break;
		}

		switch (configuration.getParitybit()) {
			case PARITY_NONE:
				bootstrap.option(JSerialCommChannelOption.PARITY_BIT, JSerialCommChannelConfig.Paritybit.NONE);
				break;
			case PARITY_ODD:
				bootstrap.option(JSerialCommChannelOption.PARITY_BIT, JSerialCommChannelConfig.Paritybit.ODD);
				break;
			case PARITY_EVEN:
				bootstrap.option(JSerialCommChannelOption.PARITY_BIT, JSerialCommChannelConfig.Paritybit.EVEN);
				break;
			case PARITY_MARK:
				bootstrap.option(JSerialCommChannelOption.PARITY_BIT, JSerialCommChannelConfig.Paritybit.MARK);
				break;
			case PARITY_SPACE:
				bootstrap.option(JSerialCommChannelOption.PARITY_BIT, JSerialCommChannelConfig.Paritybit.SPACE);
				break;
		}
	}

	@Override
	protected ChannelFuture connect(Bootstrap bootstrap, String portName) {
		return bootstrap.connect(new JSerialCommDeviceAddress(portName));
	}
}
