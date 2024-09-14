package com.drxgb.dialogtranslator.component;

import java.io.IOException;

import com.drxgb.dialogtranslator.model.PhraseGroup;
import com.drxgb.dialogtranslator.util.FXRootInitializer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

/**
 * Controlador da tela de frases.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class PhrasesPane extends VBox
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	@FXML public TabPane panGroups;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	public PhrasesPane() throws IOException
	{
		FXRootInitializer.init(this, "phrase/PhrasesView");
	}
	
	
	/*
	 * ===========================================================
	 * 			*** AÇÕES DO CONTROLADOR ***
	 * ===========================================================
	 */
	
	/**
	 * Ação ao clicar no botão "Add phrase group".
	 * 
	 * @throws IOException Quando o arquivo da tela não é encontrado.
	 */
	@FXML
	public void onBtnAddPhraseGroupAction() throws IOException
	{
		Tab tab;
		PhraseGroup group;
		PhraseGroupPane root;
		StringBuilder sb = new StringBuilder();
		ObservableList<Tab> tabs = panGroups.getTabs();
		final int LEN = tabs.size();
		
		sb.append("Group ").append(LEN + 1);
		group = new PhraseGroup(sb.toString());
		root = new PhraseGroupPane(group);
		tab = new Tab(group.getName(), root);
		
		tabs.add(tab);
		panGroups.getSelectionModel().select(tab);
	}

	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	
}
