package myServer.server;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.swing.JTextArea;
import myServer.dao.SQLHandler;

public class Server {
    public boolean startServer (String pathServerKey,String pathClientKey,String path,int port,HashMap<String,String>hm) throws IOException, KeyManagementException {
        SSLServerSocket serverSock = null;
        SSLSocket socket = null;
        boolean start = false;
        
        try{
            TLSKeys keys=new TLSKeys(pathServerKey,pathClientKey);
            //use keys to create SSLSoket
            SSLContext ssl = SSLContext.getInstance("TLS");
            ssl.init(keys.getServerKey().getKeyManagers(), keys.getClientKey().getTrustManagers(), SecureRandom.getInstance("SHA1PRNG"));
            serverSock = (SSLServerSocket)ssl.getServerSocketFactory().createServerSocket(port);
            serverSock.setNeedClientAuth(true);
            System.out.println(new myDate().getDate()+"Сервер подключен...");
            start = true;

            while (true){
                socket = (SSLSocket)serverSock.accept(); 
                System.out.println(new myDate().getDate()+"Клиент подключен");
                new Thread (new ClientHandler(socket,path,hm)).start();
            }
        } catch (IOException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException e) {
            e.printStackTrace();
        }   finally {
            if(serverSock!=null) serverSock.close();
            System.out.println(new myDate().getDate()+" Server closed...");
            if(socket!=null) socket.close();
            start = false;
        }
    return start;
    }
}
