package com.ldgmms.game;
import com.badlogic.gdx.Game;
//import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class TBDGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	Texture img;


	
	//@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		img = new Texture("badlogic.jpg");
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		//ScreenUtils.clear(1, 0, 0, 1); //not sure if necessary
		super.render(); //allegedly important
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		font.dispose();
	}
}
