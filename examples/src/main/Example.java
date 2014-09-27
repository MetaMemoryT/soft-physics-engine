package main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import softsys.DebugDraw;
import softsys.Particle;
import softsys.World;
import softsys.joints.DistanceJoint;

import java.util.Collection;

public class Example implements ApplicationListener {

  private OrthographicCamera camera;
  private ShapeRenderer shapeRenderer;
  private final Vector3 mousePosition = new Vector3();
  private World world;
  private DebugDraw worldDebugDraw;
  private Particle selected = null;
  private boolean clicked = false;

  @Override
  public void create() {
    Vector2 size = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera = new OrthographicCamera(size.x, size.y);
    shapeRenderer  = new ShapeRenderer();
    world = new World(new Vector2(0f, -0.025f), new Vector2(size.x / 2f - 32f, size.y / 2f - 32f));
    worldDebugDraw = new DebugDraw(world);
    createCloth(new Vector2(0f, 0f), 512, 512, 32, .975f);
    /*for (int i = 0; i < 128; i++) {
      Particle particle = new Particle(new Vector2(i * 1f, 0));
      world.particles.add(particle);
      if (i != 0) {
        world.joints.add(new DistanceJoint(world.particles.get(i - 1), world.particles.get(i), .75f));
      }
    }*/
  }

  public void render() {
    mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
    camera.update();
    camera.unproject(mousePosition);

    world.simulate(16);

    boolean prevClicked = clicked;
    clicked = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    if (!prevClicked && clicked) {
      Particle nearest = findNearestTo(world.particles, new Vector2(mousePosition.x, mousePosition.y));
      if (nearest != null)
        selected = nearest;
    }
    if (prevClicked && !clicked)
      selected = null;
    if (selected != null) {
      selected.position.x = mousePosition.x;
      selected.position.y = mousePosition.y;
    }

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.circle(mousePosition.x, mousePosition.y, 32f);
    shapeRenderer.end();
    worldDebugDraw.drawAll(shapeRenderer);
  }

  private Particle findNearestTo(Collection<Particle> particles, Vector2 position) {
    float length = 32f;
    Particle nearest = null;
    for (Particle particle : particles) {
      float checkLength = Vector2.dst(particle.position.x, particle.position.y, position.x, position.y);
      if (checkLength < length) {
        nearest = particle;
        length = checkLength;
      }
    }
    return nearest;
  }

  public void createCloth(Vector2 origin, int width, int height, int segments, float stiffness) {
    float xStride = width / segments;
    float yStride = height / segments;
    for (int y = 0; y < segments; ++y) {
      for (int x = 0; x < segments; ++x) {
        Vector2 p = new Vector2(
          origin.x + x * xStride - width / 2f + xStride / 2f,
          origin.y + y * yStride - height / 2f + yStride / 2f
        );
        world.particles.add(new Particle(p));
        if (x > 0)
          world.joints.add(new DistanceJoint(world.particles.get(y * segments + x), world.particles.get(y * segments + x - 1), stiffness));
        if (y > 0)
          world.joints.add(new DistanceJoint(world.particles.get(y * segments + x), world.particles.get((y - 1) * segments + x), stiffness));
      }
    }
  }

  @Override
  public void resize(int i, int i2) {
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
