package uk.co.edstow.cpacgen.pairgen;

import uk.co.edstow.cpacgen.Goal;
import uk.co.edstow.cpacgen.ReverseSplit;
import uk.co.edstow.cpacgen.util.Tuple;

import java.util.Collection;
import java.util.List;

public interface PairGenFactory {
    Collection<Tuple<List<Goal.Pair>, Goal>> applyAllUnaryOpForwards(Goal initialGoal, int depth, Goal goal);

    interface PairGen{
        Goal.Pair next();
    }

    void init(ReverseSplit rs);

    PairGen generatePairs(Goal.Bag goals, int depth);

}
