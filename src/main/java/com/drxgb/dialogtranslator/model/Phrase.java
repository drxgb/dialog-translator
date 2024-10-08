package com.drxgb.dialogtranslator.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Representa a frase.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public final class Phrase implements Serializable
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
	
	private String key;
	private Map<Language, String> texts;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	/**
	 * Cria uma nova frase inserindo uma chave.
	 * 
	 * @param key A chave que representa a frase.
	 */
	public Phrase(String key)
	{
		this.key = key;
		this.texts = new HashMap<>();
	}

	
	/*
	 * ===========================================================
	 * 			*** GETTERS E SETTERS ***
	 * ===========================================================
	 */

	/**
	 * Recebe a chave da frase.
	 * 
	 * @return A chave da frase.
	 */
	public String getKey()
	{
		return key;
	}


	/**
	 * Modifica a chave.
	 * 
	 * @param key Novo nome da chave.
	 */
	public void setKey(String key)
	{
		this.key = key;
	}
	
	
	/**
	 * Recebe o conjunto de textos.
	 * 
	 * @return O conjunto de textos.
	 */
	public Map<Language, String> getTexts()
	{
		return texts;
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
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		Phrase other = (Phrase) obj;
		if (key == null)
		{
			if (other.key != null)
				return false;
		}
		else if (!key.equals(other.key))
			return false;
		return true;
	}
}
