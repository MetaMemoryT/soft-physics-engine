package engine.joints;

import engine.Particle;
import org.joml.Vector2f;

public class AngleJoint implements Joint{

  public final Particle a;
  public final Particle b;
  public final Particle c;
  private final float angle;
  private final float stiffness;

  public AngleJoint(Particle a, Particle b, Particle c, float angle, float stiffness) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.angle = angle;
    this.stiffness = stiffness;
  }

  @Override
  public void relax(float delta) {
    float angle = angle2(b.pos, a.pos, c.pos);
    float diff = angle - this.angle;
    if (diff <= -Math.PI)
      diff += 2*Math.PI;
    else if (diff >= Math.PI)
      diff -= 2*Math.PI;
    diff *= .00125f;
    a.pos.set(rotate(a.pos, b.pos, diff));
    c.pos.set(rotate(c.pos, b.pos, -diff));
    b.pos.set(rotate(b.pos, a.pos, diff));
    b.pos.set(rotate(b.pos, c.pos, -diff));
  }

  public float angle2(Vector2f middle, Vector2f vLeft, Vector2f vRight) {
    Vector2f copyLeft = new Vector2f(vLeft);
    Vector2f copyRight = new Vector2f(vRight);
    return copyLeft.sub(middle).angle(copyRight.sub(middle));
  }


  public Vector2f rotate(Vector2f from, Vector2f origin, float theta) {
    float x = from.x - origin.x;
    float y = from.y - origin.y;
    return new Vector2f((float) (x*Math.cos(theta) - y*Math.sin(theta) + origin.x), (float) (x*Math.sin(theta) + y*Math.cos(theta) + origin.y));
  }

}
