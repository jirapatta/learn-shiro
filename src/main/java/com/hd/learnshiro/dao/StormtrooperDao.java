package com.hd.learnshiro.dao;

import java.util.Collection;

import com.hd.learnshiro.domain.Stormtrooper;

public interface StormtrooperDao {
    Collection<Stormtrooper> listStormtroopers();

    Stormtrooper getStormtrooper(String id);

    Stormtrooper addStormtrooper(Stormtrooper stormtrooper);

    Stormtrooper updateStormtrooper(String id, Stormtrooper stormtrooper);

    boolean deleteStormtrooper(String id);
}
