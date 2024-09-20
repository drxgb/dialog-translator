package com.drxgb.dialogtranslator.component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.drxgb.dialogtranslator.model.PhraseGroup;
import com.drxgb.dialogtranslator.util.FXRootInitializer;
import com.drxgb.dialogtranslator.util.PhraseGroupTabs;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.layout.VBox;

/**
 * Componente da tela de frases.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class PhrasesPane extends VBox implements Initializable
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	@FXML public TabPane panGroups;
	
	private ObservableList<PhraseGroup> phraseGroups;
	private ObservableList<Tab> tabs;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */
	
	public PhrasesPane(ObservableList<PhraseGroup> phraseGroups) throws IOException
	{
		this.phraseGroups = phraseGroups;
		FXRootInitializer.init(this, "phrase/PhrasesView");
	}
	
	
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
		tabs = panGroups.getTabs();

		tabs.addListener((ListChangeListener<Tab>) ev ->
		{
			ev.next();
			
			if (ev.wasAdded())
			{
				phraseGroups.addAll(
						ev.getAddedSubList()
							.stream()
							.map(t -> (PhraseGroup) t.getUserData())
							.filter(g -> g != null)
							.toList()
				);
			}

			if (ev.wasRemoved())
			{
				phraseGroups.removeAll(
						ev.getRemoved()
							.stream()
							.map(t -> (PhraseGroup) t.getUserData())
							.filter(g -> g != null)
							.toList()
				);
			}
		});
		
		panGroups.setTabDragPolicy(TabDragPolicy.REORDER);
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Povoa as abas dos grupos de frases.
	 * 
	 * @param phraseGroups A nova lista do grupo de frases.
	 * 
	 * @throws IOException Quando a aba não pode ser criada.
	 */
	public void populateTabs(ObservableList<PhraseGroup> phraseGroups) throws IOException
	{
		tabs.clear();
		
		for (PhraseGroup group : phraseGroups)
		{
			tabs.add(PhraseGroupTabs.makeTab(group));
		}
	}
	
	
	/**
	 * Atualiza as abas.
	 */
	public void updateTabs()
	{
		panGroups.getTabs().forEach(tab ->
		{
			Node content = tab.getContent();
			
			if (content instanceof PhraseGroupPane)
			{
				PhraseGroupPane phraseGroupPane = (PhraseGroupPane) content;
				
				phraseGroupPane.updateMasterText();
				phraseGroupPane.updateLanguagesComboBox();
			}
		});
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
		String name = PhraseGroupTabs.makeFallbackName(tabs);
		PhraseGroup group = new PhraseGroup(name);
		Tab tab = PhraseGroupTabs.makeTab(group);

		tabs.add(tab);
		panGroups.getSelectionModel().select(tab);
	}
}
