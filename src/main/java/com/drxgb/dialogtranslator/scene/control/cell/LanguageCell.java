package com.drxgb.dialogtranslator.scene.control.cell;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.component.LanguageForm;
import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.scene.control.RemoveCellButton;
import com.drxgb.dialogtranslator.util.Alerts;
import com.drxgb.dialogtranslator.util.LanguageForms;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

/**
 * Representa a célula do item da lista de idiomas.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class LanguageCell extends DraggableListCell<Language>
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	private Consumer<Language> onUpdate;
	
	
	/*
	 * ===========================================================
	 * 			*** SETTERS ***
	 * ===========================================================
	 */

	/**
	 * Define a callback que será executada quando o idioma é modificado.
	 * 
	 * @param onUpdate A callback.
	 */
	public void setOnUpdate(Consumer<Language> onUpdate)
	{
		this.onUpdate = onUpdate;
	}
	
	
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
		super.updateItem(language, empty);
		
		if (empty || language == null)
		{
			setText(null);
			setGraphic(null);
			setOnMouseClicked(null);
		}
		else
		{
			StringBuilder style = new StringBuilder();
			
			style.append("-fx-font-weight: ")
				.append(language.isMaster() ? "bold" : "normal");
			
			setStyle(style.toString());
			setText(language.getName());
			setGraphic(makeRemoveButton(language));
			setOnMouseClicked(ev ->
			{
				try
				{
					if (ev.getButton().equals(MouseButton.PRIMARY) && ev.getClickCount() == 2)
					{
						final boolean FORCE_MASTER = getListView().getItems().size() == 1;
						
						Stage formStage;
						Parent root = getScene().getRoot();
						LanguageForm form = new LanguageForm(language, FORCE_MASTER);
						StringBuilder sb = new StringBuilder();

						sb.append("Edit language - ").append(language.getName());
						
						formStage = LanguageForms.makeEditorForm(form, root);
						formStage.setTitle(sb.toString());
						formStage.showAndWait();

						if (form.isSaved())
						{
							LanguageForms.updateMasterLanguages(getListView(), language);
							
							if (onUpdate != null)
							{
								onUpdate.accept(language);
							}
						}						
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			});
		}
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */	
	
	/**
	 * Cria o botão de remover o idioma.
	 * 
	 * @param language O idioma a ser removido.
	 * @return O botão.
	 */
	private Button makeRemoveButton(Language language)
	{
		final ListView<Language> list = getListView();
		Button btnRemove = new RemoveCellButton();

		btnRemove.setOnAction(ev ->
		{
			Optional<ButtonType> option = Alerts.deletionAlertResult("language", language.getName());
			
			if (option.get() == ButtonType.YES)
			{
				list.getItems().remove(language);
				App.getInstance().getFileChangeObserver().update(true);
				LanguageForms.checkIfHasMasterLanguages(list);
			}
		});
		
		return btnRemove;
	}
}
