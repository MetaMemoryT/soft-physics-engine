package demos.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import engine.Particle;
import engine.Vector;
import engine.World;
import engine.joints.Joint;
import org.lwjgl.opengl.GL11;

import java.util.Collection;

public class WorldDebugRenderer {

  private final static Color OUTLINE_COLOR = new Color(.223f, .258f, .278f, 1f);
  private final static Color JOINT_COLOR = new Color(.643f, .807f, .227f, 1f);
  private final static Color SHAPE_COLOR = new Color(.785f, .854f, .160f, 1);
  private final static Color BACKGROUND_COLOR = new Color(.454f, .541f, .592f, 1f);
  private final static ShapeRenderer shape = new ShapeRenderer();

  public static void render(World world, Matrix4 matrix) {
    clearBackground();
    shape.setProjectionMatrix(matrix);
    renderJoints(world.joints);
    renderParticles(world.particles);
  }

  private static void clearBackground() {
    Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, BACKGROUND_COLOR.a);
    Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
  }

  private static void renderParticles(Collection<Particle> particles) {
    shape.begin(ShapeRenderer.ShapeType.Filled);
    for (Particle particle : particles) {
      renderDot(particle, 7, OUTLINE_COLOR);
      renderDot(particle, 5, SHAPE_COLOR);
    }
    shape.end();
  }

  private static final Color color = new Color();
  private static void renderJoints(Collection<Joint> joints) {
    shape.begin(ShapeRenderer.ShapeType.Filled);
    for (Joint joint : joints) {
      float tension = Math.min(joint.getTension(), 1);
      color.set(JOINT_COLOR.r + tension, JOINT_COLOR.g - tension * .5f, JOINT_COLOR.b - tension, JOINT_COLOR.a);
      renderLine(joint.red, joint.blue, 4, OUTLINE_COLOR);
      renderLine(joint.red, joint.blue, 2, color);
    }
    shape.end();
  }

  private static void renderDot(Vector point, float size, Color color) {
    shape.setColor(color);
    shape.circle(point.x, point.y, size / 2f);
  }

  private static void renderLine(Vector a, Vector b, float width, Color color) {
    shape.setColor(color);
    shape.rectLine(a.x, a.y, b.x, b.y, width);
  }

}
