package it.unipv.po.model.test;

import it.unipv.po.clientserver.client.Client;

public class ClientServerTester {
	public static void main(String args[]) {
		Client client = new Client();
		client.start();
	}
}
