
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Paulo Sergio Satiro dos Santos
 */
public class ChatClient implements Runnable{
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private ClientSocket clientSocket;
    private final Scanner scanner;
    
    public ChatClient(){
        scanner = new Scanner(System.in);
    }
    
    public void start() throws IOException{
        try{
            clientSocket = new ClientSocket(new Socket(SERVER_ADDRESS, ChatServer.PORT));
            System.out.println("Cliente conectado ao servidor em " + SERVER_ADDRESS + ":" + ChatServer.PORT);
            new Thread(this).start();
            messageLoop();
        } finally {
            clientSocket.close();
        }
    }
    
    @Override
    public void run(){
        String msg;
        while((msg = clientSocket.getMessage()) != null){
        System.out.printf("\nMensagem recebida do servidor: %s\n", msg + "\nDigite uma mensagem (ou XX para finalizar):");
        }
    }
    
    private void messageLoop() throws IOException{
        String msg;
        do{
            System.out.print("Digite uma mensagem (ou XX para finalizar): ");
            msg = scanner.nextLine();
            clientSocket.sendMsg(msg);

        }while(!msg.equals("XX"));
    }
    
    /**  
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ChatClient client = new ChatClient();
            client.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar cliente: " + ex.getMessage());
        }
        
        System.out.println("Cliente finalizado!");
    }
    
}
