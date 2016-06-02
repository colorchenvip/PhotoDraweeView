package me.relex.photodraweeview.sample.model;

import java.io.Serializable;

/**
 * Created by color on 16/6/1 17:38.
 */
public class ImageModel implements Serializable {
    private int type = 0;//type: 1=imageUrl;2=imageResource;
    private String imageUrl = null;
    private String imageResource = null;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource ="res:///" +  imageResource;
    }
}
