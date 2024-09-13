package com.drxgb.dialogtranslator.model;

/**
 * Representa um idioma.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public final class Language
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	private String name;
	private Boolean master;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */	
	
	/**
	 * Cria um idioma contendo o nome e se a linguagem é a principal.
	 * 
	 * @param name O nome do idioma.
	 * @param master Se o idioma deve ser o princpal.
	 */
	public Language(String name, Boolean master)
	{
		this.name = name;
		this.master = master;
	}


	/**
	 * Cria um idioma padrão contendo somente o nome sem ser a principal.
	 * 
	 * @param name O nome do idioma.
	 */
	public Language(String name)
	{
		this(name, false);
	}

	
	/*
	 * ===========================================================
	 * 			*** GETTERS E SETTERS ***
	 * ===========================================================
	 */

	/**
	 * Recebe o nome.
	 * 
	 * @return O nome do idioma.
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * Verifica se o idioma é o principal.
	 * 
	 * @return <code>true</code> se este idioma é o principal.
	 */
	public Boolean isMaster()
	{
		return master;
	}


	/**
	 * Define o nome do idioma.
	 * 
	 * @param name O novo nome.
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	/**
	 * Sinaliza se este idioma é o principal.
	 * 
	 * @param master O sinal de que o idioma é o principal.
	 */
	public void setMaster(Boolean master)
	{
		this.master = master;
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
		Language other = (Language) obj;
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
