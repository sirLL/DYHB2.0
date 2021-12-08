package com.dy.tablayout.listener;

public class TabEntity implements CustomTabEntity {
    private String title;
    private int selectedIcon;
    private int unselectedIcon;

    public TabEntity(String title) {
        this.title = title;
    }

    public TabEntity(String title, int selectedIcon, int unselectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unselectedIcon;
    }
}
