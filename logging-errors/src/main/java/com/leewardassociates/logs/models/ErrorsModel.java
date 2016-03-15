package com.leewardassociates.logs.models;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ErrorsModel implements Serializable {

    private static Logger log = LoggerFactory.getLogger(ErrorsModel.class);
    private Integer id;
    private String timestamp;
    private String loglevel;
    private String errormessage;
    private String stacktrace;
    private Date created;
    private Date modified;

	
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getLoglevel() {
        return this.loglevel;
    }

    public void setLoglevel(String loglevel) {
        this.loglevel = loglevel;
    }


    public String getErrormessage() {
        return this.errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }


    public String getStacktrace() {
        return this.stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }


    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


    public Date getModified() {
        return this.modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }



	
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                Method getter = this.getClass().getMethod("get"+StringUtils.capitalize(field.getName()), null);
                sb.append(field.getName()+": ");
                sb.append(getter.invoke(this, null)).append("");
            } catch (Exception e) {
                log.error("Exception outputting toString: " + e.getMessage(), e);
            }
        }
        return sb.toString();
    }

}
