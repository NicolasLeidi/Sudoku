package logica;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class eventoGrafico {

	private JPanel panel;
	
	public eventoGrafico(JPanel panel) {
		this.panel = panel;
	}
	
	public void ganar() {
		JOptionPane.showMessageDialog(panel, "Victory!");
	}
	
	public void error() {
		JOptionPane.showMessageDialog(panel, "¡Sudoku introducido inválido!");
	}
}
