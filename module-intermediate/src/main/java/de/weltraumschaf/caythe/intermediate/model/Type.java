package de.weltraumschaf.caythe.intermediate.model;

import de.weltraumschaf.commons.validate.Validate;

import java.util.*;

/**
 * The information from a module type.
 * <p>
 * This type is immutable by design.
 * </p>
 *
 * @author Sven Strittmatter &lt;weltraumschaf@googlemail.com&gt;
 * @since 1.0.0
 */
public final class Type {
    public static final Type NONE = new TypeBuilder().create();
    private TypeName name;
    private Facet facet;
    private Visibility visibility;
    private Method constructor;
    private Collection<Import> imports;
    private List<Property> properties ;
    private Collection<Method> methods  ;
    private Collection<Delegate> delegates ;

    /**
     * Use the {@link TypeBuilder builder} to create instances.
     */
    private Type() {
        super();
    }

    public TypeName getName() {
        return name;
    }

    public Facet getFacet() {
        return facet;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public Method getConstructor() {
        return constructor;
    }

    public Collection<Import> getImports() {
        return Collections.unmodifiableCollection(imports);
    }

    public Collection<Property> getProperties() {
        return Collections.unmodifiableCollection(properties);
    }

    public Property getProperty(final int index) {
        return properties.get(index);
    }

    public Collection<Method> getMethods() {
        return Collections.unmodifiableCollection(methods);
    }

    public Collection<Delegate> getDelegates() {
        return Collections.unmodifiableCollection(delegates);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Type)) {
            return false;
        }

        final Type type = (Type) o;
        return Objects.equals(name, type.name) &&
            facet == type.facet &&
            visibility == type.visibility &&
            Objects.equals(constructor, type.constructor) &&
            Objects.equals(properties, type.properties) &&
            Objects.equals(methods, type.methods) &&
            Objects.equals(delegates, type.delegates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, facet, visibility, constructor, properties, methods, delegates);
    }

    @Override
    public String toString() {
        return "Type{" +
            "name='" + name + '\'' +
            ", facet=" + facet +
            ", visibility=" + visibility +
            ", constructor=" + constructor +
            ", properties=" + properties +
            ", methods=" + methods +
            ", delegates=" + delegates +
            '}';
    }

    public static final class TypeBuilder {
        private TypeName name = TypeName.NONE;
        private Facet facet = Facet.UNDEFINED;
        private Visibility visibility = Visibility.UNDEFINED;
        private Method constructor = Method.NONE;
        private final Collection<Import> imports = new ArrayList<>();
        private final Collection<Property> properties = new ArrayList<>();
        private final Collection<Method> methods = new ArrayList<>();
        private final Collection<Delegate> delegates = new ArrayList<>();

        public TypeBuilder() {
            super();
        }

        public void setName(final TypeName name) {
            this.name = Validate.notNull(name, "name");

            if (TypeName.NONE.equals(name)) {
                throw new IllegalArgumentException("Illegal type name: " + TypeName.NONE.getFullQualifiedName() + "!");
            }
        }

        public void setFacet(final Facet f) {
            this.facet = Validate.notNull(f, "f");
        }

        public void setVisibility(final Visibility v) {
            this.visibility = Validate.notNull(v, "v");
        }

        public void setConstructor(final Method c) {
            this.constructor = Validate.notNull(c, "c");

            if (Method.NONE.equals(c)) {
                throw new IllegalArgumentException("Illegal constructor method: " + c + "!");
            }
        }

        public void addImport(final Import i) {
            Validate.notNull(i, "i");
            imports.add(i);
        }
        public void addProperty(final Property p) {
            Validate.notNull(p, "p");
            properties.add(p);
        }

        public void addMethod(final Method m) {
            Validate.notNull(m, "m");

            if (Method.NONE.equals(m)) {
                throw new IllegalArgumentException("Illegal method: " + constructor + "!");
            }

            methods.add(m);
        }

        public void addDelegate(final Delegate d) {
            Validate.notNull(d, "d");
            delegates.add(d);
        }

        public Type create() {
            final Type t = new Type();
            t.name = name;
            t.facet = facet;
            t.visibility = visibility;
            t.constructor = constructor;
            // Make defensive copies:
            t.imports = new ArrayList<>(imports);
            t.delegates = new ArrayList<>(delegates);
            t.properties = new ArrayList<>(properties);
            t.methods = new ArrayList<>(methods);
            return t;
        }
    }
}
