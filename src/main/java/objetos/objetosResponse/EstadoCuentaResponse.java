package objetos.objetosResponse;

import java.util.List;

public class EstadoCuentaResponse {
    private int anioLectivo;
    private List<Pago> pagos;
    private List<Integer> mesesPendientes;

    public EstadoCuentaResponse() {
    }

    public EstadoCuentaResponse(int anioLectivo, List<Pago> pagos, List<Integer> mesesPendientes) {
        this.anioLectivo = anioLectivo;
        this.pagos = pagos;
        this.mesesPendientes = mesesPendientes;
    }

    public int getAnioLectivo() { return anioLectivo; }
    public List<Pago> getPagos() { return pagos; }
    public List<Integer> getMesesPendientes() { return mesesPendientes; }

    public void setAnioLectivo(int anioLectivo) { this.anioLectivo = anioLectivo; }
    public void setPagos(List<Pago> pagos) { this.pagos = pagos; }
    public void setMesesPendientes(List<Integer> mesesPendientes) { this.mesesPendientes = mesesPendientes; }
}
