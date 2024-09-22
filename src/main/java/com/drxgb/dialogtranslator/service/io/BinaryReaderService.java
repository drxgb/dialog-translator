package com.drxgb.dialogtranslator.service.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.PhraseGroup;
import com.drxgb.util.ByteHandler;
import com.drxgb.util.ValueHandler;

/**
 * Responsável por ler arquivos binários.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public abstract class BinaryReaderService extends ReaderService
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */

	private byte[] buffer;
	private int pos;
	private int bytesPerChar;
	private Charset charset;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	/**
	 * @see com.drxgb.dialogtranslator.service.io.ReaderService
	 */
	public BinaryReaderService(List<Language> languages, List<PhraseGroup> groups)
	{
		super(languages, groups);

		buffer = new byte[0];
		pos = 0;
		charset = Charset.defaultCharset();
		setBytesPerChar();
	}


	/*
	 * ===========================================================
	 * 			*** MÉTODOS PROTEGIDOS ***
	 * ===========================================================
	 */
	
	/**
	 * Inicializa os bytes para leitura do arquivo binário.
	 * 
	 * @param input O fluxo de dados de entrada.
	 * @throws IOException Quando não for possível realizar a leitura.
	 */
	protected synchronized void initBytes(InputStream input) throws IOException
	{
		buffer = input.readAllBytes();
		pos = 0;
	}
	
	
	/**
	 * Verifica se a leitura do arquivo chegou ao fim.
	 * 
	 * @return Se a leitura chegou ao fim.
	 */
	protected boolean isEOF()
	{
		return pos == buffer.length;
	}
	
	
	/**
	 * Recebe a posição atual da leitura.
	 * 
	 * @return A posição da leitura.
	 */
	protected int getPosition()
	{
		return pos;
	}
	
	
	/**
	 * Troca a posição de leitura.
	 * 
	 * @param input O fluxo de dados.
	 * @param pos A nova posição.
	 */
	protected synchronized void setPosition(int pos)
	{
		this.pos = ValueHandler.clamp(pos, 0, buffer.length - 1);
	}
	
	
	/**
	 * Lê 4 bytes e converte para inteiro.
	 * 
	 * @return O valor convertido para inteiro.
	 * 
	 * @throws IOException Quando não for possível realizar a leitura.
	 */
	protected synchronized int readInt() throws IOException
	{
		ensureByteBufferCreated();
		return ByteHandler.getInteger(readBytes(Integer.BYTES));
	}
	
	
	/**
	 * Lê o próximo caractere.
	 * 
	 * @return O caractere lido.
	 * 
	 * @throws IOException Quando não for possível realizar a leitura.
	 */
	protected synchronized char readChar() throws IOException
	{
		ByteBuffer byteBuffer;
		CharBuffer charBuffer;
		char[] charArray;
		
		ensureByteBufferCreated();
		
		byteBuffer = ByteBuffer.wrap(readBytes(bytesPerChar));
		charBuffer = charset.decode(byteBuffer);
		charArray = charBuffer.array();
		
		return charArray.length > 0 ? charArray[0] : 0;
	}
	
	
	/**
	 * Lê uma string do arquivo.
	 * 
	 * @return A string lida.
	 * 
	 * @throws IOException Quando não for possível realizar a leitura.
	 */
	protected synchronized String readString() throws IOException
	{
		String result = "";		
		char ch;
		
		ensureByteBufferCreated();
		
		do
		{
			if ((ch = readChar()) != 0)
			{
				result += ch;
			}
		} while (ch != 0);

		return result;
	}
	
	
	/**
	 * Recebe o mapeador de caracteres.
	 * 
	 * @return O mapeador.
	 */
	protected Charset getCharset()
	{
		return charset;
	}


	/**
	 * Modifica o mapeador de caracteres.
	 * 
	 * @param charset O novo nome do mapeador.
	 */
	protected void setCharset(String charset)
	{
		this.charset = Charset.forName(charset);
		setBytesPerChar();
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Certifica se o buffer de bytes foi devidamente criado.
	 * 
	 * @throws IOException Quando o buffer não foi criado ainda.
	 */
	private void ensureByteBufferCreated() throws IOException
	{
		if (buffer.length == 0)
		{
			throw new IOException("No byte buffer available.");
		}
	}


	/**
	 * Lê um byte direto do buffer.
	 * 
	 * @return O byte lido.
	 */
	private byte readFromBuffer()
	{
		return (pos < buffer.length) ? buffer[pos++] : 0;
	}
	
	
	/**
	 * Lê uma quantidade de bytes e guarda em um vetor.
	 * 
	 * @param len Tamanho do vetor.
	 * @return O vetor de bytes lidos.
	 */
	private byte[] readBytes(int len)
	{
		byte[] bytes = new byte[len];
		
		for (int i = 0; i < len; ++i)
		{
			bytes[i] = readFromBuffer();
		}
		
		return bytes;
	}
	
	
	/**
	 * Define a quantidade de bytes por caratere para leitura.
	 */
	private void setBytesPerChar()
	{		
		final CharsetDecoder decoder = charset.newDecoder();
		final float acpb = decoder.averageCharsPerByte();
		
		bytesPerChar = (int) Math.floor(1.0 / (double) acpb);
	}
}
