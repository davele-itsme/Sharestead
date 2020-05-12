package dk.via.sharestead.model;

public class SliderItem {
    private String platformText;
    private int image;
    private boolean selected;

    public SliderItem(String platformText, int image)
    {
        this.platformText = platformText;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public String getPlatformText()
    {
        return platformText;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
