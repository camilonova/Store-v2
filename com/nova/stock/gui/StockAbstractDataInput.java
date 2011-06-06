package com.nova.stock.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.nova.core.Core;
import com.nova.look.modules.Modules;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.inputCheckers.MoneyCheckerField;
import com.nova.shared.inputCheckers.NumberCheckerField;
import com.nova.shared.inputCheckers.PercentCheckerField;
import com.nova.shared.inputCheckers.TextCheckerField;
import com.nova.shared.math.StoreMath;
import com.nova.shared.quick.QuickJComboBoxBuilder;

/**
 * Esta clase se encarga de proveer los metodos de creacion de la interfaz para
 * la clase <i>StockDataInput</i>, esto debido a que la interfaz es muy
 * compleja y requiere de dividir sus responsabilidades en clases delegadas.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 * @version 1.1 Carga dinamica de los datos al editar
 */
public abstract class StockAbstractDataInput extends JDialog {

	/**
	 * Representa el area donde se muestra la imagen
	 */
	protected JLabel imagenLbl;

	/**
	 * Representa el boton para buscar una imagen
	 */
	protected JButton examinarBtn;

	/**
	 * Representa el boton de remover la imagen
	 */
	protected JButton removerBtn;

	/**
	 * Representa el grupo de elecciones de "tipo de producto"
	 */
	protected ButtonGroup tipoGrp;

	/**
	 * Representa la seleccion de producto
	 */
	public JRadioButton productoRbtn;

	/**
	 * Representa la seleccion de servicio
	 */
	public JRadioButton servicioRbtn;

	/**
	 * Representa el grupo de elecciones de "al terminar"
	 */
	protected ButtonGroup continuarGrp;

	/**
	 * Representa la seleccion de continuar agregando
	 */
	public JRadioButton seguirAgregando;

	/**
	 * Representa la seleccion de terminar de agregar
	 */
	protected JRadioButton noSeguirAgregando;

	/**
	 * Representa el codigo del producto
	 */
	public NumberCheckerField codigoFdl;

	/**
	 * Representa la referencia del producto
	 */
	public TextCheckerField referenciaFdl;

	/**
	 * Representa la descripcion del producto
	 */
	public TextCheckerField descripcionFdl;

	/**
	 * Representa la marca del producto
	 */
	public QuickJComboBoxBuilder marcaCbx;

	/**
	 * Representa el proveedor del producto
	 */
	public QuickJComboBoxBuilder proveedorCbx;

	/**
	 * Representa las caracteristicas adicionales del producto
	 */
	public TextCheckerField caracteristicasFld;

	/**
	 * Representa el tipo de empaque del producto
	 */
	public QuickJComboBoxBuilder empaqueCbx;

	/**
	 * Representa las unidades por empaque del producto
	 */
	public NumberCheckerField unidadEmpaqueFld;

	/**
	 * Representa la categoria a la que pertenece
	 */
	public QuickJComboBoxBuilder categoriaCbx;

	/**
	 * Representa la cantidad disponible del producto
	 */
	public NumberCheckerField cantidadDisponibleFld;

	/**
	 * Representa la cantidad minima del producto
	 */
	public NumberCheckerField cantidadMinimaFld;

	/**
	 * Representa el costo del producto
	 */
	public MoneyCheckerField costoFld;

	/**
	 * Representa el porcentaje de utilidad
	 */
	public PercentCheckerField utilidadFld;

	/**
	 * Representa el impuesto del producto
	 */
	public QuickJComboBoxBuilder impuestoCbx;

	/**
	 * Representa el precio publico del producto
	 */
	public MoneyCheckerField precioFld;

	/**
	 * Representa el boton de limpiar formulario
	 */
	protected JButton limpiarBtn = new JButton("Limpiar");

	/**
	 * Representa el boton de aceptar
	 */
	protected JButton aceptarBtn = new JButton("Aceptar");

	/**
	 * Representa el boton de cancelar
	 */
	protected JButton cancelarBtn = new JButton("Cancelar");

	/**
	 * Representa el ID del cliente seleccionado
	 */
	protected String ProductID;

	/**
	 * Representa la ruta de la imagen
	 */
	public String rutaImagen = new String();

