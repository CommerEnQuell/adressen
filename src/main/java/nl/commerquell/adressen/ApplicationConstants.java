package nl.commerquell.adressen;

public class ApplicationConstants {
	
	public static  int DEFAULT_PAGE_SIZE;
	
	private ApplicationConstants() {}
	
	public static void setDefaultPageSize(int pageSize) {
		if (pageSize < 0) {
			throw new IllegalArgumentException("Page size may not be negative. 10 is assumed");
		}
		if (pageSize > 0) {
			DEFAULT_PAGE_SIZE = pageSize;
		} else {
			DEFAULT_PAGE_SIZE = Integer.MAX_VALUE;
		}
	}
	
	public static int getDefaultPageSize() {
		return DEFAULT_PAGE_SIZE;
	}

}
