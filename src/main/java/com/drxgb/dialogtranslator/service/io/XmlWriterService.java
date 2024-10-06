package com.drxgb.dialogtranslator.service.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.Phrase;
import com.drxgb.dialogtranslator.model.PhraseGroup;

/**
 * Responsável por salvar arquivos XML.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class XmlWriterService extends WriterService
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

	public XmlWriterService(List<Language> languages, List<PhraseGroup> groups)
	{
		super(languages, groups);
	}

	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS IMPLEMENTADOS ***
	 * ===========================================================
	 */

	/**
	 * @see com.drxgb.dialogtranslator.service.io.WriterService#write(java.io.OutputStream)
	 */
	@Override
	public void write(OutputStream output) throws IOException
	{
		try
		{
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			DOMSource source;
			StreamResult result;
			Element root;
			
			document = documentBuilder.newDocument();
			transformerFactory.setAttribute("indent-number", 4);			
			root = document.createElement("main");
			document.appendChild(root);
			writeLanguages(root);
			writeGroups(root);
			
			source = new DOMSource(document);
			result = new StreamResult(output);
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
		}
		catch (ParserConfigurationException | TransformerException e)
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
	 * Escreve os idiomas no documento.
	 * 
	 * @param parent O elemento pai onde serão inseridos os idiomas.
	 */
	private void writeLanguages(Element parent)
	{
		List<Language> languages;
		Element container;
		Element element;
		Text text;
		int index;
		
		languages = getLanguages();
		container = document.createElement("languages");
		index = 0;
		
		for (Language language : languages)
		{
			element = document.createElement("language");
			text = document.createTextNode(language.getName());
			
			if (language.isMaster())
			{
				element.setAttribute("master", "1");
			}


			element.setAttribute("index", String.valueOf(index));
			element.appendChild(text);
			container.appendChild(element);
			++index;
		}
		
		parent.appendChild(container);
	}
	
	
	/**
	 * Escreve os grupos de frases no documento.
	 * 
	 * @param parent O elemento pai onde serão inseridos os idiomas.
	 */
	private void writeGroups(Element parent)
	{
		List<PhraseGroup> groups;
		Element container;
		Element element;
		int index;
		
		groups = getGroups();
		container = document.createElement("phrase-groups");
		index = 1;
		
		for (PhraseGroup group : groups)
		{
			element = document.createElement("group");
			element.setAttribute("name", group.getName());
			element.setAttribute("index", String.valueOf(index));
			container.appendChild(element);
			writePhrases(element, group.getPhrases());
			++index;
		}
		
		parent.appendChild(container);
	}
	
	
	/**
	 * Escreve as frases no documento.
	 * 
	 * @param parent O elemento pai onde serão inseridos os idiomas.
	 * @param phrases A lista de frases.
	 */
	private void writePhrases(Element parent, List<Phrase> phrases)
	{
		Element container;
		
		for (Phrase phrase : phrases)
		{
			container = document.createElement("phrase");
			container.setAttribute("key", phrase.getKey());
			writePhraseTexts(container, phrase.getTexts());
			parent.appendChild(container);
		}
	}
	
	
	/**
	 * Escreve os textos da frase no documento.
	 * 
	 * @param parent O elemento pai onde serão inseridos os idiomas.
	 * @param texts O conjunto de textos da frase.
	 */
	private void writePhraseTexts(Element parent, Map<Language, String> texts)
	{
		List<Language> languages;
		String phraseText;
		Element element;
		Text nodeText;
		int index;
		
		languages = getLanguages();
		index = 0;
		
		for (Language language : languages)
		{
			phraseText = texts.get(language);
			element = document.createElement("text");
			nodeText = document.createTextNode(phraseText);
			
			element.setAttribute("language", String.valueOf(index));
			element.appendChild(nodeText);
			parent.appendChild(element);
			++index;
		}
	}
}
