package com.drxgb.dialogtranslator.service;

import java.io.IOException;
import java.net.URL;

import com.drxgb.dialogtranslator.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * Responsável por carregar as telas.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class ViewLoader
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	private String basePath;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	/**
	 * Cria o carregador de telas.
	 * 
	 * @param basePath O caminho base para encontrar os arquivos.
	 */
	public ViewLoader(String basePath)
	{
		this.basePath = basePath;
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Recebe o carregador FXML.
	 * 
	 * @param view Nome da tela a ser carregada.
	 * @return O carregador FXML.
	 */
	public FXMLLoader getFXMLLoader(String view)
	{
		StringBuilder sb = new StringBuilder();
		URL location;
		
		sb.append(basePath).append('/').append(view).append(".fxml");
		location = App.class.getResource(sb.toString());
		
		return new FXMLLoader(location);
	}
	
	
	/**
	 * Carrega a tela e recebe o nó raiz.
	 * 
	 * @param view Nome da tela a ser carregada.
	 * @return O nó da raiz da tela.
	 * 
	 * @throws IOException Quando o arquivo da tela não é encontrado.
	 */
	public Node load(String view) throws IOException
	{
		return (Node)(getFXMLLoader(view).load());
	}
}
