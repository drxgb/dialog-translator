package com.drxgb.dialogtranslator.scene.control;

import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Representa um botão de remoção de célula.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class RemoveCellButton extends Button
{
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	/**
	 * Cria um botão de remoção de célula com texto e conteúdo.
	 * 
	 * @param text O texto do botão.
	 * @param graphic Conteúdo interno do botão.
	 */
	public RemoveCellButton(String text, Node graphic)
	{
		super(text, graphic);
		getStyleClass().add("btn");
		getStyleClass().add("btn-cell-remove");
	}
	
	
	/**
	 * Cria um botão de remoção da célula com texto e sem conteúdo.
	 * 
	 * @param text O texto do botão.
	 */
	public RemoveCellButton(String text)
	{
		this(text, null);
	}
	
	
	/**
	 * Cria um botão de remoção de célula com "X" como texto
	 * padrão e sem conteúdo.
	 */
	public RemoveCellButton()
	{
		this("X", null);
	}
}
