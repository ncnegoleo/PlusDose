package br.com.ifpb.solarsoft.entities;

/**
 * Created by leonardo on 20/05/2015.
 */
public class SectionItem implements Item {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean isSection() {
        return true;
    }
}
