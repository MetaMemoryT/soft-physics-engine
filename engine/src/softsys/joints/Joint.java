package softsys.joints;

import softsys.Particle;
import softsys.Vec2;

public abstract class Joint {

  public final Particle red, blue;
  private final Vec2 normal = new Vec2();

  public Joint(Particle red, Particle blue) {
    this.red = red;
    this.blue = blue;
  }

  public abstract void relax(float delta);

  protected Vec2 normal() {
    normal.x = -blue.position.x + red.position.x;
    normal.y = -blue.position.y + red.position.y;
    return normal;
  }

}
