package uk.co.edstow.cain.nonlinear;

import uk.co.edstow.cain.Transformation;
import uk.co.edstow.cain.pairgen.Context;
import uk.co.edstow.cain.pairgen.PairGenFactory;
import uk.co.edstow.cain.structures.Goal;
import uk.co.edstow.cain.util.Tuple;

import java.util.Collection;
import java.util.List;

public interface LinearPairGenFactory<G extends Goal<G>> extends PairGenFactory<G>{
    Collection<Tuple<List<G>, Transformation>> generateValueConstantOps(List<G> goal, Context<G> context);
}
