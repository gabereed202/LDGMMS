package com.ldgmms.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class TBDGame extends Game {
	protected SpriteBatch batch;
	protected BitmapFont font;
	protected Player player;
	//<<< Added by Daniel Fuchs
	public ArrayList<Hero> liveHeroes;
	public ArrayList<Hero> deadHeroes;
	//>>>

	@Override
	public void create () {
		UnitParser.loadFile(); //added for testing -Dan
		batch = new SpriteBatch();
		font = new BitmapFont();
		player = new Player(new Sprite(new Texture("rogueSprite-simple.png")));
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		super.dispose();
	}
}