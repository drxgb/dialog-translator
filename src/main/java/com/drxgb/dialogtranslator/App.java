package com.drxgb.dialogtranslator;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
	 * Versão da aplicaçao.
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
		Parent root = new AnchorPane();
		
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
	 * @param args Parâmetros de entrada da aplicação recebidos do terminal.
	 */
	public static void main(String[] args)
	{
		launch();
	}

}