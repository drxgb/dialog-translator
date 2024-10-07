package com.drxgb.dialogtranslator.service.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.Phrase;
import com.drxgb.dialogtranslator.model.PhraseGroup;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.stream.JsonGenerator;

/**
 * Responsável por exportar arquivos JSON.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class JsonWriterService extends WriterService
{
	/*
	 * ===========================================================
	 * 			*** ATRIBUTOS ***
	 * ===========================================================
	 */
	
	private JsonBuilderFactory factory;
	
	
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	/**
	 * Cria o serviço de exportação para JSON.
	 * 
	 * @param languages Conjunto de idiomas.
	 * @param groups Conjunto de grupos de frases.
	 */
	public JsonWriterService(List<Language> languages, List<PhraseGroup> groups)
	{
		super(languages, groups);
		factory = Json.createBuilderFactory(null);
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
		Map<String, Boolean> config;
		JsonObject root;
		
		config = new HashMap<>();
		config.put(JsonGenerator.PRETTY_PRINTING, true);
		
		root = factory.createObjectBuilder()
				.add("languages", createLanguages())
				.add("phrase_groups", createPhraseGroups())
				.build();
		
		Json.createWriterFactory(config)
			.createWriter(output)
			.write(root);
	}

	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Cria o objeto dos idiomas.
	 * 
	 * @return O conjunto de idiomas no formato JSON.
	 */
	private JsonArray createLanguages()
	{
		List<Language> languages;
		JsonArrayBuilder builder;
		JsonObjectBuilder objLanguage;
		
		languages = getLanguages();
		builder = factory.createArrayBuilder();
		
		for (Language language : languages)
		{
			objLanguage = factory.createObjectBuilder()
					.add("name", language.getName())
					.add("master", language.isMaster());
			builder.add(objLanguage);
		}
		
		return builder.build();
	}
	
	
	/**
	 * Cria o objeto dos grupos de frases.
	 * 
	 * @return O conjunto de grupos de frases no formato JSON.
	 */
	private JsonObjectBuilder createPhraseGroups()
	{
		List<PhraseGroup> groups;
		JsonObjectBuilder builder;
		JsonObjectBuilder objGroup;
		
		groups = getGroups();
		builder = factory.createObjectBuilder();
		
		for (PhraseGroup group : groups)
		{
			objGroup = createPhrases(group.getPhrases());
			builder.add(group.getName(), objGroup);
		}
		
		return builder;
	}
	
	
	/**
	 * Cria o objeto das frases.
	 * 
	 * @return O conjunto de frases no formato JSON.
	 */
	private JsonObjectBuilder createPhrases(List<Phrase> phrases)
	{
		JsonObjectBuilder builder;
		JsonArrayBuilder objPhrase;
		
		builder = factory.createObjectBuilder();
		
		for (Phrase phrase : phrases)
		{
			objPhrase = createPhraseTexts(phrase.getTexts());
			builder.add(phrase.getKey(), objPhrase);
		}
		
		return builder;
	}
	
	
	/**
	 * Cria o objeto dos textos.
	 * 
	 * @return O conjunto de textos no formato JSON.
	 */
	private JsonArrayBuilder createPhraseTexts(Map<Language, String> texts)
	{
		List<Language> languages;
		JsonArrayBuilder builder;
		
		languages = getLanguages();
		builder = factory.createArrayBuilder();
		
		for (Language language : languages)
		{
			builder.add(texts.get(language));
		}
		
		return builder;
	}
}
