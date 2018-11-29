package the.flash.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author chao.yu
 * chao.yu@dianping.com
 * @date 2018/08/04 06:21.
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 与客户端不同的是，这里覆盖的方法是 channelRead()，这个方法在接收到客户端发来的数据之后被回调。
     * @param ctx
     * @param msg msg 参数指的就是 Netty 里面数据读写的载体
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

        // 回复数据到客户端
        // 服务端向客户端写数据逻辑与客户端侧的写数据逻辑一样
        // 先创建一个 ByteBuf，然后填充二进制数据，最后调用 writeAndFlush() 方法写出去
        System.out.println(new Date() + ": 服务端写出数据");
        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "你好，欢迎关注我的微信公众号，《闪电侠的博客》!".getBytes(Charset.forName("utf-8"));

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
