package com.example.testall.mcp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpStatelessServerFeatures;
import io.modelcontextprotocol.server.McpStatelessSyncServer;
import io.modelcontextprotocol.server.transport.WebMvcStatelessServerTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import ru.sberbank.pprb.sbbol.payments.api.PaymentsApi;
import ru.sberbank.pprb.sbbol.payments.client.invoker.ApiClient;
import ru.sberbank.pprb.sbbol.payments.client.model.FintechPayment;
import ru.sberbank.pprb.sbbol.payments.client.model.FintechPaymentIncoming;

import java.util.List;

@Configuration
public class McpServerConfig implements WebMvcConfigurer {

    public static final String PAYMENT_LIGHT_JSON_SCHEMA = """
            {
              "$schema": "http://json-schema.org/draft-07/schema#",
              "type": "object",
              "title": "Payment",
              "description": "Рублёвое платёжное поручение",
              "required": [
                "date",
                "externalId",
                "amount",
                "operationCode",
                "priority",
                "purpose",
                "payerName",
                "payerInn",
                "payerAccount",
                "payerBankBic",
                "payerBankCorrAccount",
                "payeeName",
                "payeeBankBic"
              ],
              "properties": {
                "number": {
                  "type": "string",
                  "description": "Номер документа",
                  "example": 1
                },
                "date": {
                  "type": "string",
                  "format": "date",
                  "nullable": false,
                  "description": "Дата составления документа",
                  "example": "2018-12-31"
                },
                "digestSignatures": {
                  "type": "array",
                  "items": {
                    "description": "Электронная подпись",
                    "title": "Signature",
                    "type": "object",
                    "required": [
                      "certificateUuid",
                      "base64Encoded"
                    ],
                    "properties": {
                      "certificateUuid": {
                        "type": "string",
                        "format": "uuid",
                        "description": "Уникальный идентификатор сертификата ключа проверки электронной подписи (UUID)",
                        "example": "22a6dd81-103a-4d3a-8e9b-0ba4b527f5f6"
                      },
                      "base64Encoded": {
                        "type": "string",
                        "nullable": false,
                        "minLength": 1,
                        "description": "Значение электронной подписи, закодированное в Base64",
                        "example": "HlaeIHXXEcGT1bFxo1NlpAzpr+kJ2IQrcxVdvDTep6xjsmD1FDb+6NIyLT+/T24S0mPfVCU75sieOMt71TBS7w=="
                      }
                    }
                  },
                  "description": "Электронные подписи по дайджесту документа"
                },
                "bankStatus": {
                  "type": "string",
                  "description": "Статус документа"
                },
                "bankComment": {
                  "type": "string",
                  "description": "Банковский комментарий к статусу документа"
                },
                "externalId": {
                  "type": "string",
                  "format": "uuid",
                  "nullable": false,
                  "description": "Идентификатор документа, присвоенный партнёром (UUID)",
                  "example": "22a6dd81-103a-4d3a-8e9b-0ba4b527f5f6"
                },
                "amount": {
                  "type": "number",
                  "nullable": false,
                  "default": "0",
                  "description": "Сумма платежа",
                  "example": "1.01"
                },
                "operationCode": {
                  "type": "string",
                  "pattern": "^01$",
                  "nullable": false,
                  "description": "Код операции",
                  "example": "01"
                },
                "deliveryKind": {
                  "type": "string",
                  "pattern": "^(электронно|срочно|0)",
                  "nullable": false,
                  "description": "Идентификатор документа, присвоенный партнёром (UUID)",
                  "example": "электронно"
                },
                "priority": {
                  "type": "string",
                  "nullable": false,
                  "pattern": "^[1-5]{1}$",
                  "description": "Очерёдность платежа",
                  "example": "5"
                },
                "urgencyCode": {
                  "type": "string",
                  "description": "Код срочности"
                },
                "voCode": {
                  "type": "string",
                  "pattern": "^[0-9]{5}$",
                  "description": "Код вида валютной операции",
                  "example": "61150"
                },
                "purpose": {
                  "type": "string",
                  "description": "Назначение платежа",
                  "nullable": false,
                  "maxLength": 210,
                  "example": "Оплата заказа №123. НДС нет."
                },
                "departmentalInfo": {
                  "type": "object",
                  "title": "DepartmentalInfo",
                  "description": "Реквизиты налогового, таможенного или иного бюджетного платежа",
                  "properties": {
                    "uip": {
                      "type": "string",
                      "pattern": "[A-ZА-Я0-9/]+",
                      "description": "Уникальный идентификатор платежа",
                      "maxLength": 25,
                      "example": 0
                    },
                    "drawerStatus101": {
                      "type": "string",
                      "description": "Показатель статуса налогоплательщика (реквизит - 101)",
                      "maxLength": 2,
                      "example": 1
                    },
                    "kbk": {
                      "type": "string",
                      "description": "Код бюджетной классификации (реквизит - 104)",
                      "pattern": "([A-ZА-Я0-9]{1,20})$",
                      "example": 18210102010011000000
                    },
                    "oktmo": {
                      "type": "string",
                      "description": "Код OKTMO (реквизит - 105)",
                      "pattern": "(.{1,11})$",
                      "example": 1701000
                    },
                    "reasonCode106": {
                      "type": "string",
                      "description": "Показатель основания платежа (реквизит - 106)",
                      "maxLength": 2,
                      "example": "ТП"
                    },
                    "taxPeriod107": {
                      "type": "string",
                      "description": "Налоговый период / код таможенного органа (реквизит - 107)",
                      "pattern": "^(0|[0-9]{8}|([0-9]{2}|МС|КВ|ПЛ|ГД)\\\\.[0-9]{2}\\\\.[0-9]{4})$",
                      "example": "ГД.00.2018"
                    },
                    "docNumber108": {
                      "type": "string",
                      "description": "Номер налогового документа (реквизит - 108)",
                      "maxLength": 15,
                      "example": 123
                    },
                    "docDate109": {
                      "type": "string",
                      "description": "Дата налогового документа (реквизит - 109)",
                      "pattern": "^(0|00|[0-9]{2}\\\\.[0-9]{2}\\\\.[0-9]{4})$",
                      "example": "31.12.2022"
                    },
                    "paymentKind110": {
                      "type": "string",
                      "description": "Тип налогового платежа (реквизит - 110)",
                      "maxLength": 2,
                      "example": "ПН"
                    }
                  }
                },
                "payerName": {
                  "type": "string",
                  "maxLength": 254,
                  "nullable": false,
                  "description": "Полное наименование плательщика"
                },
                "payerInn": {
                  "type": "string",
                  "pattern": "^([0-9]{5}|[0-9]{10}|[0-9]{12}|0)$",
                  "nullable": false,
                  "description": "ИНН плательщика",
                  "example": "7707083893"
                },
                "payerKpp": {
                  "type": "string",
                  "pattern": "^([0-9]{9}|0)$",
                  "description": "КПП плательщика",
                  "example": "222201001"
                },
                "payerAccount": {
                  "type": "string",
                  "pattern": "^[0-9]{20}$",
                  "nullable": false,
                  "description": "Счёт плательщика",
                  "example": "40802810600000200000"
                },
                "payerBankBic": {
                  "type": "string",
                  "pattern": "^[0-9]{9}$",
                  "nullable": false,
                  "description": "БИК банка плательщика",
                  "example": "044525225"
                },
                "payerBankCorrAccount": {
                  "type": "string",
                  "pattern": "^[0-9]{20}$",
                  "nullable": false,
                  "description": "Корсчёт банка плательщика",
                  "example": "30101810400000000225"
                },
                "payeeName": {
                  "type": "string",
                  "maxLength": 254,
                  "nullable": false,
                  "description": "Полное наименование получателя платежа"
                },
                "payeeInn": {
                  "type": "string",
                  "pattern": "^([0-9]{5}|[0-9]{10}|[0-9]{12}|0)$",
                  "description": "ИНН получателя платежа",
                  "example": "7707083893"
                },
                "payeeKpp": {
                  "type": "string",
                  "pattern": "^([0-9]{9}|0)$",
                  "description": "КПП получателя платежа",
                  "example": "222201001"
                },
                "payeeAccount": {
                  "type": "string",
                  "pattern": "^[0-9]{20}$",
                  "description": "Счёт получателя платежа",
                  "example": "40802810600000200000"
                },
                "payeeBankBic": {
                  "type": "string",
                  "pattern": "^[0-9]{9}$",
                  "nullable": false,
                  "description": "БИК получателя платежа",
                  "example": "044525225"
                },
                "payeeBankCorrAccount": {
                  "type": "string",
                  "pattern": "^[0-9]{20}$",
                  "description": "Корсчёт банка получателя платежа",
                  "example": "30101810400000000225"
                },
                "crucialFieldsHash": {
                  "type": "string",
                  "description": "Hash от ключевых полей документа"
                },
                "vat": {
                  "nullable": true,
                  "description": "Данные НДС",
                  "type": "object",
                  "title": "Vat",
                  "required": [
                    "type"
                  ],
                  "properties": {
                    "type": {
                      "type": "string",
                      "nullable": true,
                      "enum": [
                        "INCLUDED",
                        "ONTOP",
                        "NO_VAT",
                        "MANUAL"
                      ],
                      "description": "Способ расчета НДС",
                      "example": "NO_VAT"
                    },
                    "rate": {
                      "type": "string",
                      "pattern": "^(0|5|7|10|20)$",
                      "description": "Ставка НДС",
                      "example": 10,
                      "nullable": true
                    },
                    "amount": {
                      "type": "number",
                      "description": "Сумма НДС",
                      "example": 1.01,
                      "nullable": true
                    }
                  }
                },
                "incomeTypeCode": {
                  "type": "string",
                  "maxLength": 2,
                  "description": "Код вида дохода получателей выплаты по 229-ФЗ",
                  "example": "2"
                }
              }
            }
            """;

