package org.peripheral.serial;

import com.beauhinks.purejavacomm.PureJavaCommChannel;
import com.beauhinks.purejavacomm.PureJavaCommChannelConfig;
import com.beauhinks.purejavacomm.PureJavaCommChannelOption;
import com.beauhinks.purejavacomm.PureJavaCommDeviceAddress;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import org.runion.peripheral.api.serial.SerialDeviceConfiguration;

public class PureJavaCommSerialClient extends AbstractSerialClientImpl {
	@Override
	protected void configureSerialPortParams(Bootstrap bootstrap, SerialDeviceConfiguration configuration) {
		bootstrap.channel(PureJavaCommChannel.class);
		bootstrap.option(PureJavaCommChannelOption.BAUD_RATE, configuration.getBaud());
		bootstrap.option(PureJavaCommChannelOption.READ_TIMEOUT, 5000);
		bootstrap.option(PureJavaCommChannelOption.WAIT_TIME, 0);
		//bootstrap.option(PureJavaCommChannelOption.DTR, false);
		//bootstrap.option(PureJavaCommChannelOption.RTS, false);

		switch(configuration.getDatabits()) {
			case DATABITS_5:
				bootstrap.option(PureJavaCommChannelOption.DATA_BITS, PureJavaCommChannelConfig.Databits.DATABITS_5);
				break;
			case DATABITS_6:
				bootstrap.option(PureJavaCommChannelOption.DATA_BITS, PureJavaCommChannelConfig.Databits.DATABITS_6);
				break;
			case DATABITS_7:
				bootstrap.option(PureJavaCommChannelOption.DATA_BITS, PureJavaCommChannelConfig.Databits.DATABITS_7);
				break;
			case DATABITS_8:
				bootstrap.option(PureJavaCommChannelOption.DATA_BITS, PureJavaCommChannelConfig.Databits.DATABITS_8);
				break;
		}

		switch (configuration.getStopbits()) {
			case STOPBITS_1:
				bootstrap.option(PureJavaCommChannelOption.STOP_BITS, PureJavaCommChannelConfig.Stopbits.STOPBITS_1);
				break;
			case STOPBITS_2:
				bootstrap.option(PureJavaCommChannelOption.STOP_BITS, PureJavaCommChannelConfig.Stopbits.STOPBITS_2);
				break;
			case STOPBITS_1_5:
				bootstrap.option(PureJavaCommChannelOption.STOP_BITS, PureJavaCommChannelConfig.Stopbits.STOPBITS_1_5);
				break;
		}

		switch (configuration.getParitybit()) {
			case PARITY_NONE:
				bootstrap.option(PureJavaCommChannelOption.PARITY_BIT, PureJavaCommChannelConfig.Paritybit.NONE);
				break;
			case PARITY_ODD:
				bootstrap.option(PureJavaCommChannelOption.PARITY_BIT, PureJavaCommChannelConfig.Paritybit.ODD);
				break;
			case PARITY_EVEN:
				bootstrap.option(PureJavaCommChannelOption.PARITY_BIT, PureJavaCommChannelConfig.Paritybit.EVEN);
				break;
			case PARITY_MARK:
				bootstrap.option(PureJavaCommChannelOption.PARITY_BIT, PureJavaCommChannelConfig.Paritybit.MARK);
				break;
			case PARITY_SPACE:
				bootstrap.option(PureJavaCommChannelOption.PARITY_BIT, PureJavaCommChannelConfig.Paritybit.SPACE);
				break;
		}
	}

	@Override
	protected ChannelFuture connect(Bootstrap bootstrap, String portName) {
		return bootstrap.connect(new PureJavaCommDeviceAddress(portName));
	}
}
