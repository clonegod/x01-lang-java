package abstractfactory.factory;

import abstractfactory.movie.bollywood.IBollywoodMovie;
import abstractfactory.movie.tollywood.ITollywoodMovie;

public interface IMovieFactory {
	ITollywoodMovie GetTollywoodMovie();

	IBollywoodMovie GetBollywoodMovie();
}