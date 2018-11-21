package com.elliot.service;

import com.elliot.database.DatabaseConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseConnectionManagerService {

    @Autowired
    private DatabaseConnectionManager databaseConnectionManager;

    public String getTimestampOfDatabaseChange() {
        return this.databaseConnectionManager.getTimestampOfDatabaseChange();
    }
}
