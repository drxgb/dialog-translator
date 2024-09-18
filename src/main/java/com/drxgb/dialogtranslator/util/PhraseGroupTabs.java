package com.drxgb.dialogtranslator.util;

import java.io.IOException;

import com.drxgb.dialogtranslator.component.PhraseGroupPane;
import com.drxgb.dialogtranslator.model.PhraseGroup;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

/**
 * Utilitário para criar abas do grupo de frases.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public abstract class PhraseGroupTabs
{
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PÚBLICOS ESTÁTICOS ***
	 * ===========================================================
	 */
	
	/**
	 * Cria uma aba contendo o conteúdo do grupo de frases.
	 * 
	 * @param group O grupo de frases.
	 * @return Uma nova aba.
	 * 
	 * @throws IOException Quando a raiz não for carregada.
	 */
	public static Tab makeTab(PhraseGroup group) throws IOException
	{
		Tab tab = new Tab(group.getName());
		Parent root = new PhraseGroupPane(group, tab, group.getPhrases());

		tab.setContent(root);
		tab.setUserData(group);
		
		return tab;
	}
	

	/**
	 * Cria um nome genérico para a aba.
	 * 
	 * @param tabs A lista de abas.
	 * @return O nome da aba.
	 */
	public static String makeFallbackName(ObservableList<Tab> tabs)
	{
		int index = 1;
		String name = getFallbackName(index);
		SortedList<Tab> sortedTabs = new SortedList<>(tabs, (a, b) -> a.getText().compareTo(b.getText()));
		
		for (Tab tab : sortedTabs)
		{			
			if (tab.getText().equals(name))
			{
				name = getFallbackName(++index);
			}
		}
		
		return name;
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ESTÁTICOS ***
	 * ===========================================================
	 */	
	
	/**
	 * Recebe o nome genérico da aba de acordo com o índice solicitado.
	 * 
	 * @param index O índice solicitado.
	 * @return Um nome genérico para a aba.
	 */
	private static String getFallbackName(int index)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("Group ").append(index);
		return sb.toString();
	}
}
