import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> al = new ArrayList<String>();
    int num = 0;
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Brandon's Number: %d", num);
        } else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            al.add(parameters[1]);
            return String.format("Added " + parameters[1]);
        }
          else if (url.getPath().equals("/search")){
            String[] parameters = url.getQuery().split("=");
            String res = "";
            for (String s:al){
                if (s.contains(parameters[1])){
                    res+=s +"\n";
                }
            }
            return res;
          }
         else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("count")) {
                    num += Integer.parseInt(parameters[1]);
                    return String.format("Number increased by %s! It's now %d", parameters[1], num);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
