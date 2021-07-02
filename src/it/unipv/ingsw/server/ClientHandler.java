package it.unipv.ingsw.server;

import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.multiplayer.client.RemoteClientInterface;
import it.unipv.ingsw.server.utils.RemoteHandlerInterface;

public class ClientHandler implements RemoteHandlerInterface{
	RemoteClientInterface client;
	
	
	public ClientHandler(RemoteClientInterface client) {
		this.client = client;
	}


	@Override
	public boolean makeLobby() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean joinLobby(String lobbyCode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void startGame() {
		// TODO Auto-generated method stub
	}


	@Override
	public void playCard(Card playedCard) {
		// TODO Auto-generated method stub		
	}


	@Override
	public void removeFromBoard(ArrayList<Card> takenCards) {
		// TODO Auto-generated method stub	
	}
}
