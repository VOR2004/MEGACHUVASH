import java.util.*;

public class PatienceSort {
    private static class Pile extends ArrayList<Integer> implements Comparable<Pile> {
        @Override
        public int compareTo(Pile other) {
            return this.get(this.size() - 1).compareTo(other.get(other.size() - 1));
        }
    }
    private static long iterations = 0;

    public static void patienceSort(List<Integer> n) {
        List<Pile> piles = new ArrayList<>();
        for (Integer x : n) {
            int left = 0;
            int right = piles.size()-1;

            // binary search
            while (left <= right) {
                int i = left + (right - left) / 2;
                if (x.compareTo(piles.get(i).get(piles.get(i).size() - 1)) < 0) {
                    right = i - 1;
                } else {
                    left = i + 1;
                }
                iterations++;
            }
            if (left == piles.size()) {
                Pile temp = new Pile();
                piles.add(temp);
            }
            piles.get(left).add(x);
        }
        n.clear();
        List<Integer> merged = mergePiles(piles);
        n.addAll(merged);
    }
private static List<Integer> mergePiles(List<Pile> piles) {
    List<Integer> result = new ArrayList<>();
    while (!piles.isEmpty()) {
        Pile smallest = minimal(piles);
        result.add(smallest.remove(smallest.size() - 1));
        if (smallest.isEmpty()) {
            piles.remove(smallest);
        }
        iterations++;
    }
    return result;
}

    public static long getIterations() {
        return iterations;
    }
    private static Pile minimal(List<Pile> p) {
        Iterator<? extends Pile> i = p.iterator();
        Pile candidate = i.next();

        while (i.hasNext()) {
            Pile next = i.next();
            if (next.compareTo(candidate) < 0)
                candidate = next;
            iterations++;
        }
        return candidate;
    }


    public static void main(String[] args) {
        List<Integer> arr = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6 ,7 ,8));
        long start = System.nanoTime();
        patienceSort(arr);
        long fin = System.nanoTime();
        System.out.println((fin-start)/1e6);
        System.out.println(arr);
        System.out.println(getIterations());
    }
}