package com.nova.look;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.nova.core.Core;
import com.nova.look.modules.CustomerGUI;
import com.nova.look.modules.ProviderGUI;
import com.nova.look.modules.StockGUI;
import com.nova.parameter.GlobalConstants;

/**
 * Representa el panel derecho de los componentes y provee metodos para su
 * acceso a los mismos
 * 
 * @author Camilo Nova
 * @version 1.0 Imagenes en los botones
 * @version 1.1 Permisos
 * @version 1.2 Modulo Clientes
 * @version 1.3 Modulo Poveedores
 * @version 1.4 Modulo Inventario
 */
public class RightToolBar extends JToolBar {

	/**
	 * Representa el boton de acceso al modulo de clientes
	 */
	private JButton clientesBtn = new JButton(GlobalConstants.CUSTOMER_ICON);

	/**
	 * Representa el boton de acceso al modulo de proveedores
	 */
	private JButton proveedoresBtn = new JButton(GlobalConstants.PROVIDER_ICON);

	/**
	 * Representa el boton de acceso al modulo de inventarios
	 */
	private JButton inventariosBtn = new JButton(GlobalConstants.STOCK_ICON);

	/**
	 * Representa el boton de acceso al modulo de reportes
	 */
	private JButton reportesBtn = new JButton(GlobalConstants.DATA_ICON);

	/**
	 * Crea la barra de herramientas con los modulos correspondientes a los
	 * permisos del usuario
	 * 
	 * @param owner
	 *            Frame principal
	 * @since 1.0
	 */
	public RightToolBar(final Store owner) {
		clientesBtn.setToolTipText("Ir al modulo de clientes");
		proveedoresBtn.setToolTipText("Ir al modulo de proveedores");
		inventariosBtn.setToolTipText("Ir al modulo de inventarios");
		reportesBtn.setToolTipText("Ir al modulo de reportes");

		clientesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerGUI modulo = new CustomerGUI(owner);
				owner.setModulo(modulo, modulo.getMenuBar());
			}
		});
		add(clientesBtn);

		proveedoresBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProviderGUI modulo = new ProviderGUI(owner);
				owner.setModulo(modulo, modulo.getMenuBar());
			}
		});
		add(proveedoresBtn);

		inventariosBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StockGUI modulo = new StockGUI(owner);
				owner.setModulo(modulo, modulo.getMenuBar());
			}
		});
		add(inventariosBtn);

		reportesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Reportes

			}
		});
		add(reportesBtn);

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

		if (!Core.getPermision(ID, "Customer"))
			clientesBtn.setEnabled(false);
		if (!Core.getPermision(ID, "Provider"))
			proveedoresBtn.setEnabled(false);
		if (!Core.getPermision(ID, "Stock"))
			inventariosBtn.setEnabled(false);
		if (!Core.getPermision(ID, "Data"))
			reportesBtn.setEnabled(false);
	}
}
