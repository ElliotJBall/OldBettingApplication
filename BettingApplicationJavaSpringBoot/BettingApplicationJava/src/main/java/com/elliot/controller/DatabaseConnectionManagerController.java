package com.elliot.controller;

import com.elliot.service.DatabaseConnectionManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/footballTeam")
public class DatabaseConnectionManagerController {

    @Autowired
    private DatabaseConnectionManagerService databaseConnectionManagerService;

    @CrossOrigin
    @RequestMapping(value = "/mostRecentDatabaseChange", method = RequestMethod.GET, produces = "application/json")
    public String getTimestampOfDatabaseChange() {
        return this.databaseConnectionManagerService.getTimestampOfDatabaseChange();
    }
}
