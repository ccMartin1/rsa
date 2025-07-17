package mx.com.vepormas.outseer;


import com.rsa.csd.ws.AdaptiveAuthenticationInterfaceProxy;
import com.rsa.csd.ws.AnalyzeRequest;
import com.rsa.csd.ws.AnalyzeResponse;
import com.rsa.csd.ws.AuthenticateRequest;
import com.rsa.csd.ws.AuthenticateResponse;
import com.rsa.csd.ws.ChallengeRequest;
import com.rsa.csd.ws.ChallengeResponse;
import com.rsa.csd.ws.CreateUserRequest;
import com.rsa.csd.ws.CreateUserResponse;
import com.rsa.csd.ws.NotifyRequest;
import com.rsa.csd.ws.NotifyResponse;
import com.rsa.csd.ws.UpdateUserRequest;
import com.rsa.csd.ws.UpdateUserResponse;
import lombok.extern.slf4j.Slf4j;
import mx.com.vepormas.outseer.util.Constantes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.NoSuchElementException;
/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @since 2024-16-08
 * @apiNote  Clase de Connexion a los servicios de OUTSEER
 */

@Slf4j
@Service
public class OutseerClient {
    @Value("${client.uribase}")
    private String uribase;
    @Value("${client.username}")
    private String username;
    @Value("${client.password}")
    private String password;
    private AdaptiveAuthenticationInterfaceProxy proxy;

    public AdaptiveAuthenticationInterfaceProxy getProxy() {
        if (proxy == null) {
            proxy = new AdaptiveAuthenticationInterfaceProxy(uribase + "&username=" + username + "&password=" + password);
            log.info("{}", proxy.getEndpoint());
        }
        return proxy;
    }


    /*
     * (non-Javadoc)
     * @see mx.com.vepormas.banca.rsa.aa.client.RsaAAClient#analyze(com.rsa.csd.ws.AnalyzeRequest)
     */
    @CachePut(value = Constantes.CACHE_NOMBRE_ANALYZE_RESPONSE, key = "#request.runRiskType.value")
    public AnalyzeResponse analyze(AnalyzeRequest request) throws RemoteException {
        try {
            return this.getProxy().analyze(request);
        } catch (RemoteException e) {
            throw new NoSuchElementException();
        }
    }

    /*
     * (non-Javadoc)
     * @see mx.com.vepormas.banca.rsa.aa.client.RsaAAClient#challenge(com.rsa.csd.ws.ChallengeRequest)
     */

    public ChallengeResponse challenge(ChallengeRequest challengeRequest) throws RemoteException {
        return this.getProxy().challenge(challengeRequest);
    }

    /*
     * (non-Javadoc)
     * @see mx.com.vepormas.banca.rsa.aa.client.RsaAAClient#authenticate(com.rsa.csd.ws.AuthenticateRequest)
     */

    public AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest) throws RemoteException {
        return this.getProxy().authenticate(authenticateRequest);
    }

    /*
     * (non-Javadoc)
     * @see mx.com.vepormas.banca.rsa.aa.client.RsaAAClient#notify(com.rsa.csd.ws.NotifyRequest)
     */

    public NotifyResponse notify(NotifyRequest notifyRequest) throws RemoteException {
        return this.getProxy().notify(notifyRequest);
    }



    /*
     * (non-Javadoc)
     * @see mx.com.vepormas.banca.rsa.aa.client.RsaAAClient#updateUser(com.rsa.csd.ws.UpdateUserRequest)
     */

    public UpdateUserResponse updateUser(UpdateUserRequest request) throws RemoteException {
        try {
            return this.getProxy().updateUser(request);
        } catch (RemoteException e) {
            throw new NoSuchElementException();
        }
    }

    /*
     * (non-Javadoc)
     * @see mx.com.vepormas.banca.rsa.aa.client.RsaAAClient#createUser(com.rsa.csd.ws.CreateUserRequest)
     */

    public CreateUserResponse createUser(CreateUserRequest request) throws RemoteException {
        return this.getProxy().createUser(request);
    }
}
