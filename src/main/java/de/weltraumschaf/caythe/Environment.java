package de.weltraumschaf.caythe;

/**
 */
interface Environment {

    void stdOut(String output);
    void stdErr(String output);
    String stdIn();
}
