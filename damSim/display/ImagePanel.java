package display;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private BufferedImage image;

	public ImagePanel(String fileName) throws IOException {
		image = ImageIO.read(new File(fileName));
		setOpaque(true);
	}
	
	@Override
    public Dimension getPreferredSize()
    {
        return (new Dimension(image.getWidth(), image.getHeight()));
    }

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null); // see javadoc for more info on the
										// parameters
	}
}
