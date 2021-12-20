package ru.on8off.postgres.repository.masterdb.entity;

public enum ElementGroupType {
    TYPE1(1),
    TYPE2(2);

    private int id;

    ElementGroupType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ElementGroupType valueOf(Integer id){
        return switch (id) {
            case 1 -> TYPE1;
            case 2 -> TYPE2;
            default -> throw new IllegalArgumentException(id + " not supported.");
        };
    }
}
