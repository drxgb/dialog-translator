package com.drxgb.dialogtranslator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

/**
 * Teste para ler binários.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
class BinaryReaderTest
{
	@Test
	void test() throws FileNotFoundException, IOException
	{
		try (InputStream input = getClass().getResourceAsStream("test.txt"))
		{
			byte[] bytes = input.readAllBytes();
			Charset charset = Charset.forName("UTF-16LE");
			ByteBuffer buffer = ByteBuffer.wrap(bytes);
			CharBuffer decoded = charset.decode(buffer);		
			String result = decoded.toString();
			
			assertEquals("EITA BIXO\0", result);
		}
	}
}
