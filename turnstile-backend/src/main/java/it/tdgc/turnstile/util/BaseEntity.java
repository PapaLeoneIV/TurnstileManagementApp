package it.tdgc.turnstile.util;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

    protected boolean deleted;

}