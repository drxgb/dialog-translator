package com.drxgb.dialogtranslator.model;

/**
 * Representa o conteúdo da frase em um determinado idioma.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public final class PhraseText
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	private String text;
	
	
	/*
	 * ===========================================================
	 * 			*** ASSOCIAÇÕES ***
	 * ===========================================================
	 */
	
	private Phrase phrase;
	private Language language;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	/**
	 * Cria o conteúdo da frase.
	 *  
	 * @param text Conteúdo.
	 * @param phrase Representação da frase.
	 * @param language Idioma.
	 */
	public PhraseText(String text, Phrase phrase, Language language)
	{
		this.text = text;
		this.phrase = phrase;
		this.language = language;
	}
	
	
	/**
	 * Cria um conteúdo vazio da frase.
	 * 
	 * @param phrase Representação da frase.
	 * @param language Idioma.
	 */
	public PhraseText(Phrase phrase, Language language)
	{
		this("", phrase, language);
	}


	/*
	 * ===========================================================
	 * 			*** GETTERS E SETTERS ***
	 * ===========================================================
	 */
	
	/**
	 * Recebe o conteúdo.
	 * 
	 * @return O conteúdo da frase.
	 */
	public String getText()
	{
		return text;
	}


	/**
	 * Recebe a representação da frase.
	 * 
	 * @return A representação da frase.
	 */
	public Phrase getPhrase()
	{
		return phrase;
	}


	/**
	 * Recebe o idioma da frase.
	 * 
	 * @return O idioma da frase.
	 */
	public Language getLanguage()
	{
		return language;
	}


	/**
	 * Modifica o conteúdo da frase.
	 * 
	 * @param text O novo conteúdo da frase.
	 */
	public void setText(String text)
	{
		this.text = text;
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
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((phrase == null) ? 0 : phrase.hashCode());
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
		PhraseText other = (PhraseText) obj;
		if (language == null)
		{
			if (other.language != null)
				return false;
		}
		else if (!language.equals(other.language))
			return false;
		if (phrase == null)
		{
			if (other.phrase != null)
				return false;
		}
		else if (!phrase.equals(other.phrase))
			return false;
		return true;
	}	
}
