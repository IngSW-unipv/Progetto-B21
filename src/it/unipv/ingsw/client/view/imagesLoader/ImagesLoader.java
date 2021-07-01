package it.unipv.ingsw.client.view.imagesLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagesLoader {
	
	// carica immagini nel buffer
	BufferedImage image;
	public BufferedImage uploadImage(String position) {
		
		try {
			image = ImageIO.read(getClass().getResource(position));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
}
