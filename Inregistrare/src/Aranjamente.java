
import com.sun.org.apache.xml.internal.dtm.Axis;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Aranjamente {
    static class Task {
        public final static String INPUT_FILE = "in";
        public final static String OUTPUT_FILE = "out";

        int n, k;
        ArrayList<ArrayList<Integer>> all = new ArrayList<>();
        private void readInput() {
            try {
                Scanner sc = new Scanner(new File(INPUT_FILE));
                n = sc.nextInt();
                k = sc.nextInt();
                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(ArrayList<ArrayList<Integer>> result) {
            try {
                PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
                pw.printf("%d\n", result.size());
                for (ArrayList<Integer> arr : result) {
                    for (int i = 0; i < arr.size(); i++) {
                        pw.printf("%d%c", arr.get(i), i + 1 == arr.size() ?
                                '\n' : ' ');
                    }
                }
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        public void back(ArrayList<Integer> domain,ArrayList<Integer> solution)
        {
            if(domain.size()==0)
            {
                all.add(solution);
                return;
            }
            int i;
            for(i=0;i<domain.size();i++)
            {
                ArrayList<Integer> newDomain=new ArrayList<>(domain);
                ArrayList<Integer> newSolution=new ArrayList<>(solution);
                newSolution.add(domain.get(i));
                newDomain.remove(i);
                back(newDomain, newSolution);
            }
        }

        private ArrayList<ArrayList<Integer>> getResult() {
            ArrayList<Integer> domain=new ArrayList<>(n);
            ArrayList<Integer> solution=new ArrayList<>();
            for (int i = 0; i < n; ++i) {
                domain.add(i+1);
            }
            back(domain,solution);
 
            

            // TODO: Construiti toate aranjamentele de N luate cate K ale
            // multimii {1, ..., N}.

            // Pentru a adauga un nou aranjament:
            //   ArrayList<Integer> aranjament;
            //   all.add(aranjament);

            return all;
        }

        public void solve() {
            readInput();
            writeOutput(getResult());
        }
    }

    public static void main(String[] args) {
        new Task().solve();
    }
}
