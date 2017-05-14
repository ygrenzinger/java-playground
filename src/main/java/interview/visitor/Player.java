package interview.visitor;

public interface Player {
    void play(CD cd);
    void play(DVD dvd);
    void play(BluRay bluRay);
}