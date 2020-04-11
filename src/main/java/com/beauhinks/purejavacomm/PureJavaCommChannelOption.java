package com.beauhinks.purejavacomm;

import io.netty.channel.ChannelOption;

/**
 * Option for configuring a serial port connection
 */
public final class PureJavaCommChannelOption<T> extends ChannelOption<T> {
    public static final PureJavaCommChannelOption<Integer> BAUD_RATE =
            new PureJavaCommChannelOption<Integer>("BAUD_RATE");

    public static final PureJavaCommChannelOption<Boolean> DTR =
            new PureJavaCommChannelOption<Boolean>("DTR");

    public static final PureJavaCommChannelOption<Boolean> RTS =
            new PureJavaCommChannelOption<Boolean>("RTS");

    public static final PureJavaCommChannelOption<PureJavaCommChannelConfig.Stopbits> STOP_BITS =
            new PureJavaCommChannelOption<PureJavaCommChannelConfig.Stopbits>("STOP_BITS");

    public static final PureJavaCommChannelOption<PureJavaCommChannelConfig.Databits> DATA_BITS =
            new PureJavaCommChannelOption<PureJavaCommChannelConfig.Databits>("DATA_BITS");

    public static final PureJavaCommChannelOption<PureJavaCommChannelConfig.Paritybit> PARITY_BIT =
            new PureJavaCommChannelOption<PureJavaCommChannelConfig.Paritybit>("PARITY_BIT");

    public static final PureJavaCommChannelOption<Integer> WAIT_TIME =
            new PureJavaCommChannelOption<Integer>("WAIT_TIME");

    public static final PureJavaCommChannelOption<Integer> READ_TIMEOUT =
            new PureJavaCommChannelOption<Integer>("READ_TIMEOUT");

    @SuppressWarnings("deprecation")
    private PureJavaCommChannelOption(String name) {
        super(name);
    }
}
