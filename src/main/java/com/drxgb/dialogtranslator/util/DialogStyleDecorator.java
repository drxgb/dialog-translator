package com.drxgb.dialogtranslator.util;

import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;

/**
 * Responsável por decorar diálogos.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class DialogStyleDecorator extends StyleDecorator<DialogPane>
{
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	/**
	 * Cria o decorador de diálogos.
	 * 
	 * @param parent O nó que contém o conteúdo do diálogo.
	 */
	public DialogStyleDecorator(DialogPane parent)
	{
		super(parent);
	}

	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Aplica a classe de estilo aos botões.
	 * 
	 * @param classStyle A clas do estilo.
	 * @return A própria instância do decorador.
	 */
	public DialogStyleDecorator applyButtonStyleClass(String classStyle)
	{
		parent.getButtonTypes().forEach(buttonType ->
		{
			Button button = (Button) parent.lookupButton(buttonType);
			
			button.getStyleClass().add(classStyle);
		});
		
		return this;
	}
}
