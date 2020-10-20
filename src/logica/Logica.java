package logica;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.NoSuchElementException;
import java.util.Random;

public class Logica {

	private static String archivo = "inicial.txt";
	private static int dificultad = 65;
	
	private Celda[][] tablero;
	private Celda activo;
	private enum progreso {PAUSA, ENCURSO, FINALIZADO};
	private progreso estado;
	private eventoGrafico evento;
	private static Logger logger;
	
	public Logica() {
		tablero = new Celda[9][9];
		estado = progreso.PAUSA;
		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 9; j++) {
				tablero[i][j] = new Celda(i, j);
			}

		if(logger == null) {
			logger = Logger.getLogger(Logica.class.getName());
			try {
				FileHandler fh = new FileHandler("errorlog.log", true);
				logger.addHandler(fh);
				fh.setFormatter(new SimpleFormatter());
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Logger rootLogger = logger.getParent();
			for(Handler h : rootLogger.getHandlers())
				h.setLevel(Level.OFF);
		}
	}
	
	public void setEventoGrafico(eventoGrafico evento) {
		this.evento = evento;
	}
	
	public void inicializar() {
		try {
			Random r = new Random();
			InputStream in = Logica.class.getClassLoader().getResourceAsStream("sudoku/" + archivo);
			Scanner scn = new Scanner(in);
			for(int i = 0; i < 9; i++)
				for(int j = 0; j < 9; j++) {
					tablero[i][j].setValor(scn.nextInt());
				}
			if(checkear(progreso.PAUSA)) {
				estado = progreso.ENCURSO;
				for(int i = 0; i < 9; i++)
					for(int j = 0; j < 9; j++) {
						if(r.nextInt(100) < 100 - dificultad)
							tablero[i][j].setHabilitado(false);
						else {
							tablero[i][j].setValor(0);
							tablero[i][j].setHabilitado(true);
						}
					}
				evento.iniciar();
			}
			else {
				estado = progreso.PAUSA;
				sudokuIncorrecto("Solución base inválida.");
			}
			scn.close();
		} catch (NoSuchElementException e) {
			estado = progreso.PAUSA;
			sudokuIncorrecto("Archivo termina abruptamente.");			
		} catch (NumberFormatException e) {
			estado = progreso.PAUSA;
			sudokuIncorrecto("Archivo no encontrado.");	
		}
	}
	
	private void sudokuIncorrecto(String extra) {
		evento.error();
		logger.warning("Archivo de lectura introducido incorrecto. Razón: " + extra);
		System.exit(0);
	}
	
	public boolean checkear(progreso E) {
		boolean result = true;
		estado = E;
		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 9; j++)
				tablero[i][j].normal();
		for(int i = 0; i < 9; i++) {
			result = checkearColumna(i)? result : false;
			result = checkearFila(i)? result : false;
		}
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				result = checkearSub(i * 3, j * 3)? result : false;
		if(result && estado == progreso.FINALIZADO) {
			evento.ganar();
			estado = progreso.PAUSA;
		}
		return result;
	}
	
	public boolean checkearColumna(int c) {
		boolean result = true;
		boolean[] aux = new boolean[10];
		for(int i = 0; i < 9; i++)
			aux[i] = false;
		for(int i = 0; i < 9 && result; i++)
			if(tablero[i][c].getValor() != 0 && aux[tablero[i][c].getValor()]) {
				result = false;
				estado = progreso.ENCURSO;
				for(int k = 0; k < 9; k++)
					tablero[k][c].error();
			}
			else {
				aux[tablero[i][c].getValor()] = true;
				if(tablero[i][c].getValor() == 0)
					estado = progreso.ENCURSO;
			}
		return result;
	}
	
	public boolean checkearFila(int f) {
		boolean result = true;
		boolean[] aux = new boolean[10];
		for(int j = 0; j < 9; j++)
			aux[j] = false;
		for(int j = 0; j < 9 && result; j++)
			if(tablero[f][j].getValor() != 0 && aux[tablero[f][j].getValor()]) {
				result = false;
				estado = progreso.ENCURSO;
				for(int k = 0; k < 9; k++)
					tablero[f][k].error();
			}
			else {
				aux[tablero[f][j].getValor()] = true;
				if(tablero[f][j].getValor() == 0)
					estado = progreso.ENCURSO;
			}
		return result;
	}
	
	public boolean checkearSub(int f, int c) {
		boolean result = true;
		boolean[] aux = new boolean[10];
		f = (f / 3) * 3;
		c = (c / 3) * 3;
		for(int i = 0; i < 9; i++)
			aux[i] = false;
		for(int i = 0; i < 3 && result; i++)
			for(int j = 0; j < 3 && result; j++) {
				if(tablero[i + f][j + c].getValor() != 0 && aux[tablero[i + f][j + c].getValor()]) {
					result = false;
					estado = progreso.ENCURSO;
					for(int k = 0; k < 3; k++)
						for(int x = 0; x < 3; x++)
							tablero[k + f][x + c].error();
				}
				else {
					aux[tablero[i + f][j + c].getValor()] = true;
					if(tablero[i + f][j + c].getValor() == 0)
						estado = progreso.ENCURSO;
				}
			}

		return result;
	}
	
	public void setActivo(Celda celda) {
		if(estado == progreso.ENCURSO) {
			if(activo != null) {
				activo.base();
			}
			this.activo = celda;
			activo.seleccionado();
		}
	}
	
	public void cambiar(int x) {
		if(activo != null) {
			activo.setValor(x);
			checkear(progreso.FINALIZADO);
		}
	}
	
	public Celda getCelda(int i, int j) {
		return tablero[i][j];
	}
	
	public boolean enCurso() {
		return estado == progreso.ENCURSO;
	}
}
