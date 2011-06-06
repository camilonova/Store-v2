package com.nova.provider.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.nova.look.modules.Modules;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.command.Command;

/**
 * Esta clase provee el menu de archivo del modulo, con sus respectivos comandos
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class ProviderMenuBar extends JMenuBar {

	/**
	 * Menu archivo
	 */
	private JMenu archivoMnu = new JMenu("Archivo");

	/**
	 * Item de salir
	 */
	private JMenuItem salirItm = new JMenuItem("Salir",
			GlobalConstants.EXIT_ICON_SMALL);

	/**
	 * Menu de edicion
	 */
	private JMenu edicionMnu = new JMenu("Edicion");

	/**
	 * Item de nuevo cliente
	 */
	private JMenuItem nuevoItm = new JMenuItem("Nuevo Proveedor",
			GlobalConstants.NEW_ICON_SMALL);

	/**
	 * Item de nuevo cliente
	 */
	private JMenuItem editarItm = new JMenuItem("Editar Cliente",
			GlobalConstants.EDIT_ICON_SMALL);

	/**
	 * Item de borrar cliente
	 */
	private JMenuItem borrarItm = new JMenuItem("Borrar Proveedor",
			GlobalConstants.REMOVE_ICON_SMALL);

	/**
	 * Menu de ayuda
	 */
	private JMenu ayudaMnu = new JMenu("Ayuda");

	/**
	 * Item de ayuda
	 */
	private JMenuItem ayudaItm = new JMenuItem("Ayuda",
			GlobalConstants.HELP_ICON_SMALL);

	/**
	 * Item de creditos
	 */
	private JMenuItem creditosItm = new JMenuItem("Creditos",
			GlobalConstants.CREDITS_ICON_SMALL);

	/**
	 * Modulo propietario
	 */
	Modules owner;

	/**
	 * Comando del modulo
	 */
	Command comando;

	/**
	 * Crea el menu y le agrega los comandos a los items
	 * 
	 * @param own
	 *            Modulo propietario
	 */
	public ProviderMenuBar(Modules own) {
		owner = own;
		comando = owner.getCommand();

		archivoMnu.addSeparator();

		salirItm.setToolTipText("Salir a modulos");
		salirItm.setMnemonic('s');
		salirItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.exit();
			}
		});
		archivoMnu.add(salirItm);

		nuevoItm.setToolTipText("Crear un proveedor nuevo");
		nuevoItm.setMnemonic('n');
		nuevoItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.add();
			}
		});
		edicionMnu.add(nuevoItm);

		editarItm.setToolTipText("Editar el proveedor seleccionado");
		editarItm.setMnemonic('e');
		editarItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.edit();
			}
		});
		edicionMnu.add(editarItm);

		borrarItm.setToolTipText("Borrar el proveedor seleccionado");
		borrarItm.setMnemonic('b');
		borrarItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.remove();
			}
		});
		edicionMnu.add(borrarItm);

		edicionMnu.addSeparator();

		edicionMnu.add(comando.getSearchItem());
		edicionMnu.add(comando.getBackItem());
		edicionMnu.add(comando.getNextItem());

		ayudaItm.setToolTipText("Muestra la ayuda del modulo");
		ayudaItm.setMnemonic('a');
		ayudaItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Implementar ayuda del modulo
			}
		});
		ayudaMnu.add(ayudaItm);

		creditosItm.setToolTipText("Muestra los creditos de la aplicacion");
		creditosItm.setMnemonic('c');
		creditosItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Implementar creditos
			}
		});
		ayudaMnu.add(creditosItm);

		archivoMnu.setMnemonic('a');
		edicionMnu.setMnemonic('e');
		ayudaMnu.setMnemonic('y');

		add(archivoMnu);
		add(edicionMnu);
		add(ayudaMnu);
	}
}
