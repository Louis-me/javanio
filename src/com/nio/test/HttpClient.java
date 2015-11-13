package com.nio.test;



import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;

import java.net.URI;

public class HttpClient {
    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new HttpClientInboundHandler()); 
                }
            });

            // Start the client.
//            for(int k=0; k<3;k++){
            	 ChannelFuture f = b.connect(host, port).sync();

                 URI uri = new URI("http://www.weather.com.cn/adat/sk/101010100.html");
                 String msg = "Are you ok?";
                 DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                         uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

                 // 构建http请求
                 request.headers().set(HttpHeaders.Names.HOST, host);
                 request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                 request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
                 // 发送http请求
                 f.channel().write(request);
                 f.channel().flush();
                 f.channel().closeFuture().sync();
//            }
           
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        HttpClient client = new HttpClient();
            //请自行修改成服务端的IP
        for(int k=0; k<3;k++){
            client.connect("www.weather.com.cn", 80);
            }
    }
}