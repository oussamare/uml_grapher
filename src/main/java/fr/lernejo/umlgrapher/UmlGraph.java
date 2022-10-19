package fr.lernejo.umlgrapher;

import java.lang.reflect.Modifier;

public class UmlGraph {
    private final Class[] classes;

    public UmlGraph(Class[] classes) {
        this.classes = classes;
    }

    public String as(GraphType graphType) {
        return this.getMermaidSyntax();
    }

    private String getMermaidSyntax() {
        String syntax = "classDiagram\n";
        for (Class classe : this.classes) {
            syntax += "class " + classe.getSimpleName() + " {\n";
            if (Modifier.isInterface(classe.getModifiers())) {
                syntax +="    <<interface>>\n";
            }
            syntax += "}\n";
        }
        return syntax;
    }
}
