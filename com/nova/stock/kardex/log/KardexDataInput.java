package com.nova.stock.kardex.log;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.nova.core.Core;
import com.nova.look.submodules.KardexGUI;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.calendar.CalendarGUI;
import com.nova.shared.inputCheckers.MoneyCheckerField;
import com.nova.shared.inputCheckers.NumberCheckerField;
import com.nova.shared.inputCheckers.TextCheckerField;

/**
 * Esta clase construye una interfaz para la entrada y salida de elementos del
 * kardex, utilizada por el comando del submodulo Kardex
 * 
 * @author Camilo Nova
 * @version 1.0 Funcionalidad Basica
 */
public class KardexDataInput extends JDialog {

	/**
	 * Calendario de la aplicacion
	 */
	CalendarGUI calendarGUI;

	/**
	 * Entrada para el detalle de la operacion
	 */
	TextCheckerField operacionFld;

	/**
	 * Grupo de botones de tipo
	 */
	private ButtonGroup tipoGrp;

	/**
	 * Seleccion de entrada de unidades
	 */
	JRadioButton entradaRbtn;

	/**
	 * Seleccion de salida de unidades
	 */
	JRadioButton salidaRbtn;

	/**
	 * Numero de unidades
	 */
	NumberCheckerField unidadesFld;

	/**
	 * Costo de las unidades
	 */
	MoneyCheckerField costoFld;

	/**
	 * Representa el boton de aceptar
	 */
	private final JButton aceptarBtn = new JButton("Aceptar");

	/**
	 * Representa el boton de cancelar
	 */
	private final JButton cancelarBtn = new JButton("Cancelar");

	/**
	 * Frame principal
	 */
	final KardexGUI owner;

