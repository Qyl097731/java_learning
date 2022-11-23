package com.nju.netty.ch09.demo03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description
 * @date:2022/11/23 19:51
 * @author: qyl
 */
class FrameChunkDecodeTest {

    @Test
    public void test(){
        // 创建一个ByteBuf 并向他写入9字节
        ByteBuf buf = Unpooled.buffer ();
        for (int i = 0; i < 9; i++) {
            buf.writeByte (i);
        }
        ByteBuf input = buf.duplicate ();

        // 创建一个EmbeddedChannel 并向其指定三字节的帧大小的解析器
        EmbeddedChannel channel = new EmbeddedChannel (new FrameChunkDecode (3));
        assertTrue (channel.writeInbound (input.readBytes (2))); // 写入2字节，并断言他们会产生一个新帧
        try{
            channel.writeInbound (input.readBytes (4)); // 写入4个字节 必然抛出异常
        }catch (TooLongFrameException e){
//            e.printStackTrace ();
        }
        assertTrue (channel.writeInbound (input.readBytes (3))); // 写下剩余的字节，产生有效帧
        assertTrue (channel.finish ( ));  // 设置Channel的状态为已完成状态

        // Read frame 读取产生的消息，并且验证
        ByteBuf read = channel.readInbound ( );
        assertEquals (buf.readSlice (2),read);
        read.release ();

        read = channel.readInbound ();
        assertEquals (buf.skipBytes (4).readSlice (3),read);
        read.release ();
        buf.release ();
    }

}
