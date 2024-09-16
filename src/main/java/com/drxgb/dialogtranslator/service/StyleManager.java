package com.drxgb.dialogtranslator.service;

import java.util.List;

import com.drxgb.dialogtranslator.App;

/**
 * Responsável por gerenciar os estilos das telas.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class StyleManager
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	private String basePath;
	private String extension;
	private String currentStyle;
	private List<String> availableStyles;
	private List<String> observedStyleList;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	/**
	 * Cria o gerenciador de estilos.
	 * 
	 * @param avaliableStyles A lista dos estilos disponíveis.
	 * @param basePath O caminho base das folhas de estilo.
	 * @param extension A extensão dos arquivos da folha de estilo.
	 */
	public StyleManager(List<String> availableStyles, String basePath, String extension)
	{
		this.availableStyles = availableStyles;
		this.basePath = basePath;
		this.extension = extension;
	}
	
	
	/**
	 * Cria o gerenciador de estilos CSS.
	 * 
	 * @param avaliableStyles A lista dos estilos disponíveis.
	 * @param basePath O caminho base das folhas de estilo.
	 */
	public StyleManager(List<String> availableStyles, String basePath)
	{
		this(availableStyles, basePath, "css");
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Modifica o estilo.
	 * 
	 * @param style O nome do novo estilo.
	 */
	public void setStyle(String style)
	{
		if (availableStyles.contains(style))
		{
			if (observedStyleList != null)
			{
				observedStyleList.remove(styleSheetPath(currentStyle));
				observedStyleList.add(styleSheetPath(style));
			}
			
			currentStyle = style;
		}
	}
	
	
	/**
	 * Define uma lista de estilos para serem observadas pelo gerenciador.
	 * 
	 * @param list a lista de estilos a ser observada.
	 */
	public void observeStyleList(List<String> list)
	{
		observedStyleList = list;
	}
	
	
	/*
	 * ===========================================================
	 * 			*** GETTERS ***
	 * ===========================================================
	 */
	
	/**
	 * Recebe o nome do estilo atual.
	 * 
	 * @return O nome do estilo atual.
	 */
	public String getCurrentStyle()
	{
		return currentStyle;
	}
	
	
	/**
	 * Recebe a lista de estilos disponíveis.
	 * 
	 * @return A lista de estilos disponíveis.
	 */
	public List<String> getAvailableStyles()
	{
		return availableStyles;
	}
	
	
	/**
	 * Recebe os estilos que estão sendo observados pelo gerenciador.
	 * 
	 * @return A lista dos estilos observados.
	 */
	public List<String> getObservedStyleList()
	{
		return observedStyleList;
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */

	/**
	 * Monta o caminho do arquivo da folha de estilo.
	 * 
	 * @param style O nome do estilo.
	 * @return O caminho da folha de estilo.
	 */
	private String styleSheetPath(String style)
	{
		if (style == null)
		{
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		String path;
		
		sb.append(basePath)
			.append('/')
			.append(style.toLowerCase())
			.append('.')
			.append(extension);
		
		path = sb.toString();
		return App.class.getResource(path).toExternalForm();
	}
}
