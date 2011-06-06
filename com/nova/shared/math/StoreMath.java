package com.nova.shared.math;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

import com.nova.core.Core;

/**
 * Esta clase provee los metodos para hacer operaciones entre los numeros de la
 * aplicacion
 * 
 * @author Camilo Nova
 * @version 1.0
 */
public class StoreMath {

	/**
	 * Operacion de suma
	 */
	public static final int ADD = 1;

	/**
	 * Operacion de resta
	 */
	public static final int SUBSTRACT = 2;

	/**
	 * Operacion de multiplicacion
	 */
	public static final int MULTIPLY = 3;

	/**
	 * Operacion de division
	 */
	public static final int DIVIDE = 4;

	/**
	 * Presicion de los numeros
	 */
	public static final int PRESICION = Integer.parseInt(Core
			.getProperty("NUMBER PRESICION"));

	/**
	 * Formateador en texto de los numeros double
	 */
	private static final NumberFormat numberFormat = NumberFormat
			.getCurrencyInstance();

	/**
	 * Formato del numero
	 */
	private BigDecimal extendedNumber;

	/**
	 * Constructor, hace la operacion que se le indica y el resultado es
	 * retornado con el metodo toString() o con toDouble().
	 * <p>
	 * La division tiene una precision de 10 digitos despues de la coma cuando
	 * es retornada con el metodo toDouble(), al retornarse con toString() se
	 * redondea con el numero predefinido.
	 * 
	 * @param aNumber
	 *            Primer numero a operar
	 * @param bNumber
	 *            Segundo numero a operar
	 * @param operationType
	 *            Tipo de operacion, StoreMath.ADD para sumar,
	 *            StoreMath.SUBSTRACT para restar, StoreMath.MULTIPLY para
	 *            multiplicar y StoreMath.DIVIDE para dividir
	 * @since 1.0
	 */
	public StoreMath(double aNumber, double bNumber, int operationType) {
		numberFormat.setMaximumFractionDigits(PRESICION);
		extendedNumber = new BigDecimal(aNumber);
		BigDecimal bigDecimal = new BigDecimal(bNumber);

		switch (operationType) {
		case ADD:
			extendedNumber = extendedNumber.add(bigDecimal);
			break;

		case SUBSTRACT:
			extendedNumber = extendedNumber.subtract(bigDecimal);
			break;

		case MULTIPLY:
			extendedNumber = extendedNumber.multiply(bigDecimal);
			break;

		case DIVIDE:
			extendedNumber = extendedNumber.divide(bigDecimal, 10,
					BigDecimal.ROUND_FLOOR);
			break;

		default:
			break;
		}
	}

	/**
	 * Retorna el resultado en formato texto
	 */
	@Override
	public String toString() {
		return numberFormat.format(extendedNumber);
	}

	/**
	 * Retorna el resultado en formato numerico
	 * 
	 * @return Resultado en formato numerico
	 * @since 1.0
	 */
	public double toDouble() {
		return extendedNumber.doubleValue();
	}

	/**
	 * Convierte un numero en formato texto en uno numerico
	 * 
	 * @param number
	 *            Numero en formato texto
	 * @return Numero en formato numerico
	 * @since 1.0
	 */
	public static double parseDouble(String number) {
		try {
			return numberFormat.parse(number).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Convierte un numero en formato numerico en uno texto
	 * 
	 * @param number
	 *            Numero en formato numerico
	 * @return Numero en formato texto
	 * @since 1.0
	 */
	public static String parseDouble(double number) {
		return numberFormat.format(number);
	}

	/**
	 * Redondea el numero en formato numerico pasado por parametro
	 * 
	 * @param number
	 *            Numero a redondear
	 * @return Numero redondeado en formato numerico
	 * @since 1.0
	 */
	public static double round(double number) {
		return parseDouble(numberFormat.format(number));
	}
}