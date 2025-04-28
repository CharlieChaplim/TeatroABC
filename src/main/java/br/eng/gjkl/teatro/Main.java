package br.eng.gjkl.teatro;

import br.eng.gjkl.teatro.classes.Usuario;
import br.eng.gjkl.teatro.ui.CompraMenu;
import br.eng.gjkl.teatro.ui.StartupMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class Main extends Application {
    public static LinkedList<Usuario> usuarios = new LinkedList<>();
    public static Gson gson = new Gson();

    @Override
    public void start(Stage stage) throws Exception {
        File f = new File(Main.class.getResource("usuarios.json").toURI().getPath());

        if (f.exists() && !f.isDirectory()) {
            FileReader fileReader = new FileReader(Main.class.getResource("usuarios.json").toURI().getPath());
            usuarios.addAll(
                    Arrays.asList(gson.fromJson(
                            new BufferedReader(fileReader),
                            Usuario[].class)
                    )
            );
            fileReader.close();
            inciarMenuStart(stage);
        }
    }

    @Override
    public void stop() throws IOException, URISyntaxException {
        if (!usuarios.isEmpty()) {
            String a = gson.toJson(usuarios);
            FileWriter myWriter = new FileWriter(Main.class.getResource("usuarios.json").toURI().getPath());
            myWriter.write(a);
            myWriter.close();
        }
    }

    public void inciarMenuStart(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartupMenu.class.getResource("StartupView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        StartupMenu startupMenu = new StartupMenu(fxmlLoader.getController());
        stage.setTitle("Teatro - Entrada");
        stage.setResizable(false);
        stage.setScene(scene);
        startupMenu.start(stage);
    }
}
