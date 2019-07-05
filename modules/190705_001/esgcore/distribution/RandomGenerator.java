package esgcore.distribution;


public class RandomGenerator {
	
	private static final long a = 0x43FD43FD;
	private static final long c = 0xC39EC3;
	private static final long m = 16777216;
	
	private static long seed = System.currentTimeMillis()/1000;
	
	public RandomGenerator() {}
	
	public static double random() {
		
		seed = (a*seed+c)%m;
		return (double)seed/m;
	}

	public static long getSeed() {
		return seed;
	}

	public static void setSeed(long seed) {
		RandomGenerator.seed = seed;
	}

}
