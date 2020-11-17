package logica;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class tiempoGrafico {

	private JLabel[] labels;
	private Logica logica;
	private int pasoTiempo = 1000;
	
	public tiempoGrafico(JLabel[] labels, Logica logica) {
		this.labels = labels;
		this.logica = logica;
		Timer actualizacionGrafica = new Timer(pasoTiempo,new ActionListener(){
			public void actionPerformed(ActionEvent e){
				actualizarTiempo();
		    }
	    });
		actualizacionGrafica.start();
	}
	
	
	public void actualizarTiempo() {
		String tiempoTranscurrido = logica.getTiempoTranscurrido();
		for(int i = 0; i < tiempoTranscurrido.length(); i++) {
			ImageIcon imagen;
			InputStream in = tiempoGrafico.class.getClassLoader().getResourceAsStream("img/" + tiempoTranscurrido.charAt(i) + ".png");
			try {
				imagen = new ImageIcon(ImageIO.read(in));
				Image newimg = imagen.getImage().getScaledInstance(labels[i].getWidth(), labels[i].getHeight(),  java.awt.Image.SCALE_SMOOTH);
				imagen.setImage(newimg);
				labels[i].setIcon(imagen);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
	}
	
}
