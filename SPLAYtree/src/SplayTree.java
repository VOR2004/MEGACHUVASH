import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SplayTree<Key extends Comparable<Key>, Value>  {
    private Node root;
    private class Node {
        private Key key;
        private Value value;
        private Node left, right;

        public Node(Key key, Value value) {
            this.key   = key;
            this.value = value;
        }
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }
    public Value get(Key key) {
        root = splay(root, key);
        int cmp = key.compareTo(root.key);
        if (cmp == 0) return root.value;
        else return null;
    }

    public void put(Key key, Value value) {
        if (root == null) {
            root = new Node(key, value);
            return;
        }

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        if (cmp < 0) {
            Node n = new Node(key, value);
            n.left = root.left;
            n.right = root;
            root.left = null;
            root = n;
        }

        else if (cmp > 0) {
            Node n = new Node(key, value);
            n.right = root.right;
            n.left = root;
            root.right = null;
            root = n;
        }

        else {
            root.value = value;
        }

    }
    public void remove(Key key) {
        if (root == null) return;

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        if (cmp == 0) {
            if (root.left == null) {
                root = root.right;
            }
            else {
                Node x = root.right;
                root = root.left;
                splay(root, key);
                root.right = x;
            }
        }
    }
    private Node splay(Node h, Key key) {
        if (h == null) return null;
        int cmp1 = key.compareTo(h.key);
        if (cmp1 < 0) {
            if (h.left == null) {
                return h;
            }
            int cmp2 = key.compareTo(h.left.key);
            if (cmp2 < 0) {
                h.left.left = splay(h.left.left, key);
                h = rotateRight(h);
            }
            else if (cmp2 > 0) {
                h.left.right = splay(h.left.right, key);
                if (h.left.right != null)
                    h.left = rotateLeft(h.left);
            }
            if (h.left == null) return h;
            else                return rotateRight(h);
        }
        else if (cmp1 > 0) {
            if (h.right == null) {
                return h;
            }
            int cmp2 = key.compareTo(h.right.key);
            if (cmp2 < 0) {
                h.right.left  = splay(h.right.left, key);
                if (h.right.left != null)
                    h.right = rotateRight(h.right);
            }
            else if (cmp2 > 0) {
                h.right.right = splay(h.right.right, key);
                h = rotateLeft(h);
            }
            if (h.right == null) return h;
            else                 return rotateLeft(h);
        }
        else return h;
    }

    public int height() { return height(root); }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }


    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return 1 + size(x.left) + size(x.right);
    }

    // right rotate
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }

    // left rotate
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }


    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
    public static void main(String[] args) {
        SplayTree<Integer, Integer> tree = new SplayTree<>();
        int[] randoms = new int[10000];
        for (int i = 0; i < 10000; i++) {
            randoms[i] = getRandom(0, 1000000);
        }
        int[] randoms1 = new int[10000];
        for (int i = 0; i < 10000; i++) {
            randoms1[i] = getRandom(0, 1000000);
        }

        for (int i = 0; i < 10000; i++) {
            long start = System.nanoTime();
            tree.put(randoms[i], randoms1[i]);
            long fin = System.nanoTime();
            long start1 = System.nanoTime();
            tree.get(randoms1[i]);
            long fin1 = System.nanoTime();
            long start2 = System.nanoTime();
            tree.remove(randoms[i]);
            long fin2 = System.nanoTime();
            try {
                FileWriter writer = new FileWriter("test.txt", true);
                FileWriter writer1 = new FileWriter("test1.txt", true);
                FileWriter writer2 = new FileWriter("test2.txt", true);

                writer.write(""+(fin-start)/1e6);
                writer.write("\n");
                writer1.write(""+(fin1-start1)/1e6);
                writer1.write("\n");
                writer2.write(""+(fin2-start2)/1e6);
                writer2.write("\n");

                writer.close();
                writer1.close();
                writer2.close();

            } catch (IOException e) {
                System.out.println("Возникла ошибка во время записи, проверьте данные.");
            }
        }
    }
}