package com.nju.netty.ch09.demo01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description
 * @date:2022/11/23 15:16
 * @author: qyl
 */
class FixedLengthFrameDecoderTest {

    @Test
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer ( );
        for (int i = 0; i < 9; i++) {
            buf.writeByte (i);
        }

        ByteBuf input = buf.duplicate ( );
        // 创建一个EmbeddedChannel 并添加一个一个固定长的解码器
        EmbeddedChannel channel = new EmbeddedChannel (new FixedLengthFrameDecoder (3));
        // write bytes
        // 将数据写入EmbeddedChannel
        assertTrue (channel.writeInbound (input.retain ( )));
        // 标记Channel为已完成状态
        assertTrue (channel.finish ( ));

        // read message 读取所生成的消息，并验证是否有三帧，每帧都是三个字节
        ByteBuf read = channel.readInbound ( );
        assertEquals (buf.readSlice (3), read);
        read.release ();

        read = channel.readInbound ();
        assertEquals (buf.readSlice (3),read);
        read.release ();

        read = channel.readInbound ();
        assertEquals (buf.readSlice (3),read);
        read.release ();

        assertNull (channel.readInbound ());
        buf.release ();
    }

    @Test
    public void testFramesDecoded2(){
        ByteBuf buf = Unpooled.buffer ( );
        for (int i = 0; i < 9; i++) {
            buf.writeByte (i);
        }
        ByteBuf input = buf.duplicate ( );

        EmbeddedChannel channel = new EmbeddedChannel (new FixedLengthFrameDecoder (3));
        // 在writeInbound的时候会检查后续readInbound是否有足够的字节可供读取，只有有足够的字节可供读取，FixedLengthFrameDecoder才会产生输出
        assertFalse (channel.writeInbound (input.readBytes (2)));
        assertTrue (channel.writeInbound (input.readBytes (7)));

        assertTrue (channel.finish ());
        ByteBuf read = channel.readInbound ( );
        ByteBuf slice = buf.readSlice (3);
        assertEquals (slice,read);
        read.release ();

        read = channel.readInbound ( );
        slice = buf.readSlice (3);
        assertEquals (slice,read);
        read.release ();

        read = channel.readInbound ( );
        slice = buf.readSlice (3);
        assertEquals (slice,read);
        read.release ();

        assertNull (channel.readInbound ());
        buf.release ();
    }

}
