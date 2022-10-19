package fr.lernejo.umlgrapher;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Use to find relation between class
 */
public class UmlRelation {

    private final List<String[]> relations = new ArrayList<>();

    public UmlRelation(UmlType uType) {
        this.getAllRelation(uType);
    }

    public void getAllRelation(UmlType uType) {
        for (Class c : uType.getListOfClass()) {
            Class superClass = c.getSuperclass();
            if (superClass != null
                && !superClass.getSimpleName().equals("Object")) {
                relations.add(fillTab(superClass.getSimpleName(),
                    c.getSimpleName(),
                    "extends"));
            }
            for (Class inter : c.getInterfaces()) {
                relations.add(fillTab(inter.getSimpleName(),
                    c.getSimpleName(),
                    !Modifier.isInterface(c.getModifiers()) ? "implements" : "extends"));
            }
        }
    }

    private String[] fillTab(String tab0, String tab1, String tab2) {
        String[] tab = new String[3];
        tab[0] = tab0;
        tab[1] = tab1;
        tab[2] = tab2;
        return tab;
    }

    public List<String[]> getRelationsBetweenClass() {
        return relations;
    }
}
