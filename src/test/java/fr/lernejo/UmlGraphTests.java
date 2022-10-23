package fr.lernejo;
import fr.lernejo.umlgrapher.GraphType;
import fr.lernejo.umlgrapher.Launcher;
import fr.lernejo.umlgrapher.UmlGraph;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UmlGraphTests {

    @Test
    void empty_interface_with_no_relation() {
        Class[] classes = new Class[]{Machin.class};
        UmlGraph graph = new UmlGraph(classes);

        String output = graph.as(GraphType.Mermaid);

        Assertions.assertThat(output).isEqualTo("""
            classDiagram
            class Machin {
                <<interface>>
            }
            """);
    }

    @Test
    void interface_with_relation() {
        Class[] classes = new Class[]{
            Living.Animal.Ant.class,
            Living.Animal.Cat.class,
            Living.Plant.Tree.Alder.class
        };
        UmlGraph graph = new UmlGraph(classes);

        String output = graph.as(GraphType.Mermaid);

        Assertions.assertThat(output).isEqualTo("""
            classDiagram
            class Alder
            class Animal {
                <<interface>>
            }
            class Ant
            class Cat
            class Living {
                <<interface>>
            }
            class Plant {
                <<interface>>
            }
            class Tree {
                <<interface>>
            }
            Tree <|.. Alder : implements
            Living <|-- Animal : extends
            Animal <|.. Ant : implements
            Animal <|.. Cat : implements
            Living <|-- Plant : extends
            Plant <|-- Tree : extends
            """);
    }

    @Test
    void class_without_relation() {
        Class[] classes = new Class[]{School.Second.Student.class};
        UmlGraph graph = new UmlGraph(classes);

        String output = graph.as(GraphType.Mermaid);

        Assertions.assertThat(output).isEqualTo("""
            classDiagram
            class Student
            """);
    }

    @Test
    void class_with_relation() {
        Class[] classes = new Class[]{Car.Ferrari.class};
        UmlGraph graph = new UmlGraph(classes);

        String output = graph.as(GraphType.Mermaid);

        Assertions.assertThat(output).isEqualTo("""
            classDiagram
            class Car
            class Ferrari
            Car <|-- Ferrari : extends
            """);
    }

    @Test
    void launcher_main_test() {
        new Launcher();
    }

    @Test
    void class_with_one_member_test() {
        Class[] classes = new Class[]{Singleton.class};
        UmlGraph graph = new UmlGraph(classes);

        String output = graph.as(GraphType.Mermaid);

        Assertions.assertThat(output).isEqualTo("""
            classDiagram
            class Singleton {
                -Singleton instance$
                +getInstance()$ Singleton
                +supplySomeStr(int offset) String
            }
            Singleton <-- Singleton : returns
            """);
    }

    @Test
    void many_class_with_field_test() {
        Class[] classes = new Class[]{Image.class};
        UmlGraph graph = new UmlGraph(classes);

        String output = graph.as(GraphType.Mermaid);

        Assertions.assertThat(output).isEqualTo("""
            classDiagram
            class Image {
                <<interface>>
                +display()* void
            }
            class LazyLoadedImage {
                -RealImage realImage
                -String fileName
                +display() void
            }
            class RealImage {
                -String fileName
                +display() void
                -loadFromDisk(String fileName) void
            }
            Image <|.. LazyLoadedImage : implements
            RealImage <-- LazyLoadedImage : uses
            Image <|.. RealImage : implements
            """);
    }

    interface Machin {
    }
    // -c fr.lernejo.umlgrapher.Living$Animal$Ant -c fr.lernejo.umlgrapher.Living$Animal$Cat -c fr.lernejo.umlgrapher.Living$Plant$Tree$Alder
    public sealed interface Living {
        sealed interface Animal extends Living {
            final class Ant implements Animal {
            }

            final class Cat implements Animal {
            }
        }

        sealed interface Plant extends Living {
            sealed interface Tree extends Plant {
                final class Alder implements Tree {
                }
            }
        }
    }

    public class School {
        public class Second {
            public class Student {

            }
        }
    }

    public class Car {
        public class Ferrari extends Car {
        }
    }

    public static class Singleton {

        private static final Singleton instance = new UmlGraphTests.Singleton();

        public static Singleton getInstance() {
            return instance;
        }

        public String supplySomeStr(int offset) {
            return String.valueOf(43 + offset);
        }
    }

    public sealed interface Image {

        void display();

        final class RealImage implements Image {

            private final String fileName;

            public RealImage(String fileName){
                this.fileName = fileName;
                loadFromDisk(fileName);
            }

            @Override
            public void display() {
                System.out.println("Displaying " + fileName);
            }

            private void loadFromDisk(String fileName){
                System.out.println("Loading " + fileName);
            }
        }

        final class LazyLoadedImage implements Image{

            private RealImage realImage;
            private final String fileName;

            public LazyLoadedImage(String fileName){
                this.fileName = fileName;
            }

            @Override
            public void display() {
                if(realImage == null){
                    realImage = new RealImage(fileName);
                }
                realImage.display();
            }
        }
    }
}
