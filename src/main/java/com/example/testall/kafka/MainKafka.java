package com.example.testall.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainKafka {

    public static void main(String[] args) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        DefaultKafkaProducerFactory<String, String> defaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(configProps);

        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(defaultKafkaProducerFactory);

        Map<String, Object> kafkaHeaders = new HashMap<>();
//        kafkaHeaders.put(KafkaHeaders.TOPIC, "CI02591035CORPAPI.RESPONSEEVENT.V1");
        kafkaHeaders.put(KafkaHeaders.TOPIC, "CI03151766SENDER.WEBHOOKEVENT.V1");
        kafkaHeaders.put("EventId", UUID.fromString("12300000-0000-0000-0000-000000000001"));
        kafkaHeaders.put("EventTime", System.currentTimeMillis());
//        kafkaHeaders.put("EventTime", "01-01-2025");
        kafkaHeaders.put("OriginSystemCode", "test-FP");
        kafkaHeaders.put("EventType", "payment.final_statuses");
        kafkaHeaders.put("WebhookSubscriptionId", UUID.fromString("123d3bbf-eed8-4135-8b66-2e78c5b28cdb"));
        kafkaHeaders.put("DigitalId", "123321");
        kafkaHeaders.put("EpkOrgId", 999L);
        kafkaHeaders.put("ClientId", "client-id-1");

//        String payload = "{\"externalId\": \"00000000-0000-0000-0000-000000000000\"}";
        String payload = "{\n" +
                "  \"int_1\": 111,\n" +
                "  \"string_1\": \"aaa\",\n" +
                "  \"object_1\": {\n" +
                "    \"int_2\": 222,\n" +
                "    \"string_2\": \"bbb\",\n" +
                "    \"object_2\": {\n" +
                "      \"int_3\": 333,\n" +
                "      \"string_3\": \"ccc\",\n" +
                "      \"array_3\": [\"1\", \"2\"]\n" +
                "    }\n" +
                "  }\n" +
                "}";
//        String payload = "1";
        Message<String> message = MessageBuilder.createMessage(payload, new MessageHeaders(kafkaHeaders));
        kafkaTemplate.send(message);
        kafkaTemplate.flush();
    }

    public static final String SMALL_PAYLOAD = "<Request xmlns='http://bssys.com/upg/request' orgId='00000000-0000-0000-0000-000000000000'\n" +
            "         requestId='35cedc6f-e765-49a9-9045-bd6d174b6e9f' version='01.016.13' sender='Сбербанк Бизнес'\n" +
            "         senderKey='20220973A3DA9A22604B542A04531C87' receiver='SBBOL_DBO' protocolVersion='32'>\n" +
            "    <PersonalInfo contractAccessCode='---' includeEmployees='1'/>\n" +
            "</Request>";

    public static final String BIG_PAYLOAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Response sender=\"DBO\" version=\"7\" requestId=\"35cedc6f-e765-49a9-9045-bd6d174b6e9f\"\n" +
            "          responseId=\"0b7a51b5-63fd-4713-bb89-9b744c701ef8\" createTime=\"2020-07-08T13:13:40.089+03:00\"\n" +
            "          xmlns=\"http://bssys.com/upg/response\">\n" +
            "    <OrganizationsInfo>\n" +
            "        <OrganizationInfo>\n" +
            "            <OrgData>\n" +
            "                <OrgId>fb461bce-f403-48b6-8711-679ce829e8be</OrgId>\n" +
            "                <OrgForm>АО</OrgForm>\n" +
            "                <ShortName>StarTrek</ShortName>\n" +
            "                <FinancialName>StarTrek</FinancialName>\n" +
            "                <FullName>ООО \"Startrek\"</FullName>\n" +
            "                <VkFullName>ООО \"Startrek\"</VkFullName>\n" +
            "                <Accounts>\n" +
            "                    <Account accNum=\"90902840138001331933\" accountType=\"01\"\n" +
            "                             accountId=\"4af50ccb-4bfd-4924-b67f-e37e432fa2bf\">\n" +
            "                        <Bank bic=\"044525225\">\n" +
            "                            <Name>0038</Name>\n" +
            "                            <BankName>ПАО СБЕРБАНК</BankName>\n" +
            "                            <BankCity>Г. Москва</BankCity>\n" +
            "                            <SettlementType>Г</SettlementType>\n" +
            "                        </Bank>\n" +
            "                        <OtherAccountData>\n" +
            "                            <Name>Startrek основной</Name>\n" +
            "                            <CurrencyCode>840</CurrencyCode>\n" +
            "                            <CurrCode>USD</CurrCode>\n" +
            "                            <UseOrgNameInDoc>0</UseOrgNameInDoc>\n" +
            "                            <AccountType>01</AccountType>\n" +
            "                            <AccountTypeP>0</AccountTypeP>\n" +
            "                            <DBO>1</DBO>\n" +
            "                            <Business>false</Business>\n" +
            "                            <IsBusinessNewType>false</IsBusinessNewType>\n" +
            "                            <IsNotDelay>1</IsNotDelay>\n" +
            "                            <IsUrgent>1</IsUrgent>\n" +
            "                            <CreateDate>2019-11-11</CreateDate>\n" +
            "                            <State>OPEN</State>\n" +
            "                            <Mode>1</Mode>\n" +
            "                            <BlockedFullInfo>\n" +
            "                                <Debet check=\"0\">\n" +
            "                                    <BlockedSumInfo/>\n" +
            "                                    <BlockedQueuesInfo/>\n" +
            "                                    <BlockedSumQueuesInfo/>\n" +
            "                                </Debet>\n" +
            "                                <Cred check=\"0\"/>\n" +
            "                            </BlockedFullInfo>\n" +
            "                            <CartotecaDocInfo/>\n" +
            "                            <SumOvd>0</SumOvd>\n" +
            "                            <AddInfoSBK>\n" +
            "                                <AccClientInfo>0</AccClientInfo>\n" +
            "                                <ControlOper>0</ControlOper>\n" +
            "                                <AcceptPP>0</AcceptPP>\n" +
            "                                <DebetLimit>0</DebetLimit>\n" +
            "                                <SingleResidue>0</SingleResidue>\n" +
            "                                <AddAgree>0</AddAgree>\n" +
            "                                <Overdraft>0</Overdraft>\n" +
            "                                <DirectControl>0</DirectControl>\n" +
            "                                <AddDirectControl>0</AddDirectControl>\n" +
            "                            </AddInfoSBK>\n" +
            "                            <CompensatingProducts>\n" +
            "                                <AccContract>0</AccContract>\n" +
            "                                <CreditLimitSum>0</CreditLimitSum>\n" +
            "                                <MiddleLimit>0</MiddleLimit>\n" +
            "                                <MinimumBalance>0</MinimumBalance>\n" +
            "                                <Overdraft>0</Overdraft>\n" +
            "                                <CreditLimit>0</CreditLimit>\n" +
            "                            </CompensatingProducts>\n" +
            "                            <CreditContracts/>\n" +
            "                            <Adm>0</Adm>\n" +
            "                            <SelfEnCashment>0</SelfEnCashment>\n" +
            "                        </OtherAccountData>\n" +
            "                    </Account>\n" +
            "                </Accounts>\n" +
            "                <StateType>1</StateType>\n" +
            "                <StateCode>Резидент</StateCode>\n" +
            "                <OGRN>1027601067944</OGRN>\n" +
            "                <DateOGRN>1994-05-11</DateOGRN>\n" +
            "                <INN>4898113692</INN>\n" +
            "                <OKPO>81085895</OKPO>\n" +
            "                <OKATO>78000000000</OKATO>\n" +
            "                <ExportImportUFEBS>0</ExportImportUFEBS>\n" +
            "                <Addresses>\n" +
            "                    <Address>\n" +
            "                        <AddressTypeCode>actual</AddressTypeCode>\n" +
            "                        <AddressType>Фактический</AddressType>\n" +
            "                        <Country>RUS</Country>\n" +
            "                        <CountryName>Российская Федерация</CountryName>\n" +
            "                        <CountryShortName>РОССИЯ</CountryShortName>\n" +
            "                        <Zip>198825</Zip>\n" +
            "                        <Area>Гаврилов-Ямский</Area>\n" +
            "                        <City>ГАВРИЛОВ-ЯМ</City>\n" +
            "                        <SettlName>ГАВРИЛОВ-ЯМ</SettlName>\n" +
            "                        <Str>Тестовая 106738</Str>\n" +
            "                        <HNumber>1</HNumber>\n" +
            "                        <FullAddress>РОССИЙСКАЯ ФЕДЕРАЦИЯ, 198825, Ярославская область, г.ГАВРИЛОВ-ЯМ, Гаврилов-Ямский\n" +
            "                            р-он, ул. Тестовая 106738, дом 1\n" +
            "                        </FullAddress>\n" +
            "                    </Address>\n" +
            "                </Addresses>\n" +
            "                <OrgBranches>\n" +
            "                    <OrgBranch connectionDate=\"2020-07-07\" contractDate=\"2020-07-07\"\n" +
            "                               contractNum=\"247c7933-8eb0-4aa0-8838-4e510bc8fc54\"\n" +
            "                               branchId=\"53afc847-a5ae-49cf-8ab6-f58478b8e320\">\n" +
            "                        <BlockInfo>\n" +
            "                            <StopedService>0</StopedService>\n" +
            "                            <FinBlock>0</FinBlock>\n" +
            "                            <ContractRescission>0</ContractRescission>\n" +
            "                        </BlockInfo>\n" +
            "                        <ServicePacks>\n" +
            "                            <ServicePack packageId=\"368d5102-7c57-72ef-e054-00144ffaadb0\"/>\n" +
            "                        </ServicePacks>\n" +
            "                        <CreditContracts/>\n" +
            "                        <SalaryContracts/>\n" +
            "                        <ImprestAcc>0</ImprestAcc>\n" +
            "                    </OrgBranch>\n" +
            "                </OrgBranches>\n" +
            "                <OtherOrgData>\n" +
            "                    <InternationalName>TESTOVOE PREDPRIYATIE TEST 2808441397438</InternationalName>\n" +
            "                    <UseNameInDocs>0</UseNameInDocs>\n" +
            "                    <Locked>0</Locked>\n" +
            "                    <DboUse>0</DboUse>\n" +
            "                    <OrgKpp>\n" +
            "                        <KPPIndex>489819119</KPPIndex>\n" +
            "                        <CheckKPP>1</CheckKPP>\n" +
            "                    </OrgKpp>\n" +
            "                    <DictContrUse>0</DictContrUse>\n" +
            "                    <Frod>1</Frod>\n" +
            "                    <CertAuthId>A002YF</CertAuthId>\n" +
            "                    <LastCertifNum>19</LastCertifNum>\n" +
            "                    <Contacts/>\n" +
            "                    <CurrControlSettings/>\n" +
            "                    <RemoteAccessProtect>0</RemoteAccessProtect>\n" +
            "                </OtherOrgData>\n" +
            "                <AuthSigns>\n" +
            "                    <AuthSign>\n" +
            "                        <SignDeviceId>d4220b4c-4676-4ee6-9e46-2fb034d7ea1f</SignDeviceId>\n" +
            "                        <SignType>0</SignType>\n" +
            "                        <Duration unlimited=\"1\"/>\n" +
            "                        <Accept>\n" +
            "                            <Credentials>0</Credentials>\n" +
            "                        </Accept>\n" +
            "                        <Accounts>\n" +
            "                            <All>1</All>\n" +
            "                        </Accounts>\n" +
            "                        <Docs>\n" +
            "                            <All>1</All>\n" +
            "                        </Docs>\n" +
            "                    </AuthSign>\n" +
            "                    <AuthSign>\n" +
            "                        <SignDeviceId>0cdd11f5-0b09-4f48-b118-b2cc6030ccc2</SignDeviceId>\n" +
            "                        <SignType>0</SignType>\n" +
            "                        <Duration unlimited=\"1\"/>\n" +
            "                        <Accept>\n" +
            "                            <Credentials>0</Credentials>\n" +
            "                        </Accept>\n" +
            "                        <Accounts>\n" +
            "                            <All>1</All>\n" +
            "                        </Accounts>\n" +
            "                        <Docs>\n" +
            "                            <All>1</All>\n" +
            "                        </Docs>\n" +
            "                    </AuthSign>\n" +
            "                    <AuthSign>\n" +
            "                        <SignDeviceId>3b8ba994-a137-4c52-b8f3-d15a103bfc85</SignDeviceId>\n" +
            "                        <SignType>0</SignType>\n" +
            "                        <Duration unlimited=\"1\"/>\n" +
            "                        <Accept>\n" +
            "                            <Credentials>0</Credentials>\n" +
            "                        </Accept>\n" +
            "                        <Accounts>\n" +
            "                            <All>1</All>\n" +
            "                        </Accounts>\n" +
            "                        <Docs>\n" +
            "                            <All>1</All>\n" +
            "                        </Docs>\n" +
            "                    </AuthSign>\n" +
            "                    <AuthSign>\n" +
            "                        <SignDeviceId>c194f92b-4199-46a5-93db-804bdb222e9d</SignDeviceId>\n" +
            "                        <SignType>0</SignType>\n" +
            "                        <Duration unlimited=\"1\"/>\n" +
            "                        <Accept>\n" +
            "                            <Credentials>0</Credentials>\n" +
            "                        </Accept>\n" +
            "                        <Accounts>\n" +
            "                            <All>1</All>\n" +
            "                        </Accounts>\n" +
            "                        <Docs>\n" +
            "                            <All>1</All>\n" +
            "                        </Docs>\n" +
            "                    </AuthSign>\n" +
            "                    <AuthSign>\n" +
            "                        <SignDeviceId>3ad013f4-10d9-4c63-b974-08f3241fa91c</SignDeviceId>\n" +
            "                        <SignType>0</SignType>\n" +
            "                        <Duration unlimited=\"1\"/>\n" +
            "                        <Accept>\n" +
            "                            <Credentials>0</Credentials>\n" +
            "                        </Accept>\n" +
            "                        <Accounts>\n" +
            "                            <All>1</All>\n" +
            "                        </Accounts>\n" +
            "                        <Docs>\n" +
            "                            <All>1</All>\n" +
            "                        </Docs>\n" +
            "                    </AuthSign>\n" +
            "                </AuthSigns>\n" +
            "                <Beneficiars beneficiarDictStepId=\"0\"/>\n" +
            "                <Correspondents correspondentDictStepId=\"0\"/>\n" +
            "                <OrgDataVersion>7</OrgDataVersion>\n" +
            "            </OrgData>\n" +
            "            <Branches>\n" +
            "                <Branch blocked=\"0\" regNum=\"1481\" kpp=\"000000000\" bic=\"044525225\">\n" +
            "                    <BranchId>53afc847-a5ae-49cf-8ab6-f58478b8e320</BranchId>\n" +
            "                    <SystemName>175051</SystemName>\n" +
            "                    <ShortName>ДО №1784 Московского банка ПАО Сбербанк</ShortName>\n" +
            "                    <BankName>ПАО СБЕРБАНК</BankName>\n" +
            "                    <BranchType>Дополнительный офис</BranchType>\n" +
            "                    <ParentId>4ede2656-2923-4567-a679-100000001399</ParentId>\n" +
            "                    <Addresses/>\n" +
            "                    <BranchData>\n" +
            "                        <FullName>Дополнительный офис №1784 Московского банка ПАО Сбербанк</FullName>\n" +
            "                    </BranchData>\n" +
            "                    <Contacts/>\n" +
            "                    <Params>\n" +
            "                        <Param>\n" +
            "                            <Name>ENABLE_BUTTON_MODERNFORM_TO_BUDGET</Name>\n" +
            "                            <Value>false</Value>\n" +
            "                        </Param>\n" +
            "                    </Params>\n" +
            "                    <ServicePacks>\n" +
            "                        <ServicePack packageId=\"368d5102-7c57-72ef-e054-00144ffaadb0\"/>\n" +
            "                    </ServicePacks>\n" +
            "                </Branch>\n" +
            "                <Branch blocked=\"0\" regNum=\"1481\" bic=\"044525225\">\n" +
            "                    <BranchId>4ede2656-2923-4567-a679-100000001399</BranchId>\n" +
            "                    <SystemName>1398</SystemName>\n" +
            "                    <ShortName>Стромынское ОСБ №5281</ShortName>\n" +
            "                    <BankName>ПАО СБЕРБАНК</BankName>\n" +
            "                    <BranchType>Отделение банка</BranchType>\n" +
            "                    <ParentId>4ede2656-2923-4567-a679-100000001212</ParentId>\n" +
            "                    <Addresses/>\n" +
            "                    <BranchData>\n" +
            "                        <FullName>Стромынское ОСБ №5281 г. Москвы</FullName>\n" +
            "                    </BranchData>\n" +
            "                    <Contacts/>\n" +
            "                    <Params>\n" +
            "                        <Param>\n" +
            "                            <Name>ENABLE_BUTTON_MODERNFORM_TO_BUDGET</Name>\n" +
            "                            <Value>false</Value>\n" +
            "                        </Param>\n" +
            "                    </Params>\n" +
            "                    <ServicePacks>\n" +
            "                        <ServicePack packageId=\"3603f0a3-7120-0f6f-e054-00144ffaadb0\"/>\n" +
            "                    </ServicePacks>\n" +
            "                </Branch>\n" +
            "                <Branch blocked=\"0\" regNum=\"1481\" kpp=\"012345678\" inn=\"7707083893\" regDate=\"1900-03-01\"\n" +
            "                        ogrn=\"333333333333333\" bic=\"044525225\">\n" +
            "                    <BranchId>4ede2656-2923-4567-a679-100000001212</BranchId>\n" +
            "                    <SystemName>1211</SystemName>\n" +
            "                    <ShortName>МОСКОВСКИЙ БАНК ПАО Сбербанк</ShortName>\n" +
            "                    <BankName>ПАО СБЕРБАНК</BankName>\n" +
            "                    <BranchType>Филиал банка</BranchType>\n" +
            "                    <ParentId>4ede2656-2923-4567-a679-100000000001</ParentId>\n" +
            "                    <Addresses>\n" +
            "                        <Address>\n" +
            "                            <AddressTypeCode>post</AddressTypeCode>\n" +
            "                            <AddressType>Почтовый</AddressType>\n" +
            "                            <Country>RUS</Country>\n" +
            "                            <CountryName>Российская Федерация</CountryName>\n" +
            "                            <CountryShortName>РОССИЯ</CountryShortName>\n" +
            "                            <Zip>01234567890</Zip>\n" +
            "                            <Sub>Центральный</Sub>\n" +
            "                            <Area>Центральный</Area>\n" +
            "                            <City>Москва</City>\n" +
            "                            <SettlType>Г</SettlType>\n" +
            "                            <Str>Московская</Str>\n" +
            "                            <HNumber>1</HNumber>\n" +
            "                            <Corp>1</Corp>\n" +
            "                            <Office>1</Office>\n" +
            "                            <FullAddress>01234567890, RUSSIAN FEDERATION, Центральный, Центральный, г.Москва, Г ,\n" +
            "                                ул.Московская, д.1, корп./стр.1, кв.1\n" +
            "                            </FullAddress>\n" +
            "                        </Address>\n" +
            "                    </Addresses>\n" +
            "                    <BranchData>\n" +
            "                        <FullName>Московский Банк Сбербанка РФ</FullName>\n" +
            "                    </BranchData>\n" +
            "                    <Contacts/>\n" +
            "                    <Params>\n" +
            "                        <Param>\n" +
            "                            <Name>ENABLE_BUTTON_MODERNFORM_TO_BUDGET</Name>\n" +
            "                            <Value>false</Value>\n" +
            "                        </Param>\n" +
            "                    </Params>\n" +
            "                    <ServicePacks>\n" +
            "                        <ServicePack packageId=\"368d5102-7c57-72ef-e054-00144ffaadb0\"/>\n" +
            "                    </ServicePacks>\n" +
            "                </Branch>\n" +
            "                <Branch blocked=\"0\" kpp=\"000000000\" inn=\"1317131713\" bic=\"044525225\">\n" +
            "                    <BranchId>4ede2656-2923-4567-a679-100000000001</BranchId>\n" +
            "                    <SystemName>0</SystemName>\n" +
            "                    <ShortName>ПАО Сбербанк</ShortName>\n" +
            "                    <BankName>ПАО СБЕРБАНК</BankName>\n" +
            "                    <BranchType>Головное подразделение</BranchType>\n" +
            "                    <Addresses>\n" +
            "                        <Address>\n" +
            "                            <AddressTypeCode>juridical</AddressTypeCode>\n" +
            "                            <AddressType>Юридический</AddressType>\n" +
            "                            <Country>RUS</Country>\n" +
            "                            <CountryName>Российская Федерация</CountryName>\n" +
            "                            <CountryShortName>РОССИЯ</CountryShortName>\n" +
            "                            <City>Москва</City>\n" +
            "                            <FullAddress>Российская Федерация, г.Москва</FullAddress>\n" +
            "                        </Address>\n" +
            "                    </Addresses>\n" +
            "                    <BranchData>\n" +
            "                        <FullName>Публичное акционерное общество \"Сбербанк России\"</FullName>\n" +
            "                    </BranchData>\n" +
            "                    <Contacts>\n" +
            "                        <Tel>88007777777</Tel>\n" +
            "                    </Contacts>\n" +
            "                    <Params>\n" +
            "                        <Param>\n" +
            "                            <Name>SBERBANKID_OFFER_URL</Name>\n" +
            "                            <Value>https://www.sberbank.ru/common/img/uploaded/files/sberbank_id/agreement_SBBOL.pdf\n" +
            "                            </Value>\n" +
            "                        </Param>\n" +
            "                    </Params>\n" +
            "                    <ServicePacks>\n" +
            "                        <ServicePack packageId=\"368d5102-7c57-72ef-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"94733783-22a2-2848-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"6a315308-8643-4fdd-8d20-417ec652e617\"/>\n" +
            "                        <ServicePack packageId=\"194c3673-4f48-44be-bd09-5a73638cc9c5\"/>\n" +
            "                        <ServicePack packageId=\"8f881d71-ed05-4f43-b83c-521d25ab1886\"/>\n" +
            "                        <ServicePack packageId=\"9a7d8069-ab9a-7447-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-90c8-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8e7791a7-951c-076f-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"4f9c24b8-a334-5ccd-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"52ff4968-4d2b-6618-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9df8e78f-780d-3982-e053-f59e740a06f2\"/>\n" +
            "                        <ServicePack packageId=\"76d0d98d-98ad-2153-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"52ff4968-5ad9-6618-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8d4cc81e-c002-22a6-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8d4cd001-b715-43ed-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"00055944-4660-4cab-9024-08ad5fc9d402\"/>\n" +
            "                        <ServicePack packageId=\"7e38e12b-f8ef-5231-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"a638682b-dc9a-0641-e053-f59e740af35a\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-90bd-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9cc4de24-d14c-48db-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"984a31e4-bcc0-72c1-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"948548e7-62ba-58a2-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-9125-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"668e7828-ccf9-5794-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"94733783-1f66-2848-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"a638682b-dd17-0641-e053-f59e740af35a\"/>\n" +
            "                        <ServicePack packageId=\"8e779076-66f5-024a-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"5bde80e9-7030-3f3d-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9d2a2b5b-65a9-6381-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8e7793bc-bf8d-10dd-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-902f-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"94733783-1f4e-2848-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9cb11d2c-220b-2126-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9cc4de24-d16a-48db-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"2683eb44-a3fc-5d92-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"318420f5-3841-430a-bcf3-1fef3aa6f743\"/>\n" +
            "                        <ServicePack packageId=\"4ede2656-2923-4567-a693-100000000005\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-902e-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-9097-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8d4cdc03-21df-7487-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"a638682b-dcc7-0641-e053-f59e740af35a\"/>\n" +
            "                        <ServicePack packageId=\"a638682b-dd7c-0641-e053-f59e740af35a\"/>\n" +
            "                        <ServicePack packageId=\"668e7828-ccc7-5794-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-90f4-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"962be23c-0753-0cd3-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"154cd490-910b-40b4-b6b3-32786839a365\"/>\n" +
            "                        <ServicePack packageId=\"85aab506-71cc-2d35-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"a638682b-ddf9-0641-e053-f59e740af35a\"/>\n" +
            "                        <ServicePack packageId=\"6fbd3cbc-eab3-33b1-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-90c1-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"5a9d3543-0ca4-49f2-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8e7790db-be2f-02f9-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8509d25c-d8e5-51b5-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9cb11388-a8f7-1dba-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8e7792b3-c600-102b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9cb11d2c-2126-2126-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"6f84fa98-2346-0167-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8509cb8d-35b1-33fd-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"66929f58-1b7b-381a-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"a638682b-dd1a-0641-e053-f59e740af35a\"/>\n" +
            "                        <ServicePack packageId=\"914a1531-8e6d-4c88-ad02-ad0d8debfff8\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-9032-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-90a4-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"4d1894fd-ea79-2f63-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8e779136-f25a-0560-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"939b8d08-ae62-6553-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9602e35b-52ad-5593-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"6f841c6d-0557-672d-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"94733783-1f34-2848-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"871ee501-137f-49a8-ad48-3f631ece8629\"/>\n" +
            "                        <ServicePack packageId=\"8e779201-c542-0ad0-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-90a3-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"e7221b8f-48a2-40b4-91dc-7d3b4f1ada57\"/>\n" +
            "                        <ServicePack packageId=\"2920f267-5122-07ad-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-916e-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"66929f58-1b96-381a-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8d4cbca9-f1c9-6646-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"58b80811-f040-46ef-8b51-43aa4a7a0434\"/>\n" +
            "                        <ServicePack packageId=\"838b6755-bcbc-31cb-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"1635c584-b68e-4315-9add-9f38b4b4e248\"/>\n" +
            "                        <ServicePack packageId=\"52ff4966-8cdf-6618-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"5bdb7e02-6daa-41c1-9e0c-31ee5ee0571d\"/>\n" +
            "                        <ServicePack packageId=\"136d4dab-ab12-49aa-a84c-cc95215d1b73\"/>\n" +
            "                        <ServicePack packageId=\"52ff4968-4380-6618-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-9070-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-90f0-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"52ff4968-39ba-6618-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"6f841c6d-0570-672d-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-9133-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9cfb8210-7557-453c-8ec0-f1befca93bf0\"/>\n" +
            "                        <ServicePack packageId=\"5a9d3543-026c-49f2-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"76bf2376-7381-0aad-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"94733783-22d3-2848-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9d65c17d-551a-4b0f-b787-57621163c9e8\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-904f-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"76bf2376-7374-0aad-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8d4cc19e-1355-07bd-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8e779416-dd73-116b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"4ede2656-2923-4567-a693-100000000001\"/>\n" +
            "                        <ServicePack packageId=\"e8b5a987-d592-4a68-8138-57d0c93526e3\"/>\n" +
            "                        <ServicePack packageId=\"9c8abce9-bb49-364d-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"52ff4968-39cd-6618-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"6f841c6d-053d-672d-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"0750cd89-254e-42d6-8a2a-c8fa2a0a23ca\"/>\n" +
            "                        <ServicePack packageId=\"50bba22d-9f91-45ad-af8a-37313688c03a\"/>\n" +
            "                        <ServicePack packageId=\"3603f0a3-7120-0f6f-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"bc3fc339-f231-4287-9f22-31d3d03e7255\"/>\n" +
            "                        <ServicePack packageId=\"a638682b-dcba-0641-e053-f59e740af35a\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-915a-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-9148-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-9029-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"a638682b-dccd-0641-e053-f59e740af35a\"/>\n" +
            "                        <ServicePack packageId=\"0cf41d9a-ba50-4f42-b68c-ddb0238034aa\"/>\n" +
            "                        <ServicePack packageId=\"1efb58fb-9d7b-28cb-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"956aee9f-0846-54e9-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8e779471-1c91-11b3-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"78dc67e0-2bc8-5b2e-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8509c843-a5b8-25b8-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"9c68217a-9175-0d5b-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"d4c4d9d2-06d9-49d8-9605-7fc11f189945\"/>\n" +
            "                        <ServicePack packageId=\"7e38e12b-f8d8-5231-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"94733783-1f36-2848-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"8509c954-4c10-2c53-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"956aee9f-084e-54e9-e054-00144ffaadb0\"/>\n" +
            "                        <ServicePack packageId=\"a638682b-dd1e-0641-e053-f59e740af35a\"/>\n" +
            "                    </ServicePacks>\n" +
            "                </Branch>\n" +
            "            </Branches>\n" +
            "            <ServicePackages>\n" +
            "                <ServicePackage>\n" +
            "                    <PackageId>9cfb8210-7557-453c-8ec0-f1befca93bf0</PackageId>\n" +
            "                    <PackageName>E-invoicing</PackageName>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>130</ServiceId>\n" +
            "                        <ServiceName>CertificateAddRequest</ServiceName>\n" +
            "                        <AvailableInStandIn9999>false</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>135589</ServiceId>\n" +
            "                        <ServiceName>LeasingRequest</ServiceName>\n" +
            "                        <AvailableInStandIn9999>true</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>9542</ServiceId>\n" +
            "                        <ServiceName>eInvoicing</ServiceName>\n" +
            "                        <AvailableInStandIn9999>false</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>10547</ServiceId>\n" +
            "                        <ServiceName>NewSBBOLAccess</ServiceName>\n" +
            "                        <AvailableInStandIn9999>true</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>147881</ServiceId>\n" +
            "                        <ServiceName>SmartHelpBox</ServiceName>\n" +
            "                        <AvailableInStandIn9999>true</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>85559</ServiceId>\n" +
            "                        <ServiceName>clientCode</ServiceName>\n" +
            "                        <AvailableInStandIn9999>false</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>85617</ServiceId>\n" +
            "                        <ServiceName>CloudSign</ServiceName>\n" +
            "                        <AvailableInStandIn9999>false</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>86557</ServiceId>\n" +
            "                        <ServiceName>CreditAssistance</ServiceName>\n" +
            "                        <AvailableInStandIn9999>false</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>44557</ServiceId>\n" +
            "                        <ServiceName>GovReport</ServiceName>\n" +
            "                        <AvailableInStandIn9999>false</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>44559</ServiceId>\n" +
            "                        <ServiceName>ContragentCheck</ServiceName>\n" +
            "                        <AvailableInStandIn9999>false</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>49557</ServiceId>\n" +
            "                        <ServiceName>TaskForUploadDict</ServiceName>\n" +
            "                        <AvailableInStandIn9999>false</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>116573</ServiceId>\n" +
            "                        <ServiceName>OfferCloudSign</ServiceName>\n" +
            "                        <AvailableInStandIn9999>true</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>56559</ServiceId>\n" +
            "                        <ServiceName>TrafficLight</ServiceName>\n" +
            "                        <AvailableInStandIn9999>false</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>56561</ServiceId>\n" +
            "                        <ServiceName>TrafficLightForOrg</ServiceName>\n" +
            "                        <AvailableInStandIn9999>false</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                    <Service>\n" +
            "                        <ServiceId>61557</ServiceId>\n" +
            "                        <ServiceName>CreditRequestMMB</ServiceName>\n" +
            "                        <AvailableInStandIn9999>false</AvailableInStandIn9999>\n" +
            "                    </Service>\n" +
            "                </ServicePackage>\n" +
            "            </ServicePackages>\n" +
            "            <CreditContracts/>\n" +
            "            <SalaryContracts/>\n" +
            "            <SignDevices>\n" +
            "                <SignDevice>\n" +
            "                    <SignDeviceId>e56fa166-b0ca-4907-9971-1a5e98db422f</SignDeviceId>\n" +
            "                    <Alias>987774991</Alias>\n" +
            "                    <ProfileName>Круглов Денис Николаевич</ProfileName>\n" +
            "                    <Post>Аналитик</Post>\n" +
            "                    <CryptotypeId>fedcba00-0001-0004-0006-123456789000</CryptotypeId>\n" +
            "                    <CryptoTypeName>OneTimePassword</CryptoTypeName>\n" +
            "                    <SignUse>0</SignUse>\n" +
            "                    <Certificates/>\n" +
            "                </SignDevice>\n" +
            "            </SignDevices>\n" +
            "            <AuthPersons>\n" +
            "                <AuthPerson blocked=\"0\">\n" +
            "                    <Login>Bregel</Login>\n" +
            "                    <FIO>Питер Паркер Брейгель Старший</FIO>\n" +
            "                    <OrgId>fb461bce-f403-48b6-8711-679ce829e8be</OrgId>\n" +
            "                    <OneTimePassword>1</OneTimePassword>\n" +
            "                    <Tel>79169317489</Tel>\n" +
            "                    <SignDevices>\n" +
            "                        <SignDevice>\n" +
            "                            <SignDeviceId>3ad013f4-10d9-4c63-b974-08f3241fa91c</SignDeviceId>\n" +
            "                        </SignDevice>\n" +
            "                        <SignDevice>\n" +
            "                            <SignDeviceId>9a9af076-b41c-4ca9-876b-b013d78deb9e</SignDeviceId>\n" +
            "                        </SignDevice>\n" +
            "                    </SignDevices>\n" +
            "                    <CHInfo>\n" +
            "                        <CHId>1619703</CHId>\n" +
            "                        <FIO>Питер Паркер Брейгель Старший</FIO>\n" +
            "                        <BirthDate>1996-01-09</BirthDate>\n" +
            "                        <MobilTel>79169317489</MobilTel>\n" +
            "                        <Email>rezenova@mail.ru</Email>\n" +
            "                    </CHInfo>\n" +
            "                    <UserRoles>\n" +
            "                        <UserRole>\n" +
            "                            <UserRoleName>bankClient</UserRoleName>\n" +
            "                        </UserRole>\n" +
            "                    </UserRoles>\n" +
            "                    <UserId>1652819</UserId>\n" +
            "                    <UserGuid>1ad1b74a-4151-4840-8f74-681bbff91be3</UserGuid>\n" +
            "                    <Employee isIdentified=\"0\" branchName=\"Команда Startrek\" position=\"Тестировщик\" sex=\"F\"\n" +
            "                              birthPlace=\"Роддом №11\" dateOfBirth=\"1996-01-09\">\n" +
            "                        <Citizenship countryCode=\"RUS\" countryNumCode=\"643\" countryName=\"РОССИЯ\"/>\n" +
            "                        <Document endDate=\"2033-01-01\" branchCode=\"111-333\" branch=\"Отделением полиции\"\n" +
            "                                  date=\"2002-01-01\" number=\"222111\" series=\"1231\"\n" +
            "                                  typeName=\"Паспорт гражданина Российской Федерации\" typeCode=\"21\"/>\n" +
            "                    </Employee>\n" +
            "                </AuthPerson>\n" +
            "            </AuthPersons>\n" +
            "            <Sign>\n" +
            "                <Issuer>E = casbrf@sberbank.ru,2.5.4.33 = Тестирующий Q,CN = ЛавринСВ-Тестовая печать-УЦ-9,OU =\n" +
            "                    Удостоверяющий центр СБ РФ (Тестовый),O = ОАО \"Сбербанк России\",C = RU\n" +
            "                </Issuer>\n" +
            "                <SN>78213F651F734B6E862E</SN>\n" +
            "                <Value>UgWTXBj3AQkFAMjUsZVKowL9xds/6qGgmzHLAIdJthcnNRWAjL+gVe/A2i3RC5kEUQ59gUD+NxsODRpv96N0/A==</Value>\n" +
            "                <DigestName>for_upg</DigestName>\n" +
            "                <DigestVersion>1</DigestVersion>\n" +
            "            </Sign>\n" +
            "        </OrganizationInfo>\n" +
            "    </OrganizationsInfo>\n" +
            "</Response>";
}
