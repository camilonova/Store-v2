package com.nova.shared.form.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.nova.core.Core;
import com.nova.look.submodules.SubModules;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.form.dat.FormTableModel;
import com.nova.shared.form.log.FormCommand;
import com.nova.shared.inputCheckers.MoneyCheckerField;
import com.nova.shared.inputCheckers.NumberCheckerField;
import com.nova.shared.math.StoreMath;
import com.nova.shared.quick.QuickJComboBoxBuilder;

/**
 * Esta clase construye y provee toda la interfaz grafica para el formato de
 * entrada de datos del formato
 * 
 * @author Camilo Nova
 * @version 1.0
 */
public abstract class Form extends JDialog {

	/**
	 * Boton de registrar de la barra de herramientas
	 */
	final protected JButton registrarBtn;

	/**
	 * Boton de subir elemento de la barra de herramientas
	 */
	final protected JButton subirElementoBtn;

	/**
	 * Boton de bajar elemento de la barra de herramientas
	 */
	final protected JButton bajarElementoBtn;

	/**
	 * Boton de borrar fila de la barra de herramientas
	 */
	final protected JButton borrarFilaBtn;

	/**
	 * Boton de borrar todo de la barra de herramientas
	 */
	final protected JButton borrarTodoBtn;

	/**
	 * Boton de salir de la barra de herramientas
	 */
	final protected JButton salirBtn;

	/**
	 * Listado de clientes
	 */
	final protected QuickJComboBoxBuilder usuarioFormCbx;

	/**
	 * Numero del formato
	 */
	final protected JTextField consecutivoFld;

	/**
	 * Identificacion del cliente
	 */
	final protected JTextField nitFld;

	/**
	 * Ciudad del cliente
	 */
	final protected JTextField ciudadFld;

	/**
	 * Telefono del cliente
	 */
	final protected JTextField telefonoFld;

	/**
	 * Direccion del cliente
	 */
	final protected JTextField direccionFld;

	/**
	 * Codigo del articulo
	 */
	final protected NumberCheckerField codigoFld;

	/**
	 * Listado de articulos
	 */
	final protected JComboBox descripcionCbx;

	/**
	 * Cantidad de elementos del articulo
	 */
	final protected NumberCheckerField cantidadFld;

	/**
	 * Precio del articulo
	 */
	final protected MoneyCheckerField precioFld;

	/**
	 * Boton de agregar
	 */
	final protected JButton agregarBtn;

	/**
	 * Total de items
	 */
	final protected NumberCheckerField totalItemsFld;

	/**
	 * Total de unidades
	 */
	final protected NumberCheckerField totalUnidadesFld;

	/**
	 * Total de base impositiva
	 */
	final protected MoneyCheckerField totalBaseFld;

	/**
	 * Total de impuesto
	 */
	final protected MoneyCheckerField totalImpuestoFld;

	/**
	 * Total de factura (base + impuesto)
	 */
	final protected MoneyCheckerField totalFld;

	/**
	 * Dueño (GUI) de la factura
	 */
	SubModules owner;

	/**
	 * Panel de datos
	 */
	FormTablePanel dataTable;

	/**
	 * Comando del formato
	 */
	FormCommand command;

	/**
	 * Verdadero si el formato es de tipo compra
	 */
	private final boolean isSellType;

	/**
	 * Indica que el formato es de tipo factura
	 */
	public static final int INVOICE_TYPE = 21342;

	/**
	 * Indica que el formato es de tipo cotizacion
	 */
	public static final int QUOTE_TYPE = 12523;

	/**
	 * Indica que el formato es de tipo compra
	 */
	public static final int PURCHASE_TYPE = 31423;

