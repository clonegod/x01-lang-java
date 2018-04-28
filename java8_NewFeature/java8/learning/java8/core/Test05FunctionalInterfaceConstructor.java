package java8.core;

import java.io.Serializable;
import java.util.Date;

import org.junit.Test;

public class Test05FunctionalInterfaceConstructor {

    @Test
    public void testVarFactory() throws Exception {
        DateVar dateVar = makeVar("D", "Date", DateVar::new);
        dateVar.setValue(new Date());
        System.out.println(dateVar);

        DateVar dateTypedVar = makeTypedVar("D", "Date", new Date(), DateVar::new);
        System.out.println(dateTypedVar);

        TypedVarFactory<Date, DateVar> dateTypedFactory = DateVar::new;
        System.out.println(dateTypedFactory.apply("D", "Date", new Date()));

        BooleanVar booleanVar = makeVar("B", "Boolean", BooleanVar::new);
        booleanVar.setValue(true);
        System.out.println(booleanVar);

        BooleanVar booleanTypedVar = makeTypedVar("B", "Boolean", true, BooleanVar::new);
        System.out.println(booleanTypedVar);

        TypedVarFactory<Boolean, BooleanVar> booleanTypedFactory = BooleanVar::new;
        System.out.println(booleanTypedFactory.apply("B", "Boolean", true));
    }

    private <V extends Var<T>, T extends Serializable> V makeVar(final String name, final String displayName,
            final VarFactory<V> varFactory) {
        V var = varFactory.apply(name, displayName);
        return var;
    }

    private <V extends Var<T>, T extends Serializable> V makeTypedVar(final String name, final String displayName, final T value,
            final TypedVarFactory<T, V> varFactory) {
        V var = varFactory.apply(name, displayName, value);
        return var;
    }

    @FunctionalInterface
    static interface VarFactory<R> {
        // Don't need type variables for name and displayName because they are always String
        R apply(String name, String displayName);
    }

    @FunctionalInterface
    static interface TypedVarFactory<T extends Serializable, R extends Var<T>> {
        R apply(String name, String displayName, T value);
    }

    static class Var<T extends Serializable> {
        private String name;
        private String displayName;
        private T value;

        public Var(final String name, final String displayName) {
            this.name = name;
            this.displayName = displayName;
        }

        public Var(final String name, final String displayName, final T value) {
            this(name, displayName);
            this.value = value;
        }

        public void setValue(final T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("%s[name=%s, displayName=%s, value=%s]", getClass().getSimpleName(), this.name, this.displayName,
                    this.value);
        }
    }

    static class DateVar extends Var<Date> {
        public DateVar(final String name, final String displayName) {
            super(name, displayName);
        }

        public DateVar(final String name, final String displayName, final Date value) {
            super(name, displayName, value);
        }
    }

    static class BooleanVar extends Var<Boolean> {
        public BooleanVar(final String name, final String displayName) {
            super(name, displayName);
        }

        public BooleanVar(final String name, final String displayName, final Boolean value) {
            super(name, displayName, value);
        }
    }
}