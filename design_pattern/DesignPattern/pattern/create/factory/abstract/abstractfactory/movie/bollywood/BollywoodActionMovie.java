package abstractfactory.movie.bollywood;

// Bollywood Movie collections
public class BollywoodActionMovie implements IBollywoodMovie {
	
    /**
     * 工厂方法
     */
	@Override
	public String MovieName() {
		return "Bang Bang is a Bollywood Action Movie";
	}
}