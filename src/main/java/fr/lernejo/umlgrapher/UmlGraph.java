package fr.lernejo.umlgrapher;


public class UmlGraph {
    private final Class[] classes;

    public UmlGraph(Class[] classes) {
        this.classes = classes;
    }

    public String as(GraphType graphType) {
        String resulta = "";
        if (graphType == GraphType.Mermaid) {
            InternalGraphRepresentation graph = new InternalGraphRepresentation(classes);
            resulta = new MermaidFormatter().format(graph);
        }

        return resulta;
    }
}
