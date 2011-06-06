package com.nova.parameter;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Interface que representa los parametros globales y generales de la
 * aplicacion, los cuales son cargados desde un archivo externo.
 * 
 * @author Camilo Nova
 * @version 1.0 Externalizacion de cadenas
 */
public interface GlobalConstants {

	/**
	 * Proxy de conexion con la DB
	 */
	public static final String CORE = ParameterLoader
			.getString("GlobalParameter.core");

	/**
	 * Driver de conexion con la DB
	 */
	public static final String DB_DRIVER = ParameterLoader
			.getString("GlobalParameter.driver");

	/**
	 * Nombre de usuario de la base de datos
	 */
	public static final String DB_USER = ParameterLoader
			.getString("GlobalParameter.dbUser");

	/**
	 * Password del usuario en la base de datos
	 */
	public static final String DB_PASS = ParameterLoader
			.getString("GlobalParameter.dbPass");

	/**
	 * Origen de los datos remoto, o la direccion de la base de datos
	 */
	public static final String DB_REMOTE_SOURCE = ParameterLoader
			.getString("GlobalParameter.networkAddress");

	/**
	 * Origen de los datos locales, en caso que no este disponible el remoto
	 */
	public static final String DB_LOCAL_SOURCE = ParameterLoader
			.getString("GlobalParameter.localAddress");

	/**
	 * Orden ascendente de los datos
	 */
	public static final String ORDER_ASCENDANT = ParameterLoader
			.getString("GlobalParameter.sqlStr1");

	/**
	 * Orden descendente de los datos
	 */
	public static final String ORDER_DESCENDANT = ParameterLoader
			.getString("GlobalParameter.sqlStr2");

	/**
	 * Directorio en el que se ejecuta la aplicacion
	 */
	public static final String USER_DIR = System.getProperty("user.dir")
			.replace("\\", "/")
			+ "/";

	/**
	 * Directorio donde se encuentran las imagenes
	 */
	public static final String IMAGES_DIR = USER_DIR
			+ ParameterLoader.getString("GlobalParameter.imagesDir");

	/**
	 * Look and feel customizado
	 */
	public static final String CUSTOMIZED_LOOK_AND_FEEL = ParameterLoader
			.getString("GlobalParameter.look&feel");

	/**
	 * Ancho de la pantalla
	 */
	public static final int SCREEN_SIZE_WIDTH = Toolkit.getDefaultToolkit()
			.getScreenSize().width;

	/**
	 * Alto de la pantalla
	 */
	public static final int SCREEN_SIZE_HEIGHT = Toolkit.getDefaultToolkit()
			.getScreenSize().height;

	/**
	 * Imagen de la aplicacion
	 */
	public static final Image STORE_ICON_IMAGE = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.mainImage"))
			.getImage();

	/**
	 * Logo del propietario
	 */
	public static final Icon OWNER_LOGO = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.ownerLogo"));

	/**
	 * Icono de la aplicacion
	 */
	public static final Icon STORE_LOGO = new ImageIcon(IMAGES_DIR
			+ "Store.png");

	/**
	 * Icono por defecto del producto
	 */
	public static final Icon PRODUCT_DEFAULT_ICON = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.noImage"));

	/**
	 * Icono del modulo de ventas
	 */
	public static final Icon SALES_ICON = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.salesImage"));

	/**
	 * Icono del modulo de contizaciones
	 */
	public static final Icon QUOTE_ICON = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.quoteImage"));

	/**
	 * Icono del modulo de compras
	 */
	public static final Icon PURCHASE_ICON = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.purchaseImage"));

	/**
	 * Icono del modulo de parametros
	 */
	public static final Icon PARAMETER_ICON = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.parameterImage"));

	/**
	 * Icono del modulo de clientes
	 */
	public static final Icon CUSTOMER_ICON = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.customerImage"));

	/**
	 * Icono del modulo de proveedores
	 */
	public static final Icon PROVIDER_ICON = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.providerImage"));

