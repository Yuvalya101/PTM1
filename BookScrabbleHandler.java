package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;



public class BookScrabbleHandler implements ClientHandler {
    private DictionaryManager dictionaryManager;
    public BookScrabbleHandler() {
        this.dictionaryManager = DictionaryManager.get();
    }
     public void handleClient(InputStream in, OutputStream out) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    PrintWriter writer = new PrintWriter(out, true);
    String input = null;
    try {
      input = reader.readLine();
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    char firstLetter = input.charAt(0);
    String[] remainingWords = input.substring(input.indexOf(",") + 1).split(",");
    switch (firstLetter) {
      case 'Q':
        writer.println(query(remainingWords));
        break;
      case 'C':
        writer.println(challenge(remainingWords));
        break;
      default:
        break;
    }
    writer.close();
    this.close();
  }
  public String query(String ...args) {
    if (dictionaryManager.query(args))
      return "true\n";
    return "false\n";
  }
  public String challenge(String ...args) {
    if (dictionaryManager.challenge(args))
      return "true\n";
    return "false\n";
  }
  public void close() {}
  /*  @Override
    public void handleClient(InputStream in, OutputStream out) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      PrintWriter writer = new PrintWriter(out, true);
      String input = null;

      
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String read(){
        String answer=null;

        return answer;
    }*/


    
}
