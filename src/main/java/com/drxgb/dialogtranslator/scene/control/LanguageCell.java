package com.drxgb.dialogtranslator.scene.control;

import java.io.IOException;
import java.util.Optional;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.component.LanguageForm;
import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.service.StyleManager;
import com.drxgb.dialogtranslator.util.DialogStyleDecorator;
import com.drxgb.dialogtranslator.util.LanguageForms;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
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
		final App app = App.getInstance();
		final ListView<Language> list = getListView();
		Button btnRemove = new Button("X");
		
		btnRemove.getStyleClass().add("btn-cell-remove");
		btnRemove.setOnAction(ev ->
		{
			Alert alert = new Alert(
					AlertType.WARNING,
					"This action is irreversible.",
					ButtonType.YES, ButtonType.NO
			);
			DialogPane dialog = alert.getDialogPane();
			DialogStyleDecorator decorator = new DialogStyleDecorator(dialog);
			StringBuilder title = new StringBuilder();
			StringBuilder header = new StringBuilder();
			Stage mainStage = app.getStage();
			StyleManager styleManager = app.getStyleManager();
			Optional<ButtonType> option;
			
			title.append("Remove language - ").append(language.getName());
			header.append("Are you sure you want remove \"")
				.append(language.getName())
				.append("\"?");
			
			alert.setTitle(title.toString());
			alert.setHeaderText(header.toString());
			
			decorator.applyIcons(mainStage.getIcons());
			decorator.applyStyleSheets(styleManager.getObservedStyleList());
			decorator.applyButtonStyleClass("btn-primary");
			
			option = alert.showAndWait();
			
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