	/**
	 * Construye la ventana de ingreso de datos para el submodulo de kardex.
	 * 
	 * @param own
	 *            Frame owner
	 * @since 1.0
	 */
	public KardexDataInput(KardexGUI own) {
		owner = own;
		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		calendarGUI = new CalendarGUI(this);
		operacionFld = new TextCheckerField(aceptarBtn, cancelarBtn);
		tipoGrp = new ButtonGroup();
		entradaRbtn = new JRadioButton("Entrada", true);
		salidaRbtn = new JRadioButton("Salida", false);
		unidadesFld = new NumberCheckerField(12, aceptarBtn, cancelarBtn);

		entradaRbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				costoFld.setText(null);
				costoFld.setEnabled(true);
			}
		});

		costoFld = new MoneyCheckerField(aceptarBtn, cancelarBtn);

		calendarGUI.setPreferredSize(new Dimension(120, 30));
		operacionFld.setPreferredSize(new Dimension(310, 20));
		unidadesFld.setPreferredSize(new Dimension(100, 20));
		costoFld.setPreferredSize(new Dimension(100, 20));

		tipoGrp.add(entradaRbtn);
		tipoGrp.add(salidaRbtn);

		JPanel upPanel = new JPanel();
		JPanel midPanel = new JPanel();
		JPanel izqMidPanel = new JPanel();
		JPanel derMidPanel = new JPanel();
		JPanel lowPanel = new JPanel();

		upPanel.setPreferredSize(new Dimension(390, 60));
		upPanel.add(new JLabel("Fecha"));
		upPanel.add(calendarGUI);
		upPanel.add(Box.createHorizontalStrut(180));
		upPanel.add(new JLabel("Operacion"));
		upPanel.add(operacionFld);

		izqMidPanel.setBorder(BorderFactory.createTitledBorder("Tipo"));
		izqMidPanel.add(entradaRbtn);
		izqMidPanel.add(salidaRbtn);

		derMidPanel.setPreferredSize(new Dimension(200, 80));
		derMidPanel.setBorder(BorderFactory.createTitledBorder("Catidades"));
		derMidPanel.add(new JLabel("Unidades"));
		derMidPanel.add(unidadesFld);
		derMidPanel.add(new JLabel("Costo"));
		derMidPanel.add(Box.createHorizontalStrut(16));
		derMidPanel.add(costoFld);

		midPanel.setPreferredSize(new Dimension(390, 90));
		midPanel.add(izqMidPanel);
		midPanel.add(derMidPanel);

		lowPanel.add(aceptarBtn);
		lowPanel.add(cancelarBtn);

		add(upPanel, BorderLayout.NORTH);
		add(midPanel, BorderLayout.CENTER);
		add(lowPanel, BorderLayout.SOUTH);

		pack();
		setComponentsTooltip();
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
	 * @since 1.0
	 */
	private void setComponentsTooltip() {
		calendarGUI.setToolTipText("Fecha en la cual se ejecuta la operacion");
		operacionFld.setToolTipText("Descripcion de la operacion");
		entradaRbtn.setToolTipText("Seleccione si es entrada de mercancia");
		salidaRbtn.setToolTipText("Seleccione si es salida de mercancia");
		unidadesFld.setToolTipText("Unidades a aumentar o disminuir");
		costoFld.setToolTipText("Costo de las unidades ingresadas");
		aceptarBtn.setToolTipText("Ingresar datos y volver");
		cancelarBtn.setToolTipText("Descartar datos y volver");
	}

	/**
	 * Comprueba las condiciones de validez de los datos
	 * 
	 * @return True si los datos son validos, False de lo contrario
	 * @since 1.0
	 */
	boolean validarCampos() {
		if (calendarGUI.getDate().length() == 0)
			calendarGUI.setFocus();
		else if (operacionFld.getText().length() == 0)
			operacionFld.requestFocus();
		else if (unidadesFld.getText().length() == 0)
			unidadesFld.requestFocus();
		else if (costoFld.getText().length() == 0)
			costoFld.requestFocus();
		else
			return true;
		return false;
	}

	/**
	 * Muestra el dialogo para agregar un nuevo registro
	 * 
	 * @param itemID
	 *            Codigo del item a agregar el registro
	 * @since 1.0
	 */
	public void showAddDialog(final String itemID) {
		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validarCampos()) {
					boolean b = new KardexDataManager().insertSingleRecord(
							KardexDataInput.this, itemID,
							calendarGUI.getDate(), operacionFld.getText(),
							entradaRbtn.isSelected() ? entradaRbtn.getText()
									: salidaRbtn.getText(), unidadesFld
									.getText(), costoFld.getText());
					if (b) {
						owner.updateDataTable(itemID);
						dispose();
					}
				}
			}
		});
		salidaRbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				costoFld.setText(KardexDataManager.getLastCosto(itemID));
				costoFld.setEnabled(false);
			}
		});

		setTitle("Agregar Registro");
		setVisible(true);
	}

	/**
	 * Muestra el dialogo para modificar el registro seleccionado en la tabla.
	 * Solamente se puede modificar el ultimo registro si fue ingresado
	 * manualmente.
	 * 
	 * @param itemID
	 *            Codigo del item a modificar
	 * @since 1.0
	 */
	public void showUpdateDialog(final String itemID) {
		aceptarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validarCampos()) {
					boolean b = new KardexDataManager().updateSingleRecord(
							KardexDataInput.this, owner.getTableSelectedID(),
							itemID, calendarGUI.getDate(), operacionFld
									.getText(),
							entradaRbtn.isSelected() ? entradaRbtn.getText()
									: salidaRbtn.getText(), unidadesFld
									.getText(), costoFld.getText());
					if (b) {
						owner.updateDataTable(itemID);
						dispose();
					}
				}
			}
		});

		ArrayList<String> allRowData = Core.getAllRowData(
				owner.getIdentifier(), "Proceso", owner.getTableSelectedID());
		JComponent[] components = getComponentsOrder();

		for (int i = 0; i < components.length; i++) {
			if (components[i] instanceof JTextField)
				((JTextField) components[i]).setText(allRowData.get(i));
			else if (components[i] instanceof CalendarGUI)
				((CalendarGUI) components[i]).setDate(allRowData.get(i));
			else if (components[i] instanceof JRadioButton)
				if (allRowData.get(i).equals(salidaRbtn.getText()))
					salidaRbtn.doClick();
		}

		setTitle("Modificar Registro");
		setVisible(true);
	}

	/**
	 * Hace una lista de los componentes en el orden que se encuentran el la DB
	 * y lo retorna
	 * 
	 * @return Componentes ordenados en un arreglo
	 * @since 1.0
	 */
	private JComponent[] getComponentsOrder() {
		JComponent temp[] = { null, null, calendarGUI, operacionFld,
				entradaRbtn, null, unidadesFld, null, costoFld };
		return temp;
	}
}