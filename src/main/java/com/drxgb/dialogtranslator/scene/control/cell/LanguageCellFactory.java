package com.drxgb.dialogtranslator.scene.control.cell;

import com.drxgb.dialogtranslator.model.Language;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Responsável por gerar as células do item da lista de idiomas.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class LanguageCellFactory implements Callback<ListView<Language>, ListCell<Language>>
{
	/**
	 * @see javafx.util.Callback#call(java.lang.Object)
	 */
	@Override
	public ListCell<Language> call(ListView<Language> list)
	{
		return new LanguageCell();
	}
}
