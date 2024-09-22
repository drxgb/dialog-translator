package com.drxgb.dialogtranslator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.drxgb.dialogtranslator.App;

/**
 * Utilitário para manipular a lista de arquivos recentes.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public abstract class RecentFiles
{
	/*
	 * ===========================================================
	 * 			*** CONSTANTES ***
	 * ===========================================================
	 */
	
	/**
	 * Chave base do arquivo de propriedades onde armazena
	 * a lista de arquivos recentes.
	 */
	private static final String BASE_KEY = "recentFile";
	
	/**
	 * Capacidade máxima de arquivos recentes.
	 */
	private static final int MAX_FILES = 10;

	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ABSTRATOS ***
	 * ===========================================================
	 */
	
	/**
	 * Carrega a lista de arquivos recentes.
	 * 
	 * @return A lista de arquivos recentes.
	 */
	public static List<String> read()
	{
		Properties settings = App.getInstance().getSettings();
		List<String> files = new ArrayList<>();
		int index = 1;
		
		while (index <= MAX_FILES)
		{
			StringBuilder sb = new StringBuilder();
			String key;
			
			sb.append(BASE_KEY).append(index++);
			key = sb.toString();
			
			if (settings.containsKey(key))
			{
				files.add(settings.getProperty(key));
			}
		}
		
		return files;
	}
	
	
	/**
	 * Atualiza a lista de arquivos nas configurações.
	 * 
	 * @param files A lista de arquivos.
	 */
	public static void update(List<String> files)
	{
		Properties settings = App.getInstance().getSettings();
		int index = 1;
		
		clear();
		
		for (String file : files)
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append(BASE_KEY).append(index++);
			settings.setProperty(sb.toString(), file);
		}
	}

	
	/**
	 * Limpa os arquivos recentes.
	 */
	public static void clear()
	{
		Properties settings = App.getInstance().getSettings();
		
		settings.keySet()
			.stream()
			.filter(key -> ((String) key).matches("^" + BASE_KEY + "\\d+"))
			.toList()
			.forEach(key -> settings.remove(key));
	}
}
