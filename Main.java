import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Main {
  public static void main(String[] args) {
    List<String> lines = new ArrayList<String>();
    Scanner stdin = new Scanner(System.in);
    while(stdin.hasNextLine()) {
      lines.add(stdin.nextLine());
    }
    stdin.close();

    // do something with the lines array
    System.out.println("Hello world!");
  }
}
