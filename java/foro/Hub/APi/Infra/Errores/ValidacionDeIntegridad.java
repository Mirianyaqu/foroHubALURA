package foro.Hub.APi.Infra.Errores;

public class ValidacionDeIntegridad extends RuntimeException {

    public ValidacionDeIntegridad(String message) {
        super(message);
    }

    public String getErrorCode() {
        return "VALIDACION_INTEGRIDAD_ERROR";
    }
}

