package threading.week6;

public interface Rope {

    void acquire(Position position) throws InterruptedException;

    void release() throws InterruptedException;

    Position goingThrough(Monkey monkey, int delay) throws InterruptedException;
    
}
