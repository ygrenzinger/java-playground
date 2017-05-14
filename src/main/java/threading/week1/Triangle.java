package threading.week1;

import threading.graphique.Fenetre;

import javax.swing.*;
import java.awt.*;

public class Triangle {

    private static class DessineLigne implements Runnable {

        private final Point a;
        private final Point b;
        private final Fenetre fenetre;

        DessineLigne(Point a, Point b, Fenetre fenetre) {
            this.a = a;
            this.b = b;
            this.fenetre = fenetre;
        }

        @Override
        public void run() {
            fenetre.tracerLignePointAPoint(a, b);
        }
    }


    public static void main(String... args) {
        Fenetre fenetre = new Fenetre(100, 150, "Exercice 1");

        Thread a = new Thread(new DessineLigne(new Point(50,20), new Point(80,80), fenetre));
        a.start();
        Thread b = new Thread(new DessineLigne(new Point(50,20), new Point(20,80), fenetre));
        b.start();
        Thread c = new Thread(new DessineLigne(new Point(20,80), new Point(80,80), fenetre));
        c.start();
    }
}
