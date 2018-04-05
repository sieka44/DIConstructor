package uj.jwzp.w4.launcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uj.jwzp.w4.logic.CSVMovieFinder;
import uj.jwzp.w4.logic.include.MovieLister;
import uj.jwzp.w4.model.Movie;


@Slf4j
public class SpringMain {

    private static final String DEFAULT_FILE_NAME = "movies.txt";

    public static void main(String[] args) {
        /**/
        String fileName = "";
        if(args.length > 0){
            fileName = args[0];
        }else {
         fileName=DEFAULT_FILE_NAME;
        }
        /**/
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();
        String finalFileName = fileName;
        configApplicationContext.registerBean(CSVMovieFinder.class, () -> new CSVMovieFinder(finalFileName));
        configApplicationContext.scan("uj.jwzp.w4.logic.include");
        configApplicationContext.refresh();
        ApplicationContext ctx = configApplicationContext;
        MovieLister lister = (MovieLister) ctx.getBean("movieLister");

        lister.moviesDirectedBy("Tarantino").stream()
                .map(Movie::toString)
                .forEach(log::info);
    }
}
