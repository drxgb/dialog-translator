package com.drxgb.dialogtranslator.component;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.Phrase;
import com.drxgb.dialogtranslator.model.PhraseGroup;
import com.drxgb.dialogtranslator.scene.control.cell.SimpleNameCellFactory;
import com.drxgb.dialogtranslator.util.Alerts;
import com.drxgb.dialogtranslator.util.FXRootInitializer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
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
	private Tab tab;
	
	
	/*
	 * ===========================================================
	 * 			*** ASSOCIAÇÕES ***
	 * ===========================================================
	 */
	
	private final App app = App.getInstance();
	
	
	/*
	 * ===========================================================
	 * 			*** AÇÕES DO CONTROLADOR ***
	 * ===========================================================
	 */
	
	/**
	 * Cria o componente da aba do grupo de frases.
	 * 
	 * @param phraseGroup O grupo de frases.
	 * @param tab A aba que contém o grupo de frases.
	 * 
	 * @throws IOException Quando o arquivo do modelo não foi encontrado.
	 */
	public PhraseGroupPane(PhraseGroup phraseGroup, Tab tab) throws IOException
	{
		this.phraseGroup = phraseGroup;
		this.tab = tab;

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
		setupGroupNameTextField();
		setupRemoveGroupButton();
		setupLanguageComboBox();
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
	
	/**
	 * Inicializa o campo de texto do nome do grupo.
	 */
	private void setupGroupNameTextField()
	{
		txtGroupName.setText(phraseGroup.getName());
		txtGroupName.requestFocus();
		txtGroupName.textProperty().addListener((obs, oldVal, newVal) ->
		{
			tab.setText(newVal);
			phraseGroup.setName(newVal);
		});
	}
	
	
	/**
	 * Inicializa o botão de remover grupo de frases.
	 */
	private void setupRemoveGroupButton()
	{
		btnRemoveGroup.setOnAction(ev ->
		{
			Optional<ButtonType> option = Alerts.deletionAlertResult("phrase group", phraseGroup.getName());
			
			if (option.get() == ButtonType.YES)
			{
				tab.getTabPane().getTabs().remove(tab);
			}
		});
	}
	
	
	/**
	 * Inicializa a caixa de seleção dos idiomas.
	 */
	private void setupLanguageComboBox()
	{
		SimpleNameCellFactory<Language> factory = new SimpleNameCellFactory<>();
		
		cboLanguages.setItems(app.getContainer().getLanguages());
		cboLanguages.getSelectionModel().select(0);
		cboLanguages.setCellFactory(factory);
		cboLanguages.setButtonCell(factory.call(null));
	}
}
