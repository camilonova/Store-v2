package com.nova.look;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.nova.core.Core;
import com.nova.look.modules.PurchaseGUI;
import com.nova.look.modules.QuoteGUI;
import com.nova.look.modules.SaleGUI;
import com.nova.parameter.GlobalConstants;

/**
 * Representa el panel izquierdo de los componentes y provee metodos para su
 * acceso a los mismos
 * 
 * @author Camilo Nova
 * @version 1.0 Imagenes en los botones
 * @version 1.1 Permisos
 */
public class LeftToolBar extends JToolBar {

	/**
	 * Representa el boton de acceso al modulo de ventas
	 */
	private JButton ventasBtn = new JButton(GlobalConstants.SALES_ICON);

	/**
	 * Representa el boton de acceso al modulo de cotizaciones
	 */
	private JButton cotizacionesBtn = new JButton(GlobalConstants.QUOTE_ICON);

	/**
	 * Representa el boton de acceso al modulo de compras
	 */
	private JButton comprasBtn = new JButton(GlobalConstants.PURCHASE_ICON);

	/**
	 * Representa el boton de acceso al modulo de parametros
	 */
	private JButton parametrosBtn = new JButton(GlobalConstants.PARAMETER_ICON);

	/**
	 * Crea la barra de herramientas con los modulos correspondientes a los
	 * permisos del usuario
	 * 
	 * @param owner
	 *            Frame principal
	 * @since 1.0
	 */
	public LeftToolBar(final Store owner) {
		ventasBtn.setToolTipText("Ir al modulo de ventas");
		cotizacionesBtn.setToolTipText("Ir al modulo de cotizaciones");
		comprasBtn.setToolTipText("Ir al modulo de compras");
		parametrosBtn.setToolTipText("Ir al modulo de parametros");
		
		ventasBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaleGUI modulo = new SaleGUI(owner);
				owner.setModulo(modulo, modulo.getMenuBar());
			}
		});
		add(ventasBtn);

		cotizacionesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QuoteGUI modulo = new QuoteGUI(owner);
				owner.setModulo(modulo, modulo.getMenuBar());
			}
		});
		add(cotizacionesBtn);

		comprasBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PurchaseGUI modulo = new PurchaseGUI(owner);
				owner.setModulo(modulo, modulo.getMenuBar());
			}
		});
		add(comprasBtn);

		parametrosBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Parametros

			}
		});
		add(parametrosBtn);

		setPermission();

		setOrientation(VERTICAL);
		setFloatable(false);
	}

	/**
	 * Selecciona los modulos a los cuales el usuario puede acceder dependiendo
	 * los permisos que tenga registrados
	 * 
	 * @since 1.1
	 */
	private void setPermission() {
		String ID = StoreSession.getUserID();

		if (!Core.getPermision(ID, "Sale"))
			ventasBtn.setEnabled(false);
		if (!Core.getPermision(ID, "Quote"))
			cotizacionesBtn.setEnabled(false);
		if (!Core.getPermision(ID, "Purchase"))
			comprasBtn.setEnabled(false);
		if (!Core.getPermision(ID, "Parameter"))
			parametrosBtn.setEnabled(false);
	}
}
