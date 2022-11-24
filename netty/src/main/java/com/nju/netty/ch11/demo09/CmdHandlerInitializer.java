package com.nju.netty.ch11.demo09;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @description 将帧通过行尾分割解码器进行分割成Cmd对象
 * @date:2022/11/24 17:59
 * @author: qyl
 */
public class CmdHandlerInitializer extends ChannelInitializer<Channel> {
    final byte SPACE = (byte)' ';
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline ( );
        pipeline.addLast (new CmdDecoder(64*1024));
    }

    private static final class Cmd{
        private final ByteBuf name;
        private final ByteBuf args;

        private Cmd(ByteBuf name, ByteBuf args) {
            this.name = name;
            this.args = args;
        }

        public ByteBuf getName() {
            return name;
        }

        public ByteBuf getArgs() {
            return args;
        }


    }

    private class CmdDecoder extends LineBasedFrameDecoder {
        public CmdDecoder(int maxLength) {
            super (maxLength);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
            ByteBuf frame = (ByteBuf) super.decode (ctx, buffer);   // 从ByteBuf中提取由行尾序列分割的帧
            if (frame == null){
                return null;
            }
            // 查找第一个空格字符的索引，前面是名称，后面是参数
            int index = frame.indexOf (frame.readerIndex (),frame.writerIndex (),SPACE);
            return new Cmd (frame.slice (frame.readerIndex (),index)
            ,frame.slice (index+1,frame.writerIndex ()));
        }
    }

    private static final class CmdHandler extends SimpleChannelInboundHandler<Cmd>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Cmd msg) throws Exception {
            // 处理Cmd对象
        }
    }
}
