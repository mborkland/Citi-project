package edu.usf.cse.service;

import edu.usf.cse.persistence.CustomerRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordService {

    @Autowired
    private CustomerRecordRepository customerRecordRepository;


}
