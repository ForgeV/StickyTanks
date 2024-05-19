package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Buttons extends StickyTanks {
    public float x;
    public float y;
    float textureX;
    float textureY;
    public float width;
    public float height;
    float textureWidth;
    float textureHeight;
    String text;
    private boolean isTextButton, isTextureButton;
    BitmapFont font;
    public Buttons(float x, float y, float size) {
        this.x = x;
        this.y = y;
        width = height = size;
    }

    public Buttons(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Buttons(float x, float y, String text, BitmapFont font) {
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
        GlyphLayout glyphLayout = new GlyphLayout(this.font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
        isTextButton = true;
    }
    public Buttons(float x, float y, String text, BitmapFont font, boolean isTextureButton) {
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
        GlyphLayout glyphLayout = new GlyphLayout(this.font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
        textureX = x*3/2;
        textureY = y*3/2;
        textureHeight = height*3/2;
        textureWidth = width*3/2;
        isTextButton = true;
        isTextureButton = true;
    }

    public boolean click(float touchX, float touchY) {
        if (isTextButton) {
            return x < touchX & touchX < x + width & y > touchY & touchY > y - height;
        }
        return x<touchX & touchX<x+width & y<touchY & touchY<y+height;
    }


}
