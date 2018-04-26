package abstractfactory.movie.tollywood;

//Tollywood Movie collections
public class TollywoodActionMovie implements ITollywoodMovie {
	
    /**
     * 工厂方法
     */
	@Override
	public String MovieName() {
		return "Kranti is a Tollywood Action Movie";
	}
}