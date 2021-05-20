/**
 * 
 * Result Class used to store joined result
 *
 */
public class Result {
	
	int w, x, y, m, z, q, t;

	
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Result(R r, S s) {
		w = r.w;
		x = r.x;
		y = r.y;
		m = r.m;
		
		z = s.z;
		q = s.q;
		t = s.t;
		
	}
	
	@Override
	public String toString() {
		return "Result [w=" + w + ", x=" + x + ", y=" + y + ", m=" + m + ", z=" + z + ", q=" + q + ", t=" + t + "]";
	}

}
