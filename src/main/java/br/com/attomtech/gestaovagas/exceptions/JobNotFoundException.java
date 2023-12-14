package br.com.attomtech.gestaovagas.exceptions;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException() {
        super("Job not found");
    }
}
