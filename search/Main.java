import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.*;

class Main {

  static class Node {
    int priority;
    int cost;
    ArrayList<ArrayList<String>> current;
    ArrayList<int[]> path;

    public Node(int priority, int cost, ArrayList<ArrayList<String>> current, ArrayList<int[]> path) {
      this.priority = priority;
      this.cost = cost;
      this.current = current;
      this.path = path;
    }

    public void to_String() {
      System.out.println(this.priority + " " + this.cost + " " + this.current);
    }

    public int getPriority() {
      return priority;
    }
  }

  public static boolean check_goal(ArrayList<ArrayList<String>> actual, ArrayList<ArrayList<String>> goal) {
    for(int i = 0; i < goal.size(); i++) {
      if(!goal.get(i).contains("X")) {
        if(!(goal.get(i).equals(actual.get(i)))) {
          return false;
        }
      }
    }
    return true;
  }

  public static int getHeuristic(ArrayList<ArrayList<String>> current, ArrayList<ArrayList<String>> goal, int method) {
    int heuristic = 0;
    for(int i = 0; i < goal.size(); i++) {
      if(!current.get(i).equals(goal.get(i))) {
        heuristic++;
      }
    }
    if(method == 0) {
      // If UCS is wanted
      return 0;
    } else if(method == 1) {
      // If A* is wanted with an admissible heuristic
      return heuristic;
    } else {
      // If A* is wanted with an inadmissible heuristic
      return heuristic * 3;
    }
  }

  public static ArrayList<ArrayList<String>> moveBox (ArrayList<ArrayList<String>> current, int i, int j) {
    String element = "";
    ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
    ArrayList<String> temp_temp = null;
    for(int k = 0; k < current.size(); k++) {
      temp_temp = new ArrayList<String>();
      for(int p = 0; p < current.get(k).size(); p++) {
        temp_temp.add(current.get(k).get(p));
      }
      temp.add(temp_temp);
    }
    for(int k = 0; k < current.get(i).size(); k++) {
      if(current.get(i).get(k) != "") {
        element = current.get(i).get(k);
        temp.get(i).remove(k);
        temp.get(j).add(element);
        break;
      }
    }
    // System.out.println(temp);
    return temp;
  }

