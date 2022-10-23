package fr.lernejo.umlgrapher;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Use to manage class et super base on the classe to give
 */
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

    private void getAllChild(Class c) {
        Reflections reflections;
        try {
            reflections = new Reflections(new ConfigurationBuilder().forPackage("")
                .forPackage("", c.getClassLoader())
            );
        } catch (RuntimeException e) {
            System.out.println("Absence de Classe loader");
            return;
        }
        Set<Class<?>> subTypes = reflections.get(
            Scanners.SubTypes.get(c).asClass(this.getClass().getClassLoader(), c.getClassLoader())
        );
        for (Class classe : subTypes) {
            getAllChild(classe);
            if (!types.contains(classe)) types.add(classe);
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
        // found child
        this.getAllChild(c);
        types.add(c);
    }

    public Set<Class> getListOfClass() {
        return this.types;
    }
}
