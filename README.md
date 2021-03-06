![carte](https://www.lottomatica.it/content/dam/lottomatica/img/Skill/news/news-skill-cartepiacentine-napoletante-siciliane-differenze-760x270.jpg)

# Scopone Scientifico
## Gruppo B21
Il sistema simula il funzionamento del gioco "Scopone Scientifico", permettendo ai giocatori di scegliere tra due modalità di gioco: quella in multiplayer online, a seguito dell'avvio del server che gestirà i turni dei vari giocatori, e quella in single player. 

_**Al fine di poter usufruire al meglio del sistema, di seguito sono riportate le istruzioni da seguire per la configurazione dell'ambiente e per l'esecuzione del sistema stesso:**_

## **1. Download di Git**
Per poter avviare l'esecuzione del sistema sull'IDE a disposizione, è necessario scaricare il software Git che permette il clone del repository del sistema, presente su GitHub. Per scaricare il software è necessario fare riferimento alle informazioni presenti alla [pagina seguente](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git), seguendo le istruzioni per il proprio sistema operativo.

## **2. Cloning del repository**
A questo punto si può procedere effettuando il clone del repository, recandosi nella sezione "**<> Code**" del progetto e aprendo il menu a tendina che apparirà una volta premuta l'icone "Code" su sfondo verde presente sulla destra della pagina. Copiare, quindi, il link presente e lanciare da terminale il comando  
```` ruby
git clone https://github.com/IngSW-unipv/Progetto-B21.git  

````
Una volta faccio ciò, eseguire l'import del progetto tramite l'apposito comando sull'IDE a disposizione.
 
## **3. Esecuzione codice**
Importata la cartella "Scopone", per giocare in modalità single player sarà sufficiente eseguire la classe `src\it\unipv\ingsw\client\controller\ScoponeGame.java`, mentre per giocare in multiplayer sarà prima necessario avviare il server tramite l'esecuzione della classe `src\it\unipv\ingsw\server\ScoponeServer.java`.

### 4. Tutte le informazioni aggiuntive, come il glossario e le regole del gioco, sono disponibili alla pagina [Wiki del progetto](https://github.com/IngSW-unipv/Progetto-B21/wiki).


