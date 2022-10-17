package fr.lernejo.umlgrapher;

public class UmlGraph {
    private final Class class_name;
    public UmlGraph(Class class_name){
        this.class_name = class_name;
    }
    public String as(GraphType graphType){
        String chaine = "classDiagram\n";
        switch(graphType) {
            case Mermaid:
                chaine = chaine + "class "+this.class_name.getSimpleName()+" {\n";
                if(this.class_name.isInterface()){
                    chaine = chaine + "    <<interface>>\n";
                }
                chaine = chaine + "}\n";
                break;
        }
        return chaine;
    }
}
