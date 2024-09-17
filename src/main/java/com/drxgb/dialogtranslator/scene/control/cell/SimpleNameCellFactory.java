package com.drxgb.dialogtranslator.scene.control.cell;

import com.drxgb.dialogtranslator.contract.Nameable;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Responsável por gerar células contendo somente o nome do modelo.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class SimpleNameCellFactory<T extends Nameable> implements Callback<ListView<T>, ListCell<T>>
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
	public ListCell<T> call(ListView<T> list)
	{
		return new ListCell<T>()
		{
			@Override
			protected void updateItem(T item, boolean empty)
			{
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.getName());
			}
		};
	}
}
