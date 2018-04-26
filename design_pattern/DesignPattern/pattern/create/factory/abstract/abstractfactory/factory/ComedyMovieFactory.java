package abstractfactory.factory;

import abstractfactory.movie.bollywood.BollywoodComedyMovie;
import abstractfactory.movie.bollywood.IBollywoodMovie;
import abstractfactory.movie.tollywood.ITollywoodMovie;
import abstractfactory.movie.tollywood.TollywoodComedyMovie;

/**
 * 抽象工厂-对不同工厂对象封装对应的API接口
 */
//Comedy Movie Factory
public class ComedyMovieFactory implements IMovieFactory {
	
	public ITollywoodMovie GetTollywoodMovie() {
		return new TollywoodComedyMovie();
	}

	public IBollywoodMovie GetBollywoodMovie() {
		return new BollywoodComedyMovie();
	}
}