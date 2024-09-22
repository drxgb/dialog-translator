package com.drxgb.dialogtranslator.service.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.drxgb.dialogtranslator.service.io.ReaderService;
import com.drxgb.dialogtranslator.service.io.WriterService;

/**
 * Responsável por gerenciar arquivos, tais como
 * fazer carregamento e salvamento dos dados.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class FileManager
{
	/*
	 * ===========================================================
	 * 			*** ASSOCIAÇÕES ***
	 * ===========================================================
	 */

	private ReaderService reader;
	private WriterService writer;

	 
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	/**
	 * Cria o gerenciador de arquivos.
	 */
	public FileManager()
	{
		this.reader = null;
		this.writer = null;
	}
	
	 
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Salva o arquivo solicitado no disco.
	 * 
	 * @param filename O caminho do arquivo a ser salvo.
	 * 
	 * @throws FileNotFoundException Quando o arquivo não foi encontrado.
	 * @throws IOException Quando ocorrer um erro de escrita no arquivo.
	 */
	public void save(String filename) throws FileNotFoundException, IOException
	{
		try (OutputStream out = new FileOutputStream(new File(filename)))
		{
			if (writer == null)
			{
				throw new IOException("Cannot find a file writer service");
			}
			
			writer.write(out);
		}
	}
	
	
	/**
	 * Carrega o arquivo solicitado do disco.
	 * 
	 * @param filename O arquivo a ser carregado.
	 * 
	 * @throws FileNotFoundException Quando o arquivo não foi encontrado.
	 * @throws IOException Quando ocorrer um erro de leitura no arquivo.
	 */
	public void load(String filename) throws FileNotFoundException, IOException
	{
		try (InputStream in = new FileInputStream(new File(filename)))
		{
			if (reader == null)
			{
				throw new IOException("Cannot find a file reader service");
			}
			
			reader.read(in);
		}
	}
	
	
	/*
	 * ===========================================================
	 * 			*** GETTERS E SETTERS ***
	 * ===========================================================
	 */

	/**
	 * Recebe o leitor de arquivos.
	 * 
	 * @return O leitor de arquivos.
	 */
	public ReaderService getReader()
	{
		return reader;
	}


	/**
	 * Recebe o escritor de arquivos.
	 * 
	 * @return O escritor de arquivos.
	 */
	public WriterService getWriter()
	{
		return writer;
	}


	/**
	 * Define um leitor de arquivos.
	 * 
	 * @param reader O novo leitor de arquivos.
	 */
	public void setReader(ReaderService reader)
	{
		this.reader = reader;
	}


	/**
	 * Define um escritor de arquivos.
	 *
	 * @param writer O novo escritor de arquivos.
	 */
	public void setWriter(WriterService writer)
	{
		this.writer = writer;
	}
}
