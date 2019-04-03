package com.venkat.myspring.dao;


import java.io.Serializable;
import java.util.List;

public interface ExtendedRepository<T, ID extends Serializable> {
    List<T> findByAttributeContainsText(String attributeName, String text);
    T updateWith(T with, ID id);
}