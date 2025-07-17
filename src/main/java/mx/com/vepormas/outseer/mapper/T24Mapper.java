package mx.com.vepormas.outseer.mapper;


import jakarta.validation.constraints.NotNull;
import mx.com.vepormas.outseer.util.Constantes;


public class T24Mapper {

    public static String ofsDateAccount(String eventType, String customerId, String accountNumber) {
        return "VPM.GET.FECHA.REGCTA,," + Constantes.USR + Constantes.DIAG + Constantes.PASS + ",," +
                eventType + Constantes.DIAG2 + customerId + Constantes.DIAG2 + accountNumber;
    }
    private T24Mapper() {
    }

    public static  String ofsBlockUserAccount(String idCore) {
        return "VPM.ACCESO,OFS/I/PROCESS," +   Constantes.USR + "/" + Constantes.PASS +  "," + idCore + ",USER.STATUS:1:1=" +  Constantes.T24_ESTADO_BLOQUEADO;
    }
}
