package demos.rope;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.joml.Vector2f;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import demos.utils.DemoTextureLoader;
import demos.utils.FingerProcessor;
import demos.utils.rendering.DebugRenderer;
import engine.Particle;
import engine.Simulator;
import engine.joints.Joint;
import engine.joints.PinJoint;
import engine.joints.SpringJoint;

public class RopeDemoFlat implements InputProcessor {

	 protected final Simulator simulator = new Simulator();
	 protected final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	 private final FingerProcessor processor = FingerProcessor.withFingerCount(4);
	 private final List<Particle> ropeParticles = createParticles();
	 private final Collection<Joint> ropeJoints = createJointsBetween(ropeParticles);
	 private final Texture texture = DemoTextureLoader.loadTroll();
	 private final RopeRenderer ropeRenderer = new RopeRenderer();

	  {
	    simulator.particles.addAll(ropeParticles);
	    simulator.joints.addAll(ropeJoints);
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
		
		@Override public boolean keyTyped1 (char character) {
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
		
		 @Override
		 public void create() {
		   Gdx.input.setInputProcessor(this);
		 }

		 @Override
		 public void render() {
		      this.onRender();
		      this.onUpdate();
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

	  private static List<Particle> createParticles() {
	    return IntStream.rangeClosed(-4, 4)
	      .mapToObj(i -> Particle.on(0, -i * 64))
	      .collect(Collectors.toList());
	  }
	  
	  public boolean keyTyped (char character) {
		System.out.println(character);
		return false;
	  }

	  private static Collection<Joint> createJointsBetween(Collection<Particle> particles) {
	    Iterator<Particle> iterator = particles.iterator();
	    Particle previous = iterator.next();
	    Collection<Joint> joints = new ArrayList<>();
	    joints.add(PinJoint.pinToActualPlace(previous));
	    while (iterator.hasNext()) {
	      Particle next = iterator.next();
	      joints.add(SpringJoint.connect(previous, next, .125f));
	      previous = next;
	    }
	    return joints;
	  }

	  public void onUpdate() {
	    List<Vector2f> points = ropeParticles.stream().map(p -> p.pos).collect(Collectors.toList());
	    List<Vector2f> concentrated = BezierConcentrator.concentrate(points, 2);
	    ropeRenderer.renderRope(camera.combined, concentrated, texture);
	  }

	  public static void main(String[] args) {
	    GdxInitializer.initializeLazy(RopeDemo::new);
	  
	  }
}