	/**
	 * Representa el modulo propietario
	 */
	public Modules owner;

	/**
	 * Crea el panel donde se muestra la imagen del producto
	 * 
	 * @return Panel de la imagen del producto
	 * @since 1.0
	 */
	protected JPanel makeImagePanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(210, 190));
		panel
				.setBorder(BorderFactory
						.createTitledBorder("Imagen del Producto"));

		imagenLbl = new JLabel(GlobalConstants.PRODUCT_DEFAULT_ICON);
		imagenLbl.setPreferredSize(new Dimension(120, 120));

		examinarBtn = new JButton("Examinar...");
		examinarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(rutaImagen);
				if (chooser.showOpenDialog(examinarBtn) == JFileChooser.APPROVE_OPTION) {
					rutaImagen = chooser.getSelectedFile().toURI().getPath();
					imagenLbl.setIcon(new ImageIcon(rutaImagen));
				}
			}
		});

		removerBtn = new JButton("Remover");
		removerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rutaImagen = new String();
				imagenLbl.setIcon(GlobalConstants.PRODUCT_DEFAULT_ICON);
			}
		});

		JPanel panel2 = new JPanel();
		panel2.add(examinarBtn);
		panel2.add(removerBtn);

		panel.add(imagenLbl, BorderLayout.CENTER);
		panel.add(panel2, BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * Crea el panel de tipo de producto
	 * 
	 * @return Panel de tipo de producto
	 * @since 1.0
	 */
	protected JPanel makeTypePanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Tipo"));
		panel.setPreferredSize(new Dimension(210, 60));

		tipoGrp = new ButtonGroup();
		productoRbtn = new JRadioButton("Producto", true);
		productoRbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				examinarBtn.setEnabled(true);
				removerBtn.setEnabled(true);
				cantidadDisponibleFld.setEnabled(true);
				cantidadMinimaFld.setEnabled(true);
				marcaCbx.setEnabled(true);
				empaqueCbx.setEnabled(true);
				unidadEmpaqueFld.setEnabled(true);
				proveedorCbx.setEnabled(true);
				costoFld.setEnabled(true);
				utilidadFld.setEnabled(true);
			}
		});
		tipoGrp.add(productoRbtn);

		servicioRbtn = new JRadioButton("Servicio");
		servicioRbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// FIXME Corregir la entrada de datos para un servicio
				removerBtn.doClick();
				examinarBtn.setEnabled(false);
				removerBtn.setEnabled(false);
				cantidadDisponibleFld.setEnabled(false);
				cantidadMinimaFld.setEnabled(false);
				marcaCbx.setEnabled(false);
				empaqueCbx.setEnabled(false);
				unidadEmpaqueFld.setEnabled(false);
				proveedorCbx.setEnabled(false);
				costoFld.setEnabled(false);
				utilidadFld.setEnabled(false);
			}
		});
		tipoGrp.add(servicioRbtn);

		panel.add(productoRbtn);
		panel.add(servicioRbtn);

		return panel;
	}

	/**
	 * Crea el panel de continuar agregando
	 * 
	 * @return Panel de continuar agregando
	 * @since 1.0
	 */
	protected JPanel makeContinuePanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Y luego..."));
		panel.setPreferredSize(new Dimension(210, 60));

		continuarGrp = new ButtonGroup();
		seguirAgregando = new JRadioButton("Agregar mas");
		continuarGrp.add(seguirAgregando);

		noSeguirAgregando = new JRadioButton("Volver", true);
		continuarGrp.add(noSeguirAgregando);

		panel.add(seguirAgregando);
		panel.add(noSeguirAgregando);

		return panel;
	}

	/**
	 * Crea el panel de descripcion del producto
	 * 
	 * @return Panel de descripcion del producto
	 * @since 1.0
	 */
	protected JPanel makeDescriptionPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Descripcion"));
		panel.setPreferredSize(new Dimension(380, 130));

		codigoFdl = new NumberCheckerField(GlobalConstants.CODE_SIZE, aceptarBtn, cancelarBtn);
		referenciaFdl = new TextCheckerField(aceptarBtn, cancelarBtn);
		descripcionFdl = new TextCheckerField(aceptarBtn, cancelarBtn);
		marcaCbx = new QuickJComboBoxBuilder("brand", "Value");
		proveedorCbx = new QuickJComboBoxBuilder("provider", "Nombre Empresa");
		caracteristicasFld = new TextCheckerField(aceptarBtn, cancelarBtn);

		codigoFdl.setText(Core.getLastID("stock", "Codigo"));
		referenciaFdl.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (referenciaFdl.getText().length() + 1 > 10)
					e.consume();
				e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
			}
		});

		codigoFdl.setPreferredSize(new Dimension(120, 20));
		referenciaFdl.setPreferredSize(new Dimension(120, 20));
		descripcionFdl.setPreferredSize(new Dimension(280, 20));
		marcaCbx.setPreferredSize(new Dimension(90, 20));
		proveedorCbx.setPreferredSize(new Dimension(160, 20));
		caracteristicasFld.setPreferredSize(new Dimension(260, 20));

		panel.add(new JLabel("Codigo"));
		panel.add(codigoFdl);
		panel.add(new JLabel("Referencia"));
		panel.add(referenciaFdl);
		panel.add(new JLabel("Descripcion"));
		panel.add(descripcionFdl);
		panel.add(new JLabel("Marca"));
		panel.add(marcaCbx);
		panel.add(new JLabel("Proveedor"));
		panel.add(proveedorCbx);
		panel.add(new JLabel("Caracteristicas"));
		panel.add(caracteristicasFld);
		
		//TODO Implementar uso de referencia
		referenciaFdl.setEnabled(false);

		return panel;
	}

	/**
	 * Crea el panel de cantidades como cantidad minima, disponible, etc.
	 * 
	 * @return Panel de cantidades
	 * @since 1.0
	 */
	protected JPanel makeAmountsPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Cantidades"));
		panel.setPreferredSize(new Dimension(380, 110));

		categoriaCbx = new QuickJComboBoxBuilder("groups", "Name");
		empaqueCbx = new QuickJComboBoxBuilder("packing", "Value");
		unidadEmpaqueFld = new NumberCheckerField(5, aceptarBtn, cancelarBtn);
		cantidadDisponibleFld = new NumberCheckerField(12, aceptarBtn,
				cancelarBtn);
		cantidadMinimaFld = new NumberCheckerField(12, aceptarBtn, cancelarBtn);

		categoriaCbx.setPreferredSize(new Dimension(280, 20));
		empaqueCbx.setPreferredSize(new Dimension(120, 20));
		unidadEmpaqueFld.setPreferredSize(new Dimension(40, 20));
		cantidadDisponibleFld.setPreferredSize(new Dimension(90, 20));
		cantidadMinimaFld.setPreferredSize(new Dimension(90, 20));

		panel.add(new JLabel("Categoria"));
		panel.add(categoriaCbx);
		panel.add(new JLabel("Empaque"));
		panel.add(empaqueCbx);
		panel.add(new JLabel("Unidades Empaque"));
		panel.add(unidadEmpaqueFld);
		panel.add(new JLabel("Cantidad "));
		panel.add(cantidadDisponibleFld);
		panel.add(new JLabel("Cantidad Minima"));
		panel.add(cantidadMinimaFld);

		return panel;
	}

	/**
	 * Crea el panel de valores con precio, costo, etc.
	 * 
	 * @return Panel de valores
	 * @since 1.0
	 */
	protected JPanel makeValuePanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Valores"));
		panel.setPreferredSize(new Dimension(380, 90));

		costoFld = new MoneyCheckerField(aceptarBtn, cancelarBtn);
		utilidadFld = new PercentCheckerField(aceptarBtn, cancelarBtn);
		impuestoCbx = new QuickJComboBoxBuilder("tax", "Name");
		precioFld = new MoneyCheckerField(aceptarBtn, cancelarBtn);

		utilidadFld.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (costoFld.getText().length() > 0
						&& utilidadFld.getText().length() > 0) {
					double percent = Double.parseDouble(utilidadFld.getText());

					double base;
					try {
						base = costoFld.getDouble();
					} catch (ParseException e1) {
						base = 0;
					}

					precioFld.setText(new StoreMath(base, percent,
							StoreMath.MULTIPLY).toString());
				}
				super.keyReleased(e);
			}
		});
		precioFld.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (costoFld.getText().length() > 0
						&& precioFld.getText().length() > 0) {
					double base;
					try {
						base = costoFld.getDouble();
					} catch (ParseException e1) {
						base = 0;
					}

					double amount = Double.parseDouble(precioFld.getText());
					double percent = new StoreMath(amount, base,
							StoreMath.DIVIDE).toDouble();
					utilidadFld.setDouble(percent);
				}
				super.keyReleased(e);
			}
		});

		costoFld.setPreferredSize(new Dimension(120, 20));
		utilidadFld.setPreferredSize(new Dimension(80, 20));
		impuestoCbx.setPreferredSize(new Dimension(90, 20));
		precioFld.setPreferredSize(new Dimension(110, 20));

		panel.add(new JLabel("Costo $"));
		panel.add(costoFld);
		panel.add(new JLabel("Utilidad %"));
		panel.add(utilidadFld);
		panel.add(new JLabel("Impuesto %"));
		panel.add(impuestoCbx);
		panel.add(new JLabel("Precio $"));
		panel.add(precioFld);

		return panel;
	}

	/**
	 * Crea el panel de botones aceptar, cancelar y limpiar
	 * 
	 * @return Panel de botones
	 * @since 1.0
	 */
	protected JPanel makeButtonsPanel() {
		JPanel panel = new JPanel();

		limpiarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAll();
			}
		});

		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		panel.add(limpiarBtn);
		panel.add(aceptarBtn);
		panel.add(cancelarBtn);

		return panel;
	}

	/**
	 * Limpia algunos los campos de texto del formato los cuales no se
	 * encuentren deshabilitados, para el proceso de seguir agregando.
	 * 
	 * @since 1.0
	 */
	public void clear() {
		removerBtn.doClick();

		if (codigoFdl.isEnabled())
			codigoFdl.setText("");
		if (referenciaFdl.isEnabled())
			referenciaFdl.setText("");
		if (descripcionFdl.isEnabled())
			descripcionFdl.setText("");
		if (caracteristicasFld.isEnabled())
			caracteristicasFld.setText("");
		if (cantidadDisponibleFld.isEnabled())
			cantidadDisponibleFld.setText("");
		if (cantidadMinimaFld.isEnabled())
			cantidadMinimaFld.setText("");
		if (costoFld.isEnabled())
			costoFld.setText("");
		if (utilidadFld.isEnabled())
			utilidadFld.setText("");
		if (impuestoCbx.isEnabled())
			impuestoCbx.setSelectedIndex(0);
		if (precioFld.isEnabled())
			precioFld.setText("");

		codigoFdl.requestFocus();
	}

	/**
	 * Limpia todos los campos de texto del formato los cuales no se encuentren
	 * deshabilitados
	 * 
	 * @since 1.0
	 */
	protected void clearAll() {
		if (marcaCbx.isEnabled())
			marcaCbx.setSelectedIndex(0);
		if (proveedorCbx.isEnabled())
			proveedorCbx.setSelectedIndex(0);
		if (categoriaCbx.isEnabled())
			categoriaCbx.setSelectedIndex(0);
		if (empaqueCbx.isEnabled())
			empaqueCbx.setSelectedIndex(0);
		if (unidadEmpaqueFld.isEnabled())
			unidadEmpaqueFld.setText("");
		clear();
	}

	/**
	 * Hace una lista de los componentes en el orden que se encuentran el la DB
	 * y lo retorna
	 * 
	 * @return Componentes ordenados en un arreglo
	 * @since 1.1
	 */
	protected JComponent[] getComponentsOrder() {
		JComponent temp[] = { codigoFdl, referenciaFdl, categoriaCbx,
				cantidadDisponibleFld, cantidadMinimaFld, descripcionFdl,
				productoRbtn, marcaCbx, empaqueCbx, unidadEmpaqueFld,
				impuestoCbx, utilidadFld, precioFld, proveedorCbx, costoFld,
				caracteristicasFld, imagenLbl };
		return temp;
	}
}
