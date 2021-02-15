package com.example.homefit;

public class SliderItem {

    private int image;
    private int title;
    SliderItem(int image,int title)
    {
        this.image = image;
        this.title = title;

    }
    public int getImage(){
        return image;
    }
    public int getTitle(){return title;}
}
