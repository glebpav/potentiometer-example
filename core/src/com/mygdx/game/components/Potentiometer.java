package com.mygdx.game.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;

public class Potentiometer extends Actor {

    Image baseImage;
    Image spinnerImage;

    public double a = Math.PI / 3;

    private final float SPINNER_SIZE_RATIO = 0.2f;
    private final float HORIZONTAL_MARGIN_OFFSET = -11.3f;
    private final float VERTICAL_MARGIN_OFFSET = -2f;

    public Potentiometer(Skin skin) {
        baseImage = new Image(skin, "pat-base");
        spinnerImage = new Image(skin, "pat-spinner");

        setPosition(0, 0);
        addListener(new PotentiometerInputLister());
        spinnerImage.setRotation(180);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        baseImage.setSize(width, height);
        spinnerImage.setSize(width * SPINNER_SIZE_RATIO, width * SPINNER_SIZE_RATIO);
        spinnerImage.setOrigin(spinnerImage.getWidth() / 2, spinnerImage.getHeight() / 2);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        baseImage.setPosition(x, y);
        spinnerImage.setPosition(
                x + baseImage.getWidth() / 2 - baseImage.getWidth() / 2
                        - HORIZONTAL_MARGIN_OFFSET * baseImage.getWidth() / 100,
                y + baseImage.getHeight() / 2 - baseImage.getHeight() / 2
                        - VERTICAL_MARGIN_OFFSET * baseImage.getHeight() / 30
        );
        spinnerImage.setOrigin(spinnerImage.getWidth() / 2, spinnerImage.getHeight() / 2);
        System.out.println("a: " + a);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        baseImage.draw(batch, parentAlpha);
        spinnerImage.draw(batch, parentAlpha);
    }

    private class PotentiometerInputLister extends InputListener {

        double touchInitialAngle;
        double touchCurrentAngle;
        double spinnerInitialAngle;
        double spinnerCurrentAngle;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            spinnerInitialAngle = Math.toRadians(spinnerImage.getRotation());
            touchInitialAngle = getAngle(x, y);
            System.out.println("spinnerInitialAngle: " + spinnerInitialAngle);
            System.out.println("touchInitialAngle: " + touchInitialAngle);
            return true;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            touchCurrentAngle = getAngle(x, y);
            double dAngle = - touchInitialAngle + touchCurrentAngle;

            spinnerCurrentAngle = spinnerInitialAngle + dAngle;

            if (spinnerCurrentAngle < 0) {
                spinnerCurrentAngle = Math.PI * 2 + spinnerCurrentAngle;
            }

            if (spinnerCurrentAngle > Math.PI * 2) {
                spinnerCurrentAngle = spinnerCurrentAngle - Math.PI * 2;
            }

            if ((spinnerCurrentAngle + dAngle <= a)) {
                touchInitialAngle = getAngle(x, y);
                return;
            }

            if ((spinnerCurrentAngle + dAngle >= Math.PI * 2 - a) ) {
                touchInitialAngle = getAngle(x, y);
                return;
            }

            spinnerImage.setRotation((float) (Math.toDegrees(spinnerCurrentAngle)));
            spinnerInitialAngle = Math.toRadians(spinnerImage.getRotation());
            touchInitialAngle = getAngle(x, y);
        }

        private double getAngle(float x, float y) {
            double dx = (x - spinnerImage.getX() + spinnerImage.getWidth() / 2);
            double dy = (y - spinnerImage.getY() + spinnerImage.getHeight() / 2);
            double angle = Math.atan(dy / dx);

            if (dx < 0) {
                angle += Math.PI;
            }

            angle += Math.PI / 2;

            // angle = Math.max(angle, a);
            // touchCurrentAngle = Math.min(angle, 2 * Math.PI - a);
            return angle;
        }

    }

}
