package com.drxgb.dialogtranslator.util;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.component.LanguageForm;
import com.drxgb.dialogtranslator.model.Language;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Utilitário para criar forumlários de criação ou edição do idioma.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public abstract class LanguageForms
{
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ABSTRATOS ***
	 * ===========================================================
	 */
	
	/**
	 * Cria a tela do formulário do idioma.
	 * 
	 * @param form A estrutura da tela do formulário.
	 * @param parent Conteiner pai da tela a ser criada.
	 * @return A tela do formulário.
	 */
	public static Stage makeEditorForm(LanguageForm form, Parent parent)
	{
		final App app = App.getInstance();
		
		Stage mainStage = app.getStage();
		Stage formStage = new Stage();
		Scene formScene = new Scene(form);
		StyleDecorator<Parent> decorator = new StyleDecorator<>(form);
		
		formStage.setScene(formScene);
		decorator.applyIcons(mainStage.getIcons());
		decorator.applyStyleSheets(mainStage.getScene().getRoot().getStylesheets());
		
		formStage.initOwner(parent.getScene().getWindow());
		formStage.initModality(Modality.APPLICATION_MODAL);
		formStage.setResizable(false);
		
		return formStage;
	}
	
	
	/**
	 * Atualiza a lista de idiomas mantendo somente o último idioma
	 * editado para se tornar o idioma principal.
	 * 
	 * @param listView A lista de idiomas.
	 * @param language O último idioma editado.
	 */
	public static void updateMasterLanguages(ListView<Language> listView, Language language)
	{
		if (language.isMaster())
		{
			listView.getItems()
				.stream()
				.filter(lang -> ! lang.equals(language))
				.forEach(lang -> lang.setMaster(false));

			listView.refresh();
		}
	}
}
