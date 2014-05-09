package display;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import physicalObjects.*;

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
	
	/**
	 * This function sorts topologically the snowy scheme
	 * This is used so that it can be determined where to display stuff
	 * @param snowy
	 * @return
	 */
	private List<TreeSet<Connectable>> getLayers(SnowyScheme snowy){
		List<TreeSet<Connectable>> layers = new ArrayList<TreeSet<Connectable>>();
		TreeSet<Connectable> visited = new TreeSet<Connectable>();
		List<Connectable> all = snowy.getRiversAndDams();
		int layer = 0;
		layers.add(new TreeSet<Connectable>());
		layers.get(layer).add(snowy.getOcean()); // Bottom layer is always ocean
		layer++;
		while(visited.size() != all.size()){
			layers.add(new TreeSet<Connectable>());
			for(Connectable c : all){
				if(!visited.contains(c) && layers.get(layer-1).contains(c.getDownstream())){
					visited.add(c);
					layers.get(layer).add(c);
				}
			}
			layer++;
		}
		return layers;
	}
}
