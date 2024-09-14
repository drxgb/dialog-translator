package com.drxgb.dialogtranslator.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.drxgb.dialogtranslator.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controlador da tela "Sobre".
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class AboutController implements Initializable
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	@FXML public Label lblAppName;
	@FXML public Label lblAppVersion;
	@FXML public Label lblBuildInfo;
	@FXML public Label lblCopyright;
	
	
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
		StringBuilder version = new StringBuilder();
		StringBuilder buildInfo = new StringBuilder();
		StringBuilder copyright = new StringBuilder();
		
		version.append("Version ").append(App.VERSION);
		
		buildInfo.append("Compiled with Java ")
			.append(System.getProperty("java.version"))
			.append(" and JavaFX ")
			.append(System.getProperty("javafx.version"));
		
		copyright.append((char) 0xA9)
			.append(' ')
			.append(App.RELEASE_YEAR)
			.append(" - Dr.XGB");
		
		lblAppName.setText(App.NAME);
		lblAppVersion.setText(version.toString());
		lblBuildInfo.setText(buildInfo.toString());
		lblCopyright.setText(copyright.toString());
	}
	
	
	/*
	 * ===========================================================
	 * 			*** AÇÕES DO CONTROLADOR ***
	 * ===========================================================
	 */
	
	/**
	 * Ação ao cliar no link do site do desenvolvedor.
	 * 
	 * @param ev O evento disparado.
	 */
	@FXML
	public void onLnkSiteAction(ActionEvent ev)
	{
		Hyperlink link = (Hyperlink) ev.getSource();
		String url = link.getText();
		
		App.getInstance().getHostServices().showDocument(url);
	}
	
	/**
	 * Ação ao clicar no botão "Close".
	 * 
	 * @param ev O evento disparado.
	 */
	@FXML
	public void onBtnCloseAction(ActionEvent ev)
	{
		Button button = (Button) ev.getSource();
		Stage stage = (Stage) button.getScene().getWindow();
		
		stage.close();
	}
}
