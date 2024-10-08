package com.drxgb.dialogtranslator.model;

import java.io.Serializable;

import com.drxgb.dialogtranslator.contract.Nameable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Representa o grupo de frases.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public final class PhraseGroup implements Nameable, Serializable
{
	/*
	 * ===========================================================
	 * 			*** CONSTANTES ***
	 * ===========================================================
	 */
	
	private static final long serialVersionUID = 1L;

	
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	private String name;
	
	
	/*
	 * ===========================================================
	 * 			*** ASSOCIAÇÕES ***
	 * ===========================================================
	 */
	
	private ObservableList<Phrase> phrases;

	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	/**
	 * Cria um grupo de frases.
	 * 
	 * @param name O nome do grupo.
	 * @param phrases O conjunto de frases.
	 */
	public PhraseGroup(String name, ObservableList<Phrase> phrases)
	{
		this.name = name;
		this.phrases = phrases;
	}
	
	
	/**
	 * Cria um grupo de frases vazio com um nome apenas.
	 * 
	 * @param name O nome do grupo.
	 */
	public PhraseGroup(String name)
	{
		this(name, FXCollections.observableArrayList());
	}	


	/**
	 * Cria um grupo de frases vazio.
	 */
	public PhraseGroup()
	{
		this("");
	}
	
	
	/*
	 * ===========================================================
	 * 			*** GETTERS E SETTERS ***
	 * ===========================================================
	 */

	/**
	 * Recebe o nome do grupo.
	 * 
	 * @return O nome do grupo.
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * Recebe o conjunto de frases.
	 * 
	 * @return O conjunto de frases.
	 */
	public ObservableList<Phrase> getPhrases()
	{
		return phrases;
	}


	/**
	 * Modifica o nome do grupo.
	 * 
	 * @param name O novo nome.
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	/**
	 * Modifica o conjunto das frases.
	 * 
	 * @param phrases O novo conjunto das frases.
	 */
	public void setPhrases(ObservableList<Phrase> phrases)
	{
		this.phrases = phrases;
	}

	
	/*
	 * ===========================================================
	 * 			*** HASHCODE E EQUALS ***
	 * ===========================================================
	 */

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhraseGroup other = (PhraseGroup) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}
}
