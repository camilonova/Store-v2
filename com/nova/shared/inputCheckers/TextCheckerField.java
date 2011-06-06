package com.nova.shared.inputCheckers;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * Esta clase representa una caja de entrada de texto, formateada
 * especificamente para validar la entrada a texto para el formato especificado
 * por la aplicacion, el cual debe constar unicamente por letras y numeros, sin
 * caracteres.
 * <p>
 * Reemplaza los listener de teclado de la version anterior :)
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 * @version 1.1 Mejora en el algoritmo de verificacion
 */
public class TextCheckerField extends JTextField implements KeyListener {

	/**
	 * Componente siguiente
	 */
	private final JComponent nextElement;

	/**
	 * Componente de salida
	 */
	private final JComponent exitElement;

	/**
	 * Constructor por defecto de la clase, construye la caja de texto con
	 * botones fantasma conpatibles con el formulario
	 * 
	 * @since 1.0
	 */
	public TextCheckerField() {
		this(new JButton(), new JButton());
	}

	/**
	 * Constructor, construye la caja de texto y asigna los botones recibidos
	 * por parametro
	 * 
	 * @param nextElement
	 *            Componente que se activa al presionar 'enter'
	 * @param exitElement
	 *            Componente que se activa al presionar 'esc'
	 * 
	 * @since 1.0
	 */
	public TextCheckerField(JComponent nextElement, JComponent exitElement) {
		this.nextElement = nextElement;
		this.exitElement = exitElement;

		addKeyListener(this);
		addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				selectAll();
				super.focusGained(e);
			}

		});
	}

	/**
	 * Valida los siguientes eventos:
	 * <p> - Que sea unicamente letras o digitos, ningun caracter especial es
	 * permitido
	 * 
	 * @since 1.1
	 */
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();

		if (!(Character.isLetterOrDigit(c) || c == KeyEvent.VK_BACK_SPACE
				|| c == KeyEvent.VK_DELETE || c == KeyEvent.VK_MINUS || c == KeyEvent.VK_SPACE))
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
