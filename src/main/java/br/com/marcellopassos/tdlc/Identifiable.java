package br.com.marcellopassos.tdlc;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> extends Serializable {

    T getId();

}