	/**
	 * Constructor. Llama al constructor del dialogo.
	 * 
	 * @param mainFrame
	 *            Dialogo dueño
	 * @param isModal
	 *            Verdadero para modal
	 * @param isSellType
	 *            True para crear un formato de venta, False para uno de compra
	 * @since 1.0
	 */
	public Form(JFrame mainFrame, boolean isModal, boolean isSellType) {
		super(mainFrame, isModal);
		this.isSellType = isSellType;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				usuarioFormCbx.setSelectedIndex(0);
				cantidadFld.requestFocus();
			}
		});

		registrarBtn = new JButton("Registrar", GlobalConstants.SAVE_ICON_BIG);
		subirElementoBtn = new JButton("Subir", GlobalConstants.UP_ICON_BIG);
		bajarElementoBtn = new JButton("Bajar", GlobalConstants.DOWN_ICON_BIG);
		borrarFilaBtn = new JButton("Borrar Fila",
				GlobalConstants.DELETE_ROW_ICON_BIG);
		borrarTodoBtn = new JButton("Borrar Todo",
				GlobalConstants.DELETE_ALL_ICON_BIG);
		salirBtn = new JButton("Salir", GlobalConstants.EXIT_ICON_BIG);

		if (isSellType)
			usuarioFormCbx = new QuickJComboBoxBuilder("customer", "Nombre");
		else
			usuarioFormCbx = new QuickJComboBoxBuilder("provider",
					"Nombre Empresa");

		consecutivoFld = new JTextField();
		nitFld = new JTextField();
		ciudadFld = new JTextField();
		telefonoFld = new JTextField();
		direccionFld = new JTextField();

		agregarBtn = new JButton("Agregar");
		codigoFld = new NumberCheckerField(GlobalConstants.CODE_SIZE);
		descripcionCbx = new JComboBox(getFormDescriptions());
		cantidadFld = new NumberCheckerField(5);
		precioFld = new MoneyCheckerField(agregarBtn, descripcionCbx);
		cantidadFld.setText("1");

		totalItemsFld = new NumberCheckerField();
		totalUnidadesFld = new NumberCheckerField();
		totalBaseFld = new MoneyCheckerField();
		totalImpuestoFld = new MoneyCheckerField();
		totalFld = new MoneyCheckerField();
	}

	/**
	 * Determina el dueño del formato e inicializa el comando y la tabla.
	 * <p>
	 * al constructor de esta clase</b>
	 * 
	 * @param owner
	 *            Dueño del formato
	 * @since 1.0
	 */
	protected void setSubmoduleOwner(SubModules owner) {
		this.owner = owner;
		this.dataTable = new FormTablePanel(this.owner);
		this.command = new FormCommand(this, dataTable);
	}

	/**
	 * Retorna el tipo de formato.
	 * 
	 * @return True si el formato es de tipo
	 */
	public int getFormatType() {
		if (owner.getIdentifier().equals("invoice_items"))
			return INVOICE_TYPE;
		else if (owner.getIdentifier().equals("quote_items"))
			return QUOTE_TYPE;
		else if (owner.getIdentifier().equals("purchase_items"))
			return PURCHASE_TYPE;
		return -1;
	}

	/**
	 * Retorna los datos de la tabla de datos
	 * 
	 * @return Datos de los elementos de la factura
	 * @since 1.0
	 */
	public ArrayList<String[]> getData() {
		return this.dataTable.getModel().getTableData();
	}

	/**
	 * Retorna la barra de herramientas.
	 * 
	 * @return Barra de herramientas
	 * @since 1.0
	 */
	protected JToolBar getToolBar() {
		JToolBar toolBar = new JToolBar();

		registrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				command.registerFormat();
			}

		});
		subirElementoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				command.upSelectedItem();
			}

		});
		bajarElementoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				command.downSelectedItem();
			}

		});
		borrarFilaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				command.removeSelectedRow();
			}

		});
		borrarTodoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				command.removeAllRows();
			}

		});
		salirBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.exit();
			}

		});

		toolBar.setPreferredSize(new Dimension(790, 40));
		toolBar.add(registrarBtn);
		toolBar.addSeparator();
		toolBar.add(subirElementoBtn);
		toolBar.add(bajarElementoBtn);
		toolBar.addSeparator();
		toolBar.add(borrarFilaBtn);
		toolBar.add(borrarTodoBtn);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(salirBtn);
		toolBar.setFloatable(false);

		return toolBar;
	}

	/**
	 * Retorna el panel de datos del cliente o vendedor
	 * 
	 * @return Panel de datos del cliente o vendedor
	 * @since 1.0
	 */
	protected JPanel getInfoPanel() {
		final String type;
		final String table;
		final String column;
		JPanel panel = new JPanel();

		if (isSellType) {
			type = "Cliente";
			table = "customer";
			column = "Nombre";
			panel.setBorder(BorderFactory
					.createTitledBorder("Datos del Comprador"));
		} else {
			type = "Vendedor";
			table = "provider";
			column = "Nombre Empresa";
			panel.setBorder(BorderFactory
					.createTitledBorder("Datos del Vendedor"));
		}

		usuarioFormCbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cliente = (String) usuarioFormCbx.getSelectedItem();
				nitFld
						.setText(Core.getDataAt(table, column, cliente,
								"NIT/CC"));
				ciudadFld.setText(Core.getDataAt(table, column, cliente,
						"Ciudad"));
				telefonoFld.setText(Core.getDataAt(table, column, cliente,
						"Telefono"));
				direccionFld.setText(Core.getDataAt(table, column, cliente,
						"Direccion"));
			}

		});

		if (owner.getItemToShow() == null) {
			String nextConsecutiveNumber = owner.getNextConsecutiveNumber();
			consecutivoFld.setText(nextConsecutiveNumber);
			owner.setItemToShow(nextConsecutiveNumber);
		} else
			consecutivoFld.setText(owner.getItemToShow());

		consecutivoFld.setHorizontalAlignment(SwingConstants.RIGHT);
		consecutivoFld.setForeground(Color.RED);
		consecutivoFld.setFont(new Font("Arial", Font.BOLD, 16));

		panel.setPreferredSize(new Dimension(790, 80));
		usuarioFormCbx.setPreferredSize(new Dimension(350, 20));
		consecutivoFld.setPreferredSize(new Dimension(100, 20));
		nitFld.setPreferredSize(new Dimension(100, 20));
		ciudadFld.setPreferredSize(new Dimension(140, 20));
		telefonoFld.setPreferredSize(new Dimension(100, 20));
		direccionFld.setPreferredSize(new Dimension(400, 20));

		consecutivoFld.setEditable(false);
		nitFld.setEnabled(false);
		ciudadFld.setEnabled(false);
		telefonoFld.setEnabled(false);
		direccionFld.setEnabled(false);

		panel.add(new JLabel(type));
		panel.add(usuarioFormCbx);
		panel.add(new JLabel("Ciudad"));
		panel.add(ciudadFld);
		panel.add(new JLabel(owner.getFilter() + " N°"));
		panel.add(consecutivoFld);
		panel.add(new JLabel("Nit/CC"));
		panel.add(nitFld);
		panel.add(new JLabel("Telefono"));
		panel.add(telefonoFld);
		panel.add(new JLabel("Direccion"));
		panel.add(direccionFld);

		return panel;
	}

	/**
	 * Retorna el panel de insertar elementos
	 * 
	 * @return Panel de insertar elementos
	 * @since 1.0
	 */
	protected JPanel getInsertPanel() {
		final String label = isSellType ? "Precio Unitario" : "Costo Unitario";
		final String column = isSellType ? "Precio Venta" : "Costo";

		JPanel panel = new JPanel();
		final JLabel codigoLbl = new JLabel("Codigo", SwingConstants.CENTER);
		final JLabel descripcionLbl = new JLabel("Descripcion",
				SwingConstants.CENTER);
		final JLabel cantidadLbl = new JLabel("Cantidad", SwingConstants.CENTER);
		final JLabel precioLbl = new JLabel(label, SwingConstants.CENTER);

		cantidadFld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				codigoFld.requestFocus();
			}

		});
		codigoFld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codigo = codigoFld.getText();
				if (Core.isRegisteredData("stock", "Codigo", codigo)) {
					String desc = Core.getDataAt("stock", "Codigo", codigo,
							"Descripcion");
					String marca = Core.getDataAt("stock", "Codigo", codigo,
							"Marca");

					descripcionCbx.setSelectedItem(desc + " .:. " + marca);
					precioFld.setText(Core.getDataAt("stock", "Codigo", codigo,
							column));
					agregarBtn.doClick();
					codigoFld.requestFocus();
				} else {
					codigoFld.selectAll();
					Toolkit.getDefaultToolkit().beep();
				}
			}

		});
		descripcionCbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String articulo[] = ((String) descripcionCbx.getSelectedItem()).split(" .:. ");				
				ResultSet resultSet = Core.doSelect("SELECT `Codigo` , `Precio Venta` " +
						"FROM `stock` WHERE `Descripcion` = '"+articulo[0]+"' AND " +
						"`Marca` = '"+articulo[1]+"'");
				try {
					if(resultSet.next()) {
						codigoFld.setText(resultSet.getString("Codigo"));
						precioFld.setText(resultSet.getString("Precio Venta"));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

		});
		agregarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isValidData()) {
					String cantidad = cantidadFld.getText();
					String descripcionCompuesta = (String) descripcionCbx
							.getSelectedItem();

					String valorUnitario = "";
					try {
						valorUnitario = StoreMath.parseDouble(precioFld
								.getDouble());
					} catch (ParseException e1) {
						e1.printStackTrace();
					}

					String tipo = Core.getDataAt("stock", "Codigo", codigoFld
							.getText(), "Impuesto");

					double porcentaje = Double.parseDouble("0."
							+ Core.getDataAt("tax", "Name", tipo, "Value"));
					
					double tax = new StoreMath(StoreMath.parseDouble(valorUnitario),
							porcentaje, StoreMath.MULTIPLY).toDouble();

					String base = new StoreMath(Integer.parseInt(cantidad),
							StoreMath.parseDouble(valorUnitario),
							StoreMath.MULTIPLY).toString();

					String impuesto = new StoreMath(
							Integer.parseInt(cantidad), StoreMath.round(tax),
							StoreMath.MULTIPLY).toString();

					String subTotal = new StoreMath(
							StoreMath.parseDouble(base), StoreMath
									.parseDouble(impuesto), StoreMath.ADD)
							.toString();

					String[] data = { cantidad, descripcionCompuesta, valorUnitario,
							base, impuesto, subTotal };

					dataTable.getModel().addRowValues(data);
					updateTotals();
					cleanInsertPanel();
				}
			}

		});

		panel.setPreferredSize(new Dimension(790, 55));
		codigoLbl.setPreferredSize(new Dimension(100, 15));
		codigoFld.setPreferredSize(new Dimension(100, 20));
		descripcionLbl.setPreferredSize(new Dimension(400, 15));
		descripcionCbx.setPreferredSize(new Dimension(400, 20));
		cantidadLbl.setPreferredSize(new Dimension(50, 15));
		cantidadFld.setPreferredSize(new Dimension(50, 20));
		precioLbl.setPreferredSize(new Dimension(100, 15));
		precioFld.setPreferredSize(new Dimension(100, 20));

		panel.add(cantidadLbl);
		panel.add(codigoLbl);
		panel.add(descripcionLbl);
		panel.add(precioLbl);
		panel.add(Box.createHorizontalStrut(80));
		panel.add(cantidadFld);
		panel.add(codigoFld);
		panel.add(descripcionCbx);
		panel.add(precioFld);
		panel.add(agregarBtn);

		return panel;
	}

	/**
	 * Retorna el panel con la tabla de datos
	 * 
	 * @return Panel de los datos
	 * @since 1.0
	 */
	protected JScrollPane getDataTable() {
		FormTableModel model = dataTable.getModel();

		if (model != null) {
			model.addTableModelListener(new TableModelListener() {
				// Agregamos para que en cualquier cambio actualize los totales
				public void tableChanged(TableModelEvent e) {
					updateTotals();
				}

			});
		} else
			loadData();
		dataTable.setPreferredSize(new Dimension(790, 305));

		return dataTable;
	}

	/**
	 * Retorna el panel de los totales
	 * 
	 * @return Panel de los totales
	 * @since 1.0
	 */
	protected JPanel getTotalsPanel() {
		JPanel panel = new JPanel();
		Font font = new Font("Arial", Font.BOLD, 16);

		totalItemsFld.setEnabled(false);
		totalUnidadesFld.setEnabled(false);
		totalBaseFld.setEnabled(false);
		totalImpuestoFld.setEnabled(false);
		totalFld.setEnabled(false);

		totalItemsFld.setFont(font);
		totalUnidadesFld.setFont(font);
		totalBaseFld.setFont(font);
		totalImpuestoFld.setFont(font);
		totalFld.setFont(font);

		totalItemsFld.setHorizontalAlignment(SwingConstants.RIGHT);
		totalUnidadesFld.setHorizontalAlignment(SwingConstants.RIGHT);

		panel.setPreferredSize(new Dimension(790, 35));
		totalItemsFld.setPreferredSize(new Dimension(40, 20));
		totalUnidadesFld.setPreferredSize(new Dimension(60, 20));
		totalBaseFld.setPreferredSize(new Dimension(100, 20));
		totalImpuestoFld.setPreferredSize(new Dimension(100, 20));
		totalFld.setPreferredSize(new Dimension(120, 20));

		panel.add(new JLabel("Items"));
		panel.add(totalItemsFld);
		panel.add(new JLabel("Unidades"));
		panel.add(totalUnidadesFld);
		panel.add(new JLabel("Total Base"));
		panel.add(totalBaseFld);
		panel.add(new JLabel("Total Impuesto"));
		panel.add(totalImpuestoFld);
		panel.add(new JLabel("Gran Total"));
		panel.add(totalFld);

		panel.setBorder(BorderFactory.createRaisedBevelBorder());
		return panel;
	}

	/**
	 * Limpia los campos del panel de agregar
	 * 
	 * @since 1.0
	 */
	void cleanInsertPanel() {
		cantidadFld.setText("1");
		descripcionCbx.setSelectedIndex(0);
		codigoFld.setText("");
		precioFld.setText("");
		codigoFld.requestFocus();
	}

	/**
	 * Verifica que los datos a insertar sean validos
	 * 
	 * @return true si los datos son validos, false de lo contrario
	 * @since 1.0
	 */
	boolean isValidData() {
		if (cantidadFld.getText().length() < 1) {
			cantidadFld.requestFocus();
			return false;
		}
		if (!Core.isRegisteredData("stock", "Codigo", codigoFld.getText())) {
			codigoFld.requestFocus();
			return false;
		}
		if (precioFld.getText().length() < 1) {
			precioFld.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * Actualiza los totales en el formato
	 * 
	 * @since 1.0
	 */
	public void updateTotals() {
		FormTableModel model = dataTable.getModel();
		int campos = model.getColumnCount();
		int items = model.getRowCount();
		int unidades = 0;
		double base = 0;
		double impuesto = 0;
		double total = 0;

		for (int i = 0; i < items; i++) {
			unidades += Integer.parseInt((String) model.getValueAt(i, 0));
			base += StoreMath.parseDouble((String) model.getValueAt(i,
					campos - 3));
			impuesto += StoreMath.parseDouble((String) model.getValueAt(i,
					campos - 2));
			total += StoreMath.parseDouble((String) model.getValueAt(i,
					campos - 1));
		}

		totalItemsFld.setText(String.valueOf(items));
		totalUnidadesFld.setText(String.valueOf(unidades));
		totalBaseFld.setText(StoreMath.parseDouble(base));
		totalImpuestoFld.setText(StoreMath.parseDouble(impuesto));
		totalFld.setText(StoreMath.parseDouble(total));
	}

	/**
	 * Carga los datos de una formato almacenado previamente
	 * 
	 * @since 1.0
	 */
	private void loadData() {
		registrarBtn.setEnabled(false);
		subirElementoBtn.setEnabled(false);
		bajarElementoBtn.setEnabled(false);
		borrarFilaBtn.setEnabled(false);
		borrarTodoBtn.setEnabled(false);

		usuarioFormCbx.setEnabled(false);
		cantidadFld.setEnabled(false);
		codigoFld.setEnabled(false);
		descripcionCbx.setEnabled(false);
		precioFld.setEnabled(false);
		agregarBtn.setEnabled(false);

		final String table = owner.getIdentifier().split("_")[0];
		final String keyColumn = owner.getFilter();
		final String keyValue = owner.getItemToShow();
		usuarioFormCbx.setSelectedItem(Core.getDataAt(table, keyColumn,
				keyValue, "Cliente"));
		totalItemsFld.setText(Core.getDataAt(table, keyColumn, keyValue,
				"N° Items"));
		totalUnidadesFld.setText(Core.getDataAt(table, keyColumn, keyValue,
				"N° Unidades"));
		totalBaseFld.setText(Core.getDataAt(table, keyColumn, keyValue,
				"Subtotal"));
		totalImpuestoFld.setText(Core.getDataAt(table, keyColumn, keyValue,
				"Impuesto"));
		totalFld.setText(Core.getDataAt(table, keyColumn, keyValue,
				"Gran Total"));
	}

	/**
	 * Retorna el numero del consecutivo del formato
	 * 
	 * @return Numero del consecutivo del formato
	 * @since 1.0
	 */
	public String getConsecutivo() {
		return this.consecutivoFld.getText();
	}

	/**
	 * Retorna el cliente de la factura
	 * 
	 * @return Cliente de la factura
	 * @since 1.0
	 */
	public String getCliente() {
		return (String) this.usuarioFormCbx.getSelectedItem();
	}

	/**
	 * Retorna el total de items de la factura
	 * 
	 * @return Total de items
	 * @since 1.0
	 */
	public String getTotalItems() {
		return this.totalItemsFld.getText();
	}

	/**
	 * Retorna el total de unidades de la factura
	 * 
	 * @return Total de unidades
	 * @since 1.0
	 */
	public String getTotalUnits() {
		return this.totalUnidadesFld.getText();
	}

	/**
	 * Retorna el total de la base impositiva de la factura
	 * 
	 * @return Total Base impositiva
	 * @since 1.0
	 */
	public String getTotalBase() {
		return this.totalBaseFld.getText();
	}

	/**
	 * Retorna el total del impuesto de la factura
	 * 
	 * @return Total impuesto de la factura
	 * @since 1.0
	 */
	public String getTotalImpuesto() {
		return this.totalImpuestoFld.getText();
	}

	/**
	 * Retorna el total de la factura
	 * 
	 * @return Gran Total
	 * @since 1.0
	 */
	public String getTotalFormato() {
		return this.totalFld.getText();
	}
	
	/**
	 * Retorna los nombres de los productos relacionados con su
	 * respectiva marca, para ser mostrados por el JComboBox
	 * @return Nombres relacionados con Marcas
	 * @since 1.0
	 */
	public static String[] getFormDescriptions() {
		String data[] = null;
		ResultSet resultSet = Core.doSelect("SELECT `Descripcion` , `Marca` " +
				"FROM `stock` WHERE `Cantidad` > 0 ORDER BY `Descripcion` ASC ");
		try {
			resultSet.last();
			data = new String[resultSet.getRow()];
			resultSet.beforeFirst();
			
			for(int i=0; resultSet.next(); i++)
				data[i] = resultSet.getString("Descripcion") + " .:. " + resultSet.getString("Marca");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data;
	}
	
	/**
	 * Se encarga de limpiar el panel para seguir utilizando
	 * el formato sin salir de el.
	 * @since 1.0
	 *
	 */
	public void reload() {
		// Cliente
		usuarioFormCbx.setSelectedIndex(0);
		
		// Numero del formato
		String nextConsecutiveNumber = owner.getNextConsecutiveNumber();
		consecutivoFld.setText(nextConsecutiveNumber);
		owner.setItemToShow(nextConsecutiveNumber);

		// Panel de agregar
		cleanInsertPanel();
		
		// Tabla
		dataTable.getModel().removeAllRows();
		
		repaint();
	}
}
