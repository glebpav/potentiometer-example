package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.components.EnergyChart;
import com.mygdx.game.components.Potentiometer;
import com.mygdx.game.components.PowerDisplay;
import com.mygdx.game.components.TripleSwitch;
import com.mygdx.game.utils.ChartValues;

import java.util.ArrayList;
import java.util.Arrays;

public class MyGdxGame extends Game {

    public final static int SCREEN_WIDTH = 800;
    public final static int SCREEN_HEIGHT = 800;

    Stage stage;
    Skin skin;
    OrthographicCamera camera;
    FillViewport viewport;

    long initialTime;

    EnergyChart energyChart;
    PowerDisplay powerDisplay;
    Potentiometer potentiometer;

    ArrayList<ChartValues> energyList;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        viewport = new FillViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        skin = new Skin(Gdx.files.internal("style2/style.json"));
        // skin = new Skin(Gdx.files.internal("style/nuclear.json"));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        skin.add("molot-font", FontBuilder.generate(20, Color.BLACK, "fonts/Molot.otf"));

        potentiometer = new Potentiometer(skin);
        potentiometer.setPosition(50, 100);
        potentiometer.setSize(300, 120);
        stage.addActor(potentiometer);

        TripleSwitch tripleSwitch = new TripleSwitch(skin);
        tripleSwitch.setSize(200, 200);
        tripleSwitch.setPosition(400, 100);
        tripleSwitch.setState((byte) 1);
        stage.addActor(tripleSwitch);

        powerDisplay = new PowerDisplay(0, 1, 0.5f, 0.1f);
        powerDisplay.setPosition(50, 300);
        powerDisplay.setSize(300, 90);
        stage.addActor(powerDisplay);

        energyChart = new EnergyChart(skin);
        energyChart.setSize(200, 400);
        energyChart.setPosition(500, 100);
        stage.addActor(energyChart);

        energyList = new ArrayList<>(Arrays.asList(
                new ChartValues(3, 10),
                new ChartValues(5, 12),
                new ChartValues(2, 13),
                new ChartValues(5, 9),
                new ChartValues(1, 10),
                new ChartValues(10, 12),
                new ChartValues(5, 12),
                new ChartValues(5, 5))
        );

        energyChart.setValuesList(energyList, true);

		/*Label label = new Label("Potentiometer", skin, "molot-font", Color.BLACK);
		label.setPosition(100, 100);
		stage.addActor(label);*/

    }

    @Override
    public void render() {

        long currentTime = TimeUtils.millis() - initialTime;

        if (currentTime >= ChartValues.getSumTime(energyList) * 1_000L) initialTime = TimeUtils.millis();
        else energyChart.currentPosition = (int) (currentTime / 1000);
        powerDisplay.setCurrentValue((float) potentiometer.getValue());
        powerDisplay.setIdealValue(
                (float) ((ChartValues.getValue(energyList, energyChart.currentPosition) - energyChart.getMinValue())
                                        / (energyChart.getMaxValue() - energyChart.getMinValue()))
        );

        ScreenUtils.clear(Color.GRAY);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
