package com.drxgb.dialogtranslator.service;

import java.util.ArrayList;
import java.util.List;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.PhraseGroup;

/**
 * Representa o conjunto de dados que serão serializados.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class Container
{
	/*
	 * ===========================================================
	 * 			*** ASSOCIAÇÕES ***
	 * ===========================================================
	 */
	
	private List<PhraseGroup> groups;
	private List<Language> languages;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	/**
	 * Inicializa o container.
	 */
	public Container()
	{
		groups = new ArrayList<>();
		languages = new ArrayList<>();
	}


	/*
	 * ===========================================================
	 * 			*** GETTERS ***
	 * ===========================================================
	 */
	
	/**
	 * Recebe o conjunto dos grupos de frases.
	 * 
	 * @return O conjunto do grupo de frases.
	 */
	public List<PhraseGroup> getGroups()
	{
		return groups;
	}


	/**
	 * Recebe o conjunto dos idiomas.
	 * 
	 * @return O conjunto dos idiomas.
	 */
	public List<Language> getLanguages()
	{
		return languages;
	}	
}
