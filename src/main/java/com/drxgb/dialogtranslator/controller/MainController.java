package com.drxgb.dialogtranslator.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.component.LanguagesPane;
import com.drxgb.dialogtranslator.component.PhrasesPane;
import com.drxgb.dialogtranslator.service.Container;
import com.drxgb.dialogtranslator.service.io.FileType;
import com.drxgb.dialogtranslator.service.io.ReaderService;
import com.drxgb.dialogtranslator.service.io.WriterService;
import com.drxgb.dialogtranslator.service.io.XldReaderService;
import com.drxgb.dialogtranslator.service.io.XldWriterService;
import com.drxgb.dialogtranslator.service.io.XmlReaderService;
import com.drxgb.dialogtranslator.service.io.XmlWriterService;
import com.drxgb.dialogtranslator.service.manager.FileManager;
import com.drxgb.dialogtranslator.service.manager.StyleManager;
import com.drxgb.dialogtranslator.util.Alerts;
import com.drxgb.dialogtranslator.util.DialogStyleDecorator;
import com.drxgb.dialogtranslator.util.RecentFiles;
import com.drxgb.dialogtranslator.util.StyleDecorator;
import com.drxgb.javafxutils.FileChooserFactory;
import com.drxgb.util.PropertiesManager;
import com.drxgb.util.Report;
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
import javafx.scene.control.Button;
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
 * @version 1.0.1
 */
public class MainController implements Initializable
{
	/*
	 * ===========================================================
	 * 			*** CONSTANTES ***
	 * ===========================================================
	 */
	
	/**
	 * Chave do último caminho aberto nas configurações.
	 */
	private static final String OPEN_PATH_KEY = "lastOpenedPath";
	
	/**
	 * Chave do último caminho salvo nas configurações.
	 */
	private static final String SAVE_PATH_KEY = "lastSavedPath";

	/**
	 * Chave do último caminho importado nas configurações.
	 */
	private static final String IMPORT_PATH_KEY = "lastImportedPath";
	
	/**
	 * Chave do último caminho exportado nas configurações.
	 */
	private static final String EXPORT_PATH_KEY = "lastExportedPath";
	
	
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	@FXML public Parent root;
	
	// Menu
	@FXML public Menu mnuRecentFiles;
	@FXML public Menu mnuStyle;
	@FXML public MenuItem mnitSave;
	@FXML public RadioMenuItem mnitPhrases;
	@FXML public RadioMenuItem mnitLanguages;
	
	
	// Base
	@FXML public AnchorPane panMain;
	@FXML public Button btnSave;
	@FXML public ToggleGroup viewMode;
	@FXML public ToggleButton btnPhrases;
	@FXML public ToggleButton btnLanguages;
	
	// Telas internas
	private ObservableList<Node> viewModes;
	
	// Arquivos recentes
	private List<String> recentFiles;
	
	// Dados auxiliares
	private boolean hasErrors;
	
	
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
			hasErrors = false;
			
			setupRecentFiles();
			setupViewModes();
			setupStyles();
			setupViewModeMenuItems();
			setupFileChangeObserver();
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
		
		hasErrors = false;
		
		if (saveConfirmed() == ButtonType.CANCEL || hasErrors)
		{
			ev.consume();
			return;
		}

