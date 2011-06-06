package com.nova.quote.gui;

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
public class QuoteMenuBar extends JMenuBar {

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
	 * Item de nueva factura
	 */
	private JMenuItem nuevoItm = new JMenuItem("Nueva Cotizacion",
			GlobalConstants.NEW_ICON_BIG);

	/**
	 * Item de ver factura
	 */
	private JMenuItem verItm = new JMenuItem("Ver Cotizacion",
			GlobalConstants.SEE_ICON_BIG);

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
	public QuoteMenuBar(Modules own) {
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

		nuevoItm.setToolTipText("Crear una nueva cotizacion");
		nuevoItm.setMnemonic('c');
		nuevoItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.add();
			}
		});
		edicionMnu.add(nuevoItm);

		verItm.setToolTipText("Ver la cotizacion seleccionada");
		verItm.setMnemonic('v');
		verItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.see();
			}
		});
		edicionMnu.add(verItm);

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
