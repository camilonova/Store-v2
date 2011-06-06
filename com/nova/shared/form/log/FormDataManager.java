package com.nova.shared.form.log;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.DataFormatException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.nova.core.Core;
import com.nova.look.StoreSession;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.form.gui.Form;
import com.nova.shared.inputCheckers.MoneyCheckerField;
import com.nova.shared.math.StoreMath;
import com.nova.stock.kardex.log.KardexDataManager;

/**
 * Esta clase se encarga de manejar los datos provenientes del formato y
 * almacenarlos en la base de datos de la manera indicada.
 * 
 * @author Camilo Nova
 * @version 1.0
 */
public class FormDataManager extends JDialog {

	/**
	 * Representa el index de cantidad
	 */
	private static final int CANTIDAD = 0;

	/**
	 * Representa el index de descripcion
	 */
	private static final int DESCRIPCION = 1;

	/**
	 * Representa el index de valor unitario
	 */
	private static final int VALOR_UNITARIO = 2;

	/**
	 * Representa el index de base
	 */
	private static final int BASE = 3;

	/**
	 * Representa el index de impuesto
	 */
	private static final int IMPUESTO = 4;

	/**
	 * Representa el index de subtotal
	 */
	private static final int SUBTOTAL = 5;

	/**
	 * Flag de operacion completada
	 */
	boolean isCompleted = false;

	/**
	 * Etiqueta de total
	 */
	JLabel totalLbl;

	/**
	 * Campo de total
	 */
	MoneyCheckerField totalFld;

	/**
	 * Etiqueta de efectivo
	 */
	JLabel efectivoLbl;

	/**
	 * Campo de efectivo
	 */
	MoneyCheckerField efectivoFld;

	/**
	 * Boton de registrar
	 */
	private final JButton registrarBtn = new JButton("Registrar");

	/**
	 * Boton de cancelar
	 */
	private final JButton cancelarBtn = new JButton("Cancelar");

	/**
	 * Encargado de manejar los datos en el kardex
	 */
	private final KardexDataManager dataManager = new KardexDataManager();

	/**
	 * Datos provenientes del formato
	 */
	private final Form dataInput;

	/**
	 * Tipo de formato
	 */
	private final int formType;

	/**
	 * Tabla de datos
	 */
	private final String table;

