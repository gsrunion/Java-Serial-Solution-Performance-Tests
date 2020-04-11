package org.peripheral.serial;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import static org.junit.Assert.assertEquals;

class EchoClientHandler extends SimpleChannelInboundHandler<byte[]> {
	private final ByteBuf firstMessage;
	private int iterations = SerialClientTestConstants.ITERATIONS;
	private long sentTime;
	private long recvTime;
	private List<Long> times = new ArrayList<>();
	private int hash;

	public EchoClientHandler() {
		firstMessage = Unpooled.buffer(SerialClientTestConstants.BUFFER_SIZE);
		for (int i = 0; i < firstMessage.capacity(); i ++) {
			firstMessage.writeByte((byte) i);
		}
		hash = Arrays.hashCode(firstMessage.array());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		sentTime = System.nanoTime();
		ctx.writeAndFlush(firstMessage);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] data) throws Exception {
		recvTime = System.nanoTime();
		for(int x = 0; x < SerialClientTestConstants.BUFFER_SIZE; x++) {
			assertEquals(x, Byte.toUnsignedInt(data[x]));
		}
		long delta = (recvTime - sentTime) / 1000 / 1000;
		times.add(delta);
		iterations--;
		if(iterations > 0) {
			sentTime = System.nanoTime();
			ctx.writeAndFlush(data);
		} else {
			System.out.printf("count: %s max: %smS min: %smS, average: %sms\n", times.size(), Collections.max(times), Collections.min(times), times.stream().mapToLong(s -> s).average().getAsDouble());
			ctx.channel().close();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		//ctx.close();
	}
}
