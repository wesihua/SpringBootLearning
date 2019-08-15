package com.wei.boot.netty.lineStream;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Date;

/**
 * @Description:
 * @Author: weisihua
 * @Date: 2019-08-15 14:48
 **/
public class TimeServer {

    public static void main(String[] args) throws Exception {
        new TimeServer().bind(8080);
    }

    public void bind(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 加入行解码器
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            // 加入String解码器
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new TimeServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 服务端处理器
     */
    private class TimeServerHandler extends ChannelHandlerAdapter{

        private int counter;

        /**
         * channel收到数据流时处理方法
         * @param ctx
         * @param msg
         * @throws Exception
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            /*
            ByteBuf buf = (ByteBuf) msg;
            byte[] request = new byte[buf.readableBytes()];
            buf.readBytes(request);
            int index = request.length - System.getProperty("line.separator").length();
            String body = new String(request, "utf-8").substring(0, index);
             */
            // 此处可直接用string接收
            String body = (String) msg;
            System.out.println(">>>>>>>>>>>>>>>>The time server receive order : " + body + " ; the counter is : " + ++counter);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
            currentTime = currentTime + System.getProperty("line.separator");
            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.writeAndFlush(resp);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
