package com.drxgb.dialogtranslator.service.manager;

import javafx.stage.Stage;

/**
 * Responsável por atualizar o título da janela.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class StageTitleManager
{
	/*
	 * ===========================================================
	 * 			*** CONSTANTES ***
	 * ===========================================================
	 */
	
	private static final String DEFAULT_TITLE = "Untitled";
	
	
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	private String baseTitle;
	private String title;
	private Boolean unsaved;
	
	
	/*
	 * ===========================================================
	 * 			*** ASSOCIAÇÕES ***
	 * ===========================================================
	 */
	
	private Stage stage;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	/**
	 * Cria o gerenciador de títulos com a base título.
	 * 
	 * @param stage A instância da janela.
	 * @param baseTitle A base do título.
	 */
	public StageTitleManager(Stage stage, String baseTitle)
	{
		this.stage = stage;
		this.baseTitle = baseTitle;
		this.title = "";
		this.unsaved = false;
		
		updateTitle();
	}
	
	
	/**
	 * Cria o gerenciador de título sem a base do título.
	 * @param stage
	 */
	public StageTitleManager(Stage stage)
	{
		this(stage, "");
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Atualiza o título na janela.
	 */
	public void updateTitle()
	{
		stage.setTitle(getFullTitle());
	}


	/**
	 * Recebe o título completo da janela.
	 * 
	 * @return O título completo.
	 */
	public String getFullTitle()
	{
		StringBuilder sb = new StringBuilder();
		
		if (unsaved)
		{
			sb.append('*');
		}
		
		sb.append(baseTitle)
			.append(" - ")
			.append('[')
			.append(getTitle())
			.append(']');
		
		return sb.toString();
	}
	
	
	/**
	 * Verifica se a janela possui um título para o nome do arquivo.
	 * 
	 * @return Se possui título ou não.
	 */
	public boolean isUntitled()
	{
		return title == null || title.isEmpty();
	}
	

	/*
	 * ===========================================================
	 * 			*** GETTERS E SETTERS ***
	 * ===========================================================
	 */

	/**
	 * Recebe o título somente com o nome do arquivo.
	 * 
	 * @return O título.
	 */
	public String getTitle()
	{
		return isUntitled() ? DEFAULT_TITLE : title;
	}
	

	/**
	 * Modifica o título da janela.
	 * 
	 * @param title O novo título da janela.
	 */
	public void setTitle(String title)
	{		
		this.title = title;
		updateTitle();
	}


	/**
	 * Verifica se a janela contém alterações não salvas.
	 * 
	 * @return O sinal das alterações não salvas.
	 */
	public Boolean isUnsaved()
	{
		return unsaved;
	}


	/**
	 * Recebe a instância da janela onde contém o título.
	 * 
	 * @return A instância da janela.
	 */
	public Stage getStage()
	{
		return stage;
	}


	/**
	 * Sinaliza se a janela contém alterações não salvas.
	 * 
	 * @param unsaved O novo sinal das alterações não salvas.
	 */
	public void setUnsaved(Boolean unsaved)
	{
		this.unsaved = unsaved;
		updateTitle();
	}	
}
