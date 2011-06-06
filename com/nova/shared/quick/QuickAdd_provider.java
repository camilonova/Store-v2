package com.nova.shared.quick;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.zip.DataFormatException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nova.core.Core;
import com.nova.look.StoreSession;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.inputCheckers.IdentificationCheckerField;
import com.nova.shared.inputCheckers.TextCheckerField;

/**
 * Esta clase representa la interfaz de creacion rapida de un proveedor
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
final class QuickAdd_provider extends JDialog implements QuickAdd {

	/**
	 * Campo de ciudad
	 */
	final QuickJComboBoxBuilder cityCbx = new QuickJComboBoxBuilder("city", "Value");
	
	/**
	 * Campo de identificacion
	 */
	final IdentificationCheckerField idFld = new IdentificationCheckerField(cityCbx, cancelarBtn);
	
	/**
	 * Campo de nombre de la empresa
	 */
	final TextCheckerField checkerField = new TextCheckerField(idFld, cancelarBtn);

	/**
	 * Crea la interfaz para mostrar la entrada del nuevo proveedor.
	 * 
	 * @since 1.0
	 */
	public QuickAdd_provider() {
		final JPanel buttonPanel = new JPanel();
		final JPanel inputPanel = new JPanel();

		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkerField.getText().length() > 0 &&
						idFld.getText().length() > 0) {
					String emptyStr = new String();
					String data[] = { Core.getLastID("provider", "ID"),
							checkerField.getText(), idFld.getText(),
							(String) cityCbx.getSelectedItem(), emptyStr,
							emptyStr, emptyStr, emptyStr, emptyStr, emptyStr,
							emptyStr, StoreSession.getUserName() };
					try {
						Core.newData("provider", data);
					} catch (DataFormatException e1) {
						e1.printStackTrace();
					}
					dispose();
				}
			}
		});
		buttonPanel.add(aceptarBtn);

		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPanel.add(cancelarBtn);

		checkerField.setPreferredSize(new Dimension(100, 20));
		idFld.setPreferredSize(new Dimension(100, 20));
		cityCbx.setPreferredSize(new Dimension(100, 20));

		JLabel checkerLbl = new JLabel("Nombre Empresa");
		JLabel idLbl = new JLabel("NIT/CC");
		JLabel cityLbl = new JLabel("Ciudad");
		checkerLbl.setPreferredSize(new Dimension(100, 20));
		idLbl.setPreferredSize(new Dimension(100, 20));
		cityLbl.setPreferredSize(new Dimension(100, 20));

		inputPanel.add(checkerLbl);
		inputPanel.add(checkerField);
		inputPanel.add(idLbl);
		inputPanel.add(idFld);
		inputPanel.add(cityLbl);
		inputPanel.add(cityCbx);

		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		setSize(250, 150);
		setTitle("Agregar proveedor...");
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
