package com.nova.stock.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.nova.look.modules.Modules;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.command.Command;
import com.nova.stock.log.StockCommand;

/**
 * Esta clase provee el menu de archivo del modulo, con sus respectivos comandos
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class StockMenuBar extends JMenuBar {

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
	 * Item de nuevo producto
	 */
	private JMenuItem nuevoItm = new JMenuItem("Nuevo Producto",
			GlobalConstants.NEW_ICON_SMALL);

	/**
	 * Item de nuevo producto
	 */
	private JMenuItem editarItm = new JMenuItem("Editar Producto",
			GlobalConstants.EDIT_ICON_SMALL);

	/**
	 * Item de borrar producto
	 */
	private JMenuItem borrarItm = new JMenuItem("Borrar Producto",
			GlobalConstants.REMOVE_ICON_SMALL);

	/**
	 * Item de comprobar kardex
	 */
	private JMenuItem comprobarKardexItm = new JMenuItem("Comprobar Kardex");

	/**
	 * Item de mostrar el kardex
	 */
	private JMenuItem kardexItm = new JMenuItem("Ver Kardex",
			GlobalConstants.KARDEX_ICON_SMALL);

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
	public StockMenuBar(Modules own) {
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

		nuevoItm.setToolTipText("Crear un producto nuevo");
		nuevoItm.setMnemonic('n');
		nuevoItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.add();
			}
		});
		edicionMnu.add(nuevoItm);

		editarItm.setToolTipText("Editar el producto seleccionado");
		editarItm.setMnemonic('e');
		editarItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.edit();
			}
		});
		edicionMnu.add(editarItm);

		borrarItm.setToolTipText("Borrar el producto seleccionado");
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

		edicionMnu.addSeparator();

		comprobarKardexItm.setToolTipText("Comprueba el kardex del inventario");
		comprobarKardexItm.setMnemonic('c');
		comprobarKardexItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((StockCommand) comando).checkAllKardex();
			}
		});
		edicionMnu.add(comprobarKardexItm);

		kardexItm.setToolTipText("Muestra el kardex del producto seleccionado");
		kardexItm.setMnemonic('k');
		kardexItm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((StockCommand) comando).kardex();
			}
		});
		edicionMnu.add(kardexItm);

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
