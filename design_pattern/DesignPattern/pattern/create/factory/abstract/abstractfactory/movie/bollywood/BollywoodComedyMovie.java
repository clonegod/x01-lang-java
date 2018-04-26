package abstractfactory.movie.bollywood;

public class BollywoodComedyMovie implements IBollywoodMovie {
	
    /**
     * 工厂方法
     */
	@Override
	public String MovieName() {
		return "Munna Bhai MBBS is a Bollywood Comedy Movie";
	}
}