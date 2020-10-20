package logica;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class eventoGrafico {

	private JPanel panel;
	private tiempoGrafico tiempo;
	
	public eventoGrafico(JPanel panel, tiempoGrafico tiempo) {
		this.panel = panel;
		this.tiempo = tiempo;
	}
	
	public void iniciar() {
		tiempo.start();
	}
	
	public void ganar() {
		tiempo.stop();
		JOptionPane.showMessageDialog(panel, "Victory!");
	}
	
	public void error() {
		JOptionPane.showMessageDialog(panel, "¡Sudoku introducido inválido!");
	}
}
