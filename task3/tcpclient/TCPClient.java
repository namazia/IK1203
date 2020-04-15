package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {

    private static int BUFFERSIZE = 1024*8 ;


    public static String askServer(String hostname, int port, String ToServer) throws  IOException {

      if(ToServer == null){
        return askServer(hostname , port);
      }
      byte [] fromUser = new byte[BUFFERSIZE];
      byte [] fromServer = new byte[BUFFERSIZE];
      fromUser = ToServer.getBytes();
      Socket clientSocket = new Socket(hostname,port);

      clientSocket.getOutputStream().write(fromUser);
      clientSocket.getOutputStream().write('\n');

      int fromServerLength = clientSocket.getInputStream().read(fromServer);
      String output = new String(fromServer, 0,fromServerLength)+"\n";

      clientSocket.close();

      return output;


    }

    public static String askServer(String hostname, int port) throws  IOException {

      byte [] fromServer = new byte[BUFFERSIZE];
      Socket clientSocket = new Socket(hostname,port);

      int fromServerLength = clientSocket.getInputStream().read(fromServer);
      String output = new String(fromServer, 0,fromServerLength)+"\n";

      clientSocket.close();

      return output;
    }

  }
