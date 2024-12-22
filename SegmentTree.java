//this is for sum, can be modified for max, min, gcd, preety cool
// do ques 
//   307. Range Sum Query - Mutable
//   2940. Find Building Where Alice and Bob Can Meet( think of bs then not very intuitive)
public class SegmentTree {

    private static class Node {
        int val;
        int start;
        int end;
        Node left;
        Node right;


        public Node(int start, int end, int val) {
            this.start = start;
            this.end = end;
            this.val = val;
        }
        public Node(int start, int end) {
            this(start, end, 0);
        }
    }

    private final Node root;

    public SegmentTree(int[] arr) {
        this.root = build(0, arr.length - 1, arr);
    }

    public int find(int start, int end) {
        return this.find(this.root, start, end);
    }

    public void update(int index, int value) {
        this.update(this.root, index, value);
    }

    private Node build(int start, int end, int[] arr) {
        if (start == end) {
            // Base case: Leaf node (single element)
            return new Node(start, end, arr[start]);
        }

        int mid = start + (end - start) / 2; // Find the middle index
        Node node = new Node(start, end);
        node.left = build(start, mid, arr);
        node.right = build(mid + 1, end, arr);
        node.val = node.left.val + node.right.val;
        return node;
    }

    private int find(Node node, int start, int end) {
        // No overlap: The range [start, end] does not intersect with this node's range
        if (end < node.start || start > node.end) {
            return 0;
        }

        // Full overlap: The range [start, end] fully contains this node's range
        if (start <= node.start && end >= node.end) {
            return node.val;
        }

        // Partial overlap: Query both left and right subtrees
        int left = this.find(node.left, start, end);
        int right = this.find(node.right, start, end);
        return left + right; // Combine the results
    }

    // Private method to update a value in the segment tree
    private int update(Node node, int index, int value) {
        // If the index is out of the range represented by this node, return the current value
        if (node.start > index || node.end < index) {
            return node.val;
        }

        // If the node is a leaf and matches the index, update its value
        if (node.start == index && node.end == index) {
            node.val = value;
            return value;
        }

        // Otherwise, recursively update the left or right subtree
        int left = this.update(node.left, index, value);
        int right = this.update(node.right, index, value);
        node.val = left + right; // Update the current node's value based on children
        return node.val;
    }
}
