package demos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import demos.utils.FingerProcessor;
import demos.utils.rendering.DebugRenderer;
import engine.Particle;
import engine.Simulator;

import java.util.ArrayList;
import java.util.function.Supplier;

public abstract class Demo extends ApplicationAdapter {

  protected final Simulator simulator = new Simulator();
  protected final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  private final FingerProcessor processor = FingerProcessor.withFingerCount(4);

  public final void onRender() {
    processor.update(camera, simulator);
    camera.update();
    applyGravityForEachParticle();
    simulator.simulate(8);
    DebugRenderer.render(simulator, camera.combined);
  }

  private void applyGravityForEachParticle() {
    float delta = Gdx.graphics.getDeltaTime();
    for (Particle particle : simulator.particles)
      particle.pos.y -= 7f * delta;
  }

  public void onUpdate() {
	  if (Gdx.input.isKeyPressed(0))
		  System.out.println("abc");
  }

  protected static class GdxInitializer implements ApplicationListener, InputProcessor {

    public final Supplier<Demo> supplier;
    private Demo demo;

    private GdxInitializer(Supplier<Demo> supplier) {
      this.supplier = supplier;
    }
    
	@Override public boolean mouseMoved (int screenX, int screenY) {
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
		// System.out.println(keycode);
		return false;
	}
	
	@Override public boolean keyUp (int keycode) {
		return false;
	}
	
	@Override public boolean keyTyped (char character) {
		System.out.println(character);
		System.out.println();
		ArrayList<Particle> old = (ArrayList<Particle>) this.supplier.get().simulator.particles; 
		for (Particle e : old) {
			System.out.println(e.pos.toString() + e.prev.toString());
		}
		old.add(Particle.on(0, 256));
		
		return false;
	}
	
	@Override public boolean scrolled (int amount) {
		return false;
	}
	
    public static void initializeLazy(Supplier<Demo> supplier) {
      new LwjglApplication(new GdxInitializer(supplier), createDefaultConfiguration());
    }

    @Override
    public void create() {
      demo = supplier.get();
      Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
      demo.onRender();
      demo.onUpdate();
    }

    private static LwjglApplicationConfiguration createDefaultConfiguration() {
      return new LwjglApplicationConfiguration() {{
        title = "demo";
        width = 1280;
        height = 768;
        resizable = false;
        samples = 8;
      }};
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

  }

}
