package abstractfactory.factory;

import abstractfactory.movie.bollywood.BollywoodActionMovie;
import abstractfactory.movie.bollywood.IBollywoodMovie;
import abstractfactory.movie.tollywood.ITollywoodMovie;
import abstractfactory.movie.tollywood.TollywoodActionMovie;

/**
 * 抽象工厂-对不同工厂对象封装对应的API接口
 */
//Action Movie Factory
public class ActionMovieFactory implements IMovieFactory {
    /**
     * 返回抽象工厂对象
     */
	public ITollywoodMovie GetTollywoodMovie() {
		return new TollywoodActionMovie();
	}

    /**
     * 返回抽象工厂对象
     */
	public IBollywoodMovie GetBollywoodMovie() {
		return new BollywoodActionMovie();
	}
}