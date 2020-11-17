package GUI;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Logica;
import logica.botonGrafico;
import logica.eventoGrafico;
import logica.tiempoGrafico;

import javax.swing.JButton;

import java.awt.GridLayout;
import javax.swing.border.BevelBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;

public class Ventana extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana frame = new Ventana();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 788, 565);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel(new GridLayout(3, 3));
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, null, null, null));
		panel.setBounds(5, 5, 584, 516);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(599, 180, 163, 43);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel[] lblTiempo = new JLabel[6];
		for(int i = 0; i < 6; i++) {
			lblTiempo[i] = new JLabel();
			lblTiempo[i].setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, null, null, null));
			lblTiempo[i].setOpaque(true);
			lblTiempo[i].setBackground(new Color(200, 200, 200));
			panel_1.add(lblTiempo[i]);
		}
		
		JButton[][] btnCelda = new JButton[9][9];
		JPanel[][] subPanel = new JPanel[3][3];
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++) {
				subPanel[i][j] = new JPanel(new GridLayout(3,3));
				subPanel[i][j].setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, null, null, null));
				panel.add(subPanel[i][j]);
			}
		
		Logica tablero = new Logica();
		new tiempoGrafico(lblTiempo, tablero);
		eventoGrafico evento = new eventoGrafico(panel);
		tablero.setEventoGrafico(evento);
		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 9; j++) {
				btnCelda[i][j] = new JButton();
				btnCelda[i][j].setBackground(new Color(200, 200, 200));
				btnCelda[i][j].addFocusListener(new FocusAdapterCelda(tablero.getCelda(i, j), tablero));
				btnCelda[i][j].addKeyListener(new KeyAdapter() {
					@Override
					public void keyTyped(KeyEvent e) {
						if(tablero.enCurso() && e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
							tablero.cambiar(e.getKeyChar() - '0');
						}
					}
				});
				tablero.getCelda(i, j).setBoton(new botonGrafico(btnCelda[i][j]));
				subPanel[i / 3][j / 3].add(btnCelda[i][j]);
			}
		
		JButton btnStart = new JButton("Iniciar");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tablero.inicializar();
			}
		});
		btnStart.setBounds(638, 145, 89, 23);
		contentPane.add(btnStart);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(599, 247, 163, 123);
		contentPane.add(panel_2);
		
		JLabel lblNewLabel_1 = new JLabel("Iniciar - Comenzar Sudoku");
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Tambi\u00E9n para reiniciar");
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Click - Seleccionar celda");
		panel_2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("N\u00FAmero del teclado -");
		panel_2.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Modificar celda seleccionada");
		panel_2.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("(0 es borrar)");
		panel_2.add(lblNewLabel_6);
		
		
		btnStart.requestFocusInWindow(); 
	}
}
