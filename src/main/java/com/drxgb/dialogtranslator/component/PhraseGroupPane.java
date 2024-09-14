package com.drxgb.dialogtranslator.component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.Phrase;
import com.drxgb.dialogtranslator.model.PhraseGroup;
import com.drxgb.dialogtranslator.util.FXRootInitializer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Controlador da aba do grupo das frases.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class PhraseGroupPane extends ScrollPane implements Initializable
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	@FXML public TextField txtGroupName;
	@FXML public TextField txtFilterPhrases;
	@FXML public TextArea txtTranslatedText;
	@FXML public Button btnRemoveGroup;
	@FXML public ComboBox<Language> cboLanguages;
	@FXML public ListView<Phrase> lstPhrases;
	
	private PhraseGroup phraseGroup;
	
	/*
	 * ===========================================================
	 * 			*** AÇÕES DO CONTROLADOR ***
	 * ===========================================================
	 */
	
	/**
	 * Cria o componente da aba do grupo de frases.
	 * 
	 * @param phraseGroup O grupo de frases.
	 * 
	 * @throws IOException Quando o arquivo do modelo não foi encontrado.
	 */
	public PhraseGroupPane(PhraseGroup phraseGroup) throws IOException
	{
		this.phraseGroup = phraseGroup;
		FXRootInitializer.init(this, "phrase/PhraseGroupTabTemplate");
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
	 * Ação ao clicar no botão "New Phrase".
	 */
	@FXML
	public void onBtnPhraseAction()
	{
		// TODO Adicionar nova frase
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
}
