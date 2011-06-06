package com.nova.stock.kardex.log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.zip.DataFormatException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.nova.core.Core;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.math.StoreMath;
import com.nova.stock.log.StockDataInput;

/**
 * Esta clase se encarga de manejar los datos que tiene que ver con los
 * inventarios en la aplicacion.
 * <p>
 * El metodo de valuacion de inventarios que se utiliza es el de promedio
 * ponderado en el cual los resultados se encuentran entre los del metodo PEPS y
 * UEPS.
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class KardexDataManager {

	/**
	 * Indice del campo proceso en los datos
	 */
	private static final int PROCESO = 0;

	/**
	 * Indice del campo fecha en los datos
	 */
	private static final int FECHA = 1;

	/**
	 * Indice del campo tipo en los datos
	 */
	private static final int TIPO = 3;

	/**
	 * Indice del campo unidades en los datos
	 */
	private static final int UNIDADES = 5;

	/**
	 * Indice del campo valor unitario en los datos
	 */
	private static final int VALOR_UNITARIO = 6;

	/**
	 * Indice del campo valor total en los datos
	 */
	private static final int VALOR_TOTAL = 7;

	/**
	 * Numero de proceso en el kardex
	 */
	private String proceso;

	/**
	 * Codigo del articulo
	 */
	private String itemID;

	/**
	 * Fecha en la que ocurre la operacion
	 */
	private String date;

	/**
	 * Descripcion de la operacion
	 */
	private String operation;

	/**
	 * Operacion de entrada o salida
	 */
	private String type;

	/**
	 * Naturaleza de la operacion Automatica o Manual
	 */
	private String origin;

	/**
	 * Unidades ingresadas o egresadas
	 */
	private String units;

	/**
	 * Valor unitario de las unidades
	 */
	private String price;

	/**
	 * Valor total de la operacion
	 */
	private String totalOperation;

	/**
	 * Saldo de unidades luego de la operacion
	 */
	private String totalUnits;

	/**
	 * Saldo de unidades antes de la operacion
	 */
	private String unitsBefore;

	/**
	 * Costo calculado del articulo
	 */
	private String cost;

	/**
	 * Total del saldo
	 */
	private String totalPrice;

	/**
	 * Identificador de operacion de entrada
	 */
	private static final String inOperation = "Entrada";

	/**
	 * Identificador de operacion de salida
	 */
	private static final String outOperation = "Salida";

	/**
	 * Identificador de la tabla kardex
	 */
	private static final String kardexID = "kardex";

	/**
	 * Identificador de la tabla stock
	 */
	private static final String stockID = "stock";

	/**
	 * Crea el registro inicial para un producto nuevo, validando que no existan
	 * registros anteriores de el.
	 * <p>
	 * Hace un registro en el inventario de la aplicacion y tambien uno en el
	 * kardex.
	 * 
	 * @param parent
	 *            Frame de entrada de datos
	 * @param data
	 *            Datos del producto
	 * @return True si todo el proceso se ejecuto correctamente
	 * @since 1.0
	 */
	public boolean newRecord(StockDataInput parent, String[] data) {
		int unidades = Integer.parseInt(data[Core.getColumnIndex(stockID,
				"Cantidad")]);
		double precio = StoreMath.parseDouble(data[Core.getColumnIndex(stockID,
				"Costo")]);

		proceso = Core.getLastID(kardexID, "Proceso");
		itemID = data[Core.getColumnIndex(stockID, "Codigo")];
		date = DateFormat.getDateInstance().format(new Date());
		operation = "Inventario Inicial";
		type = inOperation;
		origin = "Automatico";
		units = String.valueOf(unidades);
		price = data[Core.getColumnIndex(stockID, "Costo")];
		totalOperation = new StoreMath(unidades, precio, StoreMath.MULTIPLY)
				.toString();
		totalUnits = units;
		cost = price;
		totalPrice = totalOperation;

		ResultSet resultSet = Core
				.doSelect("SELECT `Codigo` FROM `stock` WHERE `Descripcion` = '"
						+ data[Core.getColumnIndex(stockID, "Descripcion")]
						+ "' AND "
						+ "`Marca` = '"
						+ data[Core.getColumnIndex(stockID, "Marca")] + "' ");
		try {
			if (resultSet.next()) {
				JOptionPane
						.showMessageDialog(
								parent,
								"La descripcion y la marca pertenecen al articulo "
										+ resultSet.getString("Codigo")
										+ "\n"
										+ "Debe cambiar alguno de los dos para agregar el articulo",
								"Error", JOptionPane.ERROR_MESSAGE);
				parent.setFocusDescripcion();
			} else if (Core.isRegisteredData(stockID, "Codigo", itemID)) {
				JOptionPane
						.showMessageDialog(
								parent,
								"El codigo que ingreso ya se encuentra asignado, digite otro",
								"Error", JOptionPane.ERROR_MESSAGE);
				parent.setFocusCodigo();
			} else if (Core.isRegisteredData(kardexID, "Codigo", itemID)) {
				JOptionPane.showMessageDialog(parent,
						"El producto tiene registros vigentes en kardex\n"
								+ "Debe comprobar el kardex inmediatamente",
						"Error", JOptionPane.ERROR_MESSAGE);
				parent.cancelarDoClick();
			} else {
				Core.newData(stockID, data);
				Core.newData(kardexID, getOrderedData());
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Borra los registros pertenecientes a un producto en la Base de datos,
	 * stock y kardex.
	 * 
	 * @param itemCode
	 *            Codigo del producto a eliminar
	 * @return True si el proceso se ejecuto correctamente
	 * @since 1.0
	 */
	public boolean deleteAllRecords(String itemCode) {
		if (itemCode == null)
			JOptionPane.showMessageDialog(null,
					"Debe seleccionar un registro!!!", "Error",
					JOptionPane.WARNING_MESSAGE);
		else {
			int seleccion = JOptionPane
					.showConfirmDialog(
							null,
							"Esta seguro de eliminar el registro? "
									+ itemCode
									+ "\nEsto eliminara todos los procesos relacionados a el ",
							"Eliminar...", JOptionPane.YES_NO_OPTION);
			if (seleccion == JOptionPane.YES_OPTION) {
				String colName = Core.getColumnNames(stockID).get(0);
				Core.removeRow(stockID, colName, itemCode);
				Core.removeRows(kardexID, "Proceso", "Codigo", itemCode);
				return true;
			}
		}
		return false;
	}

	/**
	 * Actualiza la informacion de un producto sin alterar procesos en el kardex
	 * 
	 * @param parent
	 *            Ventana principal
	 * 
	 * @param data
	 *            Datos a actualizar
	 * @return True, si la actualizacion tuvo exito. False, de lo contrario
	 * @since 1.0
	 */
	public boolean updateProduct(StockDataInput parent, String[] data) {
		try {
			String sentence = "SELECT `Codigo`, `Descripcion` , `Marca` "
					+ "FROM `stock` WHERE `Descripcion` = '"
					+ data[Core.getColumnIndex(stockID, "Descripcion")]
					+ "' AND `Marca` = '"
					+ data[Core.getColumnIndex(stockID, "Marca")] + "'";
			ResultSet set = Core.doSelect(sentence);

			if (set.next()
					&& !set.getString("Codigo").equals(
							data[Core.getColumnIndex(stockID, "Codigo")])) {
				JOptionPane
						.showMessageDialog(
								parent,
								"La descripcion y la marca pertenecen a otro articulo\n"
										+ "Debe cambiar alguno de los dos para agregar el articulo",
								"Error", JOptionPane.ERROR_MESSAGE);
				parent.setFocusDescripcion();
			} else {
				Core.updateData(stockID, data);
				return true;
			}
		} catch (DataFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Crea un registro en el kardex y actualiza la informacion del producto en
	 * stock
	 * 
	 * @param parent
	 *            Ventana que ejecuta el metodo
	 * @param itemCode
	 *            Codigo del producto
	 * @param operationDate
	 *            Fecha de la operacion
	 * @param operacion
	 *            Descripcion de la operacion
	 * @param operationType
	 *            Entrada o Salida de unidades
	 * @param newUnits
	 *            Cantidad de unidades
	 * @param newCost
	 *            Costo del articulo
	 * @return True, si el proceso fue exitoso, False de lo contrario
	 * @since 1.0
	 */
	public boolean insertSingleRecord(JDialog parent, String itemCode,
			String operationDate, String operacion, String operationType,
			String newUnits, String newCost) {
		proceso = Core.getLastID(kardexID, "Proceso");
		itemID = itemCode;
		date = operationDate;
		operation = operacion;
		type = operationType;
		origin = "Manual";
		units = newUnits;
		price = newCost;
		totalOperation = new StoreMath(Integer.parseInt(units), StoreMath
				.parseDouble(price), StoreMath.MULTIPLY).toString();
		totalUnits = getSaldo(newUnits);
		totalPrice = getCostoValue(itemCode);
		if (operationType.equals(inOperation))
			cost = new StoreMath(StoreMath.parseDouble(totalPrice), Integer
					.parseInt(totalUnits), StoreMath.DIVIDE).toString();
		else
			cost = newCost;

		if (isValidProcess(parent)) {
			try {
				Core.newData(kardexID, getOrderedData());
				Core.setDataAt(stockID, "Codigo", this.itemID, "Cantidad",
						this.totalUnits);
				Core.setDataAt(stockID, "Codigo", this.itemID, "Costo",
						this.cost);
				return true;
			} catch (DataFormatException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Actualiza la informacion de un articulo registrado en stock sin alterar
	 * los registros del kardex, esto debido a que no se actualizan ni las
	 * cantidades, ni el costo del articulo
	 * 
	 * @param parent
	 *            Ventana principal
	 * @param lastProcessID
	 *            ID del ultimo proceso
	 * @param itemCode
	 *            Codigo del articulo
	 * @param operationDate
	 *            Fecha de la operacion
	 * @param operacion
	 *            Descripcion de la operacion
	 * @param operationType
	 *            Tipo de la operacion
	 * @param newUnits
	 *            Unidades del articulo
	 * @param newCost
	 *            Costo de las unidades
	 * @return True, si fue actualizado correctamente
	 * @since 1.0
	 */
	public boolean updateSingleRecord(JDialog parent, String lastProcessID,
			String itemCode, String operationDate, String operacion,
			String operationType, String newUnits, String newCost) {

		Core.removeRow(kardexID, "Proceso", lastProcessID);
		if (checkSingleRecord(itemCode))
			return insertSingleRecord(parent, itemCode, operationDate,
					operacion, operationType, newUnits, newCost);
		return false;
	}

	/**
	 * Verifica todos los registros de un producto
	 * 
	 * @param itemCode
	 *            Producto a verificar
	 * @return True, si fue verificado correctamente
	 * @since 1.0
	 */
	public boolean checkSingleRecord(String itemCode) {
		String data[][] = Core.getFilteredTableData(kardexID, "Proceso",
				GlobalConstants.ORDER_ASCENDANT, "Codigo", itemCode);

		for (int i = 0; i < data.length; i++) {
			Core.setDataAt(kardexID, "Proceso", data[i][PROCESO],
					"Valor Total", "$0");
			Core
					.setDataAt(kardexID, "Proceso", data[i][PROCESO], "Saldo",
							"$0");
			Core
					.setDataAt(kardexID, "Proceso", data[i][PROCESO], "Costo",
							"$0");
			Core
					.setDataAt(kardexID, "Proceso", data[i][PROCESO], "Total",
							"$0");
		}

		itemID = itemCode;
		unitsBefore = "0";
		for (int i = 0; i < data.length; i++) {
			type = data[i][TIPO];
			units = data[i][UNIDADES];
			price = data[i][VALOR_UNITARIO];
			totalOperation = new StoreMath(Double.parseDouble(units), StoreMath
					.parseDouble(price), StoreMath.MULTIPLY).toString();

			if (i == 0) {
				totalUnits = units;
				cost = price;
				totalPrice = totalOperation;
			} else {
				totalUnits = getSaldo(units);
				totalPrice = getCostoValue(itemID);
				if (type.equals(inOperation))
					cost = new StoreMath(StoreMath.parseDouble(totalPrice),
							Double.parseDouble(totalUnits), StoreMath.DIVIDE)
							.toString();
				else
					cost = price;

				unitsBefore = totalUnits;
			}

			Core.setDataAt(kardexID, "Proceso", data[i][PROCESO],
					"Valor Total", totalOperation);
			Core.setDataAt(kardexID, "Proceso", data[i][PROCESO], "Saldo",
					totalUnits);
			Core
					.setDataAt(kardexID, "Proceso", data[i][PROCESO], "Costo",
							cost);
			Core.setDataAt(kardexID, "Proceso", data[i][PROCESO], "Total",
					totalPrice);

			Core.setDataAt(stockID, "Codigo", itemID, "Cantidad", totalUnits);
			Core.setDataAt(stockID, "Codigo", itemID, "Costo", cost);
		}
		return true;
	}

	/**
	 * Verifica todos los registros de todos los productos, y tambien verifica
	 * que esten relacionados los registros del kardex con el stock
	 * 
	 * @return True, si elimino algun registro, False de lo contrario
	 * @since 1.0
	 */
	public boolean checkAllRecords() {
		boolean b = false;
		String dat[][] = Core.getAllTableData(kardexID, "Codigo", "ASC");
		for (int i = 0; i < dat.length; i++) {
			String code = dat[i][Core.getColumnIndex(kardexID, "Codigo")];
			if (!Core.isRegisteredData(stockID, "Codigo", code)) {
				Core.removeRow(kardexID, "Proceso", dat[i][Core.getColumnIndex(
						kardexID, "Proceso")]);
				b = true;
			}
		}

		String datos[][] = Core.getAllTableData(stockID, "Codigo", "ASC");
		for (int i = 0; i < datos.length; i++) {
			String code = datos[i][Core.getColumnIndex(stockID, "Codigo")];
			if (Core.isRegisteredData(kardexID, "Codigo", code))
				checkSingleRecord(code);
			else {
				Core.removeRow(stockID, "Codigo", code);
				b = true;
			}
		}
		return b;
	}

	/**
	 * Disminuye unidades del articulo identificado con el parametro itemID
	 * <p>
	 * Este metodo debe ser ejecutado cuando se venden unidades de un producto
	 * 
	 * @param proccesNumber
	 *            Numero de proceso(N° Factura)
	 * @param itemCode
	 *            Codigo del articulo vendido
	 * @param unitsToSell
	 *            Unidades vendidas
	 * @return True si el proceso se ejecuto correctamente, False de lo
	 *         contrario
	 * @version 1.0
	 */
	public boolean sellItemsUnits(String proccesNumber, String itemCode,
			String unitsToSell) {
		proceso = Core.getLastID(kardexID, "Proceso");
		itemID = itemCode;
		date = DateFormat.getDateInstance().format(new Date());
		operation = "Venta Factura N° " + proccesNumber;
		type = outOperation;
		origin = "Automatico";
		units = unitsToSell;
		price = getLastCosto(itemCode);
		totalOperation = new StoreMath(Integer.parseInt(unitsToSell), StoreMath
				.parseDouble(price), StoreMath.MULTIPLY).toString();
		totalUnits = getSaldo(unitsToSell);
		totalPrice = getCostoValue(itemCode);
		cost = price;

		try {
			Core.newData(kardexID, getOrderedData());
			Core.setDataAt(stockID, "Codigo", itemCode, "Cantidad", totalUnits);
			Core.setDataAt(stockID, "Codigo", itemCode, "Costo", cost);

			return true;
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Aumenta unidades del articulo identificado con el parametro itemID
	 * <p>
	 * Este metodo debe ser ejecutado cuando se compran unidades de un producto
	 * 
	 * @param proccesNumber
	 *            Numero de proceso(N° Factura)
	 * @param itemCode
	 *            Codigo del articulo comprado
	 * @param unitsToBuy
	 *            Unidades compradas
	 * @param itemCost
	 *            Costo unitario del articulo comprado
	 * @return True si el proceso se ejecuto correctamente, False de lo
	 *         contrario
	 * @version 1.0
	 */
	public boolean buyItemsUnits(String proccesNumber, String itemCode,
			String unitsToBuy, String itemCost) {
		proceso = Core.getLastID(kardexID, "Proceso");
		itemID = itemCode;
		date = DateFormat.getDateInstance().format(new Date());
		operation = "Compra Factura N° " + proccesNumber;
		type = inOperation;
		origin = "Automatico";
		units = unitsToBuy;
		price = itemCost;
		totalOperation = new StoreMath(Integer.parseInt(unitsToBuy), StoreMath
				.parseDouble(price), StoreMath.MULTIPLY).toString();
		totalUnits = getSaldo(unitsToBuy);
		totalPrice = getCostoValue(itemCode);
		cost = new StoreMath(StoreMath.parseDouble(totalPrice), Integer
				.parseInt(totalUnits), StoreMath.DIVIDE).toString();

		try {
			Core.newData(kardexID, getOrderedData());
			Core.setDataAt(stockID, "Codigo", itemCode, "Cantidad",
					this.totalUnits);
			Core.setDataAt(stockID, "Codigo", itemCode, "Costo", this.cost);

			return true;
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Retorna el ultimo costo valuado para el articulo pasado por parametro
	 * 
	 * @param itemCode
	 *            Codigo del articulo
	 * @return Ultimo costo valuado
	 * @since 1.0
	 */
	public static String getLastCosto(String itemCode) {
		return Core.getDataAt(stockID, "Codigo", itemCode, "Costo");
	}

	/**
	 * Verifica que la fecha sea posterior al ultimo registro automatico y que
	 * las unidades no sean negativas
	 * 
	 * @param parent
	 *            Frame de entrada de datos
	 * @return True si el proceso es valido
	 * @since 1.0
	 */
	private boolean isValidProcess(JDialog parent) {
		if (Integer.parseInt(totalUnits) < 0) {
			JOptionPane.showMessageDialog(parent,
					"El saldo de las unidades no puede ser negativo", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			String dat[][] = Core.getFilteredTableData(kardexID, "Fecha",
					GlobalConstants.ORDER_DESCENDANT, "Codigo", itemID);
			String dateToReg = dat[0][FECHA];
			Date fechaRegistro = DateFormat.getDateInstance().parse(date);
			Date fechaIngresada = DateFormat.getDateInstance().parse(dateToReg);

			if (fechaRegistro.before(fechaIngresada)) {
				JOptionPane
						.showMessageDialog(
								parent,
								"La fecha ingresada no puede ser anterior al ultimo registro",
								"Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Retorna el saldo de unidades.
	 * 
	 * @param actualUnits
	 *            Unidades del articulo
	 * @return Unidades finales del articulo
	 * @since 1.0
	 */
	private String getSaldo(String actualUnits) {
		unitsBefore = Core.getDataAt(stockID, "Codigo", itemID, "Cantidad");

		int act = Integer.parseInt(actualUnits);
		int ant = Integer.parseInt(unitsBefore);

		if (type.equals(inOperation))
			return String.valueOf(ant + act);
		return String.valueOf(ant - act);
	}

	/**
	 * Este metodo hace la valuacion del costo de las mercancias en el
	 * inventario por el procedimiento de promedio ponderado
	 * 
	 * @param itemCode
	 *            Codigo del articulo a calcular el costo
	 * 
	 * @return El costo total valorado por el metodo de promedio ponderado
	 *         formateado
	 * @since 1.0
	 */
	private String getCostoValue(String itemCode) {
		String datos[][] = Core.getFilteredTableData(kardexID, "Proceso",
				GlobalConstants.ORDER_ASCENDANT, "Codigo", itemCode);
		double entradas = 0;
		double salidas = 0;

		if (type.equals(inOperation))
			entradas = StoreMath.parseDouble(totalOperation);
		else
			salidas = StoreMath.parseDouble(totalOperation);

		for (int i = 0; i < datos.length; i++) {
			if (datos[i][TIPO].equals("Entrada"))
				entradas += StoreMath.parseDouble(datos[i][VALOR_TOTAL]);
			else
				salidas += StoreMath.parseDouble(datos[i][VALOR_TOTAL]);
		}

		return new StoreMath(entradas, salidas, StoreMath.SUBSTRACT).toString();
	}

	/**
	 * Retorna los datos ordenados para la tabla kardex
	 * 
	 * @return Datos ordenados
	 * @since 1.0
	 */
	private String[] getOrderedData() {
		String data[] = { proceso, itemID, date, operation, type, origin,
				units, price, totalOperation, totalUnits, cost, totalPrice };
		return data;
	}
}