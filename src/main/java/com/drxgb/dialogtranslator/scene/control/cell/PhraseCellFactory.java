package com.drxgb.dialogtranslator.scene.control.cell;

import com.drxgb.dialogtranslator.model.Phrase;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Responsável por gerar as células do item da lista de frases.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class PhraseCellFactory implements Callback<ListView<Phrase>, ListCell<Phrase>>
{
	/*
	 * ===========================================================
	 * 			*** MÉTODOS IMPLEMENTADOS ***
	 * ===========================================================
	 */

	/**
	 * @see javafx.util.Callback#call(java.lang.Object)
	 */
	@Override
	public ListCell<Phrase> call(ListView<Phrase> param)
	{
		return new PhraseCell();
	}

}
