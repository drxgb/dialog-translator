package com.drxgb.dialogtranslator.component;

import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.Phrase;
import com.drxgb.dialogtranslator.model.PhraseGroup;
import com.drxgb.dialogtranslator.scene.control.cell.PhraseCell;
import com.drxgb.dialogtranslator.scene.control.cell.SimpleNameCell;
import com.drxgb.dialogtranslator.service.Container;
import com.drxgb.dialogtranslator.util.Alerts;
import com.drxgb.dialogtranslator.util.FXRootInitializer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

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
	 * 			*** CONSTANTES ***
	 * ===========================================================
	 */
	
	/**
	 * A chave da propriedade da configuração que define o tamanho
	 * padrão para as divisórias do formulário da frase.
	 */
	private static final String DIVIDER_SIZE_KEY = "phraseGroupDividerSize";
	
	
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	@FXML public VBox vbPhrasesList;
	@FXML public VBox vbTranslation;
	@FXML public Button btnNewPhrase;
	@FXML public SplitPane spltPhrase;
	@FXML public TextField txtGroupName;
	@FXML public TextField txtFilterPhrases;
	@FXML public TextField txtPhraseKey;
	@FXML public TextArea txtMasterText;
	@FXML public TextArea txtTranslatedText;
	@FXML public Label lblPhraseError;
	@FXML public Button btnRemoveGroup;
	@FXML public ComboBox<Language> cboLanguages;
	@FXML public ListView<Phrase> lstPhrases;
	
	private Tab tab;
	private PhraseGroup phraseGroup;
	private ObservableList<Phrase> phrases;
	private ObservableList<Phrase> filteredPhrases;
	
	private boolean resizing;
	
	
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
	 * @param phrases A lista de frases.
	 */
	public PhraseGroupPane(PhraseGroup phraseGroup, Tab tab, ObservableList<Phrase> phrases)
	{
		this.phraseGroup = phraseGroup;
		this.tab = tab;
		this.phrases = phrases;
		this.resizing = false;

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
		setupLanguages();
		setupNewPhraseButton();
		setupGroupNameTextField();
		setupRemoveGroupButton();
		setupPhraseSplitPane();
		setupFilters();
		setupPhrasesListView();
		setupPhraseKeyInput();
		setupMasterTextInput();
		setupLanguageComboBox();
		setupTranslatedTextInput();
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Atualiza a caixa de seleção dos idiomas.
	 */
	public void updateLanguagesComboBox()
	{
		Container container = app.getContainer();
		
		cboLanguages.setItems(FXCollections.observableArrayList(container.getNonMasterLanguages()));
		txtTranslatedText.setDisable(cboLanguages.getItems().isEmpty());
		
		if (cboLanguages.getSelectionModel().isEmpty())
		{
			cboLanguages.getSelectionModel().selectFirst();
		}
	}
	
	
	/**
	 * Atualiza a tela do grupo de frases.
	 */
	public void updateView()
	{
		final boolean phrasesEmpty = phrases.isEmpty();
		
		vbTranslation.setDisable(phrasesEmpty);
		vbPhrasesList.setDisable(phrasesEmpty);
		updateLanguagesComboBox();
	}
	
	
	/**
	 * Atualiza o texto do idioma principal.
	 */
	public void updateMasterText()
	{
		Phrase phrase = lstPhrases.getSelectionModel().getSelectedItem();
		Language master = app.getContainer().getMasterLanguage();
		
		if (txtMasterText != null)
		{
			txtMasterText.setText(phrase.getTexts().get(master));
		}
	}
	
	
	/**
	 * Atualiza o tamanho da divisória do formulário da frase.
	 * 
	 * @param size O novo tamanho da divisória.
	 */
	public void updateDividerSize(Number size)
	{
		spltPhrase.getDividers().getFirst().setPosition((Double) size);
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
	public void onBtnNewPhraseAction()
	{
		Phrase phrase = new Phrase(makeFallbackName());
		
		phrases.add(phrase);
		lstPhrases.getSelectionModel().selectLast();
		txtFilterPhrases.clear();
		updateLanguagesComboBox();
	}
	
	
	/**
	 * Ação ao digitar nos campos de texto.
	 * 
	 * @param ev Evento disparado.
	 */
	@FXML
	public void onTextTypedAction(KeyEvent ev)
	{
		String ch = ev.getCharacter();
		byte charByte = ch != null ? ch.getBytes()[0] : 0x00;
		
		if (charByte != 0x1B)
		{
			app.getFileChangeObserver().update(true);
		}
	}

	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Incializa os idiomas.
	 */
	private void setupLanguages()
	{		
		// Atualiza a caixa de seleção com os idiomas exceto o principal.
		app.getContainer()
			.getLanguages()
			.addListener((ListChangeListener<Language>) ev -> updateLanguagesComboBox());
	}
	
	
	/**
	 * Inicializa o botão que cria novas frases.
	 */
	private void setupNewPhraseButton()
	{
		ObservableList<Language> languages = app.getContainer().getLanguages();
		
		btnNewPhrase.setDisable(languages.isEmpty());
		
		// Habilita/desabilita o botão de criar frases de acordo com a quantidade de idiomas.
		languages.addListener((ListChangeListener<Language>) ev ->
		{
			btnNewPhrase.setDisable(ev.getList().isEmpty());
		});
	}
	
	
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
			app.getFileChangeObserver().update(true);
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
	 * Inicializa a barra divisória entre a lista de frases
	 * e o campo de propriedades da frase.
	 */
	private void setupPhraseSplitPane()
	{		
		Properties settings = app.getSettings();
		Divider divider = spltPhrase.getDividers().getFirst();
		
		if (settings.containsKey(DIVIDER_SIZE_KEY))
		{
			divider.setPosition(Double.parseDouble(settings.getProperty(DIVIDER_SIZE_KEY)));
		}

		divider.positionProperty().addListener((obs, oldVal, newVal) ->
		{
			if (! resizing)
			{
				TabPane tabPane = (TabPane) getParent().getParent();
				
				tabPane.getTabs()
					.stream()
					.filter(t -> t != tab)
					.forEach(t -> ((PhraseGroupPane) t.getContent()).updateDividerSize(newVal));
				
				settings.setProperty(DIVIDER_SIZE_KEY, newVal.toString());
			}
		});
		
		app.getScene().addPreLayoutPulseListener(() ->
		{
			resizing = true;
			Platform.runLater(() -> resizing = false);
		});
	}
	
	
	/**
	 * Inicializa o filtro de pesquisa das frases.
	 */
	private void setupFilters()
	{		
		txtFilterPhrases.textProperty().addListener((obs, oldVal, newVal) ->
		{
			String regex = new StringBuilder()
					.append(".*(")
					.append(newVal)
					.append(").*")
					.toString();
			
			if (newVal.isEmpty())
			{
				lstPhrases.setItems(phrases);
			}
			else
			{
				filteredPhrases = FXCollections.observableArrayList(
						phrases.stream()
						.filter(phrase -> phrase.getKey().matches(regex))
						.toList()
						);
				lstPhrases.setItems(filteredPhrases);
			}
		});
	}
	
	
	/**
	 * Inicializa a lista das frases.
	 */
	private void setupPhrasesListView()
	{		
		lstPhrases.setItems(phrases);
		lstPhrases.setCellFactory(p -> new PhraseCell());

		lstPhrases.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
		{
			Language master = app.getContainer().getMasterLanguage();
			Language translated = cboLanguages.getValue();
			
			txtPhraseKey.setUserData(newVal);
			txtMasterText.setUserData(newVal);
			txtTranslatedText.setUserData(newVal);
			cboLanguages.setUserData(newVal);

			if (newVal != null)
			{
				String phraseKey = newVal.getKey();
				String masterText = newVal.getTexts().get(master);
				String translatedText = newVal.getTexts().get(translated);
				
				txtPhraseKey.setText(phraseKey != null ? phraseKey : "");
				txtMasterText.setText(masterText != null ? masterText : "");
				txtTranslatedText.setText(translatedText != null ? translatedText : "");
			}
		});
		
		lstPhrases.getSelectionModel().selectFirst();		
		phrases.addListener((ListChangeListener<Phrase>) ev -> {
			updateView();
			app.getFileChangeObserver().update(true);
		});
	}
	
	
	/**
	 * Inicializa o campo da chave da frase.
	 */
	private void setupPhraseKeyInput()
	{
		txtPhraseKey.textProperty().addListener((obs, oldVal, newVal) ->
		{
			Phrase phrase = (Phrase) txtPhraseKey.getUserData();
			
			if (phrase != null)
			{
				phrase.setKey(newVal);
				lstPhrases.refresh();
			}
			
			ensureNotEmptyPhraseKey(! (newVal == null || newVal.isBlank()));
		});
	}
	
	
	/**
	 * Inicializa o campo do texto do idioma principal.
	 */
	private void setupMasterTextInput()
	{
		txtMasterText.textProperty().addListener((obs, oldVal, newVal) ->
		{
			Phrase phrase = (Phrase) txtPhraseKey.getUserData();
			Language master = app.getContainer().getMasterLanguage();
			
			if (phrase != null)
			{
				phrase.getTexts().put(master, newVal);
			}
		});
	}
	
	
	/**
	 * Inicializa a caixa de seleção dos idiomas.
	 */
	private void setupLanguageComboBox()
	{
		Callback<ListView<Language>, ListCell<Language>> factory = p -> new SimpleNameCell<>();
		
		cboLanguages.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
		{
			Phrase phrase = (Phrase) cboLanguages.getUserData();
			
			if (phrase != null)
			{
				txtTranslatedText.setText(phrase.getTexts().get(newVal));
			}
		});
		
		cboLanguages.getSelectionModel().selectFirst();
		cboLanguages.setCellFactory(factory);
		cboLanguages.setButtonCell(factory.call(null));
	}
	
	
	/**
	 * Inicializa o campo de texto do idioma traduzido.
	 */
	private void setupTranslatedTextInput()
	{
		txtTranslatedText.textProperty().addListener((obs, oldVal, newVal) -> 
		{			
			Phrase phrase = (Phrase) txtPhraseKey.getUserData();
			Language language = cboLanguages.getValue();

			if (phrase != null)
			{
				phrase.getTexts().put(language, newVal);
			}
		});
	}
	
	
	/**
	 * Cria um nome genérico para a frase.
	 * 
	 * @return O nome da frase.
	 */
	private String makeFallbackName()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("phrase")
			.append('_')
			.append(phrases.size() + 1);
		
		return sb.toString();
	}
	
	
	/**
	 * Realiza a certificação do campo da chave da frase
	 * para válido.
	 * 
	 * @param valid Se o campo está válido.
	 */
	private void ensureNotEmptyPhraseKey(boolean valid)
	{
		if (valid)
		{
			txtPhraseKey.getStyleClass().remove("error");
		}
		else
		{
			txtPhraseKey.getStyleClass().add("error");
		}

		lblPhraseError.setText(valid ? "" : "This field cannot be empty");
		lblPhraseError.setTextFill(Color.RED);
		lblPhraseError.setVisible(! valid);
	}
}
