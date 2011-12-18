PROGRAM hello (output);

(Write 'Hello, world.' ten times.)

VAR
    i : integer;

BEGIN (hello)
    FOR i := 1 to 10 DO BEGIN
        writeln('Hello, world.');
    END;
END (hello).