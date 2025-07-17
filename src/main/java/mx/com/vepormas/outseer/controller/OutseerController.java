package mx.com.vepormas.outseer.controller;


import com.rsa.csd.ws.AnalyzeResponse;
import lombok.AllArgsConstructor;

import mx.com.vepormas.outseer.controller.pojo.NotifyOutseerResponse;
import mx.com.vepormas.outseer.controller.pojo.OTPRequest;
import mx.com.vepormas.outseer.controller.pojo.OutseerRequest;
import mx.com.vepormas.outseer.controller.pojo.OutseerResponse;
import mx.com.vepormas.outseer.controller.pojo.OutseerUserResponse;
import mx.com.vepormas.outseer.controller.pojo.RefreshOTPRequest;
import mx.com.vepormas.outseer.controller.pojo.Response;
import mx.com.vepormas.outseer.controller.pojo.ResponseBlockedUser;
import mx.com.vepormas.outseer.service.OutseerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @since 2024-16-08
 * * * Controlador REST que gestiona operaciones del servicio de outseer
 */
@AllArgsConstructor
@RestController
@RequestMapping("/web/rsa/v1/outseer")
public class OutseerController {
    @Autowired
    private OutseerService service;


    @GetMapping(value = "/date/{eventType}/{userName}/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<String>> processDate(@PathVariable String eventType, @PathVariable String userName, @PathVariable String accountNumber) {
        HttpHeaders headers = new HttpHeaders();
        var resp = service.processIntegrateDate(eventType, userName, accountNumber);
        headers.set("dateT24", resp.getData());
        return ResponseEntity.ok().headers(headers).body(resp);
    }


    @PostMapping(value = "/analyze", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<AnalyzeResponse>> processAnalyze(@RequestBody OutseerRequest request) {
        return ResponseEntity.ok().body(service.processAnalyze(request));
    }

    @PostMapping(value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<OutseerUserResponse>> processCreateUser(@RequestBody OutseerRequest request) {
        return ResponseEntity.ok(service.processCreateUser(request));
    }

    @PostMapping(value = "/challenge/{tipoCha}/{principal}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<OutseerResponse>> processChallengeOTP(@RequestBody OutseerRequest request, @PathVariable int tipoCha, @PathVariable String principal) {
        return ResponseEntity.ok().body(service.processChallengeOTP(request, tipoCha, principal));
    }

    @PostMapping(value = "/notify/{otp}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<NotifyOutseerResponse>> processNotify(@RequestBody OutseerRequest request, @PathVariable String otp) {
        return ResponseEntity.ok().body(service.processNotify(request, otp));
    }

    @GetMapping(value = "/blocked", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<ResponseBlockedUser>> getBlockedUser(
            @RequestParam String userName,
            @RequestParam String otp,
            @RequestParam String sessionID,
            @RequestParam String transactionID,
            @RequestParam String orgName,
            @RequestParam String deviceToken
    ) {
        return ResponseEntity.ok().body(service.processBlockedUser(userName, otp, sessionID, transactionID, orgName, deviceToken));
    }

    @PostMapping(value = "/validateOtp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<NotifyOutseerResponse>> processValidOtp(@RequestBody OTPRequest request) {
        return ResponseEntity.ok().body(service.processValidOTP(request));
    }

    @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<String>> processRefresh(@RequestBody RefreshOTPRequest request) {
        var resp = service.processRefreshOTP(request.getPrincipal(), request.getSessionId());
        return ResponseEntity.ok().body(resp);
    }

}
