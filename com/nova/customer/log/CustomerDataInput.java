package com.nova.customer.log;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.nova.core.Core;
import com.nova.look.StoreSession;
import com.nova.look.modules.Modules;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.inputCheckers.IdentificationCheckerField;
import com.nova.shared.inputCheckers.NumberCheckerField;
import com.nova.shared.inputCheckers.TextCheckerField;
import com.nova.shared.quick.QuickJComboBoxBuilder;

/**
 * Esta clase provee la interfaz para ingresar y editar un cliente a la
 * aplicacion. Proviene de una mejora en el sistema de clases en el cual se hace
 * una generalizacion de la interfaz y se fusionan los metodo de agregar y
 * modificar en una sola clase
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 * @version 1.1 Actualizacion de la interfaz
 * @version 1.2 Generalizacion y optimizacion de la clase
 * @version 1.3 Optimizacion de la carga de datos para editar
 */
class CustomerDataInput extends JDialog {

	/**
	 * Representa la entrada de nombre
	 */
	TextCheckerField nombreFld;

	/**
	 * Representa la entrada de NIT
	 */
	IdentificationCheckerField nitFld;

	/**
	 * Representa la entrada de ciudad
	 */
	QuickJComboBoxBuilder ciudadCbx;

	/**
	 * Representa la entrada de telefono
	 */
	NumberCheckerField telefonoFld;

	/**
	 * Representa la entrada de fax
	 */
	NumberCheckerField faxFld;

	/**
	 * Representa la entrada de movil
	 */
	NumberCheckerField movilFld;

	/**
	 * Representa la entrada de direccion
	 */
	TextCheckerField direccionFld;

	/**
	 * Representa el boton de aceptar
	 */
	private final JButton aceptarBtn = new JButton("Aceptar");

	/**
	 * Representa el boton de cancelar
	 */
	private final JButton cancelarBtn = new JButton("Cancelar");

	/**
	 * Representa el modulo propietario
	 */
	final Modules owner;

	/**
	 * Constructor de la clase, construye una interfaz de manejo de los datos
	 * necesarios para la identificacion de un cliente frente a la aplicacion
	 * 
	 * @param own
	 *            Modulo Propietario
	 * @since 1.1
	 */
	public CustomerDataInput(Modules own) {
		owner = own;

		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		nombreFld = new TextCheckerField(aceptarBtn, cancelarBtn);
		nitFld = new IdentificationCheckerField(aceptarBtn, cancelarBtn);
		ciudadCbx = new QuickJComboBoxBuilder("city", "Value");
		telefonoFld = new NumberCheckerField(10, aceptarBtn, cancelarBtn);
		faxFld = new NumberCheckerField(10, aceptarBtn, cancelarBtn);
		movilFld = new NumberCheckerField(10, aceptarBtn, cancelarBtn);
		direccionFld = new TextCheckerField(aceptarBtn, cancelarBtn);

		nombreFld.setPreferredSize(new Dimension(300, 20));
		nitFld.setPreferredSize(new Dimension(90, 20));
		ciudadCbx.setPreferredSize(new Dimension(150, 20));
		telefonoFld.setPreferredSize(new Dimension(80, 20));
		faxFld.setPreferredSize(new Dimension(80, 20));
		movilFld.setPreferredSize(new Dimension(80, 20));
		direccionFld.setPreferredSize(new Dimension(300, 20));

		JPanel upPanel = new JPanel();
		JPanel midPanel = new JPanel();
		JPanel lowPanel = new JPanel();

		upPanel.setBorder(BorderFactory.createTitledBorder("Cliente"));
		upPanel.setPreferredSize(new Dimension(390, 90));
		upPanel.add(new JLabel("Nombre"));
		upPanel.add(nombreFld);
		upPanel.add(new JLabel("NIT/CC"));
		upPanel.add(nitFld);
		upPanel.add(new JLabel("Ciudad"));
		upPanel.add(ciudadCbx);

		midPanel.setBorder(BorderFactory.createTitledBorder("Contacto"));
		midPanel.setPreferredSize(new Dimension(390, 90));
		midPanel.add(new JLabel("Telefono"));
		midPanel.add(telefonoFld);
		midPanel.add(new JLabel("Fax"));
		midPanel.add(faxFld);
		midPanel.add(new JLabel("Movil"));
		midPanel.add(movilFld);
		midPanel.add(new JLabel("Direccion"));
		midPanel.add(direccionFld);

		lowPanel.add(aceptarBtn);
		lowPanel.add(cancelarBtn);

		add(upPanel, BorderLayout.NORTH);
		add(midPanel, BorderLayout.CENTER);
		add(lowPanel, BorderLayout.SOUTH);

		pack();
		setComponentsTooltip();
		setLocation((GlobalConstants.SCREEN_SIZE_WIDTH - getWidth()) / 2,
				(GlobalConstants.SCREEN_SIZE_HEIGHT - getHeight()) / 2);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
	}

