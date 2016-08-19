package com.dpi.financial.ftcom.model.base;

import java.util.HashMap;
import java.util.Map;

public class QueryParameterUtil {
    private Map parameters = null;

    private QueryParameterUtil(String name, Object value) {
        this.parameters = new HashMap();
        this.parameters.put(name, value);
    }

    public static QueryParameterUtil with(String name, Object value) {
        return new QueryParameterUtil(name, value);
    }

    public QueryParameterUtil and(String name, Object value) {
        this.parameters.put(name, value);
        return this;
    }

    public Map parameters() {
        return this.parameters;
    }
}
