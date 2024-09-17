package com.drxgb.dialogtranslator.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.component.LanguagesPane;
import com.drxgb.dialogtranslator.component.PhrasesPane;
import com.drxgb.dialogtranslator.service.Container;
import com.drxgb.dialogtranslator.service.StyleManager;
import com.drxgb.dialogtranslator.util.DialogStyleDecorator;
import com.drxgb.dialogtranslator.util.StyleDecorator;
import com.drxgb.util.PropertiesManager;
import com.drxgb.util.ValueHandler;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controlador da janela principal da aplicação.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class MainController implements Initializable
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	@FXML public Parent root;
	
	// Menu
	@FXML public Menu mnuStyle;
	@FXML public MenuItem mnitSave;
	@FXML public RadioMenuItem mnitPhrases;
	@FXML public RadioMenuItem mnitLanguages;
	
	// Base
	@FXML public AnchorPane panMain;
	@FXML public ToggleGroup viewMode;
	@FXML public ToggleButton btnPhrases;
	@FXML public ToggleButton btnLanguages;
	
	// Telas internas
	private ObservableList<Node> viewModes;
	
	
	/*
	 * ===========================================================
	 * 			*** ASSOCIAÇÕES ***
	 * ===========================================================
	 */
	
	private final App app = App.getInstance();

	
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
		try
		{
			initializeViewModes();		
			initializeStyles();
			initializeViewModeMenuItems();
			
			App.getInstance().getFileManager().setSaveMenuItem(mnitSave);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/*
	 * ===========================================================
	 * 			*** AÇÕES DO CONTROLADOR ***
	 * ===========================================================
	 */
	
	/**
	 * Ação ao fechar a janela.
	 * 
	 * @param ev Evento da janela disparado.
	 */
	public void onCloseAction(WindowEvent ev)
	{
		final App app = App.getInstance();		
		Properties settings = app.getSettings();
		
		if (app.getFileManager().hasUnsavedChanges())
		{			
			ButtonType confirmation = saveConfirmationAlert().get();
			
			if (confirmation == ButtonType.CANCEL)
			{
				ev.consume();
				return;
			}
		}

		PropertiesManager.save(new File(App.SETTINGS_FILENAME), settings);
	}
	
	
	/**
	 * Ação ao clicar no item de menu "New".
	 */
	@FXML
	public void onMnitNewAction()
	{
		final App app = App.getInstance();		
		
		if (app.getFileManager().hasUnsavedChanges())
		{			
			ButtonType confirmation = saveConfirmationAlert().get();
			
			if (confirmation == ButtonType.CANCEL)
			{
				return;
			}
		}
		
		// TODO Novo arquivo.
	}
	
	
	/**
	 * Ação ao clicar no item de menu "Open".
	 */
	@FXML
	public void onMnitOpenAction()
	{		
		if (app.getFileManager().hasUnsavedChanges())
		{			
			ButtonType confirmation = saveConfirmationAlert().get();
			
			if (confirmation == ButtonType.CANCEL)
			{
				return;
			}
		}

		// TODO Abrir arquivo.
	}
	
	
	/**
	 * Ação ao clicar no item de menu "Save".
	 */
	@FXML
	public void onMnitSaveAction()
	{		
		if (app.getTitleManager().isUntitled())
		{
			onMnitSaveAsAction();
			return;
		}
		
		// TODO Salvar arquivo.
	}
	
	
	/**
	 * Ação ao clicar no item de menu "Save As...".
	 */
	@FXML
	public void onMnitSaveAsAction()
	{
		// TODO Salvar arquivo como...
	}
	
	
	/**
	 * Ação ao clicar no item de menu "Close".
	 */
	@FXML
	public void onMnitCloseAction(ActionEvent ev)
	{
		Stage stage = App.getInstance().getStage();
		WindowEvent wndEv = new WindowEvent(stage, ev.getEventType());
		
		stage.getOnCloseRequest().handle(wndEv);
		
		if (! wndEv.isConsumed())
		{
			stage.close();
		}
	}

	
	/**
	 * Ação ao clicar no item de menu "Phrases".
	 */
	@FXML
	public void onMnitPhrasesAction()
	{
		btnPhrases.fire();
	}
	
	
	/**
	 * Ação ao clicar no item de menu "Languages".
	 */
	@FXML
	public void onMnitLanguagesAction()
	{
		btnLanguages.fire();
	}
	
	
	/**
	 * Ação ao clicar no item do menu "About".
	 * 
	 * @throws IOException Quando o arquivo da tela não foi encontrado.
	 */
	@FXML
	public void onMnitAboutAction() throws IOException
	{		
		Parent aboutRoot = (Parent) app.getViewLoader().load("AboutView");
		Scene aboutScene = new Scene(aboutRoot);
		Stage aboutStage = new Stage();
		Stage mainStage = app.getStage();
		StyleDecorator<Parent> decorator = new StyleDecorator<>(aboutRoot);
		StringBuilder sb = new StringBuilder();
		
		sb.append("About ").append(App.NAME);
		
		aboutStage.setScene(aboutScene);
		decorator.applyIcons(mainStage.getIcons());
		decorator.applyStyleSheets(root.getStylesheets());
		
		aboutStage.setTitle(sb.toString());
		aboutStage.setResizable(false);
		aboutStage.initModality(Modality.APPLICATION_MODAL);
		aboutStage.initOwner(mainStage);
		aboutStage.showAndWait();
	}
	
	
	/**
	 * Ação ao clicar no botão "Phrases".
	 */
	@FXML
	public void onBtnPhrasesAction()
	{
		mnitPhrases.setSelected(true);
		selectViewMode(0);
	}
	
	
	/**
	 * Ação ao clicar no botão "Languages".
	 */
	@FXML
	public void onBtnLanguagesAction()
	{		
		mnitLanguages.setSelected(true);
		selectViewMode(1);
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Inicializa os modos de visualização.
	 * 
	 * @throws IOException Quando o arquivo da tela não é encontrado.
	 */
	private void initializeViewModes() throws IOException
	{
		Container container = app.getContainer();
		
		viewModes = panMain.getChildren();

		viewModes.add(new PhrasesPane());
		viewModes.add(new LanguagesPane(container.getLanguages()));

		viewModes.forEach(v ->
		{
			AnchorPane.setTopAnchor(v, 0.0);
			AnchorPane.setLeftAnchor(v, 0.0);
			AnchorPane.setBottomAnchor(v, 0.0);
			AnchorPane.setRightAnchor(v, 0.0);
		});

		selectViewMode(0);
	}
	
	
	/**
	 * Inicializa os estilos.
	 */
	private void initializeStyles()
	{
		final App app =  App.getInstance();
		final StyleManager styleManager = app.getStyleManager();
		final String STYLE_KEY = "style";
		
		List<String> availableStyles = styleManager.getAvailableStyles();
		Properties settings = app.getSettings();
		ToggleGroup group = new ToggleGroup();
		
		String style = settings.containsKey(STYLE_KEY)
				? settings.getProperty(STYLE_KEY)
				: availableStyles.getFirst();
		
		availableStyles.forEach(availableStyle ->
		{
			RadioMenuItem item = new RadioMenuItem(availableStyle);
			StringBuilder sb = new StringBuilder();
			Image icon;
			
			item.setOnAction(ev ->
			{
				styleManager.setStyle(availableStyle);
				settings.setProperty(STYLE_KEY, availableStyle);
			});
			
			sb.append("icon/style/")
				.append(availableStyle.toLowerCase())
				.append(".png");

			icon = new Image(App.class.getResourceAsStream(sb.toString()));
			
			item.setGraphic(new ImageView(icon));
			item.setToggleGroup(group);
			item.setSelected(availableStyle.equals(style));
			mnuStyle.getItems().add(item);
		});
		
		styleManager.observeStyleList(root.getStylesheets());
		styleManager.setStyle(style);
	}
	
	
	/**
	 * Inicializa os botões alternados dos modos de visualização.
	 */
	private void initializeViewModeMenuItems()
	{
		viewMode.selectedToggleProperty().addListener((obs, oldVal, newVal) ->
		{
			if (newVal == null)
			{
				oldVal.setSelected(true);
			}
		});
	}
	
	
	/**
	 * Alterna o modo de visualização da janela.
	 * 
	 * @param index O índice da visualização.
	 */
	private void selectViewMode(int index)
	{
		if (viewModes.size() > 0)
		{
			index = ValueHandler.clamp(index, 0, viewModes.size());
			viewModes.forEach(v -> v.setVisible(false));
			viewModes.get(index).setVisible(true);
		}
	}
	
	
	/**
	 * Cria um alerta de confirmação de salvamento do arquivo e entrega
	 * o resultado da decisão tomada pelo usuário.
	 * 
	 * @return O resultado da decisão tomada pelo usuário.
	 */
	private Optional<ButtonType> saveConfirmationAlert()
	{		
		Alert alert = new Alert(AlertType.WARNING, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		DialogStyleDecorator decorator = new DialogStyleDecorator(alert.getDialogPane());
		StringBuilder sb = new StringBuilder();
		Optional<ButtonType> option;
		
		sb.append("Save changes to \"")
			.append(app.getTitleManager().getTitle())
			.append("\"?");
		
		alert.setTitle("Save file");
		alert.setHeaderText(null);
		alert.setContentText(sb.toString());
		alert.initOwner(app.getStage());
		alert.initModality(Modality.APPLICATION_MODAL);
		
		decorator.applyStyleSheets(app.getStage().getScene().getRoot().getStylesheets());
		decorator.applyButtonStyleClass("btn-primary");
		
		option = alert.showAndWait();		
		
		if (option.get() == ButtonType.YES)
		{
			onMnitSaveAction();
		}
		
		return option;
	}
}
