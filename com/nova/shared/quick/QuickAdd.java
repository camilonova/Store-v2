package com.nova.shared.quick;

import javax.swing.JButton;

/**
 * Esta interface representa la generalizacion de las interfaces de usuario para
 * la creacion rapida de elementos en la DB. Es una magnifica creacion que
 * permite la carga dinamica de las interfaces, segun se requiera. ;)
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public interface QuickAdd {

	/**
	 * Representa el boton de aceptar
	 */
	JButton aceptarBtn = new JButton("Aceptar");

	/**
	 * Representa el boton de cancelar
	 */
	JButton cancelarBtn = new JButton("Cancelar");

	/**
	 * Retorna el elemento creado
	 * 
	 * @return Elemento creado
	 * @since 1.0
	 */
	public abstract String getInsertedText();
}