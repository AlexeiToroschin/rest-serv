package com.organization.test.Json;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "SRCH_RESULT",
        "ERROR_TEXT"
})
public class JsonPojo {

    @JsonProperty("SRCH_RESULT")
    private String sRCHRESULT;
    @JsonProperty("ERROR_TEXT")
    private String eRRORTEXT;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("SRCH_RESULT")
    public String getSRCHRESULT() {
        return sRCHRESULT;
    }

    @JsonProperty("SRCH_RESULT")
    public void setSRCHRESULT(String sRCHRESULT) {
        this.sRCHRESULT = sRCHRESULT;
    }
    @JsonProperty("ERROR_TEXT")
    public String getERRORTEXT() {
        return eRRORTEXT;
    }

    @JsonProperty("ERROR_TEXT")
    public void setERRORTEXT(String eRRORTEXT) {
        this.eRRORTEXT = eRRORTEXT;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
