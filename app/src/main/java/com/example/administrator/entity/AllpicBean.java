package com.example.administrator.entity;

/**
 * Created by WangYueli on 2016/10/12.
 */

public class AllpicBean {

    /**
     * Image : Http://120.27.141.95:8221/UploadFile/mediaImage/HomePage0007.jpg
     * Type : 0
     */

    private String Image;
    private String Type;

    public AllpicBean() {
    }

    public AllpicBean(String image, String type) {
        Image = image;
        Type = type;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    @Override
    public String toString() {
        return Image+"type:"+Type;
    }
}
