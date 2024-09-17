package com.drxgb.dialogtranslator.contract;

/**
 * Contrato que define um nome para o objeto.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public interface Nameable
{
	/**
	 * Recebe o nome.
	 * 
	 * @return O nome.
	 */
	String getName();
	
	
	/**
	 * Define um nome.
	 * 
	 * @param name O novo nome.
	 */
	void setName(String name);
}
