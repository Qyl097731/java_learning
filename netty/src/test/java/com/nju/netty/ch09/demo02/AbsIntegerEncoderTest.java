package com.nju.netty.ch09.demo02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description
 * @date:2022/11/23 15:56
 * @author: qyl
 */
class AbsIntegerEncoderTest {
    @Test
    public void testEncoder() {
        ByteBuf buf = Unpooled.buffer ( );
        for (int i = 0; i < 10; i++) {
            buf.writeInt (i * -1);
        }
        EmbeddedChannel channel = new EmbeddedChannel (new AbsIntegerEncoder ( ));
        // 写入ByteBuf 并断言调用readOutbound()方法将会产生数据
        assertTrue (channel.writeOutbound (buf));
        // 将该Channel标记为已完成状态
        assertTrue (channel.finish ());

        // read bytes
        for (int i = 0; i < 10; i++) {
            // 读取所产生的消息，并断言它们包含的对应的绝对值
            assertEquals (i, (Integer) channel.readOutbound ());
        }
        assertNull (channel.readOutbound ());
    }

}
