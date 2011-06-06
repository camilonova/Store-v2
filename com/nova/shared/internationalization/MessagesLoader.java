package com.nova.shared.internationalization;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Esta clase obtiene los textos que permiten la internacionalizacion de la
 * aplicacion.
 * 
 * @author Camilo Nova
 * @version 1.0
 */
public class MessagesLoader {

	/**
	 * Archivo de propiedades
	 */
	private static final String BUNDLE_NAME = "resources.locale.messages";

	/**
	 * Cargador de propiedades
	 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME, Locale.getDefault());

	private MessagesLoader() {
		// Sin Implementacion
	}

	/**
	 * Retorna la cadena especificada por la llave
	 * 
	 * @param key
	 *            Identificador de la cadena
	 * @return Cadena identificada con el parametro
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
