package com.nova.customer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.nova.look.modules.Modules;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.command.Command;
import com.nova.shared.internationalization.MessagesLoader;

/**
 * Esta clase representa la barra de herramientas de la aplicacion, muestra los
 * componentes y las funcionalidades mas importantes de la aplicacion
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 * @version 1.1 Desacoplamiento de funcionalidad buscar
 * @version 2.0 Internacionalizacion
 */
public class CustomerToolBar extends JToolBar {

	/**
	 * Representa el boton de nuevo cliente
	 */
	private JButton nuevoBtn = new JButton(GlobalConstants.NEW_ICON_BIG);

	/**
	 * Representa el boton de nuevo cliente
	 */
	private JButton modificarBtn = new JButton(GlobalConstants.EDIT_ICON_BIG);

	/**
	 * Representa el boton de borrar cliente
	 */
	private JButton borrarBtn = new JButton(GlobalConstants.REMOVE_ICON_BIG);

	/**
	 * Representa el boton de actualizar datos
	 */
	private JButton actualizarBtn = new JButton(
			GlobalConstants.REFRESH_ICON_BIG);

	/**
	 * Representa el boton de volver a los modulos
	 */
	private JButton salirBtn = new JButton(GlobalConstants.EXIT_ICON_BIG);

	/**
	 * Representa el comando de la aplicacion
	 */
	Command comando;

	/**
	 * Representa una copia de la instancia del frame principal
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
	public CustomerToolBar(Modules own) {
		owner = own;
		comando = owner.getCommand();

		nuevoBtn.setToolTipText(MessagesLoader.getString("Store.addNewClient"));
		nuevoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.add();
			}
		});
		add(nuevoBtn);

		modificarBtn.setToolTipText(MessagesLoader
				.getString("Store.modSelectedClient"));
		modificarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.edit();
			}
		});
		add(modificarBtn);

		borrarBtn.setToolTipText(MessagesLoader
				.getString("Store.delSelectedClient"));
		borrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.remove();
			}
		});
		add(borrarBtn);

		addSeparator();

		add(comando.getSearchButton());
		add(comando.getBackButton());
		add(comando.getNextButton());

		addSeparator();

		actualizarBtn.setToolTipText(MessagesLoader
				.getString("Store.updateData"));
		actualizarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.updateDataTable(null);
			}
		});
		add(actualizarBtn);

		add(Box.createHorizontalGlue());

		salirBtn
				.setToolTipText(MessagesLoader.getString("Store.backToModules"));
		salirBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.exit();
			}
		});
		add(salirBtn);

		setFloatable(false);
	}
}
