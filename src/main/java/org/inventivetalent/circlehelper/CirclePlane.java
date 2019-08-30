package org.inventivetalent.circlehelper;

public enum CirclePlane {

	XZ {
		@Override
		public int getX(int a, int b) {
			return a;
		}

		@Override
		public int getZ(int a, int b) {
			return b;
		}
	},
	XY {
		@Override
		public int getX(int a, int b) {
			return a;
		}

		@Override
		public int getY(int a, int b) {
			return b;
		}
	},
	ZY {
		@Override
		public int getZ(int a, int b) {
			return a;
		}

		@Override
		public int getY(int a, int b) {
			return b;
		}
	};

	CirclePlane() {
	}

	public int getX(int a, int b) {
		return 0;
	}

	public int getZ(int a, int b) {
		return 0;
	}

	public int getY(int a, int b) {
		return 0;
	}

}
