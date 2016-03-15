package com.leewardassociates.logs.services;
 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.leewardassociates.logs.dao.ErrorsDAO;
import com.leewardassociates.logs.models.ErrorsModel;
 
 
@Service("errorsService")
public class ErrorsService {
 
    private static Logger log = LoggerFactory.getLogger(ErrorsService.class);

	 
    @Autowired
    private ErrorsDAO errorsDao;
	 

    public List<ErrorsModel> getErrorsList() {
        return errorsDao.getErrorsList();
    }


    public ErrorsModel getErrors(Integer id) {
        return errorsDao.getErrors(id);
    }

	 
    @Transactional(propagation=Propagation.REQUIRED)
    public int updateErrors(ErrorsModel bean) {
        return errorsDao.updateErrors(bean);
    }

	 
    @Transactional(propagation=Propagation.REQUIRED)
    public int deleteErrors(Integer id) {
        return errorsDao.deleteErrors(id);
    }

	 
}
