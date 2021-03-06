package uk.co.edstow.cain.atom.pairGen;

import uk.co.edstow.cain.atom.Atom;
import uk.co.edstow.cain.atom.AtomGoal;
import uk.co.edstow.cain.pairgen.Context;
import uk.co.edstow.cain.structures.GoalBag;
import uk.co.edstow.cain.structures.GoalPair;
import uk.co.edstow.cain.util.Tuple;

import java.util.*;
import java.util.stream.Collectors;

public class SortPairGenFactory extends SimplePairGenFactory {

    public SortPairGenFactory(AtomGoal.AtomBounds bounds) {
        super(bounds);
    }

    @Override
    public PairGen<AtomGoal> generatePairs(GoalBag<AtomGoal> goals, Context<AtomGoal> context) {
        return new AddSortPairGen(goals);
    }

    private class AddSortPairGen extends SimplePairGen {
        private final HashSet<AtomGoal> goals;
        AddSortPairGen(GoalBag<AtomGoal> goals) {
            super(goals);
            this.goals = new HashSet<>(goals.asList());
        }

        @Override
        void putTransformations(AtomGoal upper) {
            List<GoalPair<AtomGoal>> pairs = getAddTransformations(upper);
            pairs.addAll(getUnaryTransformations(upper));

            List<Tuple<GoalPair<AtomGoal>, Double>> list = new ArrayList<>(pairs.size());
            for (GoalPair<AtomGoal> pair: pairs){
                HashSet<AtomGoal> goalSet = new HashSet<>(this.goals);
                goalSet.removeAll(pair.getUppers());
                goalSet.addAll(pair.getLowers());
                double v = 0;
                for (AtomGoal g: goalSet){
                    int i = 0;
                    for (Atom a: g){
                        i += a.x + a.y + a.z;
                    }
                    v += i;
                    v += Math.pow(g.atomCount(), 2);
                }
                list.add(new Tuple<>(pair, v));
            }
            list.sort(Comparator.comparingDouble(Tuple::getB));
            appendCurrentList(list.stream().map(Tuple::getA).collect(Collectors.toList()));

        }
    }
}
