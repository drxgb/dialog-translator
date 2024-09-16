package com.drxgb.dialogtranslator.util;

import java.util.List;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Responsável por decorar os alertas com estilos.
 * 
 * @param <T> O tipo do nó a ser aplicado.
 */
public class StyleDecorator<T extends Parent>
{
	/*
	 * ===========================================================
	 * 			*** ASSOCIAÇÕES ***
	 * ===========================================================
	 */
	
	protected T parent;
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	/**
	 * Cria o decorador com o nó que deve ser aplicado.
	 * 
	 * @param parent O nó.
	 */
	public StyleDecorator(T parent)
	{
		this.parent = parent;
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Aplica os estilos ao nó.
	 * 
	 * @param styles A lista de estilos.
	 * @return A própria instância do decorador.
	 */
	public StyleDecorator<T> applyStyleSheets(List<String> styles)
	{
		parent.getStylesheets().clear();
		parent.getStylesheets().addAll(styles);
		
		return this;
	}
	
	
	/**
	 * Aplica os ícones à janela onde se encontra o nó.
	 * 
	 * @param icons Conjunto de ícones.
	 * @return A própria instância do decorador.
	 */
	public StyleDecorator<T> applyIcons(List<Image> icons)
	{
		Stage stage = (Stage) parent.getScene().getWindow();
		
		stage.getIcons().clear();
		stage.getIcons().addAll(icons);
		
		return this;
	}
}
