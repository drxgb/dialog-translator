package com.drxgb.dialogtranslator.service.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.Phrase;
import com.drxgb.dialogtranslator.model.PhraseGroup;

/**
 * Responsável por carregar arquivos XLD.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class XldReaderService extends BinaryReaderService
{
	/*
	 * ===========================================================
	 * 			*** CONSTRUTORES ***
	 * ===========================================================
	 */

	public XldReaderService(List<Language> languages, List<PhraseGroup> groups)
	{
		super(languages, groups);
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS IMPLEMENTADOS ***
	 * ===========================================================
	 */

	/**
	 * Carrega o arquivo XLD.
	 * 
	 * @see com.drxgb.dialogtranslator.service.io.ReaderService#read(java.io.InputStream)
	 */
	@Override
	public void read(InputStream input) throws IOException
	{
		// Informações extraídas do arquivo
		String magicHeader;
		int langPos;
		int langCount;
		int masterLangIndex;	
		int groupPos;
		int groupCount;
		
		// Inicializar leitura
		initBytes(input);
		getLanguages().clear();
		getGroups().clear();
		
		// Leitura do cabeçalho
		magicHeader = readString();
		
		if (! magicHeader.equals("XLD"))
		{
			throw new IOException("Not a valid XLD file.");
		}
		
		// Ler os valores do cabeçalho
		langPos = readInt();
		langCount = readInt();
		groupPos = readInt();
		groupCount = readInt();
		masterLangIndex = readInt();

		// Ler dados do arquivo
		setCharset("UTF-16LE");
		readLanguages(langPos, langCount, masterLangIndex);
		readPhrasesGroup(groupPos, groupCount);		
	}
	
	
	/*
	 * ===========================================================
	 * 			*** MÉTODOS PRIVADOS ***
	 * ===========================================================
	 */
	
	/**
	 * Lê os idiomas no arquivo.
	 * 
	 * @param offset Posição de leitura.
	 * @param count Quantidade de idiomas.
	 * @param masterIndex Índice do idioma principal.
	 * 
	 * @throws IOException Quando o arquivo não consegue ser lido.
	 */
	private void readLanguages(int offset, int count, int masterIndex) throws IOException
	{
		String name;
		boolean master;

		setPosition(offset);
		
		for (int i = 0; i < count; ++i)
		{
			name = readString();
			master = i == masterIndex;
			getLanguages().add(new Language(name, master));
		}
	}
	
	
	/**
	 * Lê os grupos de frases em um arquivo.
	 * 
	 * @param offset Posição de leitura.
	 * @param count Quantidade de frases.
	 * 
	 * @throws IOException Quando o arquivo não consegue ser lido.
	 */
	private void readPhrasesGroup(int offset, int count) throws IOException
	{
		PhraseGroup group;
		String name;
		int readPos;
		int endPos;
		int size;
		
		setPosition(offset);
		
		for (int i = 0; i < count; ++i)
		{
			name = readString();
			group = new PhraseGroup(name);

			size = readInt();
			readPos = getPosition();
			endPos = readPos + size;
			
			while (readPos < endPos)
			{
				// Ler as frases				
				group.getPhrases().add(readPhrase());
				readPos = getPosition();
			}
			
			getGroups().add(group);
		}
	}
	
	
	/**
	 * Lê a frase em um arquivo.
	 * 
	 * @return A instância da frase lida.
	 * 
	 * @throws IOException Quando o arquivo não consegue ser lido.
	 */
	private Phrase readPhrase() throws IOException
	{
		Language language;
		Phrase phrase;
		String key;
		String text;
		Iterator<Language> it;
		int readPos;
		int endPos;
		int size;
		
		key = readString();
		phrase = new Phrase(key);
		it = getLanguages().listIterator();
		
		size = readInt();
		readPos = getPosition();
		endPos = readPos + size;
		
		while (readPos < endPos && it.hasNext())
		{
			// Ler os textos da frase
			language = it.next();
			
			if (language != null)
			{
				text = readString();
				phrase.getTexts().put(language, text);
				readPos = getPosition();
			}
		}
		
		if (! isEOF())
		{
			setPosition(endPos);
		}
		
		return phrase;
	}
}
