package com.drxgb.dialogtranslator.scene.control.cell;

import com.drxgb.dialogtranslator.contract.Nameable;

import javafx.scene.control.ListCell;

/**
 * Representa células que contém o nome do modelo.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class SimpleNameCell<T extends Nameable> extends ListCell<T>
{	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PROTEGIDOS ***
	 * ===========================================================
	 */

	/**
	 * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
	 */
	@Override
	protected void updateItem(T item, boolean empty)
	{
		super.updateItem(item, empty);
		setText(empty || item == null ? null : item.getName());
	}
}