    public static final String EMPTY_JSON_SCHEMA = """
    {
        "type": "object",
        "properties": {
            "number": {
               "type": "string",
               "description": "Номер документа",
               "example": 1
           }
        }
    }
    """;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new McpHandlerInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public WebMvcStatelessServerTransport webMvcStatelessServerTransport() {
        return WebMvcStatelessServerTransport.builder()
                .objectMapper(objectMapper)
                .build();

    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(WebMvcStatelessServerTransport statelessServerTransport) {
        return statelessServerTransport.getRouterFunction();
    }

    @Bean
    public McpStatelessSyncServer mcpServer(WebMvcStatelessServerTransport statelessServerTransport,
                                            List<McpStatelessServerFeatures.SyncToolSpecification> tools) {
        // Configure server capabilities with resource support
        var capabilities = McpSchema.ServerCapabilities.builder()
                .tools(true) // Tool support with list changes notifications
                .build();

        // Create the server with both tool and resource capabilities
        McpStatelessSyncServer mcpServer = McpServer.sync(statelessServerTransport)
                .serverInfo("MCP Test Payment Server", "1.0.0")
                .capabilities(capabilities)
                .tools(tools)
                // если immediateExecution = false, то тулы будут вызываться в отдельном boundedElastic-тредпуле,
                // см. описание параметра в McpStatelessServerFeatures.Async.fromSync() и
                // реализацию "return immediate ? toolResult : toolResult.subscribeOn(Schedulers.boundedElastic());" в
                // McpStatelessServerFeatures.AsyncToolSpecification.fromSync()
                .immediateExecution(true)
                .build();

        return mcpServer;
    }

    @Bean
    public PaymentsApi paymentsApi(RestClient.Builder restClientBuilder) {

        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonMessageConverter.setObjectMapper(objectMapper);
        RestClient restClient = restClientBuilder
                .messageConverters(List.of(jsonMessageConverter))
                .build();
        ApiClient apiClient = new ApiClient(restClient);
        apiClient.setBasePath("http://localhost:9876");
        return new PaymentsApi(apiClient);
    }

    @Bean
    public McpStatelessServerFeatures.SyncToolSpecification tool1(PaymentsApi paymentsApi) {
        McpStatelessServerFeatures.SyncToolSpecification tool1 = McpStatelessServerFeatures.SyncToolSpecification
                .builder()
                .tool(McpSchema.Tool.builder()
                        .name("sbbol_create_payment")
                        .description("Создать платежное поручение в Сбербанке")
                        .inputSchema(EMPTY_JSON_SCHEMA)
                        .build())
                .callHandler((ctx, request) -> {
                    FintechPaymentIncoming fintechPaymentIncoming = objectMapper.convertValue(request.arguments(), FintechPaymentIncoming.class);
                    FintechPayment payment = paymentsApi.createPayment(fintechPaymentIncoming);
                    String paymentJson;
                    try {
                        paymentJson = objectMapper.writeValueAsString(payment);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    return new McpSchema.CallToolResult(List.of(new McpSchema.TextContent(paymentJson)), null);
                })
                .build();
        return tool1;
    }
}
