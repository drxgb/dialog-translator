package com.drxgb.dialogtranslator.service;

/**
 * Contrato para notificar eventos.
 * 
 * @param <T> Tipo de dados a ser enviado na notificação.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public interface Observer<T>
{
	/**
	 * Realiza a notificação.
	 * 
	 * @param data Dados enviados na notificação.
	 */
	void update(T data);
}
