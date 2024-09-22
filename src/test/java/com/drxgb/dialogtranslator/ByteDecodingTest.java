package com.drxgb.dialogtranslator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

/**
 * Testa decodificação de bytes.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
class ByteDecodingTest
{
	@Test
	void test()
	{
		byte[] bytes = new byte[] { 0x48, 0x00, 0x65, 0x00, 0x6C, 0x00, 0x6C, 0x00, 0x6F, 0x00 };
		Charset charset = Charset.forName("UTF-16LE");
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		CharBuffer charBuffer = charset.decode(byteBuffer);
		String result = charBuffer.toString();
		
		assertEquals("Hello", result);
	}
}
