package threading.week6;

public enum Position {
    LEFT, RIGHT;

    public Position opposite() {
         switch (this) {
             case LEFT: return RIGHT;
             default:
                 return LEFT;
         }
    }
}
