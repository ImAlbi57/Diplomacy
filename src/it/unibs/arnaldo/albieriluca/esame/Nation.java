package it.unibs.arnaldo.albieriluca.esame;

import java.util.Objects;

public class Nation {
    private String name;
    private Type type;
    private boolean initialSituazion;

    public Nation(String name, Type type){
        this.name = name;
        this.type = type;
    }
    public Nation(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nation nation = (Nation) o;
        return this.name.equals(nation.getName());
    }
}
