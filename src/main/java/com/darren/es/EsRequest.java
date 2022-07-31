package com.darren.es;

/**
 * <h3>mybatis</h3>
 * <p></p>
 *
 * @author : Darren
 * @date : 2022年07月31日 11:49:46
 **/
public class EsRequest<T> {

    /**
     * SQL query to execute
     */
    private String sql;

    /**
     * The maximum number of rows (or entries) to return in one response
     * default value 1000
     */
    private Integer fetchSize;

    /**
     * Optional Elasticsearch query DSL for additional filtering.
     */
    private T filter;

    /**
     * The timeout before the request fails.
     * default 90s
     */
    private String requestTimeout;

    /**
     * The timeout before a pagination request fails.
     * default value 45s
     */
    private String pageTimeout;

    /**
     * Time-zone in ISO 8601 used for executing the query on the server. More information available here.
     *
     * default value Z (or UTC)
     */
    private String timeZone;

    /**
     * Return the results in a columnar fashion, rather than row-based fashion. Valid for json, yaml, cbor and smile.
     *
     * default value false
     */
    private Boolean columnar;

    /**
     * Throw an exception when encountering multiple values for a field (default) or be lenient
     * and return the first value from the list (without any guarantees of what that will be - typically the first in natural ascending order).
     * default value false
     */
    private Boolean fieldMultiValueLeniency;

    /**
     * Whether to include frozen-indices in the query execution or not (default).
     *
     * default value false
     */
    private Boolean indexIncludeFrozen;

    /**
     * Optional list of parameters to replace question mark (?) placeholders inside the query.
     */
    private T params;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Integer getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(Integer fetchSize) {
        this.fetchSize = fetchSize;
    }

    public T getFilter() {
        return filter;
    }

    public void setFilter(T filter) {
        this.filter = filter;
    }

    public String getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(String requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public String getPageTimeout() {
        return pageTimeout;
    }

    public void setPageTimeout(String pageTimeout) {
        this.pageTimeout = pageTimeout;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Boolean getColumnar() {
        return columnar;
    }

    public void setColumnar(Boolean columnar) {
        this.columnar = columnar;
    }

    public Boolean getFieldMultiValueLeniency() {
        return fieldMultiValueLeniency;
    }

    public void setFieldMultiValueLeniency(Boolean fieldMultiValueLeniency) {
        this.fieldMultiValueLeniency = fieldMultiValueLeniency;
    }

    public Boolean getIndexIncludeFrozen() {
        return indexIncludeFrozen;
    }

    public void setIndexIncludeFrozen(Boolean indexIncludeFrozen) {
        this.indexIncludeFrozen = indexIncludeFrozen;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }
}

