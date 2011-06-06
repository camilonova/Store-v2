package com.nova.shared.command;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import com.nova.look.modules.Modules;

/**
 * Generalizacion de los comandos de la aplicacion.
 * 
 * @author Camilo Nova
 * @version 1.0 Generalizacion de los comandos
 */
public abstract class Command {

	/**
	 * Representa el modulo propietario
	 */
	protected Modules owner;

	/**
	 * Representa la busqueda en la tabla
	 */
	private Search search;

	/**
	 * @param own
	 * @since 1.0
	 */
	public Command(Modules own) {
		owner = own;
		search = new Search(own);
	}

	/**
	 * Metodo que ejecuta el comando de agregar
	 * 
	 * @since 1.0
	 */
	public abstract void add();

	/**
	 * Metodo que ejecuta el comando de editar
	 * 
	 * @since 1.0
	 */
	public abstract void edit();

	/**
	 * Metodo que ejecuta el comando de ver
	 * 
	 * @since 1.0
	 * 
	 */
	public abstract void see();

	/**
	 * Metodo que ejecuta el comando de eliminar
	 * 
	 * @since 1.0
	 */
	public abstract void remove();

	/**
	 * Retorna la instancia del boton de buscar
	 * 
	 * @return Boton de buscar
	 * @since 1.0
	 */
	public JButton getSearchButton() {
		return search.getSearchButton();
	}

	/**
	 * Retorna la instancia del item de buscar
	 * 
	 * @return Item de buscar
	 * @since 1.0
	 */
	public JMenuItem getSearchItem() {
		return search.getSearchItem();
	}

	/**
	 * Retorna la instancia del boton de anterior
	 * 
	 * @return Boton de anterior
	 * @since 1.0
	 */
	public JButton getBackButton() {
		return search.getBackButton();
	}

	/**
	 * Retorna la instancia del item de anterior
	 * 
	 * @return Item de anterior
	 * @since 1.0
	 */
	public JMenuItem getBackItem() {
		return search.getBackItem();
	}

	/**
	 * Retorna la instancia del boton de siguiente
	 * 
	 * @return Boton de siguiente
	 * @since 1.0
	 */
	public JButton getNextButton() {
		return search.getNextButton();
	}

	/**
	 * Retorna la instancia del item de siguiente
	 * 
	 * @return Item de siguiente
	 * @since 1.0
	 */
	public JMenuItem getNextItem() {
		return search.getNextItem();
	}
}
