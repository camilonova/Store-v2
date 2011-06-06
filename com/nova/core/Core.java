package com.nova.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.zip.DataFormatException;

import com.nova.parameter.GlobalConstants;

/**
 * Clase interfaz de la aplicacion y el servidor de datos, provee todos los
 * metodos de recuperacion y actualizacion de datos en el servidor.
 * 
 * @author Camilo Nova
 * @version 2.0 Optimizacion de lectura
 */
public final class Core {

	/**
	 * Representa la conexion con la base de datos.
	 */
	private static Connection conexion;

	/**
	 * Representa la operacion hecha en la base de datos
	 */
	private static Statement operacion;

	/**
	 * Representa el resultado de la operacion hecha.
	 */
	private static ResultSet resultadoOperacion;

	/**
	 * Constructor de la clase, crea una conexion entre la aplicacion y la base
	 * de datos. La carga de la clase debe hacerse mediante un llamado al
	 * Class.forName
	 * 
	 * @since 2.0
	 */
	public Core() {
		try {
			Class.forName(GlobalConstants.DB_DRIVER).newInstance();
			conexion = DriverManager.getConnection(
					GlobalConstants.DB_REMOTE_SOURCE, GlobalConstants.DB_USER,
					GlobalConstants.DB_PASS);
			System.out.println("Conectado Remotamente.\n");
		} catch (SQLException e) {
			try {
				conexion = DriverManager.getConnection(
						GlobalConstants.DB_LOCAL_SOURCE, GlobalConstants.DB_USER,
						GlobalConstants.DB_PASS);
				System.out.println("Conectado Localmente.\n");
			} catch (SQLException e1) {
				System.err.println("No existe origen de datos!!!");
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que valida el acceso de un usuario
	 * 
	 * @param user
	 *            Usuario
	 * @param pass
	 *            Password
	 * @return ID del usuario
	 * @since 1.0
	 */
	public static String validate(String user, String pass) {
		resultadoOperacion = doSelect("SELECT `ID` FROM `users` "
				+ "WHERE 1 AND `User` = '" + user + "' AND " + "`Password` = '"
				+ pass + "' LIMIT 0,1");
		try {
			if (resultadoOperacion.next())
				return resultadoOperacion.getString("ID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Inicia la sesion del usuario identificado con el parametro, actualizando
	 * la ultima fecha de acceso. Debe ejecutarse luego de validar al usuario.
	 * 
	 * @param ID
	 *            ID del usuario
	 * @return Ultimo acceso del usuario
	 * @since 1.0
	 */
	public static String startSession(String ID) {
		String fechaHora = DateFormat.getDateTimeInstance(DateFormat.FULL,
				DateFormat.MEDIUM).format(new Date());
		String ultimaEntrada = new String();
		resultadoOperacion = doSelect("SELECT `LastSession` FROM `users` "
				+ "WHERE 1 AND `ID` = '" + ID + "' LIMIT 0,1");
		try {
			if (resultadoOperacion.next()) {
				ultimaEntrada = resultadoOperacion.getString("LastSession");
				doUpdate("UPDATE `users` SET " + "`LastSession` = '"
						+ fechaHora + "' WHERE `ID` = '" + ID + "' LIMIT 1 ;");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ultimaEntrada;
	}

	/**
	 * Termina la conexion con el servidor.
	 * 
	 * @since 1.0
	 */
	public static void exit() {
		try {
			System.out.println("Cerrando conexion con servidor...");
			if (resultadoOperacion != null) {
				optimizeTables();
				resultadoOperacion.close();
			}
			if (operacion != null)
				operacion.close();
			if (conexion != null)
				conexion.close();
			System.out.println("Conexion con servidor terminada.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna la propiedad pasada por parametro
	 * 
	 * @param property
	 *            Propiedad a retornar
	 * @return Valor de la propiedad
	 * @since 1.1
	 */
	public static String getProperty(String property) {
		resultadoOperacion = doSelect("SELECT `Value` FROM `property` "
				+ "WHERE 1 AND `Property` = '" + property + "' LIMIT 0,1");
		try {
			if (resultadoOperacion.next())
				return resultadoOperacion.getString("Value");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Property not found!!!";
	}

	/**
	 * Retorna un tip aleatorio.
	 * 
	 * @return Tip aleatorio
	 * @since 1.2
	 */
	public static String getTip() {
		try {
			resultadoOperacion = doSelect("SELECT * FROM `tip` ");
			resultadoOperacion.last();

			Random rand = new Random();
			int number = rand.nextInt(resultadoOperacion.getRow());

			resultadoOperacion = doSelect("SELECT `Value` FROM `tip` "
					+ "WHERE 1 AND `ID` = '" + String.valueOf(number)
					+ "' LIMIT 0,1");

			if (resultadoOperacion.next())
				return resultadoOperacion.getString("Value");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new String();
	}

	/**
	 * Retorna el permiso para los parametros
	 * 
	 * @param userID
	 *            ID del usuario
	 * @param type
	 *            Modulo a retornar el permiso
	 * @return True, si le esta permitido acceder al modulo False de lo
	 *         contrario
	 * @since 1.2
	 */
	public static boolean getPermision(String userID, String type) {
		resultadoOperacion = doSelect("SELECT * FROM `users` "
				+ "WHERE 1 AND `ID` = '" + userID + "' LIMIT 0,1");
		try {
			if (resultadoOperacion.next())
				return resultadoOperacion.getBoolean(type + "Permission");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Retorna los nombre de las columnas de la tabla
	 * 
	 * @param table
	 *            Tabla a recuperar los nombres
	 * @return Array con los nombres de las columnas
	 * @since 1.3
	 */
	public static ArrayList<String> getColumnNames(String table) {
		ArrayList<String> datos = new ArrayList<String>();
		resultadoOperacion = doSelect("SELECT * FROM `" + table + "`");
		try {
			ResultSetMetaData metaData = resultadoOperacion.getMetaData();
			for (int i = 0; i < metaData.getColumnCount(); i++)
				datos.add(metaData.getColumnLabel(i + 1));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datos;
	}

	/**
	 * Retorna la cantidad de filas que tiene la tabla
	 * 
	 * @param table
	 *            Tabla a evaluar
	 * @return Cantidad de filas
	 * @since 1.3
	 */
	public static int getRowsCount(String table) {
		resultadoOperacion = doSelect("SELECT * FROM `" + table + "`");
		try {
			resultadoOperacion.last();
			return resultadoOperacion.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Metodo que ingresa un nuevo registro en la base de datos en base a los
	 * parametro recibidos
	 * 
	 * @param table
	 *            Tabla a insertar los datos
	 * @param data
	 *            Datos a insertar
	 * @throws DataFormatException
	 *             Si los datos no corresponden a los de la tabla
	 * @since 1.4
	 */
	public static void newData(String table, String[] data)
			throws DataFormatException {
		String sqlSentence = "INSERT INTO `" + table + "` ( ";
		ArrayList columnNames = getColumnNames(table);

		if (columnNames.size() != data.length)
			throw new DataFormatException(
					"Datos incompatibles, revise los parametros, "
							+ columnNames.size() + " contra " + data.length);

		for (int i = 0; i < columnNames.size(); i++) {
			sqlSentence += "`" + columnNames.get(i) + "` ";
			if (i < columnNames.size() - 1)
				sqlSentence += ", ";
		}
		sqlSentence += ") VALUES (";

		for (int i = 0; i < data.length; i++) {
			sqlSentence += "'" + data[i] + "' ";
			if (i < data.length - 1)
				sqlSentence += ", ";
		}
		sqlSentence += ");";

		doUpdate(sqlSentence);
	}

	/**
	 * Metodo que actualiza los datos de un registro ubicado gracias a que la
	 * clave primaria siempre es la primera columna. Actualiza todos los campos
	 * del registro.
	 * 
	 * @param table
	 *            Tabla a evaluar
	 * @param data
	 *            Datos actualizados
	 * @throws DataFormatException
	 *             Si los datos son incorrectos para la tabla
	 * @since 1.6
	 */
	public static void updateData(String table, String[] data)
			throws DataFormatException {
		String sqlSentence = "UPDATE `" + table + "` SET ";
		ArrayList columnNames = getColumnNames(table);

		if (columnNames.size() != data.length)
			throw new DataFormatException(
					"Datos incompatibles, revise los parametros, "
							+ columnNames.size() + " contra " + data.length);

		for (int i = 1; i < columnNames.size(); i++) {
			sqlSentence += "`" + columnNames.get(i) + "` = '" + data[i] + "' ";
			if (i < columnNames.size() - 1)
				sqlSentence += ", ";
		}
		sqlSentence += "WHERE `" + columnNames.get(0) + "` = '" + data[0]
				+ "' LIMIT 1;";

		doUpdate(sqlSentence);
	}

	/**
	 * Metodo que elimina un registro en la base de datos. El registro debe ser
	 * de la primera columna en la tabla, la cual por definicion es la que esta
	 * indizada.
	 * 
	 * @param table
	 *            Tabla a evaluar
	 * @param keyColumn
	 *            Columna a la cual pertenece la llave
	 * @param keyValue
	 *            Dato llave
	 * @since 1.4
	 */
	public static void removeRow(String table, String keyColumn, String keyValue) {
		doUpdate("DELETE FROM `" + table + "` WHERE `" + keyColumn + "` = '"
				+ keyValue + "' LIMIT 1;");
	}

	/**
	 * Metodo que retorna todos los datos de una columna. El conteo de las
	 * columnas se realiza desde cero
	 * 
	 * @param table
	 *            Tabla a evaluar
	 * @param columnName
	 *            Columna de datos a retornar
	 * @return Datos de la columna
	 * @since 1.5
	 */
	public static ArrayList<String> getAllColumnData(String table,
			String columnName) {
		ArrayList<String> data = new ArrayList<String>();
		resultadoOperacion = doSelect("SELECT `" + columnName + "` FROM `"
				+ table + "` ORDER BY `" + columnName + "` ASC");
		try {
			for (int i = 0; resultadoOperacion.next(); i++)
				data.add(resultadoOperacion.getString(columnName));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Metodo que retorna el dato en la posicion determinada
	 * 
	 * @param table
	 *            Tabla a evaluar
	 * @param keyColumn
	 *            Columna identificadora
	 * @param keyValue
	 *            Valor de la columna identificadora
	 * @param column
	 *            Columna a retornar valor
	 * @return Dato en la columna determinada
	 * @since 1.6
	 */
	public static String getDataAt(String table, String keyColumn,
			String keyValue, String column) {
		String dato = new String();
		resultadoOperacion = doSelect("SELECT * FROM `" + table + "` WHERE `"
				+ keyColumn + "` = '" + keyValue + "' LIMIT 1");
		try {
			if (resultadoOperacion.next())
				dato = resultadoOperacion.getString(column);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dato;
	}

	/**
	 * Metodo que retorna el indice de una columna ubicandola por su nombre
	 * comenzando desde cero con la primera columna.
	 * 
	 * @param table
	 *            Tabla a evaluar
	 * @param columnName
	 *            Nombre de la columna
	 * @return Indice de la columna, o -1 si no existe
	 * @since 1.6
	 */
	public static int getColumnIndex(String table, String columnName) {
		resultadoOperacion = doSelect("SELECT * from `" + table + "`");
		try {
			return resultadoOperacion.findColumn(columnName) - 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Metodo que retorna todos los datos de una fila, ubicada por los parametro
	 * keyColumn y keyValue
	 * 
	 * @param table
	 *            Tabla a evaluar
	 * @param keyColumn
	 *            Columna llave
	 * @param keyValue
	 *            Valor de la llave
	 * @return Datos de la fila
	 * @since 1.6
	 */
	public static ArrayList<String> getAllRowData(String table,
			String keyColumn, String keyValue) {
		ArrayList<String> datos = new ArrayList<String>();
		resultadoOperacion = doSelect("SELECT * FROM `" + table + "` WHERE `"
				+ keyColumn + "` = '" + keyValue + "' LIMIT 1");
		try {
			if (resultadoOperacion.next())
				for (int i = 0; i < resultadoOperacion.getMetaData()
						.getColumnCount(); i++)
					datos.add(resultadoOperacion.getString(i + 1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datos;
	}

	/**
	 * Metodo que evalua si el dato existe en los parametros.
	 * 
	 * @param table
	 *            Tabla a evaluar
	 * @param keyColumn
	 *            Columna a que pertenece el dato
	 * @param keyValue
	 *            Dato a buscar
	 * @return True, si el dato existe en los parametros. False, de lo contrario
	 * @since 1.7
	 */
	public static boolean isRegisteredData(String table, String keyColumn,
			String keyValue) {
		resultadoOperacion = doSelect("SELECT `" + keyColumn + "` FROM `"
				+ table + "` WHERE `" + keyColumn + "` = '" + keyValue + "'");
		try {
			if (resultadoOperacion.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Metodo que elimina varios registros en la base de datos.
	 * 
	 * @param table
	 *            Tabla a evaluar
	 * @param uniqueKeyColumn
	 *            Columna que es unica en la tabla
	 * @param keyColumn
	 *            Columna a la cual pertenece la llave
	 * @param keyValue
	 *            Dato llave
	 * @since 1.7
	 */
	public static void removeRows(String table, String uniqueKeyColumn,
			String keyColumn, String keyValue) {
		resultadoOperacion = doSelect("SELECT `" + uniqueKeyColumn + "` FROM `"
				+ table + "` WHERE `" + keyColumn + "` = '" + keyValue + "'");
		try {
			while (resultadoOperacion.next())
				doUpdate("DELETE FROM `" + table + "` WHERE `"
						+ uniqueKeyColumn + "` = '"
						+ resultadoOperacion.getString(uniqueKeyColumn)
						+ "' LIMIT 1;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que actualiza el dato en la posicion indicada.
	 * 
	 * @param table
	 *            Tabla a actualizar
	 * @param keyColumn
	 *            Columna de la llave
	 * @param keyValue
	 *            Dato llave
	 * @param dataColumn
	 *            Columna a actualizar
	 * @param dataValue
	 *            Dato a ingresar
	 * @since 1.7
	 */
	public static void setDataAt(String table, String keyColumn,
			String keyValue, String dataColumn, String dataValue) {
		doUpdate("UPDATE `" + table + "` SET " + "`" + dataColumn + "` = '"
				+ dataValue + "' WHERE `" + keyColumn + "` = '" + keyValue
				+ "' LIMIT 1 ;");
	}

	/**
	 * Metodo que ejecuta la sentencia sql recibida por parametro, la cual debe
	 * ser de tipo UPDATE
	 * 
	 * @param sqlSentence
	 *            UPDATE a realizar
	 * @since 1.7
	 */
	public static void doUpdate(String sqlSentence) {
		try {
			operacion = conexion.createStatement();
			operacion.executeUpdate(sqlSentence);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que ejecuta la sentencia sql recibida por parametro, la cual debe
	 * ser de tipo SELECT
	 * 
	 * @param sqlSentence
	 *            SELECT a realizar
	 * @return Resultado de la operacion
	 * @since 1.7
	 */
	public static ResultSet doSelect(String sqlSentence) {
		try {
			operacion = conexion
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			return operacion.executeQuery(sqlSentence);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retorna los datos de la tabla
	 * 
	 * @param table
	 *            Tabla a evaluar
	 * @param orderBy
	 *            Columna de orden
	 * @param type
	 *            Tipo de orden (ASC, DESC)
	 * @return Datos de la tabla
	 * @since 1.3
	 */
	public static String[][] getAllTableData(String table, String orderBy,
			String type) {
		int colCount = getColumnNames(table).size();
		String[][] arrayData = new String[getRowsCount(table)][colCount];

		resultadoOperacion = getTableData(table, orderBy, type);
		try {
			for (int i = 0; resultadoOperacion.next(); i++)
				for (int j = 0; j < colCount; j++)
					arrayData[i][j] = resultadoOperacion.getString(j + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrayData;
	}

	/**
	 * Metodo que retorna los datos de una tabla especificados por los
	 * parametros, este metodo es uno mas especializado que getAllTableData(),
	 * ya que este permite determinar que columnas seleccionar en base al
	 * filtrado por columna.
	 * 
	 * @param table
	 *            Tabla a evaluar
	 * @param orderBy
	 *            Columna de orden
	 * @param type
	 *            Ascendente o Descendente
	 * @param keyColumn
	 *            Columna llave
	 * @param keyValue
	 *            Llave de la columna
	 * @return Datos de la seleccion
	 * @since 1.7
	 */
	public static String[][] getFilteredTableData(String table, String orderBy,
			String type, String keyColumn, String keyValue) {
		String[][] arrayData = null;

		resultadoOperacion = getTableData(table, orderBy, type, keyColumn,
				keyValue);
		try {
			resultadoOperacion.last();
			arrayData = new String[resultadoOperacion.getRow()][resultadoOperacion
					.getMetaData().getColumnCount()];
			resultadoOperacion.beforeFirst();

			for (int i = 0; resultadoOperacion.next(); i++)
				for (int j = 0; j < arrayData[i].length; j++)
					arrayData[i][j] = resultadoOperacion.getString(j + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrayData;
	}

	/**
	 * Obtiene los datos de la tabla pasada por parametro
	 * 
	 * @param table
	 *            Tabla a obtener datos
	 * @param orderBy
	 *            Columna a ordenar
	 * @param type
	 *            GlobalConstants.ORDER_ASCENDANT o
	 *            GlobalConstants.ORDER_DESCENTANT
	 * @return ResultSet con los resultados obtenidos
	 * @since 2.0
	 */
	public static ResultSet getTableData(String table, String orderBy,
			String type) {
		return doSelect("SELECT * FROM `" + table + "` ORDER BY `" + orderBy
				+ "` " + type + "");
	}

	/**
	 * Obtiene los datos de la tabla pasada por parametro, filtrando los valores
	 * keyColumn y keyValue al hacer la busqueda. La columna con la cual se hace
	 * el filtrado no se retorna en el ResultSet
	 * 
	 * @param table
	 *            Tabla a obtener datos
	 * @param orderBy
	 *            Columna a ordenar
	 * @param type
	 *            GlobalConstants.ORDER_ASCENDANT o
	 *            GlobalConstants.ORDER_DESCENTANT
	 * @param keyColumn
	 *            Columna a hacer filtrado
	 * @param keyValue
	 *            Valor de la columna a filtrar
	 * @return ResultSet con los resultados obtenidos
	 * @since 2.0
	 */
	public static ResultSet getTableData(String table, String orderBy,
			String type, String keyColumn, String keyValue) {

		String sql = "SELECT";
		ArrayList<String> colNames = getColumnNames(table);
		for (int i = 0; i < colNames.size(); i++) {
			String col = colNames.get(i);
			if (!col.equals(keyColumn)) {
				sql += " `" + col + "`";
				if (i < colNames.size() - 1)
					sql += ",";
			}
		}
		sql += " FROM `" + table + "` WHERE `" + keyColumn + "` = '" + keyValue
				+ "' ORDER BY `" + orderBy + "` " + type;

		return doSelect(sql);
	}

	/**
	 * Retorna el ID proximo a ingresar en la tabla
	 * 
	 * @param table
	 *            Tabla a evaluar
	 * @param column
	 *            Columna, esta debe ser de tipo autonumerica
	 * @return El ID numerico del ultimo registro en la tabla En caso de no
	 *         encontrar retorna 1 para el primer registro
	 * @since 2.0
	 */
	public static String getLastID(String table, String column) {
		long id = 1;
		resultadoOperacion = doSelect("SELECT MAX(`" + column
				+ "`) AS lastID FROM `" + table + "`");
		try {
			if (resultadoOperacion.next())
				id += Long.parseLong(resultadoOperacion.getString("lastID"));
		} catch (NumberFormatException e) {
			return "1";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return String.valueOf(id);
	}

	/**
	 * Retorna los nombres de las tablas de la base de datos
	 * 
	 * @return Nombres de las tablas de la base de datos
	 * @since 2.0
	 */
	public static ArrayList<String> getTablesNames() {
		ArrayList<String> datos = new ArrayList<String>();
		resultadoOperacion = doSelect("SHOW TABLES");
		try {
			while (resultadoOperacion.next())
				datos.add(resultadoOperacion.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datos;
	}

	/**
	 * Optimiza las tablas de la base de datos. Este metodo se ejecuta la cerrar
	 * la conexion.
	 * 
	 * @since 2.0
	 */
	public static void optimizeTables() {
		ArrayList tableNames = getTablesNames();
		for (int i = 0; i < tableNames.size(); i++) {
			doUpdate("OPTIMIZE TABLE `" + tableNames.get(i) + "`");
		}
		System.out.println("Tablas optimizadas");
	}

	// TODO Finish this implementation
	public static ResultSet search(String table, String searchFor) {
		ArrayList colNames = getColumnNames(table);
		String sql = "SELECT * FROM `" + table + "` WHERE ";
		for (int i = 0; i < colNames.size(); i++) {
			sql += "`" + colNames.get(i) + "` LIKE '%" + searchFor + "%'";
			if (i < colNames.size() - 1)
				sql += " OR ";
		}
		return doSelect(sql);
	}
}