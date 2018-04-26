package abstractfactory.movie.tollywood;

public class TollywoodComedyMovie implements ITollywoodMovie {
	
    /**
     * 工厂方法
     */
	@Override
	public String MovieName() {
		return "BasantaBilap is a Tollywood Comedy Movie";
	}
}