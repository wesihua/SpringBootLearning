package com.wei.boot.netty.unlineStream;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description:
 * @Author: weisihua
 * @Date: 2019-08-15 15:31
 **/
public class TimeClient {
    public static void main(String[] args) throws Exception {
        new TimeClient().connect("localhost", 8080);
    }

    /**
     * 连接server
     * @param host
     * @param port
     * @throws Exception
     */
    public void connect(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(host, port).sync();

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    private class TimeClientHandler extends ChannelHandlerAdapter {

        private int counter;

        private byte[] req;

        public TimeClientHandler(){
            req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
        }

        /**
         * channel连接成功后直接发送数据流
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ByteBuf message = null;
            for (int i = 0; i<100; i++){
                message = Unpooled.buffer(req.length);
                message.writeBytes(req);
                ctx.writeAndFlush(message);
            }
        }

        /**
         * 收到消息流的时候读取
         * @param ctx
         * @param msg
         * @throws Exception
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            byte[] resp = new byte[buf.readableBytes()];
            buf.readBytes(resp);
            String body = new String(resp, "utf-8");
            System.out.println("Now is : " + body + " ; the counter is : " + ++counter);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
