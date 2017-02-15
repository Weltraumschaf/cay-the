# AST Tests

Test File format:

     <source>
     #+
     <expected-ast> 

Examples:

     let a = 1 + 2 * 3
     #####
     (Unit 
         (statements
             (Let a
                 (= 
                     (*
                         (+ 2 3 [line, column])
                         3
                         [line, column])
                      ...
      )

Ein unit Test der mit Dataprovider sich alle files holt,
parsed und dementsprechend ein Source und AST Generiert
und asserted.   