import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

public class HTTPAsk {
    public static void main( String[] args) throws IOException {

    ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));



      // Our HTTP headers
      String HTTP200 = "HTTP/1.1 200 OK\r\n\r\n";
      String HTTP400 = "HTTP/1.1 400 Bad Request\r\n";
      String HTTP404 = "HTTP/1.1 404 Not Found\r\n";
      // This is where we put the data from client
      String request;
      // Data from client
      String hostname;
      String port;
      String string;

      while(true){
        try {
          // accept client
          Socket socket = serverSocket.accept();

          BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          DataOutputStream outStream = new DataOutputStream(new DataOutputStream(socket.getOutputStream()));

          StringBuilder response =  new StringBuilder();

          request = inStream.readLine();

          if(request != null){
            // Split the request into parts
            String[] params = request.split("[?&= ]");

            // Reset the data variables
            port = null;
            hostname = null;
            string = null;
            // Get the values from the array
            for(int i = 0; i < params.length; i++){
              if(params[i].equals("hostname"))
                hostname = params[++i];

                else if(params[i].equals("port"))
                port = params[++i];

              else if(params[i].equals("string"))
                string  = params[++i];
            }

            // If both hostname and port are assignd
            if(hostname != null && port != null && params[1].equals("/ask")){
              try{

                String serverResponse = TCPClient.askServer(hostname,  Integer.parseInt(port), string);

                response.append(HTTP200);

                response.append(serverResponse);
              } catch(Exception e) {
                System.out.println(e.toString());
                if (!e.getClass().equals(StringIndexOutOfBoundsException.class)) {
                  response.append(HTTP404);
                }
              }
            } else {
              // If either hostname or port is left empty (or we don't include a ask)
              response.append(HTTP400);
            }

            outStream.writeBytes(response.toString());
          }

          socket.close();
        } catch(IOException e) {
          System.err.println(e);
        }
      }
    }

}
