import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> actors;
    private ArrayList<String> genres;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        actors = new ArrayList<String>();
        genres = new ArrayList<String>();
        for (Movie m : movies) {
            String cast = m.getCast();
            String[] movieActors = cast.split("\\|");
            for (String actor : movieActors) {
                if (!actors.contains(actor)) {
                    actors.add(actor);
                }
            }

            String genre = m.getGenres();
            String[] genreList = genre.split("\\|");
            for (String g : genreList) {
                if (!genres.contains(g)) {
                    genres.add(g);
                }
            }
        }
        Collections.sort(genres);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast member: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<String> actorResults = new ArrayList<String>();

        for (String actor : actors) {
            if (actor.toLowerCase().contains(searchTerm)) {
                actorResults.add(actor);
            }
        }

        Collections.sort(actorResults);

        for (int i = 1; i < actorResults.size()+1; i++) {
            System.out.println(i + ". " + actorResults.get(i-1));
        }
        System.out.print("Which cast member would you like to learn more about?\nEnter number: ");
        int index = Integer.parseInt(scanner.nextLine());
        ArrayList<Movie> movieResults = new ArrayList<Movie>();
        for (Movie m : movies) {
            if (m.getCast().contains(actorResults.get(index-1))) {
                movieResults.add(m);
            }
        }
        for (int i = 1; i < movieResults.size()+1; i++) {
            System.out.println(i + ". " + movieResults.get(i-1));
        }
        System.out.print("Which movie do you want to know more about?\nEnter number: ");
        int selectedMovie = Integer.parseInt(scanner.nextLine());

        displayMovieInfo(movieResults.get(selectedMovie));

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String keyword = scanner.nextLine();

        keyword = keyword.toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++) {
            String movieKeywords = movies.get(i).getKeywords();
            movieKeywords = movieKeywords.toLowerCase();

            if (movieKeywords.contains(keyword)) {
                results.add(movies.get(i));
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = Integer.parseInt(scanner.nextLine());

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        Collections.sort(genres);
        for (int i = 1; i < genres.size()+1; i++) {
            System.out.println(i + ". " + genres.get(i-1));
        }

        System.out.print("Choose a genre: ");
        int choiceNum = Integer.parseInt(scanner.nextLine());

        ArrayList<Movie> moviesInGenre = new ArrayList<Movie>();

        for (Movie m : movies) {
            if (m.getGenres().contains(genres.get(choiceNum-1))) {
                moviesInGenre.add(m);
            }
        }
        for (int i = 1; i < moviesInGenre.size()+1; i++) {
            System.out.println(i + ". " + moviesInGenre.get(i-1));
        }
        System.out.print("Choose a title: ");
        choiceNum = Integer.parseInt(scanner.nextLine());

        displayMovieInfo(moviesInGenre.get(choiceNum-1));
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        sortResults(movies);
        ArrayList<Movie> topFiftyRated = new ArrayList<Movie>();
        for (int i = 0; i < 50; i++) {
            topFiftyRated.add(movies.get(i));
        }

        for (int m = 0; m < movies.size(); m++) {
            for (int i = 0; i < 50; i++) {
                if (movies.get(m).getUserRating() >= topFiftyRated.get(i).getUserRating()) {
                    topFiftyRated.add(i, movies.get(m));
                    i = 50;
                }
            }
        }
        for (int i = 0; i < 50; i++) {
            System.out.println(i+1 + ". " + topFiftyRated.get(i).getTitle() + ": " + topFiftyRated.get(i).getUserRating());
        }
        System.out.print("Choose a title: ");
        int choiceNum = Integer.parseInt(scanner.nextLine());

        displayMovieInfo(topFiftyRated.get(choiceNum-1));
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> topFiftyRevenue = new ArrayList<Movie>();
        for (int i = 0; i < 50; i++) {
            topFiftyRevenue.add(movies.get(i));
        }

        for (int m = 0; m < movies.size(); m++) {
            for (int i = 0; i < 50; i++) {
                if (movies.get(m).getRevenue() >= topFiftyRevenue.get(i).getRevenue()) {
                    topFiftyRevenue.add(i, movies.get(m));
                    i = 50;
                }
            }
        }
        for (int i = 0; i < 50; i++) {
            System.out.println(i+1 + ". " + topFiftyRevenue.get(i).getTitle() + ": " + topFiftyRevenue.get(i).getRevenue());
        }
        System.out.print("Choose a title: ");
        int choiceNum = Integer.parseInt(scanner.nextLine());

        displayMovieInfo(topFiftyRevenue.get(choiceNum-1));
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}