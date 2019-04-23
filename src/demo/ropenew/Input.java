package demo.ropenew;

import com.badlogic.gdx.InputProcessor;

public class Input implements InputProcessor {

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
	
}
