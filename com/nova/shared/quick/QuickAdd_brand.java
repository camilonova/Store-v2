package com.nova.shared.quick;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.zip.DataFormatException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nova.core.Core;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.inputCheckers.TextCheckerField;

/**
 * Esta clase representa la interfaz de creacion rapida de una marca en el
 * listado de la aplicacion
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
class QuickAdd_brand extends JDialog implements QuickAdd {

	/**
	 * Campo de nombre de la marca.
	 */
	final TextCheckerField checkerField = new TextCheckerField(aceptarBtn, cancelarBtn);
	
	/**
	 * Crea la interfaz para mostrar la entrada de nueva marca
	 * 
	 * @since 1.0
	 */
	public QuickAdd_brand() {
		JPanel buttonPanel = new JPanel();
		JPanel inputPanel = new JPanel();

		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = checkerField.getText();
				if (text.length() > 0) {
					String data[] = { text };
					try {
						Core.newData("brand", data);
					} catch (DataFormatException e1) {
						e1.printStackTrace();
					}
					dispose();
				}
				checkerField.requestFocus();
			}
		});
		buttonPanel.add(aceptarBtn);

		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPanel.add(cancelarBtn);

		checkerField.setPreferredSize(new Dimension(120, 20));
		checkerField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (checkerField.getText().length() + 1 > 30)
					e.consume();
			}
		});
		inputPanel.add(new JLabel("Marca"));
		inputPanel.add(checkerField);

		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		pack();
		setTitle("Agregar marca...");
		setLocation((GlobalConstants.SCREEN_SIZE_WIDTH - getWidth()) / 2,
				(GlobalConstants.SCREEN_SIZE_HEIGHT - getHeight()) / 2);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
		setVisible(true);
	}

	public String getInsertedText() {
		return checkerField.getText();
	}
}
