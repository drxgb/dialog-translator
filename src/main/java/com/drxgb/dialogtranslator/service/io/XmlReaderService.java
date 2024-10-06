package com.drxgb.dialogtranslator.service.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.Phrase;
import com.drxgb.dialogtranslator.model.PhraseGroup;

/**
 * Responsável por carregar arquivos XML.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class XmlReaderService extends ReaderService
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	private Document document;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	/**
	 * Cria um leitor XML.
	 * 
	 * @param languages Conjunto de idiomas.
	 * @param groups Conjunto de grupos de frases.
	 */
	public XmlReaderService(List<Language> languages, List<PhraseGroup> groups)
	{
		super(languages, groups);
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS IMPLEMENTADOS ***
	 * ===========================================================
	 */

	/**
	 * @see com.drxgb.dialogtranslator.service.io.ReaderService#read(java.io.InputStream)
	 */
	@Override
	public void read(InputStream input) throws IOException
	{
		try
		{
			DocumentBuilderFactory documentFactory;
			DocumentBuilder documentBuilder;
			
			documentFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentFactory.newDocumentBuilder();
			document = documentBuilder.parse(input);
			
			readLanguages();
			readPhraseGroups();
		}
		catch (ParserConfigurationException | SAXException e)
		{
			throw new IOException(e.getMessage());
		}
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Lê os idiomas do arquivo.
	 */
	private void readLanguages()
	{
		List<Language> languages;
		Language language;
		String name;
		boolean master;
		NodeList nodes;
		Element element;
		
		languages = getLanguages();
		nodes = document.getElementsByTagName("language");

		languages.clear();
		
		for (int i = 0; i < nodes.getLength(); ++i)
		{
			element = (Element) nodes.item(i);
			name = element.getTextContent();
			master = element.hasAttribute("master");
			language = new Language(name, master);
			languages.add(language);
		}
	}
	
	
	/**
	 * Lê os grupos de frases do arquivo.
	 */
	private void readPhraseGroups()
	{
		List<PhraseGroup> groups;
		NodeList nodes;
		Element container;
		Element element;
		String name;
		PhraseGroup group;
		
		groups = getGroups();
		container = (Element) document.getElementsByTagName("phrase-groups").item(0);
		nodes = container.getElementsByTagName("group");
		
		groups.clear();
		
		for (int j = 0; j < nodes.getLength(); ++j)
		{
			element = (Element) nodes.item(j);
			name = element.getAttribute("name");
			group = new PhraseGroup(name);
			readPhrases(element.getElementsByTagName("phrase"), group.getPhrases());
			groups.add(group);
		}
	}
	
	
	/**
	 * Lê as frases do grupo no arquivo.
	 * 
	 * @param nodes Os nós do elemento do grupo das frases.
	 * @param phrases A lista de frases.
	 */
	private void readPhrases(NodeList nodes, List<Phrase> phrases)
	{
		Element element;
		Phrase phrase;
		String key;
		
		for (int i = 0; i < nodes.getLength(); ++i)
		{
			element = (Element) nodes.item(i);
			key = element.getAttribute("key");
			phrase = new Phrase(key);
			readPhraseTexts(element.getElementsByTagName("text"), phrase.getTexts());
			phrases.add(phrase);
		}
	}
	
	
	/**
	 * Lê os textos da frase.
	 * 
	 * @param nodes Os nós do elemento da frase.
	 * @param texts O conjunto de textos da frase.
	 */
	private void readPhraseTexts(NodeList nodes, Map<Language, String> texts)
	{
		List<Language> languages;
		Element element;
		Language language;
		String text;
		int index;
		
		languages = getLanguages();
		
		for (int i = 0; i < nodes.getLength(); ++i)
		{
			element = (Element) nodes.item(i);
			index = Integer.parseInt(element.getAttribute("language"));
			language = languages.get(index);
			text = element.getTextContent();
			texts.put(language, text);
		}
	}
}
