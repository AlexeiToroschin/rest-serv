package com.organization.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.organization.test.Json.JsonPojo;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public class Operation {

    private static final Logger log = LoggerFactory.getLogger(Operation.class);

    public static String getUUID() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "")
                .toUpperCase();
    }

    public static String getTime() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .format(java.util.Calendar.getInstance().getTime());
    }

    // method for checking live status of the service
    public String getProjectName() {
       // InputStream inputStream = null;
        String result = "READY";
        return result;
    }

    public void getInnFromBody(Exchange exch) {
        exch.getIn().setHeader("inn", exch.getIn().getBody(String.class).substring(exch.getIn().getBody(String.class).indexOf("=")+1,exch.getIn().getBody(String.class).length()));
    }

    public void setAnswerNullToBody(Exchange exch) throws SQLException, JsonProcessingException {
        JsonPojo pojo = new JsonPojo();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
     //   pojo.setIBSODATE(sdfDate.format(now));
        pojo.setSRCHRESULT("NOT_FOUND");
        ObjectMapper mapper = new ObjectMapper();
        exch.getIn().setBody(mapper.writeValueAsString(pojo));
    }

    public void setAnswerNullToBodySMXDate(Exchange exch) throws SQLException, JsonProcessingException {
        JsonPojo pojo = new JsonPojo();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
    //    pojo.setSMXDATE(sdfDate.format(now));
        pojo.setSRCHRESULT("NOT_FOUND");
        ObjectMapper mapper = new ObjectMapper();
        exch.getIn().setBody(mapper.writeValueAsString(pojo));
    }



}