	/**
	 * Constructor. Prepara un dialogo para recibir la cantidad de efectivo a
	 * cancelar y registra la venta con todos los procesos en que ella incurre.
	 * 
	 * @param dataInput
	 *            Datos provenientes del formato
	 * @since 1.0
	 */
	public FormDataManager(Form dataInput) {
		super(dataInput, true);
		this.dataInput = dataInput;
		this.formType = dataInput.getFormatType();
		boolean visible;

		switch (formType) {
		case Form.INVOICE_TYPE:
			// Factura
			table = "invoice";
			visible = true;
			break;
		case Form.QUOTE_TYPE:
			// Cotizacion
			table = "quote";
			visible = false;
			isCompleted = setFormatMetaData() && setFormatData();
			break;
		case Form.PURCHASE_TYPE:
			// Compra
			table = "purchase";
			visible = false;
			isCompleted = setFormatMetaData() && setFormatData();
			break;

		default:
			// Error Gravisimo
			table = null;
			visible = false;
			break;
		}

		registrarBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					double change = efectivoFld.getDouble()
							- totalFld.getDouble();
					if (change >= 0 && setFormatMetaData() && setFormatData()) {
						isCompleted = true;
						dispose();
						Toolkit.getDefaultToolkit().beep();
						JLabel label = new JLabel("Cambio "
								+ StoreMath.parseDouble(change));
						label.setFont(new Font("Arial", Font.PLAIN, 18));
						JOptionPane.showMessageDialog(getOwner(), label);
					} else
						efectivoFld.requestFocus();
				} catch (ParseException e1) {
					efectivoFld.requestFocus();
				}
			}
		});

		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		JPanel middlePanel = new JPanel();
		JPanel lowerPanel = new JPanel();

		totalLbl = new JLabel("TOTAL");
		totalFld = new MoneyCheckerField();
		efectivoLbl = new JLabel("EFECTIVO");
		efectivoFld = new MoneyCheckerField(registrarBtn, cancelarBtn);

		totalFld.setEnabled(false);
		totalFld.setText(dataInput.getTotalFormato());
		totalFld.setHorizontalAlignment(SwingConstants.RIGHT);
		
		efectivoFld.setText(dataInput.getTotalFormato());
		efectivoFld.selectAll();

		middlePanel.setPreferredSize(new Dimension(150, 50));
		totalLbl.setPreferredSize(new Dimension(60, 20));
		totalFld.setPreferredSize(new Dimension(100, 20));
		efectivoLbl.setPreferredSize(new Dimension(60, 20));
		efectivoFld.setPreferredSize(new Dimension(100, 20));

		middlePanel.add(totalLbl);
		middlePanel.add(totalFld);
		middlePanel.add(efectivoLbl);
		middlePanel.add(efectivoFld);

		lowerPanel.add(registrarBtn, BorderLayout.WEST);
		lowerPanel.add(cancelarBtn, BorderLayout.EAST);

		add(middlePanel, BorderLayout.CENTER);
		add(lowerPanel, BorderLayout.SOUTH);

		pack();
		setTitle("Registrar");
		setLocation((GlobalConstants.SCREEN_SIZE_WIDTH - getWidth()) / 2,
				(GlobalConstants.SCREEN_SIZE_HEIGHT - getHeight()) / 2);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setAlwaysOnTop(true);
		setVisible(visible);
	}

	/**
	 * Prepara y almacena los elementos del formato
	 * 
	 * @return True si fue ejecutado correctamente, False de lo contrario
	 * @since 1.0
	 */
	boolean setFormatMetaData() {
		try {
			ArrayList<String[]> data = dataInput.getData();
			for (int i = 0; i < data.size(); i++) {
				String elemento[] = data.get(i);
				String selectedItem[] = elemento[DESCRIPCION].split(" .:. ");
				
				String codigo = null;
				String sqlSentence = "SELECT `Codigo` FROM `stock` " +
				"WHERE `Descripcion` = '"+selectedItem[0]+"' AND " +
				"`Marca` = '"+selectedItem[1]+"'";
				ResultSet resultSet = Core.doSelect(sqlSentence);
				
				try {
					if(resultSet.next())
						codigo = resultSet.getString("Codigo");
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}

				if (dataInput.getFormatType() != Form.QUOTE_TYPE) {
					if (!setStockData(codigo, elemento[CANTIDAD],
							elemento[VALOR_UNITARIO]))
						return false;
				}
				String[] formItems = {
						Core.getLastID(table + "_items", "Proceso"),
						dataInput.getConsecutivo(), elemento[DESCRIPCION],
						elemento[CANTIDAD], elemento[VALOR_UNITARIO],
						elemento[BASE], elemento[IMPUESTO], elemento[SUBTOTAL] };

				Core.newData(table + "_items", formItems);
			}
			return true;
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Prepara y almacena el formato
	 * 
	 * @return True si fue ejecutado correctamente, False de lo contrario
	 * @since 1.0
	 */
	boolean setFormatData() {
		try {
			String[] formatData = { dataInput.getConsecutivo(),
					dataInput.getCliente(),
					DateFormat.getDateInstance().format(new Date()),
					dataInput.getTotalItems(), dataInput.getTotalUnits(),
					dataInput.getTotalBase(), dataInput.getTotalImpuesto(),
					dataInput.getTotalFormato(), StoreSession.getUserName() };

			Core.newData(table, formatData);
			return true;
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Prepara y actualiza los datos del inventario
	 * 
	 * @param codigo
	 *            Codigo del articulo
	 * @param unidades
	 *            Unidades vendidas
	 * @return Verdadero si fue ejecutado correctamente
	 * @since 1.0
	 */
	boolean setStockData(String codigo, String unidades, String costo) {
		if (formType == Form.PURCHASE_TYPE)
			return dataManager.buyItemsUnits(dataInput.getConsecutivo(),
					codigo, unidades, costo);
		return dataManager.sellItemsUnits(dataInput.getConsecutivo(), codigo,
				unidades);
	}

	/**
	 * Retorna el estado final de la operacion
	 * 
	 * @return Verdadero si fue ejecutada correctamente, False de lo contrario
	 * @since 1.0
	 */
	public boolean isOperationCompleted() {
		return isCompleted;
	}

}
