package com.drxgb.dialogtranslator.service;

import java.io.InputStream;
import java.util.List;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.PhraseGroup;

/**
 * Responsável por carregar fluxo de dados.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public abstract class ReaderService
{
	/*
	 * ===========================================================
	 * 			*** ASSOCIAÇÕES ***
	 * ===========================================================
	 */
	
	private List<Language> languages;
	private List<PhraseGroup> groups;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	/**
	 * Cria o leitor.
	 * 
	 * @param languages Conjunto de idiomas.
	 * @param groups Conjunto de grupos de frases.
	 */
	public ReaderService(List<Language> languages, List<PhraseGroup> groups)
	{
		this.languages = languages;
		this.groups = groups;
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ABSTRATOS ***
	 * ===========================================================
	 */
	
	/**
	 * Realiza a leitura do fluxo de dados.
	 * 
	 * @param input O fluxo de dados a ser lido.
	 */
	public abstract void read(InputStream input);


	/*
	 * ===========================================================
	 * 			*** GETTERS ***
	 * ===========================================================
	 */
	
	/**
	 * Recebe o conjunto de idiomas.
	 * 
	 * @return O conjunto de idiomas.
	 */
	public List<Language> getLanguages()
	{
		return languages;
	}


	/**
	 * Recebe o conjunto dos grupos de frases.
	 * 
	 * @return O conjunto dos grupos de frases.
	 */
	public List<PhraseGroup> getGroups()
	{
		return groups;
	}
}
