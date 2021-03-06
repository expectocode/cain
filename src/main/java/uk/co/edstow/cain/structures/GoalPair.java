package uk.co.edstow.cain.structures;

import uk.co.edstow.cain.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoalPair<G extends Goal> {
    private final List<G> uppers;
    private final List<G> lowers;
    private final Transformation transformation;

    public List<G> getUppers() {
        return uppers;
    }

    public List<G> getLowers() {
        return lowers;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public GoalPair(G u, G l, Transformation t) {
        uppers = Collections.singletonList(u);
        lowers = Collections.singletonList(l);
        transformation = t;
    }

    public GoalPair(G u, List<G> ls, Transformation t) {
        uppers = Collections.singletonList(u);
        ArrayList<G> list = new ArrayList<>(ls);
        lowers = Collections.unmodifiableList(list);
        transformation = t;
    }

    public GoalPair(List<G> us, List<G> ls, Transformation t) {
        ArrayList<G> ulist = new ArrayList<>(us);
        uppers = Collections.unmodifiableList(ulist);
        ArrayList<G> llist = new ArrayList<>(ls);
        lowers = Collections.unmodifiableList(llist);
        transformation = t;
    }


    @Override
    public String toString() {
        return "{T:" + transformation + ", U: " + uppers.toString() + ", Ls:" + lowers.toString() + "}";
    }

}
