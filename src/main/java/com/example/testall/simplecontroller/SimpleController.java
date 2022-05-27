package com.example.testall.simplecontroller;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SimpleController {

    private static final Logger log = LoggerFactory.getLogger(SimpleController.class);

    CloseableHttpClient client = HttpClientBuilder
            .create()
            .setDefaultRequestConfig(
                    RequestConfig.custom()
                            .setConnectTimeout(60 * 1000)
                            .setConnectionRequestTimeout(5 * 1000)
                            .setSocketTimeout(60 * 1000)
                            .build()
            )
            .setMaxConnPerRoute(2)
            .setMaxConnTotal(2)
            .build();

    @Autowired
    private RestTemplate commonRestTemplate;

    @GetMapping("/hello")
    public String hello(@RequestParam(required = false)String param1, @RequestHeader HttpHeaders headers) {
        log.info("AAAAAAAAAAAAAAAAAAA =========================================== Hello");
        return "Heeello hi! 111111111111";
    }

    @GetMapping("/hello2")
    public String hello2() {
        return "Heeello hi! 2222222222";
    }

    @GetMapping("/hello3")
    public String hello3() {
        return "Heeello hi! 33333333333";
    }

    // https://localhost:10012/ott-service/TokenJsonRpcFacade.json
    @PostMapping("/ott-service/TokenJsonRpcFacade.json")
    public String ott(Object obj) {
        return "{\"jsonrpc\": \"2.0\", \"result\": 19, \"id\": 3}";
    }

    @GetMapping("/hello_2")
    public String hello_2() throws IOException {
        log.info(" /hello_2 received request");

        HttpGet get = new HttpGet("http://localhost:8081/hello_with_sleep");
        HttpResponse response = client.execute(get);

        return IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
    }

    @GetMapping("/hello_with_sleep")
    public String helloWithSleep() throws InterruptedException {
        log.info(" /hello_with_sleep received request");
        Thread.sleep(30 * 1000);
        log.info(" /hello_with_sleep send response");
        return "Heeello hi!";
    }

    @GetMapping("/slow-service-tweets")
    public List<String> getAllTweets() throws InterruptedException {
        Thread.sleep(5000L); // delay
        return Arrays.asList(
                "RestTemplate rules @user1",
                "WebClient is better @user2",
                "OK, both are useful @user1");
    }

    @GetMapping("/actuator/health/readiness")
    public ResponseEntity dataspaceHealthCheck() {
        return ResponseEntity.badRequest().body("BAD REQ");
    }

    @GetMapping("/date-header")
    public ResponseEntity getWithDateHeader() {
        return ResponseEntity.ok().header("Date", "Fri, 10 Sep 2021 15:00:00 GMT").body("1");
    }

    @PostMapping("/sowa_validate_json")
    public String sowaValidateJson(@RequestHeader HttpHeaders headers, @RequestBody byte[] object) {
        return "sowa_validate_json OK";
    }

    @PostMapping("/sowa_validate_xsd")
    public String sowaValidateXsd(@RequestHeader HttpHeaders headers, @RequestBody byte[] object) {
        return "sowa_validate_xsd OK";
    }

    @GetMapping(value = "/with-param")
    public String getWithDateHeader(@RequestParam String param1) {
        return "WITH-PARAM OK";
    }

    @PostMapping("/test-post")
    public String testPost(@RequestHeader HttpHeaders headers, @RequestParam String param1) {
        return "WITH-PARAM OK";
    }

    @GetMapping(value = "/download-virus", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileSystemResource> downloadVirus() {
        return ResponseEntity.ok().body(new FileSystemResource("virus.zip"));
    }

    @GetMapping(value = "/download-base64-virus", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> downloadBase64Virus() {
        String virusZipBase64 = "UEsDBAoAAAAAAKVRiFQAAAAAAAAAAAAAAABTABwAVXNlcnMvMTcyMzA3MjZhL0Rlc2t0b3AvV09SSy9zb3dhX2RvY2tlci9BbnRpdmlydXNfVXRpbGl0eV9SZWRIYXRfQ0xTX0M2NGJpdC92aXJ1cy9VVAkAA4XgT2KG4E9idXgLAAEE9wEAAAQUAAAAUEsDBBQACQAIAKthh1ROvXUvGgEAAFABAABdABwAVXNlcnMvMTcyMzA3MjZhL0Rlc2t0b3AvV09SSy9zb3dhX2RvY2tlci9BbnRpdmlydXNfVXRpbGl0eV9SZWRIYXRfQ0xTX0M2NGJpdC92aXJ1cy9yZWFkbWUudHh0VVQJAAMyq05ih+BPYnV4CwABBPcBAAAEFAAAAPPwj4DH3o0VW8qEuDC4OVys12TVh4n9yVuuLvW1//WGIymM2vz83QfRaqKkX5RAKiF1g2fq4GdcrR3p86Gn21E9zjXf2yMI9ZAH+hxSi5CuK6zoc1RZZ00sgoDnYP3w4brBabTsMFR6K6XSmKz9salOImQgoAr6r47/h1qU86UYQPzaS9ytrlNdvqTV0l/p0QTJzTIyEn/8l6bavrcqAXFUqHo5NXIfvCVQezgio9npJFdI8UjiRUge4J8OvAffLnKN5iSu970IYo7/Y/I4u+j8gz77n63Lq/cGSgBzr8IQMdPIgisPnMFUN8hq4i0AVFBOMKB6nGbxE4XIOhaJb2Ie7YNJz53HKb/wnHFRYXqbN2+bLbXF+VFKwFBLBwhOvXUvGgEAAFABAABQSwECHgMKAAAAAAClUYhUAAAAAAAAAAAAAAAAUwAYAAAAAAAAABAA7UEAAAAAVXNlcnMvMTcyMzA3MjZhL0Rlc2t0b3AvV09SSy9zb3dhX2RvY2tlci9BbnRpdmlydXNfVXRpbGl0eV9SZWRIYXRfQ0xTX0M2NGJpdC92aXJ1cy9VVAUAA4XgT2J1eAsAAQT3AQAABBQAAABQSwECHgMUAAkACACrYYdUTr11LxoBAABQAQAAXQAYAAAAAAABAAAApIGNAAAAVXNlcnMvMTcyMzA3MjZhL0Rlc2t0b3AvV09SSy9zb3dhX2RvY2tlci9BbnRpdmlydXNfVXRpbGl0eV9SZWRIYXRfQ0xTX0M2NGJpdC92aXJ1cy9yZWFkbWUudHh0VVQFAAMyq05idXgLAAEE9wEAAAQUAAAAUEsFBgAAAAACAAIAPAEAAE4CAAAAAA==";
        Map<String, String> map = new HashMap<>();
        map.put("field_1", "AAA");
        map.put("virus_field", virusZipBase64);
        return map;
    }

    @GetMapping(value = "/get-some-json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getSomeJson() {
        Map<String, String> map = new HashMap<>();
        map.put("field_1", "AAA");
        map.put("field_2", "BBB");
        return map;
    }

    @GetMapping(value = "/get-empty-json")
    public ResponseEntity getEmptyJson() {
        return ResponseEntity.ok()
                .header("Some-Header", "AAAA_BBBB_CCCC_DDDD")
                .header("Content-Type", "application/json")
                .build();
    }

    @GetMapping(value = "/get-invalid-json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getInvalidJson() {
        return "asdasd";
    }

    @GetMapping(value = "/get-some-xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String getSomeXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://bssys.com/upg/request\"\n" +
                "           targetNamespace=\"http://bssys.com/upg/request\" elementFormDefault=\"qualified\"\n" +
                "           attributeFormDefault=\"unqualified\" version=\"1.0\">\n" +
                "\n" +
                "    <xs:element name=\"Request\" type=\"Request\">\n" +
                "        <xs:annotation>\n" +
                "            <xs:documentation>Запрос УС к СББОЛ</xs:documentation>\n" +
                "        </xs:annotation>\n" +
                "    </xs:element>\n" +
                "\n" +
                "</xs:schema>\n";
    }


    @GetMapping(value = "/get-text-xml", produces = MediaType.TEXT_XML_VALUE)
    public String getTextXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://bssys.com/upg/request\"\n" +
                "           targetNamespace=\"http://bssys.com/upg/request\" elementFormDefault=\"qualified\"\n" +
                "           attributeFormDefault=\"unqualified\" version=\"1.0\">\n" +
                "\n" +
                "    <xs:element name=\"Request\" type=\"Request\">\n" +
                "        <xs:annotation>\n" +
                "            <xs:documentation>Запрос УС к СББОЛ</xs:documentation>\n" +
                "        </xs:annotation>\n" +
                "    </xs:element>\n" +
                "\n" +
                "</xs:schema>\n";
    }

    @GetMapping(value = "/get-empty-xml")
    public ResponseEntity getEmptyXml() {
        return ResponseEntity.ok()
                .header("Some-Header", "AAAA_BBBB_CCCC_DDDD")
                .header("Content-Type", "application/xml")
                .build();
    }

    @GetMapping(value = "/get-invalid-xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String getInvalidXml() {
        return "asdasd";
    }

    @GetMapping(value = "/fake-response-content-type", produces = "asd/asd")
    public String getFakeResponseContentType() {
        return "asdasd";
    }

    @GetMapping("/get-empty-body")
    public ResponseEntity getEmptyBody() {
        return ResponseEntity.ok()
                .header("Some-Header", "AAAA_BBBB_CCCC_DDDD")
                .build();
    }

    @GetMapping("/proxy-get-request")
    public String proxyGetRequest(@RequestHeader HttpHeaders headers) {
        log.info("BBBBBBBBBBBBBBBBBB =========================================== proxy-get-request");
        String hello = commonRestTemplate.getForObject("http://localhost:9876/hello", String.class);
        return hello;
    }
}
