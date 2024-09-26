package com.drxgb.dialogtranslator.service.io;

import java.io.IOException;

/**
 * Responsável por tratar errors de campos inválidos
 * na aplicação.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class InvalidFieldException extends IOException
{
	/*
	 * ===========================================================
	 * 			*** CONSTANTES ***
	 * ===========================================================
	 */

	private static final long serialVersionUID = 1L;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	/**
	 * Cria a exceção com uma mensagem.
	 * 
	 * @param message A mensagem de erro.
	 */
	public InvalidFieldException(String message)
	{
		super(message);
	}
}
