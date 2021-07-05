package it.unipv.ingsw.client.controller.thread;

/**
 * Interfaccia che definisce il comportamento di un thread relativo a un giocatore.
 * @author Giuseppe Lentini, Paolo Falzone, Matteo Scardovi
 *
 */
public interface PlayerThread {
	int getClick();
	void setClick(int click);
	void interrupt();
}