	/**
	 * Agrega a los componentes un tooltip explicando su funcion
	 * 
	 * @since 1.2
	 */
	private void setComponentsTooltip() {
		nombreFld
				.setToolTipText("Nombre de la persona o empresa que es cliente de la organizacion");
		nitFld.setToolTipText("NIT, Cedula o identificacion");
		ciudadCbx
				.setToolTipText("Ciudad donde reside o tiene su centro de operaciones");
		telefonoFld.setToolTipText("Telefono de contacto");
		faxFld.setToolTipText("Fax o Telefax");
		movilFld.setToolTipText("Celular del contacto principal o cliente");
		direccionFld.setToolTipText("Direccion fisica del cliente");
		aceptarBtn.setToolTipText("Ingresar datos y volver");
		cancelarBtn.setToolTipText("Descartar datos y volver");
	}

	/**
	 * Comprueba las condiciones de validez de los datos
	 * 
	 * @return True si los datos son validos, False de lo contrario
	 * @since 1.2
	 */
	boolean validarCampos() {
		if (nombreFld.getText().length() == 0)
			nombreFld.requestFocus();
		else if (nitFld.getText().length() == 0)
			nitFld.requestFocus();
		else
			return true;
		return false;
	}

	/**
	 * Muestra el dialogo para agregar un nuevo cliente
	 * 
	 * @since 1.2
	 */
	public void showAddDialog() {
		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validarCampos()) {
					String data[] = { Core.getLastID("customer", "ID"), nombreFld.getText(),
							nitFld.getText(),
							(String) ciudadCbx.getSelectedItem(),
							telefonoFld.getText(), faxFld.getText(),
							movilFld.getText(), direccionFld.getText(),
							StoreSession.getUserName() };
					try {
						Core.newData(owner.getIdentifier(), data);
						owner.updateDataTable("Cliente " + nombreFld.getText()
								+ " agregado!!!");
					} catch (DataFormatException e1) {
						e1.printStackTrace();
					}
					dispose();
				}
			}
		});

		setTitle("Agregar Cliente...");
		setVisible(true);
	}

	/**
	 * Muestra el dialogo para modificar el cliente seleccionado
	 * 
	 * @since 1.2
	 */
	public void showUpdateDialog() {
		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validarCampos()) {
					String data[] = { owner.getTableSelectedID(),
							nombreFld.getText(), nitFld.getText(),
							(String) ciudadCbx.getSelectedItem(),
							telefonoFld.getText(), faxFld.getText(),
							movilFld.getText(), direccionFld.getText(),
							StoreSession.getUserName() };
					try {
						Core.updateData(owner.getIdentifier(), data);
						owner.updateDataTable("Cliente " + nombreFld.getText()
								+ " actualizado!!!");
					} catch (DataFormatException e1) {
						e1.printStackTrace();
					}
					dispose();
				}
			}
		});

		ArrayList<String> allRowData = Core.getAllRowData(
				owner.getIdentifier(), "ID", owner.getTableSelectedID());
		JComponent[] components = getComponentsOrder();

		for (int i = 0; i < components.length; i++) {
			if (components[i] instanceof JTextField)
				((JTextField) components[i]).setText(allRowData.get(i));
			else if (components[i] instanceof JComboBox)
				((JComboBox) components[i]).setSelectedItem(allRowData.get(i));
		}

		setTitle("Editar Cliente...");
		setVisible(true);
	}

	/**
	 * Hace una lista de los componentes en el orden que se encuentran el la DB
	 * y lo retorna
	 * 
	 * @return Componentes ordenados en un arreglo
	 * @since 1.3
	 */
	private JComponent[] getComponentsOrder() {
		JComponent temp[] = { null, nombreFld, nitFld, ciudadCbx, telefonoFld,
				faxFld, movilFld, direccionFld };
		return temp;
	}
}