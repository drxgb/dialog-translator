package com.drxgb.dialogtranslator.scene.control;

import java.io.IOException;
import java.util.Optional;

import com.drxgb.dialogtranslator.component.LanguageForm;
import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.util.Alerts;
import com.drxgb.dialogtranslator.util.LanguageForms;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Representa a célula do item da lista de idiomas.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class LanguageCell extends ListCell<Language>
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
	protected void updateItem(Language language, boolean empty)
	{
		StringBuilder style = new StringBuilder();
		
		super.updateItem(language, empty);
		
		if (empty || language == null)
		{
			setText(null);
			setGraphic(null);
		}
		else
		{
			style.append("-fx-font-weight: ")
				.append(language.isMaster() ? "bold" : "normal");
			
			setStyle(style.toString());
			setText(language.getName());
			setGraphic(makeRemoveButton(language));
			setOnMouseClicked(ev ->
			{
				try
				{
					Stage formStage;
					Parent root = getScene().getRoot();
					LanguageForm form = new LanguageForm(language);
					StringBuilder sb = new StringBuilder();
					
					sb.append("Edit language - ").append(language.getName());
					
					formStage = LanguageForms.makeEditorForm(form, root);
					formStage.setTitle(sb.toString());
					formStage.showAndWait();
					
					if (form.isSaved())
					{
						LanguageForms.updateMasterLanguages(getListView(), language);
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			});
		}
	}
	
	
	/**
	 * Cria o botão de remover o idioma.
	 * 
	 * @param language O idioma a ser removido.
	 * @return O botão.
	 */
	protected Button makeRemoveButton(Language language)
	{
		final ListView<Language> list = getListView();
		Button btnRemove = new Button("X");
		
		btnRemove.getStyleClass().add("btn-cell-remove");
		btnRemove.setOnAction(ev ->
		{
			Optional<ButtonType> option = Alerts.deletionAlertResult("language", language.getName());
			
			if (option.get() == ButtonType.YES)
			{
				list.getItems().remove(language);
				setOnMouseClicked(null);
			}
		});
		
		return btnRemove;
	}
	
	
	/**
	 * Cria a fonte para o idioma principal.
	 * 
	 * @return A fonte.
	 */
	protected Font makeMasterFont()
	{
		String family = getFont().getFamily();
		double size = getFont().getSize();
		
		return Font.font(family, FontWeight.BOLD, size);
	}
}
