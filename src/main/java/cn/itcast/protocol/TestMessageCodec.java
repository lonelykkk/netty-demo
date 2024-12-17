package cn.itcast.protocol;

import cn.itcast.message.LoginRequestMessage;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/12/17 16:11
 * @Version V1.0
 */
public class TestMessageCodec {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                new MessageCodec());

        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123", "张三");
        channel.writeOutbound(message);
    }
}
