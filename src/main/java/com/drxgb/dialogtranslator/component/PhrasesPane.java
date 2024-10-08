package com.drxgb.dialogtranslator.component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.drxgb.dialogtranslator.App;
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
	private ListChangeListener<Tab> onTabListChange;
	
	
	/*
	 * ===========================================================
	 * 			*** ASSOCIAÇÕES ***
	 * ===========================================================
	 */
	
	private final App app = App.getInstance();
	
	
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
		setupTabs();
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Povoa as abas dos grupos de frases.
	 */
	public void populateTabs()
	{
		tabs.removeListener(onTabListChange);
		tabs.clear();
		tabs.addAll(
				phraseGroups.stream()
					.map(group -> PhraseGroupTabs.makeTab(group))
					.toList()
		);
		tabs.addListener(onTabListChange);
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
	 */
	@FXML
	public void onBtnAddPhraseGroupAction()
	{
		String name = PhraseGroupTabs.makeFallbackName(tabs);
		PhraseGroup group = new PhraseGroup(name);
		Tab tab = PhraseGroupTabs.makeTab(group);

		tabs.add(tab);
		panGroups.getSelectionModel().select(tab);
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Inicializa as abas.
	 */
	private void setupTabs()
	{
		onTabListChange = ev ->
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
			
			app.getFileChangeObserver().update(true);
		};
		
		tabs = panGroups.getTabs();

		tabs.addListener(onTabListChange);		
		panGroups.setTabDragPolicy(TabDragPolicy.REORDER);
	}
}
