package com.hd.learnshiro.conroller;

import java.util.Collection;

import com.hd.learnshiro.dao.StormtrooperDao;
import com.hd.learnshiro.domain.Stormtrooper;
import com.hd.learnshiro.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/troopers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class StormtrooperController {

    private final StormtrooperDao trooperDao;

    @Autowired
    public StormtrooperController(StormtrooperDao trooperDao) {
        this.trooperDao = trooperDao;
    }

    @GetMapping()
    @RequiresRoles(logical = Logical.OR, value = {"admin", "officer", "underling"})
    public Collection<Stormtrooper> listTroopers() {
        log.debug("listTroopers()");

        Subject subject = SecurityUtils.getSubject();
        log.debug("subject = {}", subject);
        Object principal = subject.getPrincipal();
        log.debug("principal = {}", principal);

        return trooperDao.listStormtroopers();
    }

    @GetMapping(path = "/{id}")
    @RequiresRoles(logical = Logical.OR, value = {"admin", "officer", "underling"})
    public Stormtrooper getTrooper(@PathVariable("id") String id) throws NotFoundException {

        Stormtrooper stormtrooper = trooperDao.getStormtrooper(id);
        if (stormtrooper == null) {
            throw new NotFoundException(id);
        }
        return stormtrooper;
    }

    @PostMapping()
    @RequiresRoles(logical = Logical.OR, value = {"admin", "officer"})
    public Stormtrooper createTrooper(@RequestBody Stormtrooper trooper) {

        return trooperDao.addStormtrooper(trooper);
    }

    @PostMapping(path = "/{id}")
    @RequiresRoles("admin")
    public Stormtrooper updateTrooper(@PathVariable("id") String id, @RequestBody Stormtrooper updatedTrooper) throws NotFoundException {

        return trooperDao.updateStormtrooper(id, updatedTrooper);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequiresRoles("admin")
    public void deleteTrooper(@PathVariable("id") String id) {
        trooperDao.deleteStormtrooper(id);
    }
}
