import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 * Esta clase es un ejemplo de como trabaja el multihilado en java.
 * @author Camilo Nova
 *
 */
public class ThreadExample extends JApplet implements Runnable {

	JTextField field = new JTextField(10);

	JButton start = new JButton("Iniciar");

	JToggleButton toggle = new JToggleButton("Mantener");

	Thread selfThread = null;

	int count = 0;

	@Override
	public void init() {
		Container cp = getContentPane();
		cp.setLayout(new FlowLayout());
		cp.add(field);

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selfThread == null) {
					selfThread = new Thread(ThreadExample.this);
					selfThread.start();
				}
			}

		});
		cp.add(start);
		cp.add(toggle);
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.err.println("Interrupted");
			}
			if (!toggle.isSelected())
				field.setText(Integer.toString(count++));
		}
	}

}
