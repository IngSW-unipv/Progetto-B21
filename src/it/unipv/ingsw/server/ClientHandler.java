package it.unipv.ingsw.server;

import java.rmi.RemoteException;
import java.util.ArrayList;

import it.unipv.ingsw.client.model.game.cards.Card;
import it.unipv.ingsw.client.model.game.player.types.HumanPlayer;
import it.unipv.ingsw.client.model.game.player.types.Player;
import it.unipv.ingsw.client.model.multiplayer.client.RemoteClientInterface;
import it.unipv.ingsw.server.utils.RemoteHandlerInterface;

public class ClientHandler implements RemoteHandlerInterface{
	RemoteClientInterface client;
	Lobby lobby;
	Player player;
	
	
	public ClientHandler(RemoteClientInterface client, ScoponeServer server) {
		this.client = client;
		try {
			player = new HumanPlayer(client.getPlayerName());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendMessage(String msg) {
		client.printMessage(msg);
	}
	
	public void requestMove() {
		client.play();
	}
	
	public void setHand(ArrayList<Card> hand) {
		client.setHand(hand);
	}
	
	public void setCardsOnBoard(ArrayList<Card> board) {
		client.setCardsOnBoard(board);
	}
	
	public void notifyGameStart() {
		client.openGameView();
	}
	
	public void notifyGameEnd() {
		client.openLobbyView();
	}
	
	public void disconnectFromLobby() {
		client.disconnect();
	}

	@Override
	public String makeLobby() {
		lobby = new Lobby(player);
		String code = lobby.toString().substring(lobby.toString().length()-5);
		lobby.setCode(code);
		
		return code;
	}


	@Override
	public boolean joinLobby(String lobbyCode) {
		return true; ////sistemare
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



	@Override
	public void nextTurn() {
		// TODO Auto-generated method stub
		
	}

}
