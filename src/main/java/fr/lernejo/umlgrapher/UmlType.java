package fr.lernejo.umlgrapher;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;


public class UmlType {

    private final Set<Class> types = new TreeSet<>(Comparator
        .<Class, String>comparing(Class::getSimpleName)
        .thenComparing(Class::getPackageName));

    public UmlType(Class[] classes) {
        this.getAllClass(classes);
    }

    private void getAllClass(Class[] classes) {
        for (Class c : classes) {
            recursionSearch(c);
        }
    }

    private void recursionSearch(Class c) {
        Class superClass = c.getSuperclass();
        if (superClass != null
            && !superClass.getSimpleName().equals("Object"))
            recursionSearch(superClass);

        for (Class inter : c.getInterfaces()) {
            recursionSearch(inter);
        }

        types.add(c);
    }

    public Set<Class> getListOfClass() {
        return this.types;
    }
}
