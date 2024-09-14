package com.drxgb.dialogtranslator;

import java.io.IOException;

import com.drxgb.dialogtranslator.controller.MainController;
import com.drxgb.dialogtranslator.service.Container;
import com.drxgb.dialogtranslator.service.StageTitleManager;
import com.drxgb.dialogtranslator.service.ViewLoader;

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
 * @version 2.0.0
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
	public static final String VERSION = "2.0.0";
	
	
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
	 * O carregador de telas.
	 */
	private ViewLoader viewLoader;
	
	/**
	 * O conteiner de dados serializáveis da aplicação.
	 */
	private Container container;
	
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
		setup();
		
		Image icon = new Image(getClass().getResourceAsStream("icon/app.png"));
		FXMLLoader loader = viewLoader.getFXMLLoader("MainView");
		Parent root = loader.load();
		MainController controller = loader.getController();
		
		scene = new Scene(root, 640, 480);
		titleManager = new StageTitleManager(stage, NAME);
		
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
		launch();
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
		return (Stage)(getScene().getWindow());
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Configurações iniciais.
	 */
	private void setup()
	{
		instance = this;
		viewLoader = new ViewLoader("view");
		
		setupContainer();
	}
	
	
	/**
	 * Inicializa o conteiner.
	 */
	private void setupContainer()
	{
		container = new Container();
	}
}