import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main( String[] args) throws IOException {

		ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
		String httpHeader = "HTTP/1.1 200 OK\r\n\r\n";
		String input; //data stored from clientInput

		while(true){
			Socket socket = server.accept();
			BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream clientOutput = new DataOutputStream(socket.getOutputStream());

			StringBuilder textShown = new StringBuilder();
			textShown.append(httpHeader);

			while((input = clientInput.readLine()) != null && input.length() != 0){
				textShown.append(input + "\r\n");

			}

			clientOutput.writeBytes(textShown.toString()); //Echo
			clientInput.close();
			clientOutput.close();
			socket.close();
		}
	}
}
