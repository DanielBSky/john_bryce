package com.brodsky.DAO.DBDAO;

public enum Categories {
    SPECTACLE(1),
    CONCERT(2),
    PARTY(3),
    CONFERENCE(4),
    LECTURE(5);

    private final int ID;

    private Categories(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}

