package com.leewardassociates.logs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.leewardassociates.logs.common.util.DBConstants;
import com.leewardassociates.logs.models.ErrorsModel;

@Repository("errorsDao")
public class ErrorsDAO implements DBConstants {
	
    private static Logger log = LoggerFactory.getLogger(ErrorsDAO.class);

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private ErrorsDAO() {
    }
    
    public ErrorsDAO(DataSource dataSource) {
    	this();
    	setDataSource(dataSource);
    }
    
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }
    
    public JdbcTemplate getTemplate() {
        return this.jdbcTemplate;
    }
	
    public int addErrors(ErrorsModel bean) {
        StringBuffer sInsertStmt = new StringBuffer(200);
        sInsertStmt.append( "INSERT INTO " + ERRORS + " (")
            .append(" id " )
            .append(", timestamp " )
            .append(", loglevel " )
            .append(", errormessage " )
            .append(", stacktrace " )
            .append(", created " )
            .append(", modified " )
            .append(") VALUES ( ")
            .append(", ?")
            .append(", ?")
            .append(", ?")
            .append(", ?")
            .append(", ?")
            .append(", ?")
            .append(", ?")
            .append(")");
        Object[] args = {
            bean.getId(), 
            bean.getTimestamp(), 
            bean.getLoglevel(), 
            bean.getErrormessage(), 
            bean.getStacktrace(), 
            bean.getCreated(), 
            bean.getModified()};
        int numRows = getTemplate().update(sInsertStmt.toString(), args);
        return getAutoIncrementKey();
    }


    public int updateErrors(ErrorsModel bean) {
        StringBuffer sUpdateStmt = new StringBuffer(200);
        sUpdateStmt.append("UPDATE " + ERRORS)
        .append(" SET ")
        .append(" id = ? " )
        .append(", timestamp = ? " )
        .append(", loglevel = ? " )
        .append(", errormessage = ? " )
        .append(", stacktrace = ? " )
        .append(", created = ? " )
        .append(", modified = ? " ); 
        StringBuffer sWhereStmt = new StringBuffer(100);
        sWhereStmt.append(" WHERE id = ?");
        sUpdateStmt.append( sWhereStmt );
        Object[] args = {
            bean.getId(), 
            bean.getTimestamp(), 
            bean.getLoglevel(), 
            bean.getErrormessage(), 
            bean.getStacktrace(), 
            bean.getCreated(), 
            bean.getModified()};
        int numRows = getTemplate().update(sUpdateStmt.toString(), args);
        return numRows;
    }


    public ErrorsModel getErrors(Integer id) {
    String sqlString = "select " +
        "id" +
        ", timestamp" +
        ", loglevel" +
        ", errormessage" +
        ", stacktrace" +
        ", created" +
        ", modified" +
        " from " + ERRORS + " where id = ?";
        Object[] args = {id};
        List<ErrorsModel> matches = getTemplate().query(sqlString, args, new RowMapper<ErrorsModel>() {
            public ErrorsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                ErrorsModel model = new ErrorsModel();
                    model.setId(rs.getInt("id"));
                    model.setTimestamp(rs.getString("timestamp"));
                    model.setLoglevel(rs.getString("loglevel"));
                    model.setErrormessage(rs.getString("errormessage"));
                    model.setStacktrace(rs.getString("stacktrace"));
                    model.setCreated(rs.getTimestamp("created")!=null?new java.util.Date(rs.getTimestamp("created").getTime()):null);
                    model.setModified(rs.getTimestamp("modified")!=null?new java.util.Date(rs.getTimestamp("modified").getTime()):null);
                return model;
            }
        });
        return matches!=null&&matches.size()>0?matches.get(0):null;
    }


    public List<ErrorsModel> getErrorsList() {
    String sqlString = "select " +
        "id" +
        ", timestamp" +
        ", loglevel" +
        ", errormessage" +
        ", stacktrace" +
        ", created" +
        ", modified" +
        " from " + ERRORS;
        return getTemplate().query(sqlString, new RowMapper<ErrorsModel>() {
            public ErrorsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                ErrorsModel model = new ErrorsModel();
                    model.setId(rs.getInt("id"));
                    model.setTimestamp(rs.getString("timestamp"));
                    model.setLoglevel(rs.getString("loglevel"));
                    model.setErrormessage(rs.getString("errormessage"));
                    model.setStacktrace(rs.getString("stacktrace"));
                    model.setCreated(rs.getTimestamp("created")!=null?new java.util.Date(rs.getTimestamp("created").getTime()):null);
                    model.setModified(rs.getTimestamp("modified")!=null?new java.util.Date(rs.getTimestamp("modified").getTime()):null);
                return model;
            }
        });
    }


    public int deleteErrors(Integer id) {
        StringBuffer sDeleteStmt = new StringBuffer(200);
        sDeleteStmt.append("DELETE FROM " + ERRORS);
        StringBuffer sWhereStmt = new StringBuffer(100);
        sWhereStmt.append(" WHERE id = ?");
        sDeleteStmt.append(sWhereStmt);
        Object[] args = {id};
        int numRows = getTemplate().update(sDeleteStmt.toString(), args);
        return numRows;
    }


    public int getAutoIncrementKey() {
        String sqlString = "select last_insert_id()";
        return getTemplate().queryForObject(sqlString, null, Integer.class);
    }


}