  public static void expand(ArrayList<ArrayList<String>> current, int height, ArrayList<Node> frontier, ArrayList<ArrayList<String>> goal, ArrayList<ArrayList<ArrayList<String>>> visited, int cost, ArrayList<int[]> path) {
    int new_cost = 0, priority = 0;
    // System.out.println("Posible actions:");
    ArrayList<ArrayList<String>> moved = new ArrayList<ArrayList<String>>();
    for(int i = 0; i < current.size(); i++) {
      for(int j = 0; j < current.size(); j++) {
        if( i != j) {
          if(current.get(j).size() < height) {
            if(current.get(i).size() != 0) {
              // System.out.println(current + " current");
              new_cost = cost + Math.abs(i - j) + 1;
              moved = moveBox(current, i, j);
              for(int k = 0; k < moved.size(); k++) {
                if(moved.get(k).size() > 1) {
                  if(moved.get(k).contains("")) {
                    int index = moved.get(k).indexOf("");
                    moved.get(k).remove(index);
                    // System.out.println(moved + " " + index);
                  }
                } else if(moved.get(k).size() == 0){
                  moved.get(k).add("");
                }
              }
              // System.out.println(moved);
              priority = new_cost + getHeuristic(current, goal, 1);
              if(!visited.contains(moved) && !frontier.contains(moved)) {
                int[] temp_path = new int[2];
                temp_path[0] = i;
                temp_path[1] = j;
                ArrayList<int[]> new_path = new ArrayList<int[]>();
                if(path.size() > 0) {
                  for(int k = 0; k < path.size(); k++) {
                    new_path.add(path.get(k));
                  }
                }
                new_path.add(temp_path);
                // for(int k = 0; k < new_path.size(); k++) {
                //   System.out.print(Arrays.toString(new_path.get(k)) + " ");
                // }
                // System.out.println();
                Node new_node = new Node(priority, new_cost, moved, new_path);
                frontier.add(new_node);
              }
            }
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    // List<String> lines = new ArrayList<String>();
    // Scanner stdin = new Scanner(System.in);
    // while(stdin.hasNextLine()) {
    //   lines.add(stdin.nextLine());
    // }
    // stdin.close();
    //
    // int height = Integer.parseInt(lines.get(0));
    int height = 2;

    ArrayList<ArrayList<String>> start = new ArrayList<ArrayList<String>>();
    ArrayList<String> iI = new ArrayList<String>();
    // char[] initial = lines.get(1).toCharArray();
    // for (int j = 0; j < initial.length; j++) {
    //   if (initial[j] == ';') {
    //     start.add(iI);
    //     iI = new ArrayList<String>();
    //   }
    //   else if ((initial[j] != '(') && (initial[j] != ')') && (initial[j] != ',') && (initial[j] != ' ')) {
    //     iI.add(String.valueOf(initial[j]));
    //   }
    //   else if ((initial[j] == ')') && (initial[(j - 1)] == '(')) {
    //     iI.add("");
    //   }
    // }
    // start.add(iI);

    ArrayList<ArrayList<String>> goal = new ArrayList<ArrayList<String>>();
    ArrayList<String> fI = new ArrayList<String>();
    // initial = lines.get(2).toCharArray();
    // for (int k = 0; k < initial.length; k++) {
    //   if (initial[k] == ';') {
    //     goal.add(fI);
    //     fI = new ArrayList<String>();
    //   }
    //   else if ((initial[k] != '(') && (initial[k] != ')') && (initial[k] != ',') && (initial[k] != ' ')) {
    //     fI.add(String.valueOf(initial[k]));
    //   }
    //   else if ((initial[k] == ')') && (initial[(k - 1)] == '(')) {
    //     fI.add("");
    //   }
    // }
    // goal.add(fI);



    iI.add("A");
    start.add(iI);
    iI = new ArrayList<String>();
    iI.add("B");
    start.add(iI);
    iI = new ArrayList<String>();
    iI.add("");
    start.add(iI);
    iI = new ArrayList<String>();
    iI.add("");
    start.add(iI);


    fI.add("B");
    fI.add("A");
    goal.add(fI);
    fI = new ArrayList<String>();
    fI.add("");
    goal.add(fI);
    fI = new ArrayList<String>();
    fI.add("");
    goal.add(fI);

    System.out.println(start);
    System.out.println(goal);

    ArrayList<Node> frontier = new ArrayList<>();

    ArrayList<int[]> path = new ArrayList<int[]>();
    Node n = new Node(0,0,start, path);
    frontier.add(n);

    ArrayList<ArrayList<ArrayList<String>>> visited = new ArrayList<ArrayList<ArrayList<String>>>();
    ArrayList<ArrayList<String>> visited_node = new ArrayList<ArrayList<String>>();

    int k = 0;
    while(true) {
      if(frontier.size() == 0) {
        System.out.println("No solution found");
        return;
      }
      int min = 1000;
      int pos = 0, j = 0;
      for(Node node: frontier) {
        if(node.priority < min) {
          min = node.priority;
          pos = j;
        }
        j++;
      }
      Node current = frontier.get(pos);
      frontier.remove(pos);
      if(check_goal(current.current, goal)) {
        System.out.println(current.cost);
        for(int i = current.path.size() - 1; i >= 0; i--) {
          System.out.print(Arrays.toString(current.path.get(i)).replace('[', '(').replace(']', ')'));
          if(i != 0) {
            System.out.print("; ");
          }
        }
        System.out.println();
        return;
      }
      visited_node = current.current;
      visited.add(visited_node);

      // System.out.println(visited);
      expand(current.current, height, frontier, goal, visited, current.cost, current.path);
      k++;
    }
  }
}
