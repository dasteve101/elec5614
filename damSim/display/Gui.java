package display;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui extends JFrame {

	private int count;
	/**
	 * 
	 */
	private static final long serialVersionUID = -9154178185647969468L;

	public Gui() {
		count = 0;
		Container cp = getContentPane();
		cp.setLayout(new GridBagLayout());
		cp.add(new JLabel("Counter"));
		final JTextField tfCount = new JTextField("0", 10);
		tfCount.setEditable(false);
		cp.add(tfCount);
		try{
			final JPanel damImg = new ImagePanel("dam.jpg");
			damImg.setSize(100, 100);
			damImg.setLocation(200, 400);
			cp.add(damImg);
			System.out.println("Created");
		}
		catch( IOException e){
			System.err.println("damImg could not be created");
		}
		
		JButton btnCount = new JButton("Increment");
		cp.add(btnCount);

		// Allocate an anonymous instance of an anonymous inner class that
		// implements ActionListener as ActionEvent listener
		btnCount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				++count;
				tfCount.setText(count + "");
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit program if
														// close-window button
														// clicked
		setTitle("Dam Simulation"); // "this" JFrame sets title
		setSize(600, 400); // "this" JFrame sets initial size
		setVisible(true); // "this" JFrame shows
	}

}
