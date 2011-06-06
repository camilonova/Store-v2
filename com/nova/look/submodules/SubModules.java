package com.nova.look.submodules;

import java.util.ArrayList;

import com.nova.look.modules.Modules;

/**
 * Esta clase abstrae los submodulos de la aplicacion, los cuales se
 * caracterizan por estar incluidos en un modulo principal, pero que necesitan
 * tener un comportamiento de modulo a la vez.
 * 
 * @author Camilo Nova
 * @version 1.0
 * 
 */
public interface SubModules extends Modules {

	/**
	 * Retorna el codigo del item que se esta mostrando
	 * 
	 * @return Item a mostrar
	 * @since 1.0
	 */
	public String getItemToShow();

	/**
	 * Actualiza el codigo del item que se esta mostrando
	 * 
	 * @param newItemCode
	 *            Codigo del nuevo item
	 * @since 1.0
	 */
	public void setItemToShow(String newItemCode);

	/**
	 * Retorna el filtro para la tabla con los datos
	 * 
	 * @return Filtro en la base de datos
	 * @since 1.0
	 */
	public String getFilter();

	/**
	 * Retorna los nombres de los campos indispensables para la entrada de datos
	 * al submodulo
	 * 
	 * @return Nombres de los campos indispensables para la entrada de datos
	 * @since 1.0
	 */
	public ArrayList<String> getDataForInput();

	/**
	 * Retorna el numero que debe llevar un nuevo formato
	 * 
	 * @return Numero del formato a crear
	 * @since 1.0
	 */
	public String getNextConsecutiveNumber();

}