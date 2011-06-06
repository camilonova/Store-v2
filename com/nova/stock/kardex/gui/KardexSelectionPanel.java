package com.nova.stock.kardex.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nova.core.Core;
import com.nova.look.submodules.SubModules;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.form.gui.Form;
import com.nova.shared.inputCheckers.MoneyCheckerField;
import com.nova.shared.inputCheckers.NumberCheckerField;
import com.nova.shared.submodule.StoreSubModuleTablePanel;

/**
 * Esta clase, provee de una interfaz para la seleccion y muestra en tiempo real
 * de los procesos del kardex de un articulo
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class KardexSelectionPanel extends JPanel {

	/**
	 * Representa el campo de codigo
	 */
	NumberCheckerField codigoFld;

	/**
	 * Representa el campo de descripcion
	 */
	JComboBox descripcionCbx;

	/**
	 * Representa el campo de cantidad
	 */
	private NumberCheckerField cantidadFld;

	/**
	 * Representa el campo de cantidad minima
	 */
	private NumberCheckerField minimoFld;

	/**
	 * Representa el campo de costo
	 */
	private MoneyCheckerField costoFld;

	/**
	 * Representa el campo de precio
	 */
	private MoneyCheckerField precioFld;

	/**
	 * Representa el subModulo
	 */
	private final SubModules owner;

	/**
	 * Representa el panel donde se muestran los datos
	 */
	private final StoreSubModuleTablePanel panel;

	/**
	 * Construye un panel donde se muestra alguna informacion acerca del
	 * articulo seleccionado y permite seleccionar otro y cargar los datos en
	 * tiempo real
	 * 
	 * @param kardexGUI
	 *            SubModulo
	 * @param kardexPanel
	 *            Panel donde se muestran los datos
	 * @since 1.0
	 */
	public KardexSelectionPanel(SubModules kardexGUI,
			StoreSubModuleTablePanel kardexPanel) {
		owner = kardexGUI;
		panel = kardexPanel;

		codigoFld = new NumberCheckerField(GlobalConstants.CODE_SIZE);
		descripcionCbx = new JComboBox(Form.getFormDescriptions());
		cantidadFld = new NumberCheckerField();
		minimoFld = new NumberCheckerField();
		costoFld = new MoneyCheckerField();
		precioFld = new MoneyCheckerField();

		loadData(kardexGUI.getItemToShow());
		codigoFld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData(codigoFld.getText());
			}
		});
		descripcionCbx.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String selectedItem[] = ((String) descripcionCbx.getSelectedItem()).split(" .:. ");
					String sqlSentence = "SELECT `Codigo` FROM `stock` " +
							"WHERE `Descripcion` = '"+selectedItem[0]+"' AND " +
							"`Marca` = '"+selectedItem[1]+"'";
					ResultSet resultSet = Core.doSelect(sqlSentence);
					try {
						if(resultSet.next())
							loadData(resultSet.getString("Codigo"));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		cantidadFld.setEditable(false);
		minimoFld.setEditable(false);
		costoFld.setEditable(false);
		precioFld.setEditable(false);

		codigoFld.setPreferredSize(new Dimension(120, 20));
		descripcionCbx.setPreferredSize(new Dimension(330, 20));
		cantidadFld.setPreferredSize(new Dimension(80, 20));
		minimoFld.setPreferredSize(new Dimension(80, 20));
		costoFld.setPreferredSize(new Dimension(80, 20));
		precioFld.setPreferredSize(new Dimension(80, 20));

		add(new JLabel("Codigo"));
		add(codigoFld);
		add(new JLabel("Descripcion"));
		add(descripcionCbx);
		add(new JLabel("Cantidad"));
		add(cantidadFld);
		add(new JLabel("Minimo"));
		add(minimoFld);
		add(new JLabel("Costo"));
		add(costoFld);
		add(new JLabel("Precio"));
		add(precioFld);

		setPreferredSize(new Dimension(590, 50));
	}

	/**
	 * Carga los datos del articulo seleccionado en la interfaz
	 * 
	 * @param itemID
	 *            Codigo del articulo seleccionado
	 * @since 1.0
	 */
	public void loadData(String itemID) {
		if (Core.isRegisteredData("stock", "Codigo", itemID)) {
			String selectedItem = Core.getDataAt("stock", "Codigo", itemID,
					"Descripcion") + " .:. " +
					Core.getDataAt("stock", "Codigo", itemID, "Marca");
			String message = (String) descripcionCbx.getSelectedItem();

			codigoFld.setText(itemID);
			descripcionCbx.setSelectedItem(selectedItem);
			cantidadFld.setText(Core.getDataAt("stock", "Codigo", itemID,
					"Cantidad"));
			minimoFld.setText(Core.getDataAt("stock", "Codigo", itemID,
					"Minimo"));
			costoFld
					.setText(Core.getDataAt("stock", "Codigo", itemID, "Costo"));
			precioFld.setText(Core.getDataAt("stock", "Codigo", itemID,
					"Precio Venta"));
			panel.updateData(itemID);

			((JDialog) owner).setTitle("Kardex del producto " + message);
			owner.setStatusBarText("Registros del articulo \"" + message
					+ "\" cargados");
		} else {
			owner.setStatusBarText("El codigo ingresado, no existe!!!");
			Toolkit.getDefaultToolkit().beep();
		}
		codigoFld.selectAll();
	}
}
