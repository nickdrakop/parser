/**
 @author nick.drakopoulos
 */
package com.ef.parser.data;

public abstract class AbstractEntity<T> {
    public abstract T getId();

    public abstract void setId(T id);
}
