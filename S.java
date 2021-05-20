public class S {
	int y, z, q, t;

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getQ() {
		return q;
	}

	public void setQ(int q) {
		this.q = q;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public S(int y, int z, int q, int t) {
		super();
		this.y = y;
		this.z = z;
		this.q = q;
		this.t = t;
	}

	@Override
	public String toString() {
		return "S [y=" + y + ", z=" + z + ", q=" + q + ", t=" + t + "]";
	}
	
	

}
