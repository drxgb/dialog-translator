package com.drxgb.dialogtranslator.component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.util.FXRootInitializer;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	/**
	 * Cria o componente da lista de idiomas.
	 * 
	 * @throws IOException Quando o arquivo do componente não é encontrado.
	 */
	public LanguagesPane() throws IOException
	{
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
		// TODO Inicialização da aba do grupo de frases
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
		LanguageForm form = new LanguageForm(new Language());
		
		openFormView(form, false);
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
	public void setLanguages(List<Language> languages)
	{
		lstLanguages.setItems(FXCollections.observableList(languages));
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Abre a modal do formulário.
	 * 
	 * @param form A instância do formulário.
	 * @param isUpdate Sinal se a instância está sendo alterada.
	 */
	private void openFormView(LanguageForm form, boolean isUpdate)
	{
		Stage mainStage = App.getInstance().getStage();
		Stage formStage = new Stage();
		Scene formScene = new Scene(form);
		StringBuilder sb = new StringBuilder();
		Parent root = mainStage.getScene().getRoot();
		
		if (isUpdate)
		{
			sb.append("Edit language")
				.append(" - ")
				.append(form.getLanguage().getName());
		}
		else
		{
			sb.append("New language");
		}
		
		form.getStylesheets().addAll(root.getStylesheets());
		formStage.setTitle(sb.toString());
		formStage.setScene(formScene);
		formStage.getIcons().clear();
		formStage.getIcons().addAll(mainStage.getIcons());
		formStage.initOwner(this.getScene().getWindow());
		formStage.initModality(Modality.APPLICATION_MODAL);
		formStage.setResizable(false);
		formStage.showAndWait();
	}
}
