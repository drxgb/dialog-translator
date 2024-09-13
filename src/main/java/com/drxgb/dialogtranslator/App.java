package com.drxgb.dialogtranslator;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Aplicação principal.
 * 
 * @author Dr.XGB
 * @version 1.0.0
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
	 * Cena principal da aplicação.
	 */
	private static Scene scene;
	
	
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainView.fxml"));
		Parent root = loader.load();
		
		scene = new Scene(root, 640, 480);
		stage.setScene(scene);
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
	 * Recebe a cena atual.
	 * 
	 * @return A cena atual.
	 */
	public static Scene getScene()
	{
		return scene;
	}

	
	/**
	 * Recebe a janela da aplicação.
	 * 
	 * @return A janela da aplicação.
	 */
	public static Stage getStage()
	{
		return (Stage)(getScene().getWindow());
	}
}