package com.drxgb.dialogtranslator.util;

import java.io.IOException;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.service.ViewLoader;

import javafx.fxml.FXMLLoader;

/**
 * Utilitário para inicialização de elementos raiz do FXML.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public abstract class FXRootInitializer
{
	/**
	 * Inicializa o elemento FXML raiz.
	 * 
	 * @param root O elemento elegível a ser raiz.
	 * @param fxmlPath O caminho do arquivo FXML a ser carregado.
	 */
	public static void init(Object root, String fxmlPath)
	{
		try
		{
			ViewLoader viewLoader = App.getInstance().getViewLoader();
			FXMLLoader fxmlLoader = viewLoader.getFXMLLoader(fxmlPath);
			
			fxmlLoader.setRoot(root);
			fxmlLoader.setController(root);
			fxmlLoader.load();
		}
		catch (IOException e)
		{
			// TODO Janela de erro - Raiz não encontrada.
			e.printStackTrace();
		}
	}
}
