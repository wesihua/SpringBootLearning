package com.wei.boot.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Date;

/**
 * @Description: netty服务端
 * @Author: weisihua
 * @Date: 2019-08-15 10:35
 **/
public class TimeServer {

    public static void main(String[] args) throws Exception {
        new TimeServer().bind(8080);
    }

    public void bind(int port) throws Exception {
        /*
         * 配置服务端线程组
         * NioEventLoopGroup 是个线程组，包含了一组NIO线程，实际上它们就是Reactor线程组
         * boss用于接收客户端连接
         * worker用于进行SocketChannel网络读写
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 用于启动NIO服务端的辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // NioServerSocketChannel 对应于 java nio 类库中的 ServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    // 设置tcp参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 绑定I/O事件的处理类
                    .childHandler(new ChildChannelHandler());
            // 绑定端口，并同步等待成功
            ChannelFuture f = b.bind(port).sync();

            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {

            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    private class TimeServerHandler extends ChannelHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req, "utf-8");
            System.out.println(">>>>>>>>>>>>>>>>The time server receive order : " + body);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
            // 返回值
            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.write(resp);
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
