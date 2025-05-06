package com.example.testall.resttemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GzipRestTemplateMain {

    public static final String BODY = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:upg=\"http://upg.sbns.bssys.com/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <upg:sendRequestsSRP>\n" +
            "         <upg:requests>\n" +
            "        <![CDATA[\n" +
            "         <Request xmlns=\"http://bssys.com/upg/request\" receiver=\"SBBOL_DBO\"\n" +
            "         sender=\"GOAPI_HOLD_Asia\" version=\"01.010.02\"\n" +
            "         orgId=\"e434742c-1f5e-409b-b974-225fab8c9f73\"\n" +
            "         requestId=\"e434742c-1f5e-409b-b974-225fab8c9f74\" protocolVersion=\"35\">\n" +
            "\n" +
            "<PayDocRu\n" +
            "    docExtId='1110099c-1f5e-409b-b974-225fab8c9f74'\n" +
            "    sentForSign='1'>\n" +
            "    <AccDoc\n" +
            "    accDocNo='23'\n" +
            "    docDate='2023-03-15'\n" +
            "    docSum='2'\n" +
            "    transKind='01'\n" +
            "    paytKind='0'\n" +
            "    priority='5'>\n" +
            "    <Purpose>Оплата по договору. В том числе НДС 18 % - 15.25</Purpose>\n" +
            "    </AccDoc>\n" +
            "    <Payer inn='4459195834'\n" +
            "    personalAcc='40702810438178296467'>\n" +
            "    <Name>Питончик</Name>\n" +
            "    <Bank bic='044525225' correspAcc='30101810400000000225'>\n" +
            "    <Name>ПАО СБЕРБАНК</Name>\n" +
            "    </Bank>\n" +
            "    </Payer>\n" +
            "    <Payee inn='3385137939' personalAcc='40702810202147494148'>\n" +
            "    <Name>Мопс</Name>\n" +
            "    <Bank bic='040173604'\n" +
            "    correspAcc='30101810200000000604'>\n" +
            "    <Name>АЛТАЙСКОЕ ОТДЕЛЕНИЕ N8644 ПАО СБЕРБАНК</Name>\n" +
            "    </Bank>\n" +
            "    </Payee>\n" +
            "    <Credit flagTargetAssignment='0' flagUseOwnMeans='0'/>\n" +
            "    </PayDocRu>\n" +
            "</Request>\n" +
            "]]>\n" +
            "         </upg:requests>\n" +
            "         <upg:sessionId>1b453cf0-af70-4bfa-8ee2-db447e2ec822</upg:sessionId>\n" +
            "      </upg:sendRequestsSRP>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            HttpHeaders httpHeaders = request.getHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_ENCODING, "gzip");
//            httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip");
            return execution.execute(request, compress(body));
        });
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Forwarded-For", "10.1.1.1, 10.2.2.2, 1.2.3.4, 10.3.3.3");
        headers.add("Content-Type", "text/xml;charset=UTF-8");
        headers.add("X-Request-Id", "e434742c-1f5e-409b-b974-225fab8c9f71");
        headers.add("X-Request-Receipt-Timestamp", "1");
        headers.add("UPG_GATE_IP", "127.0.0.1");
        headers.add("UPG_CLIENT_IP", "127.0.0.1");
        ResponseEntity<Void> asd = restTemplate.exchange("http://localhost:8090/sbns-upg/upg", HttpMethod.POST,
                new HttpEntity<>(BODY, headers), Void.class);
        int i = 0;
    }

    public static byte[] compress(byte[] body) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(baos)) {
            gzipOutputStream.write(body);
        }
        return baos.toByteArray();
    }
}
