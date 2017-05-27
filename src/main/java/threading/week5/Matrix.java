package threading.week5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

import static java.lang.Integer.parseInt;

public class Matrix {

    private final int rows;
    private final int columns;
    private final int[][] data;

    public Matrix(int rows, int columns, int[][] data) {
        this.rows = rows;
        this.columns = columns;
        this.data = data;
    }

    public static Matrix of(File file) throws FileNotFoundException {
        try (Scanner input = new Scanner(file)) {
            String[] line = input.nextLine().split(" ");
            int rows = parseInt(line[0]);
            int columns = parseInt(line[1]);
            int[][] data = new int[rows][columns];

            for (int i = 0; i < rows; i++) {
                line = input.nextLine().split(" ");
                for (int j = 0; j < columns; j++) {
                    data[i][j] = parseInt(line[j]);
                }
            }
            return new Matrix(rows, columns, data);
        }
    }

    public static Matrix product(Matrix m1, Matrix m2) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        int rows = m1.rows;
        int columns = m1.columns;
        int[][] data = new int[rows][columns];
        List<MatrixProductUnit> computeUnits = new ArrayList<>(rows *m2.columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m2.columns; j++) {
                computeUnits.add(new MatrixProductUnit(m1, m2, i, j));
            }
        }
        List<Future<MatrixProductUnitResult>> futures = executor.invokeAll(computeUnits);
        executor.shutdown();
        for (Future<MatrixProductUnitResult> future : futures) {
            MatrixProductUnitResult result = future.get();
            data[result.row][result.column] = result.result;
        }
        
        return new Matrix(rows, m2.columns, data);
    }

    public int at(int row, int column) {
        return data[row][column];
    }

    private static class MatrixProductUnitResult {

        private final int row;
        private final int column;
        private final int result;

        private MatrixProductUnitResult(int row, int column, int result) {
            this.row = row;
            this.column = column;
            this.result = result;
        }
    }


    private static class MatrixProductUnit implements Callable<MatrixProductUnitResult> {
        private final Matrix m1;
        private final Matrix m2;
        private final int row;
        private final int column;

        MatrixProductUnit(Matrix m1, Matrix m2, int row, int column) {
            this.m1 = m1;
            this.m2 = m2;
            this.row = row;
            this.column = column;
        }

        @Override
        public MatrixProductUnitResult call() throws Exception {
            if (!(row < m1.rows && column < m2.columns)) {
                throw new IllegalArgumentException();
            }
            int n = 0;
            int result = 0;
            while (n < m2.rows) {
                result += m1.at(row, n) * m2.at(n, column);
                n++;
            }
            return new MatrixProductUnitResult(row, column, result);
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }

}
