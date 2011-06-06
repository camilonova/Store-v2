package com.nova.purchase.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.nova.look.modules.Modules;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.command.Command;

/**
 * Esta clase representa la barra de herramientas de la aplicacion, muestra los
 * componentes y las funcionalidades mas importantes de la aplicacion
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class PurchaseToolBar extends JToolBar {

	/**
	 * Boton de crear factura
	 */
	private JButton nuevoBtn = new JButton(GlobalConstants.NEW_ICON_BIG);

	/**
	 * Boton de ver factura
	 */
	private JButton verBtn = new JButton(GlobalConstants.SEE_ICON_BIG);

	/**
	 * Boton de actualizar datos
	 */
	private JButton actualizarBtn = new JButton(
			GlobalConstants.REFRESH_ICON_BIG);

	/**
	 * Boton de volver a los modulos
	 */
	private JButton salirBtn = new JButton(GlobalConstants.EXIT_ICON_BIG);

	/**
	 * Comando del modulo
	 */
	Command comando;

	/**
	 * Frame Principal
	 */
	Modules owner;

	/**
	 * Crea una barra de herramientas, con los comandos generales de la
	 * aplicacion
	 * 
	 * @param own
	 *            Modulo propietario
	 * @since 1.0
	 */
	public PurchaseToolBar(Modules own) {
		owner = own;
		comando = owner.getCommand();

		nuevoBtn.setToolTipText("Crea una nueva compra");
		nuevoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.add();
			}
		});
		add(nuevoBtn);

		verBtn.setToolTipText("Ver una compra");
		verBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.see();
			}
		});
		add(verBtn);

		addSeparator();

		add(comando.getSearchButton());
		add(comando.getBackButton());
		add(comando.getNextButton());

		addSeparator();

		actualizarBtn.setToolTipText("Actualizar datos");
		actualizarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.updateDataTable(null);
			}
		});
		add(actualizarBtn);

		add(Box.createHorizontalGlue());

		salirBtn.setToolTipText("Volver a modulos");
		salirBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.exit();
			}
		});
		add(salirBtn);

		setFloatable(false);
	}
}
