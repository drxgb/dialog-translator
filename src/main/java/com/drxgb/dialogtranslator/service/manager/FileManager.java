package com.drxgb.dialogtranslator.service.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.model.Phrase;
import com.drxgb.dialogtranslator.model.PhraseGroup;
import com.drxgb.dialogtranslator.service.Container;
import com.drxgb.dialogtranslator.service.io.InvalidFieldException;
import com.drxgb.dialogtranslator.service.io.ReaderService;
import com.drxgb.dialogtranslator.service.io.WriterService;

import javafx.collections.ObservableList;

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
	 * @throws IOException Quando ocorrer um erro de escrita no arquivo.
	 */
	public void save(String filename) throws IOException
	{
		if (writer == null)
		{
			throw new IOException("Cannot find a file writer service");
		}
		
		validate();
		
		try (OutputStream out = new FileOutputStream(new File(filename)))
		{			
			writer.write(out);
		}
	}
	
	
	/**
	 * Carrega o arquivo solicitado do disco.
	 * 
	 * @param filename O arquivo a ser carregado.
	 * 
	 * @throws IOException Quando ocorrer um erro de leitura no arquivo.
	 */
	public void load(String filename) throws IOException
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
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Valida os dados.
	 * 
	 * @throws InvalidFieldException Quando os dados não são válidos.
	 */
	private void validate() throws InvalidFieldException
	{
		final Container container = App.getInstance().getContainer();
		ObservableList<PhraseGroup> groups = container.getGroups();
		List<Integer> invalidIndexes = new LinkedList<>();
		
		for (PhraseGroup group : groups)
		{
			List<Phrase> phrases = group.getPhrases();
			
			for (int i = 0; i < phrases.size(); ++i)
			{
				String key = phrases.get(i).getKey();
				
				if (key == null || key.isBlank())
				{
					invalidIndexes.add(i);
				}
			}
		}
		
		if (! invalidIndexes.isEmpty())
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("Some phrases has invalid keys: indexes -> [ ");
			invalidIndexes.stream().forEach(i -> sb.append(i).append(", "));
			sb.append(" ]. Please check for errors and fix them.");

			throw new InvalidFieldException(sb.toString());
		}
	}
}
