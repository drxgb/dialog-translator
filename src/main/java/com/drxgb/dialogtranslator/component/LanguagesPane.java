package com.drxgb.dialogtranslator.component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.scene.control.cell.LanguageCell;
import com.drxgb.dialogtranslator.util.FXRootInitializer;
import com.drxgb.dialogtranslator.util.LanguageForms;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controlador da tela de idiomas.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class LanguagesPane extends VBox implements Initializable
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	@FXML public ListView<Language> lstLanguages;
	
	private ObservableList<Language> languages;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	/**
	 * Cria o componente da lista de idiomas.
	 * 
	 * @param languages A lista de idiomas.
	 * @throws IOException Quando o arquivo do componente não é encontrado.
	 */
	public LanguagesPane(ObservableList<Language> languages) throws IOException
	{
		this.languages = languages;
		FXRootInitializer.init(this, "language/LanguagesView");
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS IMPLEMENTADOS ***
	 * ===========================================================
	 */
	
	/**
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		lstLanguages.setCellFactory(p -> new LanguageCell());
		lstLanguages.setItems(languages);
	}
	
	
	/*
	 * ===========================================================
	 * 			*** AÇÕES DO CONTROLADOR ***
	 * ===========================================================
	 */
	
	/**
	 * Ação ao clicar no botão "New Language".
	 * 
	 * @throws IOException Quando o arquivo do componente não é encontrado.
	 */
	@FXML
	public void onBtnNewLanguageAction() throws IOException
	{
		Stage formStage;
		LanguageForm form;
		Language language = new Language();
		boolean forceMaster = lstLanguages.getItems().isEmpty();		
		
		form = new LanguageForm(language, forceMaster);
		formStage = LanguageForms.makeEditorForm(form, this);
		formStage.setTitle("New language");
		formStage.showAndWait();
		
		if (form.isSaved())
		{
			lstLanguages.getItems().add(language);
			LanguageForms.updateMasterLanguages(lstLanguages, language);
		}
	}
	
	
	/*
	 * ===========================================================
	 * 			*** SETTERS ***
	 * ===========================================================
	 */
	
	/**
	 * Modifica a lista de idiomas.
	 * 
	 * @param languages A nova lista de idiomas.
	 */
	public void setLanguages(ObservableList<Language> languages)
	{
		lstLanguages.setItems(languages);
	}
}
