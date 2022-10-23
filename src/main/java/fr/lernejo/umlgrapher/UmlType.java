package fr.lernejo.umlgrapher;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

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

    private void getChild(Class c) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
            .forPackage("")
            .forPackage("", c.getClassLoader())
        );
        Set<Class<?>> subTypes = reflections.get(
            Scanners.SubTypes
                .get(c)
                .asClass(this.getClass().getClassLoader(), c.getClassLoader())
        );
        for (Class classe : subTypes) {
            if (!types.contains(classe)) types.add(classe);
        }
    }


    private void recursionSearch(Class s) {
        Class superClass = s.getSuperclass();
        if (superClass != null
            && !superClass.getSimpleName().equals("Object"))
            recursionSearch(superClass);

        for (Class inter : s.getInterfaces()) {
            recursionSearch(inter);
        }
        this.getChild(s);

        types.add(s);
    }

    public Set<Class> getListOfClass() {
        return this.types;
    }

}
