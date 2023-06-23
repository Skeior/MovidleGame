package bm.erciyes.edu.tr.project.View;


import bm.erciyes.edu.tr.project.Controller.MovieController;
import bm.erciyes.edu.tr.project.Model.Movie;
import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MovidleApplication extends Application {
     Movie randomMovie;
    private ListView<String> suggestionsListView;
    public int gamemode=0;

    public void gameSection(int gamemode) throws Exception {
        randomMovie = MovieController.randomMovieSelector();
        System.out.println(gamemode);
        Stage stage=new Stage();
        Media game = new Media(new File("src\\main\\resources\\As\\game.mp3").toURI().toString());
        MediaPlayer mediaPlayerg = new MediaPlayer(game);
        mediaPlayerg.setVolume(0.04);
        mediaPlayerg.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayerg.seek(Duration.ZERO);
            }
        });
        mediaPlayerg.play();


        String imageName = "174.jpg";
        Path imagePath = Paths.get("src", "main", "resources", "As", imageName).toAbsolutePath();
        String imageUrl = imagePath.toUri().toString();

        Image img = new Image(imageUrl);
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(1920);
        imgView.setFitHeight(1080);
        imgView.setOpacity(0.75);
        Label imgs = new Label();
        imgs.setGraphic(imgView);


        final VBox[] a = {new VBox()};
        a[0].setSpacing(10);
        HBox bttx=new HBox();
        TextField tx = new TextField();
        tx.setPrefSize(500,25);
        tx.maxHeight(25);
        tx.maxWidth(500);
        tx.setOpacity(0.90);
        final boolean[] first = {true};
        bttx.getChildren().add(tx);
        bttx.setAlignment(Pos.CENTER);



        suggestionsListView = new ListView<>();
        suggestionsListView.setOpacity(0.90);

        tx.setOnKeyTyped(event -> {
            String userInput = tx.getText().toLowerCase();
            updateSuggestions(userInput);
        });

        suggestionsListView.setOnMouseClicked(event -> {
            String selectedItem = suggestionsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                tx.setText(selectedItem);
                tx.requestFocus();

            }
        });
        suggestionsListView.setMaxSize(500,200);
        VBox txs = new VBox(bttx,suggestionsListView);
        txs.setAlignment(Pos.TOP_CENTER);

        final int[] trycounter = {1};


        tx.setOnKeyPressed(actionEvent -> {
            try {
                String enteredText = tx.getText();
                if(actionEvent.getCode()== KeyCode.ENTER){
                    Movie selectedMovie = MovieController.getMovie(enteredText);
                    if (selectedMovie != null&& trycounter[0] !=6 ) {

                        if (gamemode == 2 && selectedMovie.year.equals(randomMovie.year)) {
                            filterMovieListByYear(selectedMovie.year);


                        }

                        if (gamemode == 2 && selectedMovie.genre.equals(randomMovie.genre)) {
                            filterMovieListByGenre(selectedMovie.genre);


                        }

                        if (gamemode == 2 && selectedMovie.origin.equals(randomMovie.origin)) {
                            filterMovieListByOrigin(selectedMovie.origin);

                        }

                        if (gamemode == 2 && selectedMovie.director.equals(randomMovie.director)) {
                            filterMovieListByDirector(selectedMovie.director);

                        }

                        if (gamemode == 2 && selectedMovie.star.equals(randomMovie.star)) {
                            filterMovieListByStar(selectedMovie.star);

                        }

                        ImageView imgView2 = loadMovieImageAsync(selectedMovie.imdb_link);

                        imgView2.setFitWidth(1920); // Set the fitWidth to the image's width
                        imgView2.setFitHeight(1080);
                        imgView2.setOpacity(0.75);
                        Label imgs2 = new Label();
                        imgs2.setGraphic(imgView2);

                        trycounter[0]++;
                        if (first[0]){
                            a[0].getChildren().add(paneDesign(randomMovie,"TITLE","YEAR","GENRE", "ORIGIN","DIRECTOR","STAR","RANK",first[0]));
                            first[0] =false;
                        }
                        a[0].getChildren().add(paneDesign(randomMovie, selectedMovie.title, selectedMovie.year, selectedMovie.genre, selectedMovie.origin, selectedMovie.director, selectedMovie.star,selectedMovie.no,false));
                        VBox root = new VBox();
                        root.setSpacing(20);
                        root.getChildren().addAll(txs, a[0]);
                        StackPane sc=new StackPane();
                        sc.getChildren().addAll(imgs2,root);
                        mediaPlayerg.play();
                        Scene scene = new Scene(sc, 1920, 1080);
                        stage.setScene(scene);
                        stage.show();



                        if(selectedMovie.title.equals(randomMovie.title)){
                            mediaPlayerg.stop();
                            Pane pane = new Pane ();
                            Text text1 = new Text (20 , 20 , "  Winner Winner Chicken Dinner");
                            text1 . setFont ( Font . font (" Courier ", FontWeight. BOLD ,  15));
                            text1.setTextAlignment(TextAlignment.CENTER);
                            pane.getChildren().add(text1);

                            String path = "src\\main\\resources\\As\\win.mp3";
                            Media media = new Media(new File(path).toURI().toString());
                            MediaPlayer mediaPlayer = new MediaPlayer(media);
                            mediaPlayer.setVolume(0.04);
                            mediaPlayer.setAutoPlay(true);


                            StackPane winpane = new StackPane();
                            Rectangle squarewin = new Rectangle(400, 150);
                            squarewin.setFill(Color.WHITE);
                            squarewin.setStroke(Color.BLACK);
                            Button restart=new Button("Restart");
                            restart.setPrefSize(150, 30);

                            winpane.getChildren().addAll(squarewin,pane,restart);
                            Scene winscene=new Scene(winpane,400,150);
                            Stage stagewin=new Stage();
                            stagewin.setScene(winscene);
                            stagewin.setAlwaysOnTop(true);
                            stagewin.setTitle("Winner");
                            stagewin.initStyle(StageStyle.UTILITY);
                            stagewin.show();


                            restart.setOnMouseClicked(ActionEvent -> {
                                mediaPlayerg.play();
                                stagewin.close();
                                try {
                                    first[0]=true;
                                    a[0].getChildren().clear();
                                    trycounter[0]=1;
                                    randomMovie = MovieController.randomMovieSelector();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                StackPane root1 =new StackPane();
                                root1.getChildren().addAll(txs);
                                Scene textFieldScene = new Scene(new StackPane(imgs,txs), 1920, 1080

                                );
                                stage.setScene(textFieldScene);
                                stage.setMaximized(true);
                                stage.setTitle("Movidle");
                                stage.show();
                            });

                        }

                    }
                    if(trycounter[0] ==6&&!selectedMovie.title.equals(randomMovie.title)){
                        Pane pane = new Pane ();
                        mediaPlayerg.pause();
                        Text text1 = new Text (20 , 20 , "You are out of guesses. You can start a new game or add more guessing attempts");

                        text1 . setFont ( Font . font (" Courier ", FontWeight. BOLD , 15));
                        text1.setWrappingWidth(350);
                        text1.setTextAlignment(TextAlignment.CENTER);
                        pane . getChildren (). add ( text1 );


                        Media media = new Media(new File("src\\main\\resources\\As\\lose.mp3").toURI().toString());
                        MediaPlayer mediaPlayer = new MediaPlayer(media);
                        mediaPlayer.setVolume(0.1);
                        mediaPlayer.setAutoPlay(true);


                        StackPane losepane = new StackPane();
                        Rectangle squarelose = new Rectangle(400, 150);
                        squarelose.setFill(Color.WHITE);
                        squarelose.setStroke(Color.BLACK);
                        Button restart=new Button("Restart");
                        restart.setPrefSize(150, 50);
                        Button secondchance=new Button("+5 Guess");
                        secondchance.setPrefSize(150, 50);


                        HBox buttons=new HBox();
                        buttons.getChildren().addAll(restart,secondchance);
                        losepane.getChildren().addAll(squarelose,pane,buttons);
                        buttons.setAlignment(Pos.CENTER);

                        buttons.setSpacing(20);
                        Scene losescene=new Scene(losepane,400,150);
                        Stage stagelose=new Stage();
                        stagelose.setScene(losescene);
                        stagelose.setAlwaysOnTop(true);
                        stagelose.setTitle("You Lose");
                        stagelose.initStyle(StageStyle.UTILITY);
                        stagelose.show();


                        restart.setOnMouseClicked(ActionEvent -> {
                            mediaPlayerg.play();
                            stagelose.close();
                            try {
                                first[0]=true;
                                a[0].getChildren().clear();
                                trycounter[0]=1;
                                randomMovie = MovieController.randomMovieSelector();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            StackPane root =new StackPane();
                            root.getChildren().addAll(txs);
                            Scene textFieldScene = new Scene(new StackPane(imgs,txs), 1920, 1080);
                            stage.setScene(textFieldScene);
                            stage.setMaximized(true);
                            stage.setTitle("Movidle");
                            stage.show();

                        });
                        secondchance.setOnMouseClicked(ActionEvent -> {

                            mediaPlayerg.play();
                            Media media1 = new Media(new File("src\\main\\resources\\As\\heal.mp3").toURI().toString());
                            MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
                            mediaPlayer1.setVolume(0.1);
                            mediaPlayer1.setAutoPlay(true);

                            stagelose.close();
                            try {
                                first[0]=true;
                                a[0].getChildren().clear();
                                trycounter[0]=1;

                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                    }
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
        });


        Scene textFieldScene = new Scene(new StackPane(imgs,txs), 1920, 1080);
        stage.setScene(textFieldScene);
        stage.setMaximized(true);
        stage.setTitle("Movidle");
        stage.show();
    }
    private ImageView loadMovieImageAsync(String imageUrl) {
        Task<Image> imageLoadingTask = new Task<>() {
            @Override
            protected Image call() throws Exception {
                return new Image(imageUrl);
            }
        };

        ImageView imageView=new ImageView();
        imageLoadingTask.setOnSucceeded(event -> {
            Image loadedImage = imageLoadingTask.getValue();
            imageView.setImage(loadedImage);
        });

        imageLoadingTask.setOnFailed(event -> {
            Throwable exception = imageLoadingTask.getException();
        });

        // Resim yükleme görevini başlat
        Thread loadingThread = new Thread(imageLoadingTask);
        loadingThread.start();
        return imageView;
    }




    public void startStage() {
        Pane intro = new Pane();
        Text introtext = new Text(20, 20, "Normal: It has been configured according to the requirements of the project.\n" +
                "Easy: If you correctly identify a feature of the movie,It removes movies that do not possess that feature." );
        introtext.setTextAlignment(TextAlignment.CENTER);
        introtext.setWrappingWidth(600);
        introtext.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 15));

        Button exit = new Button("Exit");
        exit.setPrefSize(150, 50);
        Button back = new Button("Back");
        back.setPrefSize(150, 50);
        HBox button=new HBox();
        button.setAlignment(Pos.CENTER);
        button.getChildren().addAll(back,exit);
        button.setSpacing(40);
        VBox vbox1 = new VBox();
        Rectangle squareintro = new Rectangle(600, 200);
        squareintro.setFill(Color.WHITE);
        squareintro.setStroke(Color.BLACK);
        vbox1.getChildren().addAll(introtext, button);
        vbox1.setAlignment(Pos.CENTER);
        vbox1.setSpacing(70);
        intro.getChildren().addAll(squareintro,vbox1);

        Scene introScene = new Scene(intro, 600, 200);
        introScene.setFill(Color.BLACK);


        Pane start = new Pane();
        Text startText = new Text(20, 20, "\n" + " Welcome to the game! You have 5 possible guesses.\nYou can select from recommended panel or you can directly write the name of the movie. After making your selection, press Enter." );
        startText.setTextAlignment(TextAlignment.CENTER);
        startText.setWrappingWidth(600);
        startText.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 15));

        Rectangle squareStart = new Rectangle(600, 200);
        squareStart.setFill(Color.WHITE);
        squareStart.setStroke(Color.BLACK);

        HBox buttons=new HBox();
        Button normal = new Button("Normal");
        normal.setPrefSize(150, 50);
        Button easy = new Button("Easy");
        easy.setPrefSize(150, 50);
        Button introb = new Button("Mode Introduction");
        introb.setPrefSize(150, 50);

        buttons.getChildren().addAll(normal,easy,introb);
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(startText, buttons);
        vbox.setSpacing(50);
        vbox.setAlignment(Pos.CENTER);
        start.getChildren().addAll(squareStart, vbox);

        Scene startScene = new Scene(start, 600, 200);
        startScene.setFill(Color.BLACK);

        Stage stage = new Stage();
        stage.setScene(startScene);
        stage.setAlwaysOnTop(true);
        stage.setTitle("Welcome");

        stage.show();
        normal.setOnMouseClicked(event -> {
            gamemode=1;
            stage.close();
            try {
                gameSection(gamemode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        easy.setOnMouseClicked(event -> {
            gamemode=2;
            stage.close();
            try {
                gameSection(gamemode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        introb.setOnMouseClicked(event -> {
            stage.setScene(introScene);
        });
        back.setOnMouseClicked(event -> {
            stage.setScene(startScene);
        });
        exit.setOnMouseClicked(event -> {
            stage.close();
        });

    }
    private void updateSuggestions(String userInput) {
        suggestionsListView.getItems().clear();
        List<String> suggestions = new ArrayList<>();
        suggestions.clear();
        for (String item : MovieController.titlelist) {
            if (item.toLowerCase().startsWith(userInput)) {
                suggestions.remove(item);
                suggestions.add(item);
            }
        }

        suggestionsListView.getItems().addAll(suggestions);
    }
    private void updateSuggestions2() {
        suggestionsListView.getItems().clear();
        List<String> suggestions2 = new ArrayList<>();


        for (String item : MovieController.titlelist) {
                suggestions2.remove(item);
                suggestions2.add(item);
        }

        suggestionsListView.getItems().addAll(suggestions2);
    }

    private void filterMovieListByYear(String year) {
        for (Movie item : MovieController.movielist) {
            if (!item.year.equals(year)) {
                MovieController.titlelist.remove(item.title);
            }
        }
        updateSuggestions2();
    }

    private void filterMovieListByGenre(String genre) {
        for (Movie item : MovieController.movielist) {
            if (!item.genre.equals(genre)) {
                MovieController.titlelist.remove(item.title);
            }
        }

        updateSuggestions2();
    }

    private void filterMovieListByOrigin(String origin) {
        for (Movie item : MovieController.movielist) {
            if (!item.origin.equals(origin)) {
                MovieController.titlelist.remove(item.title);
            }
        }
        updateSuggestions2();
    }

    private void filterMovieListByDirector(String director) {
        for (Movie item : MovieController.movielist) {
            if (!item.director.equals(director)) {
                MovieController.titlelist.remove(item.title);
            }
        }
        updateSuggestions2();
    }

    private void filterMovieListByStar(String star) {
        for (Movie item : MovieController.movielist) {
            if (!item.star.equals(star)) {
                MovieController.titlelist.remove(item.title);
            }
        }
        updateSuggestions2();
    }
    @Override
    public void start(Stage stage) throws Exception {
        startStage();
    }

    public HBox paneDesign(Movie randomMovie, String titlex, String yearx, String genrex, String originx, String directorx, String starx,String nox,Boolean a) {


        StackPane pane = new StackPane();
        Rectangle square = new Rectangle(150, 100);
        Text title = new Text();
        title.setFont(Font.font("Arial",FontWeight.BOLD, 20));
        title.setWrappingWidth(150);
        title.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().addAll(square, title);
        if (randomMovie.title.compareTo(titlex) == 0) {
            Media media1 = new Media(new File("src\\main\\resources\\As\\green.wav").toURI().toString());
            MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
            mediaPlayer1.setVolume(0.04);
            mediaPlayer1.setAutoPlay(true);
            FillTransition ft = new FillTransition(Duration.millis(500), square, Color.WHEAT, Color.GREEN);
            ft.setCycleCount(5);
            ft.setAutoReverse(true);
            ft.play();

        } else if(!a) {

            FillTransition ft = new FillTransition(Duration.millis(1000), square, Color.WHEAT, Color.DARKRED);
            ft.setCycleCount(1);
            ft.setAutoReverse(true);
            ft.play();
        }



        StackPane pane1 = new StackPane();
        Rectangle square1 = new Rectangle(150, 100);
        Text year = new Text();
        year.setFont(Font.font("Arial",FontWeight.BOLD, 20));
        pane1.getChildren().addAll(square1, year);
        if (randomMovie.year.compareTo(yearx) == 0) {
            Media media1 = new Media(new File("src\\main\\resources\\As\\green.wav").toURI().toString());
            MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
            mediaPlayer1.setVolume(0.04);
            mediaPlayer1.setAutoPlay(true);
            FillTransition ft1 = new FillTransition(Duration.millis(500), square1, Color.WHEAT, Color.GREEN);
            ft1.setCycleCount(5);
            ft1.setAutoReverse(true);
            ft1.play();
        } else if(!a&&(randomMovie.year.compareTo(yearx))>0) {

            String currentDir = System.getProperty("user.dir");
            String imagePath = currentDir + "\\src\\main\\resources\\As\\up.jpg";
            Image img = new Image("file:" + imagePath);
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(150);
            imgView.setFitHeight(100);
            imgView.setOpacity(0.35);
            Label imgs = new Label();
            imgs.setGraphic(imgView);

            pane1.getChildren().add(imgs);

        }
        else if(!a&&(randomMovie.year.compareTo(yearx))<0) {

            String currentDir = System.getProperty("user.dir");
            String imagePath = currentDir + "\\src\\main\\resources\\As\\Down.jpg";
            Image img = new Image("file:" + imagePath);
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(150);
            imgView.setFitHeight(100);
            imgView.setOpacity(0.35);
            Label imgs = new Label();
            imgs.setGraphic(imgView);
            pane1.getChildren().add(imgs);

        }

        StackPane pane2 = new StackPane();
        Rectangle square2 = new Rectangle(150, 100);
        Text genre = new Text();
        genre.setFont(Font.font("Arial",FontWeight.BOLD, 20));
        pane2.getChildren().addAll(square2, genre);
        if (randomMovie.genre.compareTo(genrex) == 0) {
            Media media1 = new Media(new File("src\\main\\resources\\As\\green.wav").toURI().toString());
            MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
            mediaPlayer1.setVolume(0.04);
            mediaPlayer1.setAutoPlay(true);
            FillTransition ft2 = new FillTransition(Duration.millis(500), square2, Color.WHEAT, Color.GREEN);
            ft2.setCycleCount(5);
            ft2.setAutoReverse(true);
            ft2.play();
        } else if(!a) {
            FillTransition ft2 = new FillTransition(Duration.millis(3000), square2, Color.WHEAT, Color.DARKRED);
            ft2.setCycleCount(1);
            ft2.setAutoReverse(true);
            ft2.play();
        }



        StackPane pane3 = new StackPane();
        Rectangle square3 = new Rectangle(150, 100);
        Text origin = new Text();
        origin.setFont(Font.font("Arial",FontWeight.BOLD, 20));
        pane3.getChildren().addAll(square3, origin);

        if (randomMovie.origin.compareTo(originx) == 0) {
            Media media1 = new Media(new File("src\\main\\resources\\As\\green.wav").toURI().toString());
            MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
            mediaPlayer1.setVolume(0.04);
            mediaPlayer1.setAutoPlay(true);
            FillTransition ft3 = new FillTransition(Duration.millis(500), square3, Color.WHEAT, Color.GREEN);
            ft3.setCycleCount(5);
            ft3.setAutoReverse(true);
            ft3.play();
        } else if(!a) {

            FillTransition ft3 = new FillTransition(Duration.millis(4000), square3, Color.WHEAT, Color.DARKRED);
            ft3.setCycleCount(1);
            ft3.setAutoReverse(true);
            ft3.play();
        }



        StackPane pane4 = new StackPane();
        Rectangle square4 = new Rectangle(150, 100);
        Text director = new Text();
        director.setFont(Font.font("Arial",FontWeight.BOLD, 20));
        director.setWrappingWidth(150);
        director.setTextAlignment(TextAlignment.CENTER);
        pane4.getChildren().addAll(square4, director);

        if (randomMovie.director.compareTo(directorx) == 0) {
            Media media1 = new Media(new File("src\\main\\resources\\As\\green.wav").toURI().toString());
            MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
            mediaPlayer1.setVolume(0.04);
            mediaPlayer1.setAutoPlay(true);
            FillTransition ft4 = new FillTransition(Duration.millis(500), square4, Color.WHEAT, Color.GREEN);
            ft4.setCycleCount(5);
            ft4.setAutoReverse(true);
            ft4.play();
        } else if(!a) {

            FillTransition ft4 = new FillTransition(Duration.millis(4500), square4, Color.WHEAT, Color.DARKRED);
            ft4.setCycleCount(1);
            ft4.setAutoReverse(true);
            ft4.play();
        }




        StackPane pane5 = new StackPane();
        Rectangle square5 = new Rectangle(150, 100);
        Text star = new Text();
        star.setFont(Font.font("Arial",FontWeight.BOLD, 20));
        star.setWrappingWidth(150);
        star.setTextAlignment(TextAlignment.CENTER);
        pane5.getChildren().addAll(square5, star);
        if (randomMovie.star.compareTo(starx) == 0) {
            Media media1 = new Media(new File("src\\main\\resources\\As\\green.wav").toURI().toString());
            MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
            mediaPlayer1.setVolume(0.04);
            mediaPlayer1.setAutoPlay(true);
            FillTransition ft5 = new FillTransition(Duration.millis(500), square5, Color.WHEAT, Color.GREEN);
            ft5.setCycleCount(5);
            ft5.setAutoReverse(true);
            ft5.play();
        } else if (!a){
            FillTransition ft5 = new FillTransition(Duration.millis(6000), square5, Color.WHEAT, Color.DARKRED);
            ft5.setCycleCount(1);
            ft5.setAutoReverse(true);
            ft5.play();
        }

        StackPane pane6 = new StackPane();
        Rectangle square6 = new Rectangle(150, 100);
        Text no = new Text();
        no.setFont(Font.font("Arial",FontWeight.BOLD, 20));
        no.setWrappingWidth(150);
        no.setTextAlignment(TextAlignment.CENTER);
        pane6.getChildren().addAll(square6, no);

        if (randomMovie.no.compareTo(nox) == 0) {
            Media media1 = new Media(new File("src\\main\\resources\\As\\green.wav").toURI().toString());
            MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
            mediaPlayer1.setVolume(0.04);
            mediaPlayer1.setAutoPlay(true);
            FillTransition ft6 = new FillTransition(Duration.millis(500), square6, Color.WHEAT, Color.GREEN);
            ft6.setCycleCount(5);
            ft6.setAutoReverse(true);
            ft6.play();
        } else if (!a){
            FillTransition ft6 = new FillTransition(Duration.millis(500), square6, Color.WHEAT, Color.DARKRED);
            ft6.setCycleCount(1);
            ft6.setAutoReverse(true);
            ft6.play();
        }


        title.setFill(Color.WHITE);
        year.setFill(Color.WHITE);
        genre.setFill(Color.WHITE);
        origin.setFill(Color.WHITE);
        director.setFill(Color.WHITE);
        star.setFill(Color.WHITE);
        no.setFill(Color.WHITE);

        title.setText(titlex);
        year.setText(yearx);
        genre.setText(genrex);
        origin.setText(originx);
        director.setText(directorx);
        star.setText(starx);
        no.setText(nox);


        HBox texts = new HBox();
        texts.setAlignment(Pos.CENTER);
        if(a){

            square.setWidth(150);
            square.setHeight(35);
            square1.setWidth(150);
            square1.setHeight(35);
            square2.setWidth(150);
            square2.setHeight(35);
            square3.setWidth(150);
            square3.setHeight(35);
            square4.setWidth(150);
            square4.setHeight(35);
            square5.setWidth(150);
            square5.setHeight(35);
            square6.setWidth(150);
            square6.setHeight(35);
            square5.setFill(Color.CADETBLUE);
            square5.setStroke(Color.BLACK);
            square4.setFill(Color.CADETBLUE);
            square4.setStroke(Color.BLACK);
            square3.setFill(Color.CADETBLUE);
            square3.setStroke(Color.BLACK);
            square2.setFill(Color.CADETBLUE);
            square2.setStroke(Color.BLACK);
            square1.setFill(Color.CADETBLUE);
            square1.setStroke(Color.BLACK);
            square.setFill(Color.CADETBLUE);
            square.setStroke(Color.BLACK);
            square6.setFill(Color.CADETBLUE);
            square6.setStroke(Color.BLACK);
        }
        texts.setSpacing(40);
        texts.getChildren().addAll(pane6,pane, pane1, pane2, pane3, pane4, pane5);
        return texts;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
