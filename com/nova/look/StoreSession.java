package com.nova.look;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import com.nova.core.Core;
import com.nova.parameter.GlobalConstants;
import com.nova.shared.inputCheckers.TextCheckerField;
import com.nova.shared.security.CryptoStore;

/**
 * Esta clase inicia la sesion de un usuario en la aplicacion, validando sus
 * datos de usuario y contraseña.<p>
 * Se ha mejorado la conexion a la base de datos con el fin de mejorar la velocidad
 * de carga de la aplicacion, logrado esto se redujo el tiempo de carga en un 50%
 * logrado mediante el uso de threads.
 * 
 * @author Camilo Nova
 * @version 1.0 Validacion del usuario
 * @version 1.1 Recuperacion de datos del usuario
 * @version 1.2 Implementacion de threads
 */
public class StoreSession extends JDialog implements Runnable {

	/**
	 * Representa el ID del usuario
	 */
	static String UserID;

	/**
	 * Representa el nombre del usuario
	 */
	static String UserName;

	/**
	 * Representa la fecha del ultimo acceso
	 */
	static String UserLastIn;

	/**
	 * Representa el area donde se digita el usuario
	 */
	TextCheckerField usuarioFld;

	/**
	 * Representa el area donde se digita la contraseña
	 */
	JPasswordField passwordFld;

	/**
	 * Representa el boton de entrar
	 */
	JButton entrarBtn;

	/**
	 * Representa el boton de cancelar
	 */
	JButton cancelarBtn;

	/**
	 * Inicia una sesion en la aplicacion validando el usuario.
	 * 
	 * @since 1.0
	 */
	protected StoreSession() {
		setLayout(new GridLayout(3, 2));
		
		Thread conexionDB = new Thread(this);
		conexionDB.start();

		entrarBtn = new JButton("Entrar");
		cancelarBtn = new JButton("Cancelar");
		usuarioFld = new TextCheckerField(entrarBtn, cancelarBtn);
		passwordFld = new JPasswordField();

		passwordFld.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					entrarBtn.doClick();
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					cancelarBtn.doClick();
			}
		});

		entrarBtn.setToolTipText("Validar datos y entrar");
		cancelarBtn.setToolTipText("Salir de la aplicacion");

		entrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = usuarioFld.getText();
				String pass = CryptoStore.encriptar(String
						.copyValueOf(passwordFld.getPassword()));

				if (pass != null) {
					UserID = Core.validate(user, pass);
					UserLastIn = Core.startSession(UserID);
					UserName = Core.getDataAt("users", "ID", UserID, "Name");
				}
				
				if (UserID != null)
					dispose();
				else {
					usuarioFld.setText("");
					passwordFld.setText("");
					usuarioFld.requestFocus();
				}
			}
		});

		cancelarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Core.exit();
				dispose();
				System.exit(0);
			}
		});

		add(new JLabel("Usuario:"));
		add(usuarioFld);
		add(new JLabel("Password:"));
		add(passwordFld);
		add(entrarBtn);
		add(cancelarBtn);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cancelarBtn.doClick();
			}
		});

		setSize(200, 100);
		setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 100,
				Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 50);
		setTitle("Valide su Acceso");
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
		setVisible(true);		
	}
	
	/**
	 * Instancia la conexion a la DB mediante un hilo de sistema.
	 * @since 1.2
	 */
	public void run() {
		try {
			Class.forName(GlobalConstants.CORE).newInstance();
		} catch (Exception e) {
			System.exit(-1);
		}
	}

	/**
	 * Metodo estatico que retorna el ID del usuario
	 * 
	 * @return ID del usuario
	 * @since 1.1
	 */
	public static String getUserID() {
		return UserID;
	}

	/**
	 * Metodo estatico que retorna el nombre del usuario
	 * 
	 * @return Nombre del usuario
	 * @since 1.1
	 */
	public static String getUserName() {
		return UserName;
	}

	/**
	 * Metodo estatico que retorna el ultimo acceso del usuario
	 * 
	 * @return Ultimo acceso del usuario
	 * @since 1.1
	 */
	public static String getUserLastTimeIn() {
		return UserLastIn;
	}

	/**
	 * Metodo que retorna la propiedad del classloader
	 * 
	 * @return ClassLoader Property
	 * @since 1.1
	 */
	protected String getLoader() {
		return Core.getProperty("CLASSLOADER");
	}
}
