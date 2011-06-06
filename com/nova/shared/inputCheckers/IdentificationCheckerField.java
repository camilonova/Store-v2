package com.nova.shared.inputCheckers;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * Esta clase representa una caja de entrada de texto, formateada
 * especificamente para convertir nit y CC al formato especificado por la
 * aplicacion, el cual debe llevar los puntos de separacion y la barra seguida
 * de un numero para el nit.
 * <p>
 * El numero de identificacion mas largo contiene 9 digitos y el del NIT tiene
 * un tamaño maximo de 11 caracteres.
 * <p>
 * 
 * Reemplaza los listener de teclado de la version anterior :)
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 * @version 1.1 Mejora en el algoritmo de verificacion
 */
public class IdentificationCheckerField extends JTextField implements
		KeyListener {

	/**
	 * Componente siguiente
	 */
	private final JComponent nextElement;

	/**
	 * Componente de salida
	 */
	private final JComponent exitElement;

	/**
	 * Constructor predeterminado, construye la caja de texto.
	 * 
	 * @since 1.0
	 */
	public IdentificationCheckerField() {
		this(new JButton(), new JButton());
	}

	/**
	 * Constructor, construye la caja de texto y los parametros recibidos como
	 * botones de utilidad de formulario
	 * 
	 * @param nextElement
	 *            Componente que se activa al presionar 'enter'
	 * @param exitElement
	 *            Componente que se activa al presionar 'esc'
	 * 
	 * @since 1.0
	 */
	public IdentificationCheckerField(JComponent nextElement,
			JComponent exitElement) {
		this.nextElement = nextElement;
		this.exitElement = exitElement;
		setHorizontalAlignment(RIGHT);

		addKeyListener(this);
		addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				setText(getText().replace(".", ""));
				selectAll();
			}

			public void focusLost(FocusEvent e) {
				setText(getText());
			}
		});
	}

	/**
	 * Retorna el texto formateado como numero de identificacion
	 * 
	 * @since 1.1
	 */
	@Override
	public String getText() {
		String text = super.getText().replace(".", "");

		if (text.endsWith("-") || !text.contains("-") && text.length() < 7
				|| text.contains("-") && text.length() < 9)
			requestFocus();
		else {
			String string = text;
			String end = new String();

			if (text.split("-").length == 2) {
				string = text.split("-")[0];
				end = "-" + text.split("-")[1];
			}
			text = new String();

			for (int i = 0; i < string.length(); i++) {
				if ((string.length() - i) % 3 == 0 && i != 0)
					text += ".";
				text += string.charAt(i);
			}
			text += end;
		}
		return text;
	}

	/**
	 * Restinge los siguientes eventos:
	 * <p> - Que sea una letra
	 * <p> - Que contenga solamente un caracter '-'
	 * <p> - Que no comience con '-'
	 * <p> - Que la cadena sea de longitud 9 sin el caracter '-'
	 * <p> - Que exista solamente un numero luego del caracer '-'
	 * <p>
	 * 
	 * @since 1.1
	 */
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		String text = getText().replace(".", "");

		if ((!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE
				|| c == KeyEvent.VK_DELETE || c == KeyEvent.VK_MINUS))
				|| text.contains("-")
				&& c == KeyEvent.VK_MINUS
				|| text.startsWith("-")
				|| (!text.contains("-") && text.length() == 9 && c != KeyEvent.VK_MINUS)
				|| (text.split("-").length == 2))

			e.consume();
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (nextElement instanceof JButton) {
				JButton nextBtn = (JButton) nextElement;
				nextBtn.doClick();
			} else
				nextElement.requestFocus();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (exitElement instanceof JButton) {
				JButton exitBtn = (JButton) exitElement;
				exitBtn.doClick();
			} else
				exitElement.requestFocus();
		}
	}

	public void keyPressed(KeyEvent e) {
		// Sin Implementacion
	}
}
