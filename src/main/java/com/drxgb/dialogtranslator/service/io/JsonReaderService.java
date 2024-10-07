package com.drxgb.dialogtranslator.service.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.Phrase;
import com.drxgb.dialogtranslator.model.PhraseGroup;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;

/**
 * Responsável por importar arquivos JSON.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class JsonReaderService extends ReaderService
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	private JsonObject root;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	/**
	 * Cria o serviço de importação de JSON.
	 * 
	 * @param languages Conjunto de idiomas.
	 * @param groups Conjunto de grupo de frases.
	 */
	public JsonReaderService(List<Language> languages, List<PhraseGroup> groups)
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
		JsonReader reader = Json.createReader(input);
		
		root = reader.readObject();
		readLanguages();
		readPhraseGroups();
	}
	

	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */

	/**
	 * Lê os idiomas.
	 */
	private void readLanguages()
	{
		List<Language> languages;
		JsonArray container;
		JsonObject objLanguage;
		Language language;
		String name;
		boolean master;
		
		container = root.getJsonArray("languages");
		languages = getLanguages();
		languages.clear();
		
		for (JsonValue value : container)
		{
			objLanguage = value.asJsonObject();
			name = objLanguage.getString("name");
			master = objLanguage.getBoolean("master");
			language = new Language(name, master);
			languages.add(language);
		}
	}
	
	
	/**
	 * Lê os grupos de frases.
	 */
	private void readPhraseGroups()
	{
		List<PhraseGroup> groups;
		Set<String> groupSet;
		JsonObject container;
		JsonObject objGroup;
		PhraseGroup group;
		
		container = root.getJsonObject("phrase_groups");
		groupSet = container.keySet();
		groups = getGroups();
		groups.clear();
		
		for (String name : groupSet)
		{
			objGroup = container.getJsonObject(name);
			group = new PhraseGroup(name);
			readPhrases(group.getPhrases(), objGroup);
			groups.add(group);
		}
	}
	
	
	/**
	 * Lê as frases.
	 * 
	 * @param phrases Lista de frases.
	 * @param group O grupo de frases em JSON.
	 */
	private void readPhrases(List<Phrase> phrases, JsonObject group)
	{
		Set<String> phraseSet;
		JsonArray objPhrase;
		Phrase phrase;
		
		phraseSet = group.keySet();
		
		for (String key : phraseSet)
		{
			objPhrase = group.getJsonArray(key);
			phrase = new Phrase(key);
			readPhraseTexts(phrase.getTexts(), objPhrase);
			phrases.add(phrase);
		}
	}
	
	
	/**
	 * Lê os textos da frase.
	 * 
	 * @param texts O conjunto de textos.
	 * @param phrase A frase em JSON.
	 */
	private void readPhraseTexts(Map<Language, String> texts, JsonArray phrase)
	{
		Iterator<Language> langIt;
		Iterator<JsonValue> phraseIt;
		Language language;
		String text;
		
		langIt = getLanguages().iterator();
		phraseIt = phrase.iterator();
		
		while (phraseIt.hasNext())
		{
			language = langIt.next();
			text = ((JsonString) phraseIt.next()).getString();
			texts.put(language, text);
		}
	}
}
