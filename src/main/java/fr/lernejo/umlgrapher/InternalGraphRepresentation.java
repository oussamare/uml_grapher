package fr.lernejo.umlgrapher;

public class InternalGraphRepresentation {
    private final UmlType uType;
    private final UmlRelation uRelation;

    public InternalGraphRepresentation(Class[] classes) {
        this.uType = new UmlType(classes);
        this.uRelation = new UmlRelation(uType);
    }

    public UmlType getUmlType() {
        return uType;
    }

    public UmlRelation getUmlRelation() {
        return uRelation;
    }
}
