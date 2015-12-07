package de.weltraumschaf.caythe.backend;

/**
 */
interface Environment {

    void stdOut(String output);
    void stdErr(String output);
    String stdIn();
}
