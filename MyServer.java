package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {
    private int port;
    private ClientHandler ch;
    private volatile boolean stop;
    private ServerSocket server;
    private Socket currentClient;
    
    public MyServer(int port,ClientHandler ch) {
        this.port = port;
        this.ch = ch;
        this.stop = false;
    }
    
    private void runServer(){
        try {
            this.server=new ServerSocket(this.port);
            this.server.setSoTimeout(1000);
            while(!this.stop){
                try{
                    Socket aClient=this.server.accept(); // blocking call
                    this.currentClient = aClient;
                    try {
                        this.ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                        aClient.getInputStream().close();
                        aClient.getOutputStream().close();
                        aClient.close();
                    }   
                    catch (IOException e) {/*...*/}
                }   
                catch(SocketTimeoutException e) {/*...*/}
            }
            this.server.close();
        } 
        catch (Exception e) {
        }
    }

    public void start(){
      new Thread(()->runServer()).start();
    }

    public void close(){
        this.stop=true;
        try {
            if(this.currentClient!=null && !this.currentClient.isClosed()){
                this.currentClient.getInputStream().close();
                this.currentClient.getOutputStream().close();
                this.currentClient.close();
            }
        } 
        catch (Exception e) {
        }
        try {
            this.server.close();
        } 
        catch (Exception e) {
        }
    }
}



