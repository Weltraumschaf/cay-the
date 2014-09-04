# Cay-The

## Specification

- Codemust always be inside a method.
- Methods must always be inside class.
- Only one class per file, and filename must be same as classname (class Foo -> Foo.ct)
- The runtime invokes a designated main method.
- No semicolon at end of statement.
- Null does not exist.
- Class
    - No inheritence, but delegate pattern.
- Properties
    - properties are always private.
- Methods
    - are private by default.
    - are final by default.
    - are readonly by default
    - support multiple dispatch.
    - support multiple return.
    - to overwrite one the keyword overwrite is required.

## Links

- https://theantlrguy.atlassian.net/wiki/display/~admin/2013/09/01/Matching+parse+tree+patterns%2C+paths
- https://theantlrguy.atlassian.net/wiki/display/~admin/2012/12/08/Tree+rewriting+in+ANTLR+v4
- https://theantlrguy.atlassian.net/wiki/display/ANTLR4/Parse+Tree+Listeners
- http://stackoverflow.com/questions/16419707/antlr4-tokenizing-a-huge-set-of-keywords

## Example Code

    interface Baz<T> {
      T bla()
    }

    interface Snafu {
      blub()
    }

    class Bar {
      public doSomething() {
        String[] words = new String[10]
        words[0] = "hello"
        words[1] = "world"
        // ...
        String[] foo = ["foo", "bar", "baz"]

        for word : words {
          // ...
        }
      }
    }

    class Foo<T> implements Baz<T>, Snafu {

      property (readwrite) String name
      property (readwrite) Integer age
      property String surename

      delegate Bar doesSomething

      public Foo() {
        // constructor
      }

      public String description() {
        Closure c = (Integer arg1, Integer arg2) { // … }
        c(1, 2)
        return "Foo"
      }

      public Integer description() {
        str, int = multiReturn()
        return int
      }

      @implementation
      public T bla() {
        // …
      }

      @Implementation
      public blub() {}

      String, Integer multiReturn() {
        return "foo", 42
      }
    }

    class Foo2 {
      delegate Foo d

      overwrite public blub() {
        d.blub()
      }
    }

    import Boolean.*

    class Main implements Application {

      @Implementation
      public Integer main(List<String> args) {
        if true {

        }

        for str, int : new Map<String, Integer>() {

        }

        Foo<String> foo = new Foo()
        foo.doSomething() // delegate
        foo.blub()
        return 0
      }

    }

    /**
     * System Library.
     */

    public annotation Implementation {}

    public annotation Main {}

    public interface Application {
      @Main
      public Integer main(List<String> args) {}
    }

    public interface List<T> {}

    public interface Set<T> {}

    public interface Map<K, V> {}

    public class String {}

    public class Integer {}

    public class Float {}

    public class Boolean {
      public const true = new Boolean(1)
      public const false = new Boolean(0)
    }

    /**
     * Used like this:
     *
     * class MyClass {
     *   delegate Object super
     * }
     */
    public class Object {
      String toString() {}
      Integer hashCode() {}
      Boolean isEquals(Object other) {}
    }
