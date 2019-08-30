package org.inventivetalent.circlehelper;

import java.util.ArrayList;
import java.util.List;

public class ShapeManager {

	public static boolean    visible = false;
	public static Pos        center  = new Pos(0, 0, 0);
	public static CircleType type    = CircleType.SPHERE;
	public static int        radiusX = 0;
	public static int        radiusY = 0;
	public static int        radiusZ = 0;

	public static List<Pos> currentPositions = new ArrayList<>();

	public static void resetAndGenerate() {
		currentPositions.clear();

		if (type == CircleType.CIRCLE) {
			//TODO
		}
		if (type == CircleType.SPHERE) {
			generateSphere();
		}

		System.out.println("RX: " + radiusX);
		System.out.println("RY: " + radiusY);
		System.out.println("RZ: "+radiusZ);
		System.out.println(currentPositions);
	}

	public static void generateCircle(CirclePlane plane, int radiusA, int radiusB) {
//TODO
	}

	public static void generateSphere() {

		double radiusX = ShapeManager.radiusX+.5;
		double radiusY = ShapeManager.radiusY+.5;
		double radiusZ = ShapeManager.radiusZ+.5;

		/// https://github.com/EngineHub/WorldEdit/blob/487da77a72e38cca7a120dc2fd37d23c22786f77/worldedit-core/src/main/java/com/sk89q/worldedit/EditSession.java#L1559
		final double invRadiusX = 1 / radiusX;
		final double invRadiusY = 1 / radiusY;
		final double invRadiusZ = 1 / radiusZ;

		final int ceilRadiusX = (int) Math.ceil(radiusX);
		final int ceilRadiusY = (int) Math.ceil(radiusY);
		final int ceilRadiusZ = (int) Math.ceil(radiusZ);

		double nextXn = 0;
		forX: for (int x = 0; x <= ceilRadiusX; ++x) {
			final double xn = nextXn;
			nextXn = (x + 1) * invRadiusX;
			double nextYn = 0;
			forY: for (int y = 0; y <= ceilRadiusY; ++y) {
				final double yn = nextYn;
				nextYn = (y + 1) * invRadiusY;
				double nextZn = 0;
				forZ: for (int z = 0; z <= ceilRadiusZ; ++z) {
					final double zn = nextZn;
					nextZn = (z + 1) * invRadiusZ;

					double distanceSq = lengthSq(xn, yn, zn);
					if (distanceSq > 1) {
						if (z == 0) {
							if (y == 0) {
								break forX;
							}
							break forY;
						}
						break forZ;
					}

//					if (!filled) {
						if (lengthSq(nextXn, yn, zn) <= 1 && lengthSq(xn, nextYn, zn) <= 1 && lengthSq(xn, yn, nextZn) <= 1) {
							continue;
						}
//					}

					addPos(x, y, z);
					addPos(-x, y, z);
					addPos(x, -y, z);
					addPos(x, y, -z);
					addPos(-x, -y, z);
					addPos(x, -y, -z);
					addPos(-x, y, -z);
					addPos(-x, -y, -z);
				}
			}
		}
	}

	static void addPos(int x, int y, int z) {
		currentPositions.add(new Pos(center.x+x, center.y+y, center.z+z));
	}

	private static double lengthSq(double x, double y, double z) {
		return (x * x) + (y * y) + (z * z);
	}

	private static double lengthSq(double x, double z) {
		return (x * x) + (z * z);
	}

}
