# Internal Representation

The basic building unit is a type, which consists of:

- imports
- delegates
- properties
- constructors
- methods

Some basic internal data structures:

- List: Contains values of same type in an ordered sequence accessible by index in a variable sized data structure.
- tuple: Contains values of different types in an ordered sequence accessible by index in a fixed sized data structure. 
- map: Contains values of same type accessible by string name in a variable sized data structure.

## List

External representation:
```
[ value1, value2, value3, ..., valueN ]
```

## Tuple

External representation:
```
( type1 : value1, type2 : value2, type3 : value3, ..., typeN : valueN )
```

## Map

External representation:
```
{ key1 : value1, key2 : value2, key3 : value3, ..., key : valueN }
```
