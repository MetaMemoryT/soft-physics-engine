package demo.ropenew;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import demos.Demo.GdxInitializer;
import demos.rope.RopeDemo;

public class Input implements InputProcessor {

	boolean dragging;
	@Override public boolean mouseMoved (int screenX, int screenY) {
		// we can also handle mouse movement without anything pressed
//		camera.unproject(tp.set(screenX, screenY, 0));
		return false;
	}
	
	@Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		return true;
	}
	
	@Override public boolean touchDragged (int screenX, int screenY, int pointer) {
		return true;
	}
	
	@Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		return true;
	}

	
	@Override public boolean keyDown (int keycode) {	
		return false;
	}
	
	@Override public boolean keyUp (int keycode) {
		return false;
	}
	
	@Override public boolean keyTyped (char character) {
		System.out.println(character);
		return false;
	}
	
	@Override public boolean scrolled (int amount) {
		return false;
	}
	
	public static void main (String[] arg) {
		
	}
}
