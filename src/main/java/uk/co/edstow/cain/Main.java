package uk.co.edstow.cain;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import uk.co.edstow.cain.pairgen.PairGenFactory;
import uk.co.edstow.cain.scamp5.*;
import uk.co.edstow.cain.scamp5.emulator.Scamp5Emulator;
import uk.co.edstow.cain.structures.Atom;
import uk.co.edstow.cain.structures.Goal;
import uk.co.edstow.cain.traversal.SOT;
import uk.co.edstow.cain.util.Bounds;
import uk.co.edstow.cain.util.Tuple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

import static uk.co.edstow.cain.RegisterAllocator.Register.*;

@SuppressWarnings("unused")
class Main {
    public static void main(String[] args) {
//        test();
        for (int i = 0; i < args.length; i++) {
            new FileRun(args[i]).run();

        }
//        DemoSuite.runDemo();

    }



    public static void test() {
        List<Goal> final_goals = new ArrayList<>();
        int[] divisions = new int[1];



        int[][] multi3 = new int[][]{
                { 1, 2, 7, 2, 2},
                { 1, 5, 3, 0, 7},
                { 2, 7, 0, 2, 5},
                { 8, 7, 5, 6, 0},
                { 0, 8, 6, 8, 4}
        };
//        final_goals.add(new Goal.Factory(multi3).get());

        int[][] multiSobelV = new int[][]{
                { 0, 0, 0, 0, 0},
                { 0, 1, 0, -1, 0},
                { 0, 2, 0, -2, 0},
                { 0, 1, 0, -1, 0},
                { 0, 0, 0, 0, 0}
        };
//        final_goals.add(new Goal.Factory(multiSobelV).get());
//        divisions[0] = 0;

        int[][] multiBox2x2 = new int[][]{
                { 0, 0, 0, 0, 0},
                { 0, 0, 1, 1, 0},
                { 0, 0, 1, 1, 0},
                { 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0}
        };
        //final_goals.add(new Goal.Factory(multiBox2x2).get());

        int[][] multiBox3x3 = new int[][]{
                { 0, 0, 0, 0, 0},
                { 0, 1, 1, 1, 0},
                { 0, 1, 1, 1, 0},
                { 0, 1, 1, 1, 0},
                { 0, 0, 0, 0, 0}
        };
        //final_goals.add(new Goal.Factory(multiBox3x3).get());

        int[][] multiBox5x5 = new int[][]{
                { 1, 1, 1, 1, 1},
                { 1, 1, 1, 1, 1},
                { 1, 1, 1, 1, 1},
                { 1, 1, 1, 1, 1},
                { 1, 1, 1, 1, 1}
        };
        //final_goals.add(new Goal.Factory(multiBox5x5).get());

        int[][] multiBox7x7 = new int[][]{
                { 1, 1, 1, 1, 1, 1, 1},
                { 1, 1, 1, 1, 1, 1, 1},
                { 1, 1, 1, 1, 1, 1, 1},
                { 1, 1, 1, 1, 1, 1, 1},
                { 1, 1, 1, 1, 1, 1, 1},
                { 1, 1, 1, 1, 1, 1, 1},
                { 1, 1, 1, 1, 1, 1, 1}
        };
        //final_goals.add(new Goal.Factory(multiBox7x7).get());

        int[][] multiGuass3x3 = new int[][]{
                { 0, 0, 0, 0, 0},
                { 0, 1, 2, 1, 0},
                { 0, 2, 4, 2, 0},
                { 0, 1, 2, 1, 0},
                { 0, 0, 0, 0, 0}
        };
//        final_goals.add(new Goal.Factory(multiGuass3x3, 4).get());
//        divisions[0] = 6;
//        final_goals.add(new Goal.Factory(multiGuass3x3).get());
//        divisions = 4;

        int[][] multiGuass5x5 = new int[][]{
                { 0, 1, 2, 1, 0},
                { 1, 4, 6, 4, 1},
                { 2, 6, 10, 6, 2},
                { 1, 4, 6, 4, 1},
                { 0, 1, 2, 1, 0}
        };
        final_goals.add(new Goal.Factory(multiGuass5x5).get());
        divisions[0] = 6;

        int[][] multi1 = new int[][]{
                { 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0},
                { 0, 0, 4, 0, 0},
                { 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0}
        };
        //final_goals.add(new Goal.Factory(multi1).get());
//
//
//        int[][] multi3 = new int[][]{
//                { 0, 0, 0, 0, 0},
//                { 0, 0, 0, 0, 0},
//                { 0, 1, 2, 0, 0},
//                { 0, 2, 5, 2, 0},
//                { 0, 1, 2, 1, 0}
//        };
//        final_goals.add(new Goal.Factory(multi3).get());
//
//        int[][] multi4 = new int[][]{
//                { 0, 1, 2, 1, 0},
//                { 0, 1, 4, 3, 1},
//                { 0, 0, 0, 2, 1},
//                { 0, 0, 0, 0, 0},
//                { 0, 0, 0, 0, 0}
//        };
//        final_goals.add(new Goal.Factory(multi4).get());

//        divisions = new int[]{0,0};
//        final_goals.add(new Goal.Factory(new Atom(0, 0,0,true), new Atom(0, 0,0,true), new Atom(0,0,1,true)).get());

//        divisions = new int[]{0};
//        final_goals.add(new Goal());
//        final_goals.add(new Goal());

        RegisterAllocator.Register[] availableRegisters = new RegisterAllocator.Register[]{A, B, C, D, E, F};
        RegisterAllocator registerAllocator = new RegisterAllocator(new RegisterAllocator.Register[]{A}, availableRegisters);

        PairGenFactory pairGenFactory = new Scamp5PairGenFactory<>(rs -> new ThresholdConfigGetter(rs, availableRegisters));

        ReverseSearch.RunConfig config = new ReverseSearch.RunConfig();
        config.setWorkers(4)
                .setRegisterAllocator(registerAllocator).setLivePrintPlans(2)
                .setTimeOut(true).setLiveCounter(true).setSearchTime(6000)
                .setTraversalAlgorithm(SOT.SOTFactory())
        .setForcedDepthReduction(1)
        .setForcedCostReduction(0);
        ReverseSearch rs = new ReverseSearch(divisions, final_goals, pairGenFactory, config);
        rs.search();

        System.out.println("print plans");
        double min = Double.MAX_VALUE;
        int iMin = 0;
        for (int i = 0; i < rs.getPlans().size(); i ++){
            Plan pl = rs.getPlans().get(i);
            System.out.println("=======================================================================================");
            System.out.println(pl);

            System.out.println("length: " + pl.depth() + " Cost: "+rs.costFunction.apply(pl));
            System.out.println("CircuitDepths:" + Arrays.toString(pl.circuitDepths()));
            //System.out.println(pl.toGoalsString());
            RegisterAllocator.Mapping mapping = registerAllocator.solve(pl, rs.getInitialGoals());
//            System.out.println(mapping);
            String code = pl.produceCode(mapping);
//            System.out.println(code);

            emulate(final_goals, divisions, availableRegisters, registerAllocator, code, false);

            if(rs.costFunction.apply(pl) < min){
                iMin = i;
                min = rs.costFunction.apply(pl);
            }
        }
        rs.printStats();

        System.out.println("Best");
        Plan p = rs.getPlans().get(iMin);
        System.out.println("length: " + p.depth() + " Cost: "+rs.costFunction.apply(p));
        System.out.println("CircuitDepths:" + Arrays.toString(p.circuitDepths()));
        System.out.println(p);
        System.out.println(p.toGoalsString());
        RegisterAllocator.Mapping mapping = registerAllocator.solve(p, rs.getInitialGoals());
        System.out.println(mapping);
        String code = p.produceCode(mapping);
        System.out.println(code);

        emulate(final_goals, divisions, availableRegisters, registerAllocator, code, true);


    }

    private static void emulate(List<Goal> final_goals, int[] divisions, RegisterAllocator.Register[] availableRegisters, RegisterAllocator registerAllocator, String code, boolean full) {
//        System.out.println(new Bounds(final_goals).largestMagnitude());
        Scamp5Emulator emulator = new Scamp5Emulator(new Bounds(final_goals).largestMagnitude()*2);
        RegisterAllocator.Register[] initRegisters = registerAllocator.getInitRegisters();
        for (int i = 0; i < initRegisters.length; i++) {
            RegisterAllocator.Register r = initRegisters[i];
            emulator.run(String.format("input(%s,%d)", r, (1 << divisions[i]) * 128));
        }
        emulator.pushCode(code);
        emulator.flushInstructionBuffer();
        for (int i = 0; i < final_goals.size(); i++) {
            System.out.println("Goal: " + i + " In: " + availableRegisters[i].toString());
//            System.out.println(final_goals.get(i));
            Map<Tuple<Integer, Tuple<Integer, String>>, Double> testMap = emulator.getRawProcessingElementContains(0, 0, availableRegisters[i].toString());
//            System.out.println(testMap);
            System.out.println(emulator.getRegToString(0, 0, availableRegisters[i].toString()));

            Goal.Factory factory = new Goal.Factory();
            testMap.forEach((tuple, d) -> {
                if(d!=0) {
                    factory.add(new Atom(tuple.getA(), tuple.getB().getA(), Arrays.binarySearch(RegisterAllocator.Register.values(), RegisterAllocator.Register.valueOf(tuple.getB().getB())), d >= 0), Math.abs(d.intValue()));
                }
            });

            Goal testOut = factory.get();


            String trueOut = "true out:\n" + testOut.getCharTableString(true, true, true, true);
            String targetOut = "target out:\n" + final_goals.get(i).getCharTableString(false, false, true, true);
            if(full) {
                System.out.println(trueOut);
                System.out.println(targetOut);
            }
            boolean error = false;
            Iterator<Tuple<Atom, Integer>> iterator = final_goals.get(i).uniqueCountIterator();
            while (iterator.hasNext()){
                Tuple<Atom, Integer> t = iterator.next();
                Tuple<Integer, Tuple<Integer, String>> coordinate = Tuple.triple(t.getA().x, t.getA().y, RegisterAllocator.Register.values()[t.getA().z].toString());
                Double d = testMap.get(coordinate);
                int expected = t.getA().positive? t.getB(): -t.getB();
                if(d == null || Double.compare(expected, d) != 0){
                    System.out.println("INTEGRITY CHECK ERROR");
                    System.out.println(coordinate);
                    System.out.println(d);
                    System.out.println(t.getB());
                    error = true;
                }
                testMap.remove(coordinate);
            }
            if(!testMap.isEmpty()){
                System.out.println("INTEGRITY CHECK ERROR!");
                System.out.println(testMap);
                error = true;
            }
            if(error) {
                System.out.println(trueOut);
                System.out.println(targetOut);
            }

        }
    }

}