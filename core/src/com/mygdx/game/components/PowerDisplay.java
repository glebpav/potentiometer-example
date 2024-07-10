package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PowerDisplay extends Actor {
    private final float minValue;
    private final float maxValue;
    private float idealValue;
    private final float inaccuracy;
    private float currentValue;
    private final ShapeRenderer shapeRenderer;

    private static final Color PROGRESS_BACKGROUND = new Color(0xDDf8E2ff);
    private static final Color PROGRESS_FOREGROUND = new Color(0x85CA92ff);
    private static final Color INACCURACY_BORDERS = new Color(0xFFFFFFff);
    private static final Color BORDER_COLOR = new Color(0x000000ff);

    public PowerDisplay(float minValue, float maxValue, float idealValue, float inaccuracy) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.idealValue = idealValue;
        this.inaccuracy = inaccuracy;
        this.currentValue = minValue;
        this.shapeRenderer = new ShapeRenderer();
        setSize(200, 50);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(BORDER_COLOR);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());

        float lineSize = Math.min(Math.round(Math.min(getHeight(), getWidth()) / 20), 5);

        float LinedX = getX() + lineSize;
        float LinedY = getY() + lineSize;
        float LinedWidth = getWidth() - lineSize * 2;
        float LinedHeight = getHeight() - lineSize * 2;

        shapeRenderer.setColor(PROGRESS_BACKGROUND);
        shapeRenderer.rect(LinedX, LinedY, LinedWidth, LinedHeight);

        float progressWidth = (currentValue - minValue) / (maxValue - minValue) * LinedWidth;

        shapeRenderer.setColor(PROGRESS_FOREGROUND);
        shapeRenderer.rect(LinedX, LinedY, progressWidth, LinedHeight);

        shapeRenderer.setColor(INACCURACY_BORDERS);
        if(idealValue - inaccuracy > minValue) {
            shapeRenderer.rect(LinedX + (idealValue - inaccuracy - minValue) / (maxValue - minValue) * LinedWidth, LinedY, lineSize, LinedHeight);
        }
        if (idealValue + inaccuracy < maxValue) {
            shapeRenderer.rect(LinedX + (idealValue + inaccuracy - minValue) / (maxValue - minValue) * LinedWidth, LinedY, lineSize, LinedHeight);
        }
        shapeRenderer.end();

        batch.begin();
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = Math.max(minValue, Math.min(maxValue, currentValue));
    }

    public void setIdealValue(float idealValue) {
        if (idealValue < minValue || idealValue > maxValue) throw new IllegalArgumentException();
        this.idealValue = idealValue;
    }
}
