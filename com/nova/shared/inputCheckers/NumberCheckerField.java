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
 * especificamente para convertir numericamente la entrada al formato
 * especificado por la aplicacion, el cual debe constar unicamente por numeros.
 * <p>
 * Reemplaza los listener de teclado de la version anterior :)
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 * @version 1.1 Mejora en el algoritmo de verificacion
 */
public class NumberCheckerField extends JTextField implements KeyListener {

	/**
	 * Representa la cantidad maxima de numeros
	 */
	private int maxNumbers;

	/**
	 * Componente siguiente
	 */
	private final JComponent nextElement;

	/**
	 * Componente de salida
	 */
	private final JComponent exitElement;

	/**
	 * Constructor por defecto de la clase, construye una caja de texto con un
	 * maximo de numeros de 12
	 * 
	 * @since 1.0
	 */
	public NumberCheckerField() {
		this(12);
	}

	/**
	 * Constructor, crea una caja de texto con un numero maximo de digitos
	 * establecidos por el parametro
	 * 
	 * @param maxNumber
	 *            Numero maximo de numeros a recibir
	 * @since 1.0
	 */
	public NumberCheckerField(int maxNumber) {
		this(maxNumber, new JTextField(), new JTextField());
	}

	/**
	 * Constructor de la clase, asigna los parametros recibidos a las
	 * caracteristicas de la clase
	 * 
	 * @param maxNumber
	 *            Numero maximo de numeros a recibir
	 * @param nextElement
	 *            Componente que se activa al presionar 'enter'
	 * @param exitElement
	 *            Componente que se activa al presionar 'esc'
	 * 
	 * @since 1.0
	 */
	public NumberCheckerField(int maxNumber, JComponent nextElement,
			JComponent exitElement) {
		this.nextElement = nextElement;
		this.exitElement = exitElement;
		maxNumbers = maxNumber;
		setHorizontalAlignment(RIGHT);

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
	 * Restringe los siguientes eventos:
	 * <p> - Que sea una letra
	 * <p> - Que la longitud sea menor a la indicada
	 * 
	 * @since 1.1
	 */
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();

		if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)
				|| getText().length() == maxNumbers)
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
