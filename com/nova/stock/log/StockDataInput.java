package com.nova.stock.log;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.nova.core.Core;
import com.nova.look.StoreSession;
import com.nova.look.modules.Modules;
import com.nova.parameter.GlobalConstants;
import com.nova.stock.gui.StockAbstractDataInput;
import com.nova.stock.kardex.log.KardexDataManager;

/**
 * Esta clase provee la interfaz para ingresar y editar un producto a la
 * aplicacion. Proviene de una mejora en el sistema de clases en el cual se hace
 * una generalizacion de la interfaz y se fusionan los metodo de agregar y
 * modificar en una sola clase
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 * @version 1.1 Actualizacion de la interfaz
 * @version 1.2 Generalizacion y optimizacion de la clase
 * @version 1.3 Caracteristicas especiales
 */
public class StockDataInput extends StockAbstractDataInput {

	/**
	 * Constructor de la clase, construye una interfaz de manejo de los datos
	 * necesarios para la identificacion de un proveedor frente a la aplicacion
	 * 
	 * @param own
	 *            Modulo Propietario
	 * @since 1.1
	 */
	public StockDataInput(Modules own) {
		owner = own;
		setLayout(new FlowLayout());

		JPanel panelA = new JPanel(new FlowLayout());
		JPanel panelB = new JPanel(new FlowLayout());

		panelA.setPreferredSize(new Dimension(210, 360));
		panelB.setPreferredSize(new Dimension(390, 360));

		panelA.add(makeImagePanel());
		panelB.add(makeDescriptionPanel());
		panelA.add(makeTypePanel());
		panelB.add(makeAmountsPanel());
		panelA.add(makeContinuePanel());
		panelB.add(makeValuePanel());

		add(panelA);
		add(panelB);
		add(makeButtonsPanel());

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				setFocusCodigo();
			}
		});
		setComponentsTooltip();
		setSize(650, 440);
		setLocation((GlobalConstants.SCREEN_SIZE_WIDTH - getWidth()) / 2,
				(GlobalConstants.SCREEN_SIZE_HEIGHT - getHeight()) / 2);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
	}

	/**
	 * Agrega a los componentes un tooltip explicando su funcion
	 * 
	 * @since 1.2
	 */
	private void setComponentsTooltip() {
		imagenLbl.setToolTipText("Imagen del Producto");
		examinarBtn.setToolTipText("Buscar una imagen para el producto");
		removerBtn.setToolTipText("Remover la imagen del producto");

		codigoFdl
				.setToolTipText("Codigo numerico que identifica al elemento (Codigo de Barras)");
		referenciaFdl
				.setToolTipText("Referencia interna o del proveedor del elemento");
		descripcionFdl.setToolTipText("Descripcion del elemento");
		marcaCbx.setToolTipText("Marca comercial del producto");
		proveedorCbx.setToolTipText("Proveedor del producto");
		caracteristicasFld
				.setToolTipText("Caracteristicas adicionales del producto");

		productoRbtn.setToolTipText("Seleccione si es un producto fisico");
		servicioRbtn.setToolTipText("Seleccione si es un servicio");

		categoriaCbx
				.setToolTipText("Elija la categoria a la que pertenece el elemento");
		empaqueCbx.setToolTipText("Elija el tipo de empaque del producto");
		unidadEmpaqueFld
				.setToolTipText("Elija las unidades por empaque del producto");
		cantidadDisponibleFld
				.setToolTipText("Cantidad disponible del producto");
		cantidadMinimaFld
				.setToolTipText("Cantidad minima del producto (para hacer pedido)");

		seguirAgregando
				.setToolTipText("Agrega este elemento y luego se prepara para agregar otro");
		noSeguirAgregando
				.setToolTipText("Agrega este elemento y vuelve al modulo");

		costoFld.setToolTipText("Costo neto del articulo");
		utilidadFld
				.setToolTipText("Porcentaje de utilidad de ganancia (entre 0 y 100)");
		impuestoCbx.setToolTipText("Impuesto que posee el articulo");
		precioFld.setToolTipText("Precio de venta antes de impuesto");

		limpiarBtn.setToolTipText("Limpiar todos los campos de la ventana");
		aceptarBtn.setToolTipText("Ingresar los datos y agregar elemento");
		cancelarBtn.setToolTipText("Cerrar la ventana y volver");
	}

	/**
	 * Comprueba las condiciones de validez de los datos
	 * 
	 * @return True si los datos son validos, False de lo contrario
	 * @since 1.2
	 */
	boolean validarCampos() {
		if (codigoFdl.isEnabled() && codigoFdl.getText().length() == 0)
			codigoFdl.requestFocus();
		else if (descripcionFdl.isEnabled()
				&& descripcionFdl.getText().length() == 0)
			descripcionFdl.requestFocus();
		else if (unidadEmpaqueFld.isEnabled()
				&& unidadEmpaqueFld.getText().length() == 0)
			unidadEmpaqueFld.requestFocus();
		else if (cantidadDisponibleFld.isEnabled()
				&& cantidadDisponibleFld.getText().length() == 0)
			cantidadDisponibleFld.requestFocus();
		else if (cantidadMinimaFld.isEnabled()
				&& cantidadMinimaFld.getText().length() == 0)
			cantidadMinimaFld.requestFocus();
		else if (costoFld.isEnabled() && costoFld.getText().length() == 0)
			costoFld.requestFocus();
		else if (utilidadFld.isEnabled() && utilidadFld.getText().length() == 0)
			utilidadFld.requestFocus();
		else if (precioFld.isEnabled() && precioFld.getText().length() == 0)
			precioFld.requestFocus();
		else if (cantidadMinimaFld.isEnabled()
				&& Integer.parseInt(cantidadMinimaFld.getText()) >= Integer
						.parseInt(cantidadDisponibleFld.getText()))
			cantidadMinimaFld.requestFocus();
		else
			return true;
		return false;
	}

	/**
	 * Muestra el dialogo para agregar un nuevo producto
	 * 
	 * @since 1.2
	 */
	public void showAddDialog() {
		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!validarCampos())
					return;
				String data[] = {
						codigoFdl.getText(),
						referenciaFdl.getText(),
						(String) categoriaCbx.getSelectedItem(),
						cantidadDisponibleFld.getText(),
						cantidadMinimaFld.getText(),
						descripcionFdl.getText(),
						productoRbtn.isSelected() ? productoRbtn.getText()
								: servicioRbtn.getText(), (String) marcaCbx.getSelectedItem(),
						(String) empaqueCbx.getSelectedItem(),
						unidadEmpaqueFld.getText(),
						(String) impuestoCbx.getSelectedItem(),
						utilidadFld.getText(), precioFld.getText(),
						(String) proveedorCbx.getSelectedItem(),
						costoFld.getText(), caracteristicasFld.getText(),
						rutaImagen, StoreSession.getUserName() };
				
				if (new KardexDataManager()
						.newRecord(StockDataInput.this, data)) {
					if (owner != null)
						owner.updateDataTable("Elemento "
								+ descripcionFdl.getText() + " agregado!!!");
					
					if (seguirAgregando.isSelected()) {
						clear();
						codigoFdl.setText(Core.getLastID("stock", "Codigo"));
					}
					else
						dispose();
				}
			}
		});

		setTitle("Agregar Producto...");
		setVisible(true);
	}

	/**
	 * Muestra el dialogo para modificar el producto seleccionado
	 * 
	 * @since 1.2
	 */
	public void showUpdateDialog() {
		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!validarCampos())
					return;
				String data[] = {
						codigoFdl.getText(),
						referenciaFdl.getText(),
						(String) categoriaCbx.getSelectedItem(),
						cantidadDisponibleFld.getText(),
						cantidadMinimaFld.getText(),
						descripcionFdl.getText(),
						productoRbtn.isSelected() ? productoRbtn.getText()
								: servicioRbtn.getText(), (String) marcaCbx.getSelectedItem(),
						(String) empaqueCbx.getSelectedItem(),
						unidadEmpaqueFld.getText(),
						(String) impuestoCbx.getSelectedItem(),
						utilidadFld.getText(), precioFld.getText(),
						(String) proveedorCbx.getSelectedItem(),
						costoFld.getText(), caracteristicasFld.getText(),
						rutaImagen, StoreSession.getUserName() };
				if (new KardexDataManager().updateProduct(StockDataInput.this,
						data)) {
					owner.updateDataTable("Elemento "
							+ descripcionFdl.getText() + " actualizado!!!");
					dispose();
				}
			}
		});

		ArrayList<String> allRowData = Core.getAllRowData(
				owner.getIdentifier(), "Codigo", owner.getTableSelectedID());
		JComponent[] components = getComponentsOrder();

		for (int i = 0; i < components.length; i++) {
			if (components[i] instanceof JTextField)
				((JTextField) components[i]).setText(allRowData.get(i));
			else if (components[i] instanceof JComboBox)
				((JComboBox) components[i]).setSelectedItem(allRowData.get(i));
			else if (components[i] instanceof JLabel) {
				rutaImagen = allRowData.get(i);
				if (rutaImagen.length() > 0)
					imagenLbl.setIcon(new ImageIcon(rutaImagen));
				else
					imagenLbl.setIcon(GlobalConstants.PRODUCT_DEFAULT_ICON);
			} else if (components[i] instanceof JRadioButton) {
				if (servicioRbtn.getText().equals(allRowData.get(i)))
					servicioRbtn.doClick();
			}
		}

		codigoFdl.setEnabled(false);
		cantidadDisponibleFld.setEnabled(false);
		costoFld.setEnabled(false);
		seguirAgregando.setEnabled(false);

		setTitle("Editar Producto...");
		setVisible(true);
	}

	/**
	 * Hace que codigo tenga el focus
	 * 
	 * @since 1.3
	 */
	public void setFocusCodigo() {
		codigoFdl.requestFocus();
	}

	/**
	 * Hace click en el boton cancelar
	 * 
	 * @since 1.3
	 */
	public void cancelarDoClick() {
		cancelarBtn.doClick();
	}

	/**
	 * Hace que la descripcion tenga el focus
	 * 
	 * @since 1.3
	 */
	public void setFocusDescripcion() {
		descripcionFdl.requestFocus();
	}
}