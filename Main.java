import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Main {
  public static int main(String[] args) {
    List<String> lines = new ArrayList<String>();
    Scanner stdin = new Scanner(System.in);
    while(stdin.hasNextLine()) {
      lines.add(stdin.nextLine());
    }
    stdin.close();

    int a = Integer.parseInt(lines.get(0));
    int b = Integer.parseInt(lines.get(1));
    System.out.println(a+b);
    return 0;
  }
}
