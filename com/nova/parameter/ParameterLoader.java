package com.nova.parameter;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Esta clase se encarga de cargar el archivo de parametros para la clase
 * GlobalParameter
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
class ParameterLoader {

	/**
	 * Nombre del recurso
	 */
	private static final String BUNDLE_NAME = "resources.parameter";

	/**
	 * Recurso cargado
	 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	/**
	 * Constructor privado, impide la instanciacion de la clase.
	 * 
	 * @since 1.0
	 */
	private ParameterLoader() {
		// Impide la instanciacion de la clase
	}

	/**
	 * Obtiene la propiedad identificada por el parametro
	 * 
	 * @param key
	 *            Propiedad a retornar
	 * @return Valor de la propiedad
	 * @since 1.0
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}