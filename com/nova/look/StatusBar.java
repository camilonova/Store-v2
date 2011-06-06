package com.nova.look;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * Esta clase representa la barra de estado de la aplicacion, donde se informa
 * de los cambios o de las acciones tomadas por la aplicacion
 * 
 * @author Camilo Nova
 * @version 1.0
 */
public class StatusBar extends JLabel {

	/**
	 * Constructor de la clase. Determina el color de fondo el borde y el texto
	 * de bienvenida.
	 * 
	 * @param text
	 *            Texto inicial a mostrar
	 */
	public StatusBar(String text) {
		setBorder(BorderFactory.createRaisedBevelBorder());
		setBackground(Color.GRAY);
		setText(text);
	}

	/**
	 * Metodo sobrecargado para corregir la alineacion del texto
	 */
	@Override
	public void setText(String text) {
		super.setText("  " + text);
	}
}
