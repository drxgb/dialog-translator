package com.drxgb.dialogtranslator.scene.control.cell;

import java.util.Optional;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.model.Phrase;
import com.drxgb.dialogtranslator.scene.control.RemoveCellButton;
import com.drxgb.dialogtranslator.util.Alerts;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

/**
 * Representa a célula do item da lista de frases.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class PhraseCell extends DraggableListCell<Phrase>
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
	protected void updateItem(Phrase phrase, boolean empty)
	{
		super.updateItem(phrase, empty);
		
		if (empty || phrase == null)
		{
			setText(null);
			setGraphic(null);
		}
		else
		{
			setText(phrase.getKey());
			setGraphic(makeRemoveButton(phrase));
		}
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */	
	
	/**
	 * Cria o botão de remover a frase.
	 * 
	 * @param phrase A frase a ser removida.
	 * @return O botão.
	 */
	private Button makeRemoveButton(Phrase phrase)
	{
		final ListView<Phrase> list = getListView();
		Button btnRemove = new RemoveCellButton();

		btnRemove.setOnAction(ev ->
		{
			Optional<ButtonType> option = Alerts.deletionAlertResult("phrase", phrase.getKey());
			
			if (option.get() == ButtonType.YES)
			{
				list.getItems().remove(phrase);
				App.getInstance().getFileChangeObserver().update(true);
			}
		});
		
		return btnRemove;
	}
}
