package it.unipv.ingsw.client.controller.multiplayer;

public class ServerIP {
	private static ServerIP instance = null; 
	private String ip = "localhost"; 
	
	private ServerIP() {} 
	
    public static ServerIP getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (instance == null) {
            instance = new ServerIP();
        }
        return instance;
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
    
    
    
}
