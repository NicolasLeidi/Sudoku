package logica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class tiempoGrafico {

	private Timer timer;
	private int tiempo;
	private JLabel horas, minutos, segundos;
	
	public tiempoGrafico(JLabel h, JLabel m, JLabel s) {
		tiempo = 0;
		timer = new Timer(1000,new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tiempo++;
				horas.setText(tiempo / 3600 < 10? "0" + tiempo/3600 : "" + tiempo / 3600);
				minutos.setText((tiempo / 60) % 60 < 10 ? "0" + (tiempo / 60) % 60 : "" + (tiempo / 60) % 60);
				segundos.setText(tiempo % 60 < 10? "0" + tiempo % 60 : "" + tiempo % 60);
		    }
	    });
		horas = h;
		minutos = m;
		segundos = s;
	}
	
	public void start() {
		tiempo = 0;
		timer.start();
	}
	
	public void stop() {
		timer.stop();
	}
	
	public int getTime() {
		return tiempo;
	}
	
}
