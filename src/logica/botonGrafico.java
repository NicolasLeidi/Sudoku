package logica;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class botonGrafico {
	
	private JButton boton;
	private ImageIcon imagen;
	public enum estado {NORMAL, ERROR, SELECCIONADO};
	
	public botonGrafico(JButton boton) {
		this.boton = boton;
	}
	
	public void setHabilitado(boolean b) {
		boton.setEnabled(b);
	}
	
	public void setColor(estado e) {
		switch(e) {
		case ERROR:
			boton.setBackground(new Color(220, 50, 50));
			break;
		case NORMAL:
			boton.setBackground(new Color(200, 200, 200));
			break;
		case SELECCIONADO:
			boton.setBackground(new Color(50, 150, 150));
			break;
		}
	}
	
	public void setValor(int n) {
		if(n == 0)
			imagen = null;
		else{
			InputStream in = botonGrafico.class.getClassLoader().getResourceAsStream("img/" + n + ".png");
			try {
				imagen = new ImageIcon(ImageIO.read(in));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		actualizar();
	}
	
	private void actualizar() {
		if(imagen != null) {
			Image newimg = imagen.getImage().getScaledInstance(boton.getWidth(), boton.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			imagen.setImage(newimg);
		}
		boton.setIcon(imagen);
		
	}

}
