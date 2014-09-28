package softsys.joints;

import softsys.AngleParticle;
import softsys.Particle;
import softsys.Vector;

public class AngleJoint extends Joint {

  public AngleJoint(AngleParticle red, Particle blue) {
    super(red, blue, 0f);
  }

  @Override
  public void relax(float delta) {
    float len = Vector.distanceBetween(red, blue);
    float dir = red.angleTo(blue);

    //dir += (0f - dir) * delta;
    //dir +=

    float diff = Vector.angdiff(0f, dir);
    dir += diff * delta * .0125f;

    Vector pos = new Vector().set(red).add(ld(len, dir));

    blue.set(pos);
  }

  private Vector _ld = new Vector();
  private Vector ld(float length, float direction) {
    return _ld.set(
      (float)Math.cos(direction) * length,
      (float)Math.sin(direction) * length
    );
  }

}
