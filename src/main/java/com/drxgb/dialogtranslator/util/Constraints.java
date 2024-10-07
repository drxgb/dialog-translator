package com.drxgb.dialogtranslator.util;

import com.drxgb.dialogtranslator.App;

import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;

/**
 * Responsável por aplicar regras aos componentes
 * de interface gráfica.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public abstract class Constraints
{
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ESTÁTICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Filtra somente os alfunuméricos, hífens ou sublinhas.
	 * 
	 * @param txt O campo de texto.
	 */
	public static void onlyAlphanumericHiphenAndUnderscore(TextInputControl txt)
	{
		final String pattern = "[^A-Za-z0-9\\-_]";
		Tooltip tooltip = txt.getTooltip();
		
		if (tooltip == null)
		{
			tooltip = new Tooltip("Only A-Z, a-z, 0-9, hiphens and undersocre are accepted.");
			txt.setTooltip(tooltip);
		}
		
		txt.textProperty().addListener((obs, oldValue, newValue) ->
		{			
			if (newValue != null && newValue.matches(".*" + pattern + "+.*"))
			{
				txt.setText(newValue.replaceAll(pattern, ""));
				txt.getTooltip().show(App.getInstance().getStage());
			}
			else
			{
				txt.getTooltip().hide();
			}
		});
		txt.focusedProperty().addListener(ev -> txt.getTooltip().hide());
	}
}
