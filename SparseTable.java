package SparseTable;


public class SparseTable {
    private int[][] table;
    private int[] log;

    // Build the Sparse Table
    public SparseTable(int[] arr) {
        int n = arr.length;
        int maxLog = (int) (Math.log(n) / Math.log(2)) + 1;

        // Initialize Sparse Table and Log Array
        table = new int[n][maxLog];
        log = new int[n + 1];

        // Precompute the logarithms
        for (int i = 2; i <= n; i++) {
            log[i] = log[i / 2] + 1;
        }

        // Build the table for the first level
        for (int i = 0; i < n; i++) {
            table[i][0] = arr[i];
        }

        // Build the Sparse Table for higher levels
        for (int j = 1; (1 << j) <= n; j++) {
            for (int i = 0; (i + (1 << j)) <= n; i++) {
                table[i][j] = Math.max(table[i][j - 1], table[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    // Query the maximum in range [L, R]
    public int query(int L, int R) {
        int j = log[R - L + 1];
        return Math.max(table[L][j], table[R - (1 << j) + 1][j]);
    }
}
