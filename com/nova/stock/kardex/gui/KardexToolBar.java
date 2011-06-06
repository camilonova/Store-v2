package com.nova.stock.kardex.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.nova.look.submodules.KardexGUI;
import com.nova.parameter.GlobalConstants;
import com.nova.stock.kardex.log.KardexCommand;

/**
 * Barra de herramientas del modulo
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class KardexToolBar extends JToolBar {

	/**
	 * Representa el boton de ajustar cantidades
	 */
	private JButton ajustarBtn = new JButton(GlobalConstants.NEW_ICON_BIG);

	/**
	 * Representa el boton de modificar registro
	 */
	private JButton modificarBtn = new JButton(GlobalConstants.EDIT_ICON_BIG);

	/**
	 * Representa el boton de verificar registro
	 */
	private JButton verificarBtn = new JButton(GlobalConstants.CHECK_ICON_BIG);

	/**
	 * Representa el boton de volver a inventario
	 */
	private JButton volverBtn = new JButton(GlobalConstants.EXIT_ICON_BIG);

	/**
	 * Modulo principal
	 */
	final KardexGUI owner;

	/**
	 * Comando del modulo
	 */
	KardexCommand comando;

	/**
	 * Constructor, crea la barra de herramientas y agrega los botones
	 * 
	 * @param frame
	 *            Modulo principal
	 * @since 1.0
	 */
	public KardexToolBar(KardexGUI frame) {
		owner = frame;
		comando = (KardexCommand) owner.getCommand();

		ajustarBtn.setToolTipText("Ajustar cantidades del producto");
		ajustarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.add();
			}
		});
		add(ajustarBtn);

		modificarBtn.setToolTipText("Modificar el registro seleccionado");
		modificarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.edit();
			}
		});
		add(modificarBtn);

		addSeparator();

		verificarBtn.setToolTipText("Verificar el articulo seleccionado");
		verificarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comando.checkKardexProduct();
			}
		});
		add(verificarBtn);

		addSeparator();

		add(comando.getSearchButton());
		add(comando.getBackButton());
		add(comando.getNextButton());

		add(Box.createHorizontalGlue());

		volverBtn.setToolTipText("Volver a inventario");
		volverBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.exit();
			}
		});
		add(volverBtn);

		setFloatable(false);
	}
}
