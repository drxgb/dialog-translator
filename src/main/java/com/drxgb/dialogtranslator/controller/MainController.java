package com.drxgb.dialogtranslator.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.drxgb.dialogtranslator.App;
import com.drxgb.dialogtranslator.component.LanguagesPane;
import com.drxgb.dialogtranslator.component.PhrasesPane;
import com.drxgb.util.ValueHandler;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
	
	// Menu
	@FXML public Menu mnuStyle;
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
			initializeToggleButtons();
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
		// TODO: Verificar arquivo não salvo.
	}
	
	
	/**
	 * Ação ao clicar no item de menu "Close".
	 */
	@FXML
	public void onMnitCloseAction()
	{
		App.getInstance().getStage().close();
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
		final App app = App.getInstance();
		
		Parent root = (Parent) app.getViewLoader().load("AboutView");
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		Stage mainStage = app.getStage();
		StringBuilder sb = new StringBuilder();
		
		sb.append("About ").append(App.NAME);
		
		stage.setTitle(sb.toString());
		stage.setScene(scene);
		stage.getIcons().clear();
		stage.getIcons().addAll(mainStage.getIcons());
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(mainStage);
		stage.showAndWait();
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
		viewModes = panMain.getChildren();
		viewModes.add(new PhrasesPane());
		viewModes.add(new LanguagesPane());
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
		if (viewModes.size() > 0)
		{
			index = ValueHandler.clamp(index, 0, viewModes.size());
			viewModes.forEach(v -> v.setVisible(false));
			viewModes.get(index).setVisible(true);
		}
	}
}
