package br.com.capelaum.job_management.exceptions;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException() {
        super("Vaga não existe");
    }
}
