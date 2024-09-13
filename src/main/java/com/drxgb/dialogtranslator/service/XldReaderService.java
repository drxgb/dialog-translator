package com.drxgb.dialogtranslator.service;

import java.io.InputStream;
import java.util.List;

import com.drxgb.dialogtranslator.model.Language;
import com.drxgb.dialogtranslator.model.PhraseGroup;

/**
 * Responsável por carregar arquivos XLD.
 * 
 * @author Dr.XGB
 * @version 1.0.0
 */
public class XldReaderService extends ReaderService
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
	 * @see com.drxgb.dialogtranslator.service.ReaderService#read(java.io.InputStream)
	 */
	@Override
	public void read(InputStream input)
	{
		// TODO Carregar arquivo XLD
	}

}
