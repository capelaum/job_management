package br.com.capelaum.job_management.exceptions;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException() {
        super("Empresa não existe");
    }
}
