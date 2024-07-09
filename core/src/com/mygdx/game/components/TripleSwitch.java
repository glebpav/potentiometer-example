package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class TripleSwitch extends Actor {

    Image baseImage;
    Image buttonImage;
    Label[] labels;

    byte currentState = 0;

    public TripleSwitch (Skin skin) {
        baseImage = new Image(skin, "triple-switch-base");
        buttonImage = new Image(skin, "triple-switch-button");
        labels = new Label[] {
                new Label("1", skin, "molot-font", Color.BLACK),
                new Label("2", skin, "molot-font", Color.BLACK),
                new Label("3", skin, "molot-font", Color.BLACK)
        };

        setSize(100, 100);
        setPosition(0, 0);
        setState((byte) 1);

        addListener(new SwitcherTripleInputListener());
    }

    public void setState(byte state) {
        if (state < 0 || state >= 3) throw new IllegalArgumentException();

        setPosition(getX(), getY());
        currentState = state;
        for (int i = 0; i < labels.length; i++) {
            labels[i].getColor().a = (i == currentState) ? 1f : 0f;
        }
        System.out.println("pos y: " + (buttonImage.getY() + ((2 * currentState + 1) / 2f) * buttonImage.getHeight()));
        buttonImage.setPosition(
                buttonImage.getX(),
                (baseImage.getY() + 1f/10 * baseImage.getWidth()) + (currentState) * buttonImage.getHeight()
        );
    }

    @Override
    public void setSize(float width, float height) {
        // width is ignored
        width = (200 / 520f) * height;
        baseImage.setSize(width, height);
        buttonImage.setSize((4f/5) * width, (4f/5) * width);
        super.setSize(width, height);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        baseImage.setPosition(x, y);
        buttonImage.setPosition(
                x + 1f/10 * baseImage.getWidth(),
                y + 1f/10 * baseImage.getWidth() + ((currentState) * buttonImage.getHeight())
        );
        for (int i = 0; i < labels.length; i++) {
            labels[i].setPosition(
                    buttonImage.getX() + buttonImage.getWidth() / 2f - labels[i].getWidth() / 2,
                    (y + 1f/10 * baseImage.getWidth()) + buttonImage.getHeight() * ((2 * i + 1) / 2f) - labels[i].getHeight() / 2
            );
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        baseImage.draw(batch, parentAlpha);
        buttonImage.draw(batch, parentAlpha);
        for (Label label : labels)
            label.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        baseImage.act(delta);
        buttonImage.act(delta);
        for(Label label : labels) label.act(delta);
    }

    public void switchLower() {
        if (currentState <= 0) return;
        currentState -= 1;

        /*MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition(buttonImage.getX(), buttonImage.getY() - buttonImage.getHeight());
        moveAction.setDuration(0.2f);
        buttonImage.addAction(moveAction);*/
        buttonImage.addAction(Actions.moveTo(
                buttonImage.getX(),
                buttonImage.getY() - buttonImage.getHeight(),
                0.2f
        ));


        labels[currentState].addAction(sequence(Actions.delay(0.2f), Actions.fadeIn(0.2f)));
        labels[currentState + 1].addAction(sequence(Actions.fadeOut(0.1f)));
    }

    public void switchUpper() {
        if (currentState >= 2) return;
        currentState += 1;
        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition(buttonImage.getX(), buttonImage.getY() + buttonImage.getHeight());
        moveAction.setDuration(0.2f);
        buttonImage.addAction(moveAction);
        labels[currentState].addAction(sequence(Actions.delay(0.2f), Actions.fadeIn(0.2f)));
        labels[currentState - 1].addAction(sequence(Actions.fadeOut(0.1f)));

    }

    private class SwitcherTripleInputListener extends InputListener {

        Vector2 initialPosition;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            initialPosition = new Vector2(x, y);
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (y < initialPosition.y) switchLower();
            else if (y > initialPosition.y) switchUpper();
        }
    }



}
