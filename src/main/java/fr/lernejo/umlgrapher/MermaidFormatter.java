package fr.lernejo.umlgrapher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
                String fieldString = MermaidFieldRepresentation(classe);
                String methodString = MermaidMethodRepresentation(classe);
                boolean condition = !(fieldString + methodString).equals("") || Modifier.isInterface(classe.getModifiers());
                if (condition) syntax +=" {\n";
                if (Modifier.isInterface(classe.getModifiers())) {
                    syntax +="    <<interface>>\n";
                    condition = true;
                }
                syntax += fieldString;
                syntax += methodString;
                if (condition) syntax +="}";
                syntax += "\n";
            }
            return syntax;
        }

        private String MermaidUmlRelationRepresentation(List<String[]> relations) {
            String syntax = "";

            for (String[] rel : relations) {
                syntax += rel[0];
                if (rel[2].equals("implements")) syntax += " <|.. ";
                else if (rel[2].equals("extends")) syntax += " <|-- ";
                else syntax += " <-- ";
                syntax += rel[1] + " : " + rel[2];
                syntax += "\n";
            }
            return syntax;
        }

        private String MermaidFieldRepresentation(Class c) {
            String syntax = "";
            for (Field field : c.getDeclaredFields()) {
                if (!field.isSynthetic()) {
                    syntax += "    " + (Modifier.isPrivate(field.getModifiers()) ? "-" : "+");
                    syntax += field.getType().getSimpleName() + " " + field.getName();
                    syntax += (Modifier.isStatic(field.getModifiers()) ? "$" : "") + "\n";
                }
            }
            return syntax;
        }

        private String MermaidMethodRepresentation(Class c) {
            String syntax = "";
            for (Method method : c.getDeclaredMethods()) {
                if (!method.isSynthetic()) {
                    syntax += "    " + (Modifier.isPrivate(method.getModifiers()) ? "-" : "+");
                    syntax += method.getName() + "(";
                    ArrayList<String> params = new ArrayList<>();
                    for (Parameter param : method.getParameters()) {
                        params.add(param.getType().getSimpleName() + " " + param.getName());
                    }
                    syntax += params.stream().collect(Collectors.joining(",")) + ")";
                    syntax += (Modifier.isStatic(method.getModifiers()) ? "$"
                        : Modifier.isAbstract(method.getModifiers()) ? "*" : "") + " ";
                    syntax += method.getReturnType().getSimpleName() + "\n";
                }
            }
            return syntax;
        }
    }
