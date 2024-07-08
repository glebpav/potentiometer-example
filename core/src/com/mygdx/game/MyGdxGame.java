package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.components.Potentiometer;
import com.mygdx.game.components.TripleSwitch;
import com.sun.org.apache.xpath.internal.operations.Or;

public class MyGdxGame extends Game {

	public final static int SCREEN_WIDTH = 800;
	public final static int SCREEN_HEIGHT = 600;

	Stage stage;
	Skin skin;
	OrthographicCamera camera;
	FillViewport viewport;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		viewport = new FillViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
		skin = new Skin(Gdx.files.internal("style/style.json"));
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);

		Potentiometer potentiometer = new Potentiometer(skin);
		potentiometer.setPosition(100, 100);
		potentiometer.setSize(300, 120);
		stage.addActor(potentiometer);

		TripleSwitch tripleSwitch = new TripleSwitch(skin);
		tripleSwitch.setSize(200, 200);
		tripleSwitch.setPosition(500, 100);
		tripleSwitch.setState((byte) 1);
		stage.addActor(tripleSwitch);


	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.GRAY);
		stage.act();
		stage.draw();
	}
	
	@Override
	public void dispose () {

	}
}
