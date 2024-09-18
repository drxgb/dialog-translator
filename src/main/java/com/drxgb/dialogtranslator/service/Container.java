package com.drxgb.dialogtranslator.service;

import java.util.Optional;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.PhraseGroup;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	
	private ObservableList<PhraseGroup> groups;
	private ObservableList<Language> languages;
	
	
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
		groups = FXCollections.observableArrayList();
		languages = FXCollections.observableArrayList();
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Recebe o idioma principal.
	 * 
	 * @return O idioma principal.
	 */
	public Language getMasterLanguage()
	{
		Optional <Language> result = getLanguages()
				.stream()
				.filter(lang -> lang.isMaster())
				.findFirst();
		
		return result.isPresent() ? result.get() : null;
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
	public ObservableList<PhraseGroup> getGroups()
	{
		return groups;
	}


	/**
	 * Recebe o conjunto dos idiomas.
	 * 
	 * @return O conjunto dos idiomas.
	 */
	public ObservableList<Language> getLanguages()
	{
		return languages;
	}	
}
