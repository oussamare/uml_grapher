package fr.lernejo.umlgrapher;


import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

    public class MermaidFormatter {

        public String format(InternalGraphRepresentation graphRepresentation) {
            return MermaidUmlTypeRepresentation(graphRepresentation.getUmlType()
                .getListOfClass()) + MermaidUmlRelationRepresentation(graphRepresentation.getUmlRelation()
                .getRelationsBetweenClass());
        }

        private String MermaidUmlTypeRepresentation(Set<Class> classes) {
            String syntax = "classDiagram\n";
            for (Class classe : classes) {
                syntax += "class " + classe.getSimpleName();
                if (Modifier.isInterface(classe.getModifiers())) {
                    syntax +=" {\n    <<interface>>\n}";
                }
                syntax += "\n";
            }
            return syntax;
        }

        private String MermaidUmlRelationRepresentation(List<String[]> relations) {
            String syntax = "";

            for (String[] rel : relations) {
                syntax += rel[0];
                if (rel[2].equals("implements"))
                    syntax += " <|.. ";
                else syntax += " <|-- ";
                syntax += rel[1] + " : " + rel[2];
                syntax += "\n";
            }
            return syntax;
        }
    }

