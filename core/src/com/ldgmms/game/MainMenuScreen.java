package com.ldgmms.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen{
    final TBDGame game;

    OrthographicCamera camera;

    public MainMenuScreen(final TBDGame game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Placeholder Main Menu, taken from libgdx page", 100, 150); //will need to replace with actual menu settings
        game.font.draw(game.batch, "Push button to start game", 100, 100);
        game.batch.end();

        if (Gdx.input.isTouched()){
            game.setScreen(new StrategyScreen(game));
            dispose();
        }
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

    @Override
    public void dispose() {

    }
    //will still need to implement update and dispose, add buttons to start new game, load game, exit
}
