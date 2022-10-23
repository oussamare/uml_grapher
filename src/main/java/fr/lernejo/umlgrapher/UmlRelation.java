package fr.lernejo.umlgrapher;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
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
            getSuperClassRelation(c);
            getInstancesRelation(c);
            getMethodRelation(c);
            getFieldRelation(c);
        }
    }

    private void getFieldRelation(Class c) {
        for (Field field : c.getDeclaredFields()) {
            if (!field.getType().getName().startsWith("java.") && !field.isSynthetic()) {
                String[] tab = fillTab(field.getType().getSimpleName(), c.getSimpleName(), "returns");
                if (relations.stream().filter(t -> Arrays.deepEquals(t, tab)).toList().isEmpty()) {
                    tab[2] = "uses";
                    relations.add(tab);
                }
            }
        }
    }

    private void getMethodRelation(Class c) {
        for (Method method : c.getDeclaredMethods()) {
            if (!method.getReturnType().getName().startsWith("java.")
                && !method.getReturnType().getName().equals("void")
                && !method.isSynthetic()) {
                relations.add(fillTab(method.getReturnType().getSimpleName(), c.getSimpleName(), "returns"));
            }
        }
    }

    private void getInstancesRelation(Class c) {
        for (Class inter : c.getInterfaces()) {
            relations.add(fillTab(inter.getSimpleName(), c.getSimpleName(),
                !Modifier.isInterface(c.getModifiers()) ? "implements" : "extends"));
        }
    }

    private void getSuperClassRelation(Class c) {
        Class superClass = c.getSuperclass();
        if (superClass != null && !superClass.getSimpleName().equals("Object")) {
            relations.add(fillTab(superClass.getSimpleName(), c.getSimpleName(), "extends"));
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
