import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String s = "";
    int cnt = 0;
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("%s", s);
        } else {
            if (url.getPath().contains("/add-message")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    if(cnt == 0){
                        s+=parameters[1];
                        cnt+=1;
                    }
                    else
                        s+="\n"+parameters[1];
                    return String.format("%s",s);
                }
            }
            return "404 Not Found!";
        }
    }
}

public class StringServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}



