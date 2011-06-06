package com.nova.stock.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.nova.look.modules.Modules;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.command.Command;
import com.nova.stock.log.StockCommand;

/**
 * Esta clase representa la barra de herramientas de la aplicacion, muestra los
 * componentes y las funcionalidades mas importantes de la aplicacion
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 * @version 1.1 Desacoplamiento de funcionalidad buscar
 */
public class StockToolBar extends JToolBar {

	/**
	 * Representa el boton de nuevo producto
	 */
	private JButton nuevoBtn = new JButton(GlobalConstants.NEW_ICON_BIG);

	/**
	 * Representa el boton de nuevo producto
	 */
	private JButton modificarBtn = new JButton(GlobalConstants.EDIT_ICON_BIG);

	/**
	 * Representa el boton de borrar producto
	 */
	private JButton borrarBtn = new JButton(GlobalConstants.REMOVE_ICON_BIG);

	/**
	 * Represanta el boton de kardex
	 */
	private JButton kardexBtn = new JButton(GlobalConstants.KARDEX_ICON_BIG);

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
	public StockToolBar(Modules own) {
		owner = own;
		comando = owner.getCommand();

		nuevoBtn.setToolTipText("Agregar un nuevo producto");
		nuevoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.add();
			}
		});
		add(nuevoBtn);

		modificarBtn.setToolTipText("Modificar el producto seleccionado");
		modificarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.edit();
			}
		});
		add(modificarBtn);

		borrarBtn.setToolTipText("Borrar el producto selecionado");
		borrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.remove();
			}
		});
		add(borrarBtn);

		addSeparator();

		kardexBtn.setToolTipText("Mostrar el kardex del producto seleccionado");
		kardexBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((StockCommand) comando).kardex();
			}
		});
		add(kardexBtn);

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
