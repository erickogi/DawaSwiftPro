package com.tufike.taxi.common.models;

public enum CRUD {
    CREATE(0),
    READ(1),
    UPDATE(2),
    DELETE(3);
    private int value;
    CRUD(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static CRUD get(int code) {
        for(CRUD s : values()) {
            if(s.value == code) return s;
        }
        return READ;
    }
}
