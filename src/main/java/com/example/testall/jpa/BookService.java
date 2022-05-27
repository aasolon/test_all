package com.example.testall.jpa;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class BookService {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void postConstruct() throws SQLException {
        Connection conn = dataSource.getConnection();
        DSLContext context = DSL.using(conn, SQLDialect.H2);
        Field<Boolean> isActive = DSL.field("ISACTIVE", Boolean.class);
        Field<String> issuer = DSL.field("ISSUER", String.class);
        Field<String> serialNumber = DSL.field("SERIALNUMBER", String.class);
        SelectConditionStep<Record1<Boolean>> and = context
                .select(isActive)
                .from("CERTIFICATE")
                .where(serialNumber.eq("serialNumber"));
        int i = 0;
    }
}
