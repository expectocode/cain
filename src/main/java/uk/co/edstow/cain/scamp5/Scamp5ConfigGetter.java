package uk.co.edstow.cain.scamp5;

import uk.co.edstow.cain.pairgen.Config;
import uk.co.edstow.cain.pairgen.PairGenFactory;
import uk.co.edstow.cain.structures.Goal;
import uk.co.edstow.cain.structures.GoalBag;

public interface Scamp5ConfigGetter<G extends Goal<G>, T extends Scamp5Config<G>> {
    PairGenFactory.PairGen<G> getScamp5Strategy(GoalBag<G> goals, Config<G> config, boolean movOnly);
    default PairGenFactory.PairGen<G> getScamp5Strategy(GoalBag<G> goals, Config<G> config){
        return getScamp5Strategy(goals, config, false);
    }
    T getScamp5ConfigForDirectSolve(GoalBag<G> goals, Config<G> config);
}
