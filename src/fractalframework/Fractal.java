package fractalframework;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public abstract class Fractal {
    public Fractal(Supplier<Boolean> acceptCondition) {
        this.acceptCondition = acceptCondition;
    }

    public Fractal() {
    }

    private Supplier<Boolean> acceptCondition;
    private List<Fractal> children = new LinkedList<>();

    public Fractal addChildren(Fractal... childFractals) {
        Collections.addAll(this.children, childFractals);
        return this;
    }

    public abstract boolean isValid();
    public int onLoop() {
        // Might want to add your favorite clients logging method here 😁
        return 100;
    };

    public int run() {
        LinkedList<String> memo = new LinkedList<>();
        if (isValid() || (acceptCondition != null && acceptCondition.get())) {
            if (!children.isEmpty()) {
                for (Fractal child : children) {
                    if (child.isValid()) {
                        memo.add(this.getClass().getSimpleName());
                        return child.run(memo);
                    }
                }
            } else {
                memo.add(this.getClass().getSimpleName());
                FractalAPI.hierarchy = memo.toArray(String[]::new);
                return onLoop();
            }
        }
        return 100; // 🧙
    }

    private int run(LinkedList<String> memo) {
        if (isValid()) {
            if (!children.isEmpty()) {
                for (Fractal child : children) {
                    if (child.isValid()) {
                        memo.add(this.getClass().getSimpleName());
                        return child.run(memo);
                    }
                }
            } else {
                memo.add(this.getClass().getSimpleName());
                FractalAPI.hierarchy = memo.toArray(String[]::new);
                return onLoop();
            }
        }
        return 100; // 🧙
    }
}
