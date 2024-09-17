package com.drxgb.dialogtranslator.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import com.drxgb.dialogtranslator.App;

import javafx.scene.control.MenuItem;

/**
 * Responsável por gerenciar arquivos, tais como checar
 * alterações, fazer carregamento e salvamento.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class FileManager
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	private boolean unsavedChanges;
	
	
	/*
	 * ===========================================================
	 * 			*** ASSOCIAÇÕES ***
	 * ===========================================================
	 */
	
	private App app;
	private MenuItem saveMenuItem;
	private ReaderService reader;
	private WriterService writer;

	 
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	/**
	 * Cria o gerenciador de arquivos.
	 * 
	 * @param app A instância da aplicação.
	 */
	public FileManager(App app)
	{
		Objects.requireNonNull(app);
		
		this.app = app;
		this.saveMenuItem = null;
		this.reader = null;
		this.writer = null;

		setUnsavedChanges(true);
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
		try (OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(filename))))
		{
			if (writer == null)
			{
				throw new IOException("Cannot find a file writer service");
			}
			
			writer.write(out);
			setUnsavedChanges(false);
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
		try (InputStream in = new BufferedInputStream(new FileInputStream(new File(filename))))
		{
			if (reader == null)
			{
				throw new IOException("Cannot find a file reader service");
			}
			
			reader.read(in);
			setUnsavedChanges(false);
		}
	}
	
	
	/*
	 * ===========================================================
	 * 			*** GETTERS E SETTERS ***
	 * ===========================================================
	 */

	/**
	 * Verifica se o arquivo possui alterações não salvas.
	 * 
	 * @return Sinal das alterações não salvas.
	 */
	public boolean hasUnsavedChanges()
	{
		return unsavedChanges;
	}
	
	
	/**
	 * Recebe o item do menu de salvar.
	 * 
	 * @return O item do menu.
	 */
	public MenuItem getSaveMenuItem()
	{
		return saveMenuItem;
	}


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
	 * Sinaliza se a aplicação possui alterações não salvas.
	 * 
	 * @param unsavedChanges Sinal das alterações não salvas.
	 */
	public void setUnsavedChanges(boolean unsavedChanges)
	{
		final StageTitleManager titleManager = app.getTitleManager();
		
		if (titleManager != null)
		{
			titleManager.setUnsaved(unsavedChanges);
		}
		if (saveMenuItem != null)
		{
			saveMenuItem.setDisable(! unsavedChanges);
		}
		
		this.unsavedChanges = unsavedChanges;
	}
	
	
	/**
	 * Define o item do menu de ação de salvar.
	 * 
	 * @param saveMenuItem O item do menu.
	 */
	public void setSaveMenuItem(MenuItem saveMenuItem)
	{
		saveMenuItem.setDisable(! unsavedChanges);
		this.saveMenuItem = saveMenuItem;
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
