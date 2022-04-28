package com.ldgmms.game;


import com.badlogic.gdx.Screen;

import java.util.ArrayList;

public class StrategyScreen implements Screen {
    final TBDGame game;
    UnitParser.UnitObjectList buildableUnits = UnitParser.loadFile();   //loads all the generic units we can build as specified in the JSON file, note that it is still missing key information
                                                                        //that must be passed to the constructor such as which team the unit is being built for, as well was what skills the unit has

    public StrategyScreen(final TBDGame game){
        this.game = game;

        //constructor for strategy layer should be here
    }


    @Override
    public void show() {

    }

    public void render(float delta){
        // clear screen, update camera, do batch drawing, process user input
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void dispose(){

    }
}
