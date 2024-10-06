package com.drxgb.dialogtranslator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import com.drxgb.dialogtranslator.controller.MainController;
import com.drxgb.dialogtranslator.service.Container;
import com.drxgb.dialogtranslator.service.FileChangeObserver;
import com.drxgb.dialogtranslator.service.ViewLoader;
import com.drxgb.dialogtranslator.service.manager.FileManager;
import com.drxgb.dialogtranslator.service.manager.StageTitleManager;
import com.drxgb.dialogtranslator.service.manager.StyleManager;
import com.drxgb.dialogtranslator.util.Alerts;
import com.drxgb.util.PropertiesManager;
import com.drxgb.util.Report;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Aplicação principal.
 * 
 * @author Dr.XGB
 * @version 2.0.1
 */
public class App extends Application
{
	/*
	 * ===========================================================
	 * 			*** CONSTANTES ***
	 * ===========================================================
	 */
	
	/**
	 * Nome da aplicação.
	 */
	public static final String NAME = "XGB's Dialog Translator";
	
	/**
	 * Versão da aplicação.
	 */
	public static final String VERSION = "2.0.1";
	
	/**
	 * O ano de lançamento da aplicação.
	 */
	public static final Integer RELEASE_YEAR = 2024;
	
	/**
	 * O nome do arquivo de configuração.
	 */
	public static final String SETTINGS_FILENAME = "settings.properties";
	
	
	/*
	 * ===========================================================
	 * 			*** ASSOCIAÇÕES ***
	 * ===========================================================
	 */
	
	/**
	 * A instância da aplicação.
	 */
	private static App instance;
	
	/**
	 * Gerenciador de título da janela da aplicação.
	 */
	private StageTitleManager titleManager;
	
	/**
	 * O gerenciador de estilos da aplicação.
	 */
	private StyleManager styleManager;
	
	/**
	 * O gerenciador de arquivos da aplicação.
	 */
	private FileManager fileManager;
	
	/**
	 * Notificador de alterações no arquivo.
	 */
	private FileChangeObserver fileChangeObserver;

	/**
	 * O carregador de telas.
	 */
	private ViewLoader viewLoader;
	
	/**
	 * O conteiner de dados serializáveis da aplicação.
	 */
	private Container container;
	
	/**
	 * O arquivo de configuração da aplicação.
	 */
	private Properties settings;
	
	/**
	 * A lista de extensões de arquivo suportadas pela aplicação.
	 */
	private List<String> supportedFileExtensions;
	
	/**
	 * Cena principal da aplicação.
	 */
	private Scene scene;
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ***
	 * ===========================================================
	 */	

	/**
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws IOException
	{
		setup(stage);
		
		Image icon = new Image(getClass().getResourceAsStream("icon/app.png"));
		FXMLLoader loader = viewLoader.getFXMLLoader("MainView");
		Parent root = loader.load();
		MainController controller = loader.getController();
		
		scene = new Scene(root, 640, 480);
		
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.setOnCloseRequest(ev -> controller.onCloseAction(ev));
		stage.show();
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ESTÁTICOS ***
	 * ===========================================================
	 */

	/**
	 * Ponto de entrada da aplicação.
	 * 
	 * @param args Parâmetros de entrada da aplicação recebidos do terminal.
	 */
	public static void main(String[] args)
	{
		try
		{
			Locale.setDefault(Locale.US);
			launch();
		}
		catch (Throwable t)
		{
			Report.writeErrorLog(t);
			Alerts.showError(t);
		}
	}
	
	
	/**
	 * Recebe a instância da aplicação.
	 * 
	 * @return A instância da aplicação.
	 */
	public static App getInstance()
	{
		return instance;
	}
	
	
	/*
	 * ===========================================================
	 * 			*** GETTERS ***
	 * ===========================================================
	 */
	
	/**
	 * Recebe o gerenciador de título da janela da aplicação.
	 * 
	 * @return O gerenciador de título.
	 */
	public StageTitleManager getTitleManager()
	{
		return titleManager;
	}
	
	
	/**
	 * Recebe o gerenciador de estilos.
	 * 
	 * @return O gerenciador de estilos.
	 */
	public StyleManager getStyleManager()
	{
		return styleManager;
	}
	
	
	/**
	 * Recebe o gerenciador de arquivos.
	 * 
	 * @return O gerenciador de arquivos.
	 */
	public FileManager getFileManager()
	{
		return fileManager;
	}
	
	
	/**
	 * Recebe o notificador de alterações do arquivo.
	 * @return
	 */
	public FileChangeObserver getFileChangeObserver()
	{
		return fileChangeObserver;
	}


	/**
	 * Recebe o carregdor de telas da aplicação.
	 * 
	 * @return O carregador de telas.
	 */
	public ViewLoader getViewLoader()
	{
		return viewLoader;
	}
	
	
	/**
	 * Recebe o conteiner e dados serializáveis.
	 * 
	 * @return O conteiner.
	 */
	public Container getContainer()
	{
		return container;
	}
	
	
	/**
	 * Recebe o arquivo de configuração da aplicação.
	 * 
	 * @return O arquivo de configuração.
	 */
	public Properties getSettings()
	{
		if (settings == null)
		{
			settings = PropertiesManager.load(new File(SETTINGS_FILENAME));
		}
		
		return settings;
	}
	
	
	/**
	 * Recebe a lista de extensões de arquivo suportadas pela aplicação.
	 * 
	 * @return A lista de extensões.
	 */
	public List<String> getSupportedFileExtensionsList()
	{
		return supportedFileExtensions;
	}

	
	/**
	 * Recebe a cena atual.
	 * 
	 * @return A cena atual.
	 */
	public Scene getScene()
	{
		return scene;
	}


	/**
	 * Recebe a janela da aplicação.
	 * 
	 * @return A janela da aplicação.
	 */
	public Stage getStage()
	{
		return scene != null ? (Stage) (scene.getWindow()) : null;
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Configurações iniciais.
	 * 
	 * @param stage A janela da aplicação.
	 * 
	 * @throws IOException Quando algum arquivo não é encontrado.
	 */
	private void setup(Stage stage) throws IOException
	{
		instance = this;

		container = new Container();
		viewLoader = new ViewLoader("view");
		titleManager = new StageTitleManager(stage, NAME);
		fileManager = new FileManager();
		
		setupSupportedFileExtensionsList();
		setupStyles();
		setupFileChangeObserver();
	}
	
	
	/**
	 * Inicializa a lista de arquivos suportados.
	 */
	private void setupSupportedFileExtensionsList()
	{
		supportedFileExtensions = new ArrayList<>();
		supportedFileExtensions.add("*.xld");
	}
	
	
	/**
	 * Inicializa os estilos.
	 * 
	 * @param root A raiz que terá seu estilo gerenciado.
	 */
	private void setupStyles()
	{
		List<String> styles = new LinkedList<>();
		String basePath = "style";
		
		styles.add("Light");
		styles.add("Dark");

		styleManager = new StyleManager(styles, basePath);
	}
	
	
	/**
	 * Inicializa o notificador de alteração do arquivo.
	 */
	private void setupFileChangeObserver()
	{
		fileChangeObserver = new FileChangeObserver();		
		fileChangeObserver.subscribe(changed -> titleManager.setUnsaved(changed));
	}
}