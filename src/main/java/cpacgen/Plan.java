package cpacgen;

import cpacgen.util.Bounds;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Plan {
    private List<Step> start = new ArrayList<>();
    private List<Step> all = new ArrayList<>();
    private List<Step> head = new ArrayList<>();
    private final Goal initalGoal;

    public Plan(Goal.Bag finalGoal, Goal initalGoal, String comment){
        finalGoal.setImmutable();
        Goal.Pair p = new Goal.Pair(null, finalGoal, new Transformation.Null());
        Step s = new Step(p, finalGoal, comment);
        start.add(s);

        all.addAll(start);
        head.addAll(start);
        this.initalGoal = initalGoal;
    }

    private Plan(List<Step> init, Goal initalGoal){
        start.addAll(init);
        this.initalGoal = initalGoal;
    }

    public Plan newAdd(Goal.Pair newPair, Goal.Bag currentGoals, String comment) {
        Plan out = new Plan(start, this.initalGoal);
        out.all.addAll(all);
        out.head.addAll(head);
        Step newStep = new Step(newPair, currentGoals, comment);
        for(Step step: out.all){
            for(Goal lowerGoal: step.goalPair.getLowers()){
                if(lowerGoal.same(newPair.getUpper())){
                    newStep.links.add(step);
                    out.head.remove(step);
                }
            }
        }
        out.all.add(newStep);
        out.head.add(newStep);
        return out;
    }

    public List<Step> getAll() {
        return Collections.unmodifiableList(all);
    }

    public String produceCode(Map<Integer, RegisterAllocator.Register> registerMap) {
        StringBuilder sb = new StringBuilder("Kernel Code!\n");
        for (int i = all.size()-1; i >= 0; i--) {
            Step step = all.get(i);
            RegisterAllocator.Register upper = registerMap.get(i);
            List<RegisterAllocator.Register> lowers = new ArrayList<>();
            for(Goal lower: step.getLowers()){
                int lowerStep = -1;
                for (int j = i+1; j < all.size() && lowerStep<0; j++) {
                    if(all.get(j).getUpper().same(lower)){
                        lowerStep = j;
                    }
                }
                if (lowerStep < 0 && lower.same(this.initalGoal)){
                    lowerStep = all.size();
                }
                assert lowerStep > 0;
                lowers.add(registerMap.get(lowerStep));
            }
            sb.append(step.code(upper, lowers));
            sb.append("\n");
        }
        return sb.toString();
    }

    public static class Step {
        private final String comment;
        private final Goal.Pair goalPair;
        private final Goal.Bag currentGoals;
        private final List<Step> links;
        private String name;

        private Step(Goal.Pair t, Goal.Bag currentGoals, String comment) {
            goalPair = t;
            this.comment = comment;
            links = new ArrayList<>();
            this.currentGoals = new Goal.Bag(currentGoals);
        }

        public Goal.Bag liveGoals(){
            return currentGoals;
        }
        public Goal getUpper(){
            return goalPair.getUpper();
        }
        public List<Goal> getLowers(){
            return goalPair.getLowers();
        }


        @Override
        public String toString() {
            return "Step{" +
                    "goalPair=" + goalPair.toString() +
                    ", " + comment +
                    '}';
        }

        public String toStringN() {
            return name +" " + goalPair.toStringN() +
                    "\n" + comment;
        }

        private void addEdgesToGraph(Graph graph){
            Node node = graph.getNode(name);
            node.addAttribute("ui.label", toStringN());

            node.setAttribute("ui.class", "basicBlock");

            for(Step s: links) {
                graph.addEdge(name + "-" +s.name, name, s.name, true);
                //s.addEdgesToGraph(graph);
            }
        }

        public String toGoalsString() {

            Bounds b = new Bounds(new Bounds(currentGoals), new Atom(0,0,0, true));
            int height = 1 + b.yMax - b.yMin;
            int width = 1 + b.xMax - b.xMin;
            List<String[][]> arrays = new ArrayList<>();

            for (int i = 0; i < currentGoals.size(); i++) {
                String[][] tableArray = currentGoals.get(i).getCharTable(b, width, height, this.goalPair.getLowers().contains(currentGoals.get(i)));
                arrays.add(tableArray);
            }

            StringBuilder sb = new StringBuilder();
            for (int j = height+1; j >= 0; j--) {
                for (String[][] array : arrays) {
                    for (int i = 0; i < array[j].length; i++) {
                        sb.append(array[j][i]);
                    }
                    sb.append(' ');
                }
                sb.append("\n");
            }
            return sb.toString();
        }

        public String code(RegisterAllocator.Register upper, List<RegisterAllocator.Register> lowers) {
            return goalPair.getTransformation().code(upper, lowers);
        }
    }

    public Double cost(){
        return all.stream().mapToDouble(x -> x.goalPair.getTransformation().cost()).sum();
    }

    public int depth(){
        return all.size()-1;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("plan:\n");
        for (int i = 0; i<all.size(); i++) {
            sb.append(i+1);
            sb.append(": ");
            sb.append(all.get(i).toString());
            sb.append('\n');
        }
        return sb.toString();
    }

    public String toGoalsString(){
        StringBuilder sb = new StringBuilder("plan:\n");
        for (int i = 0; i<all.size(); i++) {
            sb.append(i);
            sb.append(": ");
            sb.append(all.get(i).toStringN());
            sb.append("\n");
            sb.append(all.get(i).toGoalsString());
            sb.append('\n');
        }
        return sb.toString();
    }


    public void display(){
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Graph graph = new SingleGraph("Plan");

        graph.setStrict(true);
        String styleSheet =
                "node { stroke-mode: plain; " +
                        "fill-color: black; " +
                        "shape: rounded-box; " +
                        "size: 50px, 50px; " +
                        "text-size:24; " +
                        "padding: 4px, 4px; " +
                        "text-alignment: at-right;" +
                        "text-padding: 3px;" +
                        "text-background-mode: plain;" +
                        "}" +
                "edge { arrow-shape: arrow; " +
                        "arrow-size: 10px, 10px; " +
                        "}";
        graph.addAttribute("ui.stylesheet",styleSheet);
        for (int i = 0; i < all.size(); i++) {
            all.get(i).name = "N"+i;
            graph.addNode(all.get(i).name);
        }
        for (Step s: all){
            s.addEdgesToGraph(graph);
        }

        Viewer viewer = graph.display();
        viewer.enableAutoLayout();
        //viewer.addDefaultView(false).getCamera().setViewPercent(0.5);



    }




}
