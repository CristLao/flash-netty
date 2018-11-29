package the.flash.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author chao.yu
 * chao.yu@dianping.com
 * @date 2018/08/04 06:23.
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * channelActive()方法，这个方法会在客户端连接建立成功之后被调用
     * 客户端连接建立成功之后，调用到 channelActive() 方法，在这个方法里面，我们编写向服务端写数据的逻辑
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端写出数据");

        // 1.获取数据
        // 首先我们需要获取一个 netty 对二进制数据的抽象 ByteBuf
        ByteBuf buffer = getByteBuf(ctx);

        // 2.写数据
        // ctx.channel().writeAndFlush() 把数据写到服务端
        ctx.channel().writeAndFlush(buffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "你好，闪电侠!".getBytes(Charset.forName("utf-8"));
        // ctx.alloc() 获取到一个 ByteBuf 的内存管理器，这个内存管理器的作用就是分配一个 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        // 把字符串的二进制数据填充到 ByteBuf，这样我们就获取到了 Netty 需要的一个数据格式
        buffer.writeBytes(bytes);

        return buffer;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 客户端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }
}
