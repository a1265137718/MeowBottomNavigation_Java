package com.etebarian.navigation;

public class Model {
    public Model(int id, int icon) {
        this.id = id;
        this.icon = icon;
    }

    private int id;
    private int icon;
    String count = MeowBottomNavigationCell.EMPTY_VALUE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}