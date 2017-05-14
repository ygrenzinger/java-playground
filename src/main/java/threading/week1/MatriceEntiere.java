package threading.week1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

class MatriceEntiere {

    private final int lignes;
    private final int colonnes;
    private final int[][] data;

    private MatriceEntiere(int lignes, int colonnes) {
        data = new int[lignes][colonnes];
        this.lignes = lignes;
        this.colonnes = colonnes;
    }

    MatriceEntiere(File file) throws FileNotFoundException {
        try (Scanner input = new Scanner(file)) {
            String[] line = input.nextLine().split(" ");
            lignes = parseInt(line[0]);
            colonnes = parseInt(line[1]);
            data = new int[lignes][colonnes];

            for (int i = 0; i < lignes; i++) {
                line = input.nextLine().split(" ");
                for (int j = 0; j < colonnes; j++) {
                    data[i][j] = parseInt(line[j]);
                }
            }
        }
    }

    int getElem(int i, int j) {
        return data[i][j];
    }

    void setElem(int i, int j, int val) {
        data[i][j] = val;
    }

    static int produitLigneColonne(MatriceEntiere m1, int i,
                                   MatriceEntiere m2, int j)
            throws TaillesNonConcordantesException {
        if (!(i < m1.lignes && j < m2.colonnes)) {
            throw new TaillesNonConcordantesException();
        }
        int n = 0;
        int sum = 0;
        while (n < m2.lignes) {
            sum += m1.getElem(i, n) * m2.getElem(n, j);
            n++;
        }
        return sum;
    }

    static class TaillesNonConcordantesException extends Exception {
    }

    static MatriceEntiere productMatrix(MatriceEntiere m1, MatriceEntiere m2) throws InterruptedException {
        ExecutorService taskExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        MatriceEntiere result = new MatriceEntiere(m1.lignes, m2.colonnes);
        for (int i = 0; i < m1.lignes; i++) {
            for (int j = 0; j < m2.colonnes; j++) {
                taskExecutor.execute(new CalculElem(m1, m2, result, i, j));
            }
        }
        taskExecutor.shutdown();
        taskExecutor.awaitTermination(2L, TimeUnit.SECONDS);
        return result;
    }

    public static class CalculElem implements Runnable {
        private final MatriceEntiere m1;
        private final MatriceEntiere m2;
        private final MatriceEntiere result;
        private final int i;
        private final int j;

        CalculElem(MatriceEntiere m1, MatriceEntiere m2, MatriceEntiere result, int i, int j) {
            this.m1 = m1;
            this.m2 = m2;
            this.result = result;
            this.i = i;
            this.j = j;
        }

        @Override
        public void run() {
            try {
                int v = MatriceEntiere.produitLigneColonne(m1, i, m2, j);
                result.setElem(i, j, v);
            } catch (TaillesNonConcordantesException e) {
                System.out.println("Erreur de taille de matrice");
            }
        }
    }
}
