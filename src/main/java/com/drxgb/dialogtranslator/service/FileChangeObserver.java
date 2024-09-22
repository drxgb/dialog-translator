package com.drxgb.dialogtranslator.service;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Responsável por notificar eventos quando
 * o arquivo é modificado.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class FileChangeObserver implements Observer<Boolean>
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	private List<Consumer<Boolean>> onChangeListeners;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	/**
	 * Cria o notificador.
	 */
	public FileChangeObserver()
	{
		onChangeListeners = new LinkedList<>();
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS IMPLEMENTADOS ***
	 * ===========================================================
	 */	
	
	/**
	 * Sinaliza a alteração para todos os eventos.
	 * 
	 * @param changed Sinal de alteração.
	 */
	public void update(Boolean changed)
	{
		onChangeListeners.forEach(callback -> callback.accept(changed));
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Adiciona um evento que deve ser disparado quando
	 * ocorrer alguma alteração.
	 * 
	 * @param onChange Callback do evento.
	 */
	public void subscribe(Consumer<Boolean> onChange)
	{
		onChangeListeners.add(onChange);
	}
	
	
	/**
	 * Remove o evento de disparo.
	 * 
	 * @param onChange Callback a ser removido.
	 */
	public void unsubscribe(Consumer<Boolean> onChange)
	{
		onChangeListeners.remove(onChange);
	}
}
