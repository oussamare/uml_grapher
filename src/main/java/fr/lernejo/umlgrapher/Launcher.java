package fr.lernejo.umlgrapher;

import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

public class Launcher implements Callable<Integer> {
    @Option(names = {"-c", "--classes"}, description = "Fill in the classes used for the analysis.", required = true)
    private final Class[] classes = null;
    @Option(names = {"-g", "--graph-type"}, description = "Allows you to select the type of graph you want.")
    private final GraphType graphType = GraphType.Mermaid;

    @Override
    public Integer call() {
        UmlGraph graph = new UmlGraph(classes);
        String output = graph.as(graphType);
        System.out.println(output);
        return 0;
    }

    public static void main(String... args) {
        try {
            int exitCode = new CommandLine(new Launcher()).execute(args);
            System.exit(exitCode);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }
}
