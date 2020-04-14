package org.peripheral.serial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxChannelConfig;
import io.netty.channel.rxtx.RxtxChannelOption;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import org.runion.peripheral.api.serial.SerialDeviceConfiguration;

public class RxTxSerialNettyClient extends AbstractNettySerialClientImpl {
	@Override
	protected void configureSerialPortParams(Bootstrap bootstrap, SerialDeviceConfiguration configuration) {
		bootstrap.channel(RxtxChannel.class);
		bootstrap.option(RxtxChannelOption.BAUD_RATE, configuration.getBaud());
		bootstrap.option(RxtxChannelOption.READ_TIMEOUT, 5000);
		bootstrap.option(RxtxChannelOption.WAIT_TIME, 0);

		switch(configuration.getDatabits()) {
			case DATABITS_5:
				bootstrap.option(RxtxChannelOption.DATA_BITS, RxtxChannelConfig.Databits.DATABITS_5);
				break;
			case DATABITS_6:
				bootstrap.option(RxtxChannelOption.DATA_BITS,  RxtxChannelConfig.Databits.DATABITS_6);
				break;
			case DATABITS_7:
				bootstrap.option(RxtxChannelOption.DATA_BITS,  RxtxChannelConfig.Databits.DATABITS_7);
				break;
			case DATABITS_8:
				bootstrap.option(RxtxChannelOption.DATA_BITS,  RxtxChannelConfig.Databits.DATABITS_8);
				break;
		}

		switch (configuration.getStopbits()) {
			case STOPBITS_1:
				bootstrap.option(RxtxChannelOption.STOP_BITS, RxtxChannelConfig.Stopbits.STOPBITS_1);
				break;
			case STOPBITS_2:
				bootstrap.option(RxtxChannelOption.STOP_BITS, RxtxChannelConfig.Stopbits.STOPBITS_2);
				break;
			case STOPBITS_1_5:
				bootstrap.option(RxtxChannelOption.STOP_BITS, RxtxChannelConfig.Stopbits.STOPBITS_1_5);
				break;
		}

		switch (configuration.getParitybit()) {
			case PARITY_NONE:
				bootstrap.option(RxtxChannelOption.PARITY_BIT, RxtxChannelConfig.Paritybit.NONE);
				break;
			case PARITY_ODD:
				bootstrap.option(RxtxChannelOption.PARITY_BIT, RxtxChannelConfig.Paritybit.ODD);
				break;
			case PARITY_EVEN:
				bootstrap.option(RxtxChannelOption.PARITY_BIT, RxtxChannelConfig.Paritybit.EVEN);
				break;
			case PARITY_MARK:
				bootstrap.option(RxtxChannelOption.PARITY_BIT, RxtxChannelConfig.Paritybit.MARK);
				break;
			case PARITY_SPACE:
				bootstrap.option(RxtxChannelOption.PARITY_BIT, RxtxChannelConfig.Paritybit.SPACE);
				break;
		}
	}

	@Override
	protected ChannelFuture connect(Bootstrap bootstrap, String portName) {
		return bootstrap.connect(new RxtxDeviceAddress(portName));
	}
}
