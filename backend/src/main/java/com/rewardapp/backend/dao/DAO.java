package com.rewardapp.backend.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    List<T> list();
    void create(T t);
    Optional<T> get(Integer id);
    void update(T t, Integer id);
    void delete(Integer id);
}