		PropertiesManager.save(new File(App.SETTINGS_FILENAME), settings);
	}
	
	
	/**
	 * Ação ao clicar no item de menu "New".
	 */
	@FXML
	public void onMnitNewAction()
	{
		if (saveConfirmed() != ButtonType.CANCEL)
		{
			Container container = app.getContainer();
			
			container.getLanguages().clear();
			container.getGroups().clear();
			reloadMainView(null);
		}
	}
	
	
	/**
	 * Ação ao clicar no item de menu "Open".
	 */
	@FXML
	public void onMnitOpenAction()
	{
		if (saveConfirmed() != ButtonType.CANCEL)
		{
			Stage stage = app.getStage();
			Properties settings = app.getSettings();
			String path;
			
			File file = FileChooserFactory.openSingleFile(
					stage,
					"Open file",
					"Dialog files",
					settings.getProperty(OPEN_PATH_KEY),
					app.getSupportedFileExtensionsList()
			);
			
			if (file != null)
			{
				path = file.getAbsolutePath();
				settings.setProperty(OPEN_PATH_KEY, file.getParent());
				loadFile(path, makeReader(FileType.XLD));
				reloadMainView(path);
				addToRecentFilesList(path);
			}
		}
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
		
		saveFile(app.getTitleManager().getTitle(), makeWriter(FileType.XLD));
	}
	
	
	/**
	 * Ação ao clicar no item de menu "Save As...".
	 */
	@FXML
	public void onMnitSaveAsAction()
	{
		Stage stage = app.getStage();
		Properties settings = app.getSettings();
		String path;
		
		File file = FileChooserFactory.saveFile(
				stage,
				"Save file",
				"Dialog files",
				settings.getProperty(SAVE_PATH_KEY),
				app.getSupportedFileExtensionsList()
		);
		
		if (file != null)
		{
			path = file.getAbsolutePath();
			settings.setProperty(SAVE_PATH_KEY, file.getParent());
			saveFile(path, makeWriter(FileType.XLD));
			addToRecentFilesList(path);
			app.getTitleManager().setTitle(path);
		}
	}
	
	
	/**
	 * Ação ao clicar no item de menu "Import > XML..."
	 */
	@FXML
	public void onMnitImportXmlAction()
	{
		if (saveConfirmed() != ButtonType.CANCEL)
		{
			Stage stage = app.getStage();
			Properties settings = app.getSettings();
			List<String> extensions = new ArrayList<>();
			String path;
			File file;
			
			extensions.add("*.xml");
			file = FileChooserFactory.openSingleFile(
					stage,
					"Import file from XML",
					"XML files",
					settings.getProperty(IMPORT_PATH_KEY),
					extensions
			);
			
			if (file != null)
			{
				path = file.getAbsolutePath();
				settings.setProperty(IMPORT_PATH_KEY, file.getParent());
				loadFile(path, makeReader(FileType.XML));
				reloadMainView(null, true);
			}
		}
	}
	
	
	/**
	 * Ação ao clicar no item de menu "Import > JSON..."
	 */
	@FXML
	public void onMnitImportJsonAction()
	{
		// TODO: Importar de JSON.
	}
	
	
	/**
	 * Ação ao clicar no item de menu "Export > XML..."
	 */
	@FXML
	public void onMnitExportXmlAction()
	{
		Stage stage = app.getStage();
		Properties settings = app.getSettings();
		List<String> extensions = new ArrayList<>();
		String path;
		File file;
		
		extensions.add("*.xml");
		file = FileChooserFactory.saveFile(
				stage,
				"Export file to XML",
				"XML files",
				settings.getProperty(EXPORT_PATH_KEY),
				extensions
		);
		
		if (file != null)
		{
			path = file.getAbsolutePath();
			settings.setProperty(EXPORT_PATH_KEY, file.getParent());
			saveFile(path, makeWriter(FileType.XML));
			raiseSuccessAlert(path, FileAction.EXPORT);
		}
	}
	
	
	/**
	 * Ação ao clicar no item de menu "Export > JSON..."
	 */
	@FXML
	public void onMnitExportJsonAction()
	{
		// TODO: Exportar para JSON.
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
	 * Ação ao clicar no item de menu de um arquivo recente.
	 * 
	 * @param ev O evento disparado.
	 */
	private void onMnitLoadRecentFilesAction(String file)
	{
		if (saveConfirmed() != ButtonType.CANCEL)
		{			
			addToRecentFilesList(file);
			loadFile(file, makeReader(FileType.XLD));
		}		
	}
	
	/**
	 * Inicializa a lista de arquivos recentes.
	 */
	private void setupRecentFiles()
	{
		recentFiles = RecentFiles.read();
		updateRecentFilesItems();
	}

	
	/**
	 * Inicializa os modos de visualização.
	 * 
	 * @throws IOException Quando o arquivo da tela não é encontrado.
	 */
	private void setupViewModes() throws IOException
	{
		Container container = app.getContainer();
		PhrasesPane phrasesPane = new PhrasesPane(container.getGroups());
		LanguagesPane languagesPane = new LanguagesPane(container.getLanguages(), phrasesPane);
		
		viewModes = panMain.getChildren();

		viewModes.add(phrasesPane);
		viewModes.add(languagesPane);

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
	private void setupStyles()
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
	private void setupViewModeMenuItems()
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
	 * Adiciona as notificações de alteração do arquivo.
	 */
	private void setupFileChangeObserver()
	{
		app.getFileChangeObserver().subscribe(changed -> mnitSave.setDisable(! changed));
		app.getFileChangeObserver().subscribe(changed -> btnSave.setDisable(! changed));
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
			index = ValueHandler.clamp(index, 0, viewModes.size() - 1);
			viewModes.forEach(v -> v.setVisible(false));
			viewModes.get(index).setVisible(true);
		}
	}
	
	
	/**
	 * Certifica se há arquivos com modificações não salvas.
	 * 
	 * @return Sinal da modificação não salva.
	 */
	private boolean hasUnsavedChanges()
	{
		return app.getTitleManager().isUnsaved();
	}
	
	
	/**
	 * Recebe a opção da confirmação de salvamento.
	 * 
	 * @return 	A opção de salvamento. Se a confirmação não foi realizada,
	 * 			será entregue o valor <code>null</code>.
	 */
	private ButtonType saveConfirmed()
	{
		if (hasUnsavedChanges())
		{			
			return saveConfirmationAlert().get();
		}
		
		return null;
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
	
	
	/**
	 * Atualiza a lista do menu de arquivos recentes.
	 * 
	 * @param files A lista de arquivos recentes.
	 */
	private void updateRecentFilesItems()
	{
		ObservableList<MenuItem> items = mnuRecentFiles.getItems();
		int index = 1;
		
		items.clear();
		
		for (String file : recentFiles)
		{
			MenuItem item = new MenuItem();
			StringBuilder sb = new StringBuilder();

			sb.append(index++).append(": ").append(file);

			item.setText(sb.toString());
			item.setUserData(file);
			item.setOnAction(ev -> onMnitLoadRecentFilesAction(file));
			items.add(item);
		}
	}
	
	
	/**
	 * Adiciona o arquivo carregado à lista de arquivos recentes.
	 * 
	 * @param file O caminho do arquivo carregado.
	 */
	private void addToRecentFilesList(String file)
	{
		if (recentFiles.contains(file))
		{
			recentFiles.remove(file);
		}
		
		recentFiles.addFirst(file);
		RecentFiles.update(recentFiles);
		updateRecentFilesItems();
	}
	
	
	/**
	 * Recarrega a tela principal.
	 * 
	 * @param title O novo título da janela.
	 */
	private void reloadMainView(String title, boolean update)
	{
		PhrasesPane phrasesPane = (PhrasesPane) viewModes.getFirst();
		
		phrasesPane.populateTabs();
		app.getTitleManager().setTitle(title);
		app.getFileChangeObserver().update(update);
	}
	
	
	/**
	 * Recarrega a tela principal.
	 * 
	 * @param title O novo título da janela.
	 */
	private void reloadMainView(String title)
	{
		reloadMainView(title, false);
	}
	
	
	/**
	 * Carrega o arquivo solicitado.
	 * 
	 * @param filename O nome do arquivo.
	 * @param reader O serviço de leitura do arquivo.
	 */
	private void loadFile(String filename, ReaderService reader)
	{
		try
		{
			FileManager fileManager = app.getFileManager();

			fileManager.setReader(reader);
			fileManager.load(filename);
		}
		catch (Throwable t)
		{
			Report.writeErrorLog(t);
			Alerts.showError(t);
			hasErrors = true;
		}
	}
	
	
	/**
	 * Salva o arquivo solicitado.
	 * 
	 * @param filename O nome do arquivo.
	 * @param write O serviço de escrita do arquivo.
	 */
	private void saveFile(String filename, WriterService writer)
	{
		try
		{
			FileManager fileManager = app.getFileManager();

			fileManager.setWriter(writer);
			fileManager.save(filename);
			app.getFileChangeObserver().update(false);
		}
		catch (Throwable t)
		{
			Report.writeErrorLog(t);
			Alerts.showError(t);
			hasErrors = true;
		}
	}
	
	
	/**
	 * Cria o método de leitura de arquivo.
	 * 
	 * @param type O tipo de arquivo.
	 * @return O método de leitura.
	 */
	private ReaderService makeReader(FileType type)
	{
		Container c = app.getContainer();
		ReaderService reader;
		
		switch (type)
		{
		case XLD:
			reader = new XldReaderService(c.getLanguages(), c.getGroups());
			break;
			
		case XML:
			reader = new XmlReaderService(c.getLanguages(), c.getGroups());
			break;
			
		case JSON:
		default:
			reader = null;
		}
		
		return reader;
	}
	
	
	/**
	 * Cria o método de escrita de arquivo.
	 * 
	 * @param type O tipo de arquivo.
	 * @return O método de escrita.
	 */
	private WriterService makeWriter(FileType type)
	{
		Container c = app.getContainer();
		WriterService writer;
		
		switch (type)
		{
		case XLD:
			writer = new XldWriterService(c.getLanguages(), c.getGroups());
			break;
			
		case XML:
			writer = new XmlWriterService(c.getLanguages(), c.getGroups());
			break;
			
		case JSON:
		default:
			writer = null;
		}
		
		return writer;
	}
	
	
	/**
	 * Exibe um alerta quando a importação ou exportação teve êxito.
	 * 
	 * @param file Nome do arquivo.
	 * @param action Ação (<code>imported</code> ou <code>exported</code>)
	 */
	private void raiseSuccessAlert(String file, FileAction action)
	{
		String title;
		String message;
		
		final boolean imported = action == FileAction.IMPORT;
		
		title = new StringBuilder()
				.append(imported ? "Import" : "Export")
				.append(" file")
				.toString();

		message = new StringBuilder()
				.append("The file ")
				.append(file)
				.append(" was ")
				.append(imported ? "imported" : "exported")
				.append(" successfully!")
				.toString();
		
		Alerts.showInfo(title, message);
	}
	
	
	/*
	 * ===========================================================
	 * 			*** ENUMERAÇÕES ***
	 * ===========================================================
	 */
	
	/**
	 * Representa o tipo de ação do arquivo.
	 * 
	 * @author Dr.XGB
	 * @version 1.0.0
	 */
	private enum FileAction
	{
		IMPORT,
		EXPORT,
	}
}
