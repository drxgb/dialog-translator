package com.drxgb.dialogtranslator.component;

import java.io.IOException;

import com.drxgb.dialogtranslator.model.PhraseGroup;
import com.drxgb.dialogtranslator.util.FXRootInitializer;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * Componente da tela de frases.
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
		ObservableList<Tab> tabs = panGroups.getTabs();
		
		group = new PhraseGroup(makeFallbackName());
		tab = new Tab(group.getName());
		root = new PhraseGroupPane(group, tab);
		
		tab.setContent(root);
		tabs.add(tab);
		panGroups.getSelectionModel().select(tab);
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Cria um nome genérico para a aba.
	 * 
	 * @return O nome da aba.
	 */
	private String makeFallbackName()
	{
		Callback<Integer, String> updateName = i -> {
			StringBuilder sb = new StringBuilder();
			
			sb.append("Group ").append(i);
			return sb.toString();
		};

		int index = 1;
		String name = updateName.call(index);
		SortedList<Tab> tabs = new SortedList<>(panGroups.getTabs(), (a, b) -> a.getText().compareTo(b.getText()));
		
		for (Tab tab : tabs)
		{			
			if (tab.getText().equals(name))
			{
				name = updateName.call(++index);
			}
		}
		
		return name;
	}
}
