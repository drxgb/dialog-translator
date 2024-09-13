package com.drxgb.dialogtranslator.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.drxgb.util.ValueHandler;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

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
	
	// Menu
	@FXML public Menu mnuStyle;
	@FXML public RadioMenuItem mnitPhrases;
	@FXML public RadioMenuItem mnitLanguages;
	
	// Base
	@FXML public StackPane stkMain;
	@FXML public ToggleGroup viewMode;
	@FXML public ToggleButton btnPhrases;
	@FXML public ToggleButton btnLanguages;

	
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
		// TODO Inicializar os modos de visão de arquivos FXML distintos.
		
		initializeToggleButtons();
	}
	
	
	/*
	 * ===========================================================
	 * 			*** AÇÕES DO CONTROLADOR ***
	 * ===========================================================
	 */
	
	@FXML
	public void onMnitPhrasesAction()
	{
		btnPhrases.fire();
	}
	
	
	@FXML
	public void onMnitLanguagesAction()
	{
		btnLanguages.fire();
	}
	
	
	@FXML
	public void onBtnPhrasesAction()
	{
		mnitPhrases.setSelected(true);
		selectViewMode(0);
	}
	
	
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
	 * Inicializa os botões alternados dos modos de visualização.
	 */
	private void initializeToggleButtons()
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
		ObservableList<Node> viewModes = stkMain.getChildren();
		
		index = ValueHandler.clamp(index, 0, viewModes.size() - 1);
		viewModes.forEach(v -> v.setVisible(false));
		viewModes.get(index).setVisible(true);
	}
}
