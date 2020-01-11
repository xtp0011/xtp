package com.xtp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;

@ChannelHandler.Sharable
public  class MyWebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handshaker;
    private static final String WEB_SOCKET_UTL = "ws://localhost:8080/websocket";

    //服务端处理客户端请求的核心方法
    @Override
    protected void channelRead0(ChannelHandlerContext context, Object msg) throws Exception {
        //处理http握手请求业务
        if(msg instanceof FullHttpRequest){
            handHttpRequest(context,(FullHttpRequest) msg);
        }else if(msg instanceof WebSocketFrame){//处理连接业务
            handWebSocketFrame(context,(WebSocketFrame)msg);
        }
    }

    //处理连接业务
    private void handWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        //判断是否关闭
        if(frame instanceof CloseWebSocketFrame){
            handshaker.close(ctx.channel(),((CloseWebSocketFrame) frame).retain());
        }
        //判断是否ping消息
        if(frame instanceof PingWebSocketFrame){
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        //判断是否时二进制消息
        if(!(frame instanceof TextWebSocketFrame)){
            System.out.println("目前不支持二进制消息");
            throw  new RuntimeException("【"+this.getClass().getName()+"】"+"不支持消息");
        }

        //返回应答消息
        String req = ((TextWebSocketFrame) frame).text();
        System.out.println("服务度收到客户端的消息》》》"+req);
        TextWebSocketFrame twf = new TextWebSocketFrame(new Date().toString()+
                ctx.channel().id() +">>>>>>> "+req);
        //服务群发消息
        NettyConfig.group.writeAndFlush(twf);
    }

    //处理握手请求业务
    private void handHttpRequest(ChannelHandlerContext context, FullHttpRequest req) {
        if(!req.decoderResult().isSuccess()||!("websocket".equals(req.headers().get("Upgrade")))){
            sendHttpResponse(context,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(WEB_SOCKET_UTL,null,false);
        handshaker = wsFactory.newHandshaker(req);
        if(handshaker==null){
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(context.channel());
        }else {
            handshaker.handshake(context.channel(),req);
        }

    }

    //处理响应信息
    private void sendHttpResponse(ChannelHandlerContext context, FullHttpRequest req,
                                  DefaultFullHttpResponse res) {
        if(res.status().code()!=200){
            ByteBuf byteBuf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(byteBuf);
            byteBuf.release();
        }
        //服务端向客户端发送数据
        ChannelFuture future = context.channel().writeAndFlush(res);
        if(res.status().code()!=200){
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    //连接时调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyConfig.group.add(ctx.channel());
        System.out.println("客户端与服务端连接开启...");
    }

    //断开连接时调用
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyConfig.group.remove(ctx.channel());
        System.out.println("客户端与服务端连接关闭...");
    }

    //发送数据结束时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    //出现异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if(ctx.channel().isActive()) ctx.close();
    }
}