	/**
	 * Icono del modulo de inventario
	 */
	public static final Icon STOCK_ICON = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.stockImage"));

	/**
	 * Icono del modulo de reportes
	 */
	public static final Icon DATA_ICON = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.dataImage"));

	/**
	 * Icono de nuevo grande
	 */
	public static final Icon NEW_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.newIconBig"));

	/**
	 * Icono de eliminar grande
	 */
	public static final Icon REMOVE_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.removeIconBig"));

	/**
	 * Icono de buscar grande
	 */
	public static final Icon SEARCH_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.searchIconBig"));

	/**
	 * Icono de anterior grande
	 */
	public static final Icon PREVIOUS_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.previousIconBig"));

	/**
	 * Icono de siguiente grande
	 */
	public static final Icon NEXT_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.nextIconBig"));

	/**
	 * Icono de actualizar grande
	 */
	public static final Icon REFRESH_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.refreshIconBig"));

	/**
	 * Icono de salir grande
	 */
	public static final Icon EXIT_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.exitIconBig"));

	/**
	 * Icono de salir grande
	 */
	public static final Icon EDIT_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.editIconBig"));

	/**
	 * Icono de kardex grande
	 */
	public static final Icon KARDEX_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.kardexIconBig"));

	/**
	 * Icono de verificar grande
	 */
	public static final Icon CHECK_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.checkIconBig"));

	/**
	 * Icono de nuevo pequeño
	 */
	public static final Icon NEW_ICON_SMALL = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.newIconSmall"));

	/**
	 * Icono de eliminar pequeño
	 */
	public static final Icon REMOVE_ICON_SMALL = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.removeIconSmall"));

	/**
	 * Icono de buscar pequeño
	 */
	public static final Icon SEARCH_ICON_SMALL = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.searchIconSmall"));

	/**
	 * Icono de anterior pequeño
	 */
	public static final Icon PREVIOUS_ICON_SMALL = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.previousIconSmall"));

	/**
	 * Icono de siguiente pequeño
	 */
	public static final Icon NEXT_ICON_SMALL = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.nextIconSmall"));

	/**
	 * Icono de actualizar pequeño
	 */
	public static final Icon REFRESH_ICON_SMALL = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.refreshIconSmall"));

	/**
	 * Icono de salir pequeño
	 */
	public static final Icon EXIT_ICON_SMALL = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.exitIconSmall"));

	/**
	 * Icono de ayuda pequeño
	 */
	public static final Icon HELP_ICON_SMALL = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.helpIconSmall"));

	/**
	 * Icono de creditos pequeño
	 */
	public static final Icon CREDITS_ICON_SMALL = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.creditsIconSmall"));

	/**
	 * Icono de salir pequeño
	 */
	public static final Icon EDIT_ICON_SMALL = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.editIconSmall"));

	/**
	 * Icono de kardex pequeño
	 */
	public static final Icon KARDEX_ICON_SMALL = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.kardexIconSmall"));

	/**
	 * Icono de verificar grande
	 */
	public static final Icon CHECK_ICON_SMALL = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.checkIconSmall"));

	/**
	 * Icono de guardar grande
	 */
	public static final Icon SAVE_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.saveIconBig"));

	/**
	 * Icono de subir grande
	 */
	public static final Icon UP_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.upIconBig"));

	/**
	 * Icono de bajar grande
	 */
	public static final Icon DOWN_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.downIconBig"));

	/**
	 * Icono de borrar una fila grande
	 */
	public static final Icon DELETE_ROW_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.deleteRowIconBig"));

	/**
	 * Icono de borrar todo grande
	 */
	public static final Icon DELETE_ALL_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.deleteAllIconBig"));

	/**
	 * Icono de ver grande
	 */
	public static final Icon SEE_ICON_BIG = new ImageIcon(IMAGES_DIR
			+ ParameterLoader.getString("GlobalParameter.seeIconBig"));

	/**
	 * Longitud maxima del codigo de los productos
	 */
	public static final int CODE_SIZE = 18;

}