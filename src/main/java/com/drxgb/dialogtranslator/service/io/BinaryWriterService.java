package com.drxgb.dialogtranslator.service.io;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.PhraseGroup;
import com.drxgb.util.ByteContainer;
import com.drxgb.util.ByteHandler;

import javafx.util.Callback;

/**
 * Responsável por escrever arquivos binários.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public abstract class BinaryWriterService extends WriterService
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	private OutputStream out;
	private Charset charset;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	/**
	 * @see com.drxgb.dialogtranslator.service.io.WriterService
	 */
	public BinaryWriterService(List<Language> languages, List<PhraseGroup> groups)
	{
		super(languages, groups);
		this.charset = Charset.defaultCharset();
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PROTEGIDOS ***
	 * ===========================================================
	 */
	
	/**
	 * Inicializa o fluxo de escrita.
	 * 
	 * @param output O fluxo de dados.
	 */
	protected synchronized void initStream(OutputStream output)
	{
		out = new BufferedOutputStream(output);
	}

	
	/**
	 * Escreve um string ao arquivo.
	 * 
	 * @param data O texto a ser escrito.
	 * 
	 * @throws IOException Quando não é possível escrever no arquivo.
	 */
	protected synchronized void writeString(String data) throws IOException
	{
		out.write(getBytesFromString(data));
		out.flush();
	}
	
	
	/**
	 * Escreve um número inteiro ao arquivo.
	 * 
	 * @param data O valor a ser escrito.
	 * 
	 * @throws IOException Quando não é possível escrever no arquivo.
	 */
	protected synchronized void writeInt(int data) throws IOException
	{
		out.write(getBytesFromInteger(data));
		out.flush();
	}
	
	
	/**
	 * Escreve uma tripa de bytes ao arquivo.
	 * 
	 * @param bytes A tripa de bytes.
	 * 
	 * @throws IOException Quando não é possível escrever no arquivo.
	 */
	protected synchronized void writeBytes(byte[] bytes) throws IOException
	{
		out.write(bytes);
		out.flush();
	}
	
	
	/**
	 * Pula <code>n</code> bytes na esctira do arquivo.
	 * 
	 * @param length Quantidade de bytes a ser saltada.
	 * 
	 * @throws IOException	Quando não é possível escrever no arquivo
	 * 						ou quando a quantidade de salto fornecida
	 * 						for negativa.
	 */
	protected synchronized void skip(int length) throws IOException
	{
		if (length < 0)
		{
			throw new IOException("Cannot skip write position to back");
		}
		
		out.write(new byte[length]);
		out.flush();
	}
	
	
	/**
	 * Recebe um conjunto de bytes de uma string.
	 * 
	 * @param data A string recebida.
	 * @return Um conjunto de bytes decodificado.
	 */
	protected byte[] getBytesFromString(String data)
	{
		return (data + "\0").getBytes(charset);
	}
	
	
	/**
	 * Recebe um conjunto de bytes de um número inteiro.
	 * 
	 * @param value O número.
	 * @return Um conjunto de bytes decodificado.
	 */
	protected byte[] getBytesFromInteger(int value)
	{
		return ByteHandler.getBytes(value).toByteArray();
	}
	
	
	/**
	 * Recebe um conjunto de bytes de uma lista.
	 * 
	 * @param <T> Tipo de dados da lista.
	 * @param list A lista a ser lida.
	 * @param getName Callback para receber o nome do item da lista.
	 * @return Um conjunto de bytes decodificado.
	 */
	protected <T> byte[] getBytesFromList(List<T> list, Callback<T, String> getName)
	{
		ByteContainer container = new ByteContainer();
		String name;
		
		for (T item : list)
		{
			name = getName.call(item);
			addBytesToContainer(container, getBytesFromString(name));
		}
		
		return container.toByteArray();
	}
	
	
	/**
	 * Adiciona bytes ao conteiner de bytes.
	 * 
	 * @param container O conteiner.
	 * @param bytes O conjunto de bytes a ser incluído.
	 */
	protected void addBytesToContainer(ByteContainer container, byte[] bytes)
	{
		for (int i = 0; i < bytes.length; ++i)
		{
			container.append(bytes[i]);
		}
	}


	/**
	 * Recebe o mapeador de carcteres.
	 * 
	 * @return O mapeador.
	 */
	protected Charset getCharset()
	{
		return charset;
	}


	/**
	 * Modifica o mapeadoer de caracteres.
	 * 
	 * @param charset O nome do novo mapeador.
	 */
	protected void setCharset(String charsetName)
	{
		charset = Charset.forName(charsetName);
	}
}
