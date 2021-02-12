package ch.cqa.uselesstools.usefultools;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.vector.Vector3d;

public class UsefulFunctions
{

  public static double interpolate_with_clipping(double x, double x1, double x2, double y1, double y2)
  {
    if (x1 > x2) {
      double temp = x1; x1 = x2; x2 = temp;
      temp = y1; y1 = y2; y2 = temp;
    }

    if (x <= x1) return y1;
    if (x >= x2) return y2;
    double xFraction = (x - x1) / (x2 - x1);
    return y1 + xFraction * (y2 - y1);
  }

  public static Vector3d scalarMultiply(Vector3d source, double multiplier)
  {
    return new Vector3d(source.x * multiplier, source.y * multiplier, source.z * multiplier);
  }

  public static ListNBT serializeVec3d(Vector3d vec3d) {
    ListNBT listnbt = new ListNBT();
    listnbt.add(DoubleNBT.valueOf(vec3d.x));
    listnbt.add(DoubleNBT.valueOf(vec3d.y));
    listnbt.add(DoubleNBT.valueOf(vec3d.z));
    return listnbt;
  }

  public static Vector3d deserializeVec3d(CompoundNBT nbt, String tagname) {
    ListNBT listnbt = nbt.getList(tagname, NBTtypesMBE.DOUBLE_NBT_ID);
    Vector3d retval = new Vector3d(listnbt.getDouble(0), listnbt.getDouble(1), listnbt.getDouble(2));
    return retval;
  }
}
