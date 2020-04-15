import java.net.*;
import java.io.*;

public class ConcHTTPAsk {
  public static void main( String[] args) throws IOException {
    int httpPort;
    if(args.length > 0){
      httpPort = Integer.parseInt(args[0]);
    } else { httpPort = 8888; }
    try{
      // Get the port number first argument and open port
      ServerSocket HTTPSocket = new ServerSocket(httpPort);
      while(true){
        // accept client and create thread with connection
        (new Thread(new MyRunnable(HTTPSocket.accept()))).start();
      }
    } catch(IOException e){
      System.err.println(e);
    }
  }
}
