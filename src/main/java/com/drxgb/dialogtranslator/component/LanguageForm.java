package com.drxgb.dialogtranslator.component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.util.FXRootInitializer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Componente do formulário do idioma.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class LanguageForm extends VBox implements Initializable
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	@FXML public TextField txtName;
	@FXML public CheckBox chkMaster;
	
	private Language language;
	private boolean saved;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	/**
	 * Cria o componente do formulário do idioma definindo,
	 * também, se o mesmo deve ser o principal.
	 * 
	 * @param language O idioma a ser alterado.
	 * @param forceMaster Força o idioma criado a ser o principal.
	 * 
	 * @throws IOException Se o arquivo do modelo não foi encontrado.
	 */
	public LanguageForm(Language language, boolean forceMaster) throws IOException
	{
		this.language = language;
		this.saved = false;
		
		FXRootInitializer.init(this, "language/LanguageFormTemplate");
		
		if (forceMaster)
		{
			forceMasterLanguage();
		}
	}
	
	
	/**
	 * Cria o componente do formulário mas o mesmo não será
	 * inicializado a ser o principal.
	 * 
	 * @param language O idioma a ser alterado.
	 * 
	 * @throws IOException Se o arquivo do modelo não foi encontrado.
	 */
	public LanguageForm(Language language) throws IOException
	{
		this(language, false);
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
		txtName.setText(language.getName());
		chkMaster.setSelected(language.isMaster());
	}

	 
	/*
	 * ===========================================================
	 * 			*** AÇÕES DO CONTROLADOR ***
	 * ===========================================================
	 */

	/**
	 * Ação ao clicar no botão "Cancel".
	 */
	@FXML
	public void onBtnCancelAction()
	{
		saved = false;
		closeWindow();
	}
	
	
	/**
	 * Ação ao clicar no botão "Save".
	 */
	@FXML
	public void onBtnSaveAction()
	{
		language.setName(txtName.getText());
		language.setMaster(chkMaster.isSelected());
		saved = true;
		closeWindow();
	}
	
	
	/*
	 * ===========================================================
	 * 			*** GETTERS ***
	 * ===========================================================
	 */
	
	/**
	 * Recebe o idioma que está sendo alterado no formulário.
	 * 
	 * @return A instância do idioma.
	 */
	public Language getLanguage()
	{
		return language;
	}
	
	
	/**
	 * Verifica se o formulário foi salvo.
	 * 
	 * @return Se o formulário foi salvo.
	 */
	public boolean isSaved()
	{
		return saved;
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Bloqueia o campo que define o idioma principal pois o idioma
	 * deve ser, obrigatoriamente, o principal.
	 */
	private void forceMasterLanguage()
	{
		chkMaster.setSelected(true);
		chkMaster.setDisable(true);
	}
	
	
	/**
	 * Fecha a janela.
	 */
	private void closeWindow()
	{
		((Stage) this.getScene().getWindow()).close();		
	}
}
