package com.leewardassociates.logs.main;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leewardassociates.logs.common.util.DBUtils;
import com.leewardassociates.logs.dao.ErrorsDAO;
import com.leewardassociates.logs.models.ConnectionInfoModel;
import com.leewardassociates.logs.models.ErrorsModel;

public class LogErrorListener {

	private static Logger log = LoggerFactory.getLogger(LogErrorListener.class);
	static Connection conn = null;
	private LogErrorListener listener = null;
	
	private LogErrorListener() {
	}
	
	public LogErrorListener getInstance() {
		if (this.listener == null) {
			this.listener = new LogErrorListener();
		}
		return this.listener;
	}
	
	public void establishConnection(ConnectionInfoModel cim) {
		try {
			DBUtils.registerDriver(cim.getDriverClassName());
			DBUtils.init(cim);			
			conn = DBUtils.getConnection();
		} catch (Exception e) {
			log.error("Exception setting up database connection: "+ e.getMessage(), e);
		}
	}
	
	public void logError(ErrorsModel error) {
//		ErrorsDAO dao = new ErrorsDAO(conn.get);
	}
}
