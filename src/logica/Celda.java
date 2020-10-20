package logica;
public class Celda {
	
	private enum modo {NORMAL, ERROR};
	private int valor, fila, columna;
	private modo estado;
	private botonGrafico boton;
	
	public Celda(int fila, int columna) {
		valor = 0;
		this.fila = fila;
		this.columna = columna;
		estado = modo.NORMAL;
	}
	
	public int getFila() {
		return fila;
	}
	
	public int getColumna() {
		return columna;
	}
	
	public int getValor() {
		return valor;
	}
	
	public void setBoton(botonGrafico boton) {
		this.boton = boton;
	}
	
	public void setHabilitado(boolean habilitado) {
		boton.setHabilitado(habilitado);
	}
	
	public void setValor(int x) {
		valor = x;
		if(valor == 0)
			boton.setValor(0);
		else
			boton.setValor(valor);
	}
	
	public void error() {
		estado = modo.ERROR;
		boton.setColor(botonGrafico.estado.ERROR);
	}
	
	public void normal() {
		estado = modo.NORMAL;
		boton.setColor(botonGrafico.estado.NORMAL);
	}
	
	public void seleccionado() {
		boton.setColor(botonGrafico.estado.SELECCIONADO);
	}
	
	public void base() {
		switch(estado) {
		case ERROR:
			boton.setColor(botonGrafico.estado.ERROR);
			break;
		case NORMAL:
			boton.setColor(botonGrafico.estado.NORMAL);
			break;
		}
	}
}
