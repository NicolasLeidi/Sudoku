package GUI;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import logica.Celda;
import logica.Logica;

public class FocusAdapterCelda extends FocusAdapter{
	
	private Celda celda;
	private Logica tablero;
	
	public FocusAdapterCelda(Celda celda, Logica tablero) {
		super();
		this.celda = celda;
		this.tablero = tablero;
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		tablero.setActivo(celda);
	}

}
