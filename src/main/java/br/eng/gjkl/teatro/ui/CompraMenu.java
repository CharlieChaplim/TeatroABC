package br.eng.gjkl.teatro.ui;

import br.eng.gjkl.teatro.Main;
import br.eng.gjkl.teatro.classes.Area;
import br.eng.gjkl.teatro.classes.Cadeira;
import br.eng.gjkl.teatro.classes.Peca;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.util.Callback;
import org.controlsfx.control.CheckListView;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.controlsfx.control.IndexedCheckModel;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CompraMenu extends Application {
    static List<Peca> pecas = new LinkedList<Peca>();
    static String[] pecasNome = {"Romeu e Julieta", "Harry Potter e a criança amaldiçoada", "Digital Circus"};
    static String[] sessoes = {"Manhã", "Tarde", "Noite"};
    static Area[] areas = {
            new Area("Balcao Nobre", 50, 250),
            new Area("Camarote 1", 10, 80),
            new Area("Camarote 2", 10, 80),
            new Area("Camarote 3", 10, 80),
            new Area("Camarote 4", 10, 80),
            new Area("Camarote 5", 10, 80),
            new Area("Frisa 1", 5, 120),
            new Area("Frisa 2", 5, 120),
            new Area("Frisa 3", 5, 120),
            new Area("Frisa 4", 5, 120),
            new Area("Frisa 5", 5, 120),
            new Area("Frisa 6", 5, 120),
            new Area("Plateia A", 25, 40),
            new Area("Plateia B", 100, 60)
    };
    private CompraController controller;

    public CompraMenu(CompraController controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage stage) throws Exception {
        criarPecas();

        controller.pecaBox.getItems().addAll(pecasNome);
        controller.pecaBox.setValue("Peça");
        /*
        Precisamos implementar o carregamento de sessao e area
        com o controller.pecaBox on action <-
         */
        controller.pecaBox.setOnAction(
                this::pecaBoxAction
        );
        controller.sessaoBox.setOnAction(
                this::sessaoBoxAction
        );
        controller.areaBox.setOnAction(
                event -> {
                    try {
                        areaBoxAction(event);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        controller.sessaoBox.setDisable(true);
        controller.areaBox.setDisable(true);
        stage.show();
    }

    public static void criarPecas() {
        for (String nomePeca : pecasNome) {
            List<Cadeira> cadeiras = new LinkedList<>();
            pecas.add(new Peca(nomePeca, sessoes, cadeiras));

            for (Area area : areas) {
                for (int i = 0; i < area.getQtdMaxCadeiras(); i++) {
                    cadeiras.add(new Cadeira(area, i));
                }
            }
        }
    }

    public void atualizarImagem() {
        String areaSelecionada = controller.areaBox.getValue();
        if (areaSelecionada != null) {
            if (areaSelecionada.isEmpty() || areaSelecionada.equals("Área")) {
                areaSelecionada = "Teatro PB";
            }
        } else {
            areaSelecionada = "Teatro PB";
        }
        
        controller.imageView.setImage(new Image(
                CompraMenu.class.getResourceAsStream(STR."images/\{areaSelecionada}.png")
        ));
    }
    public void areaBoxAction(ActionEvent event) throws URISyntaxException, MalformedURLException {
        atualizarCadeiras();
        atualizarImagem();
    }
    public void sessaoBoxAction(ActionEvent event) {
        EventHandler<ActionEvent> areaHandler = controller.areaBox.getOnAction();
        controller.areaBox.setOnAction(null);

        controller.areaBox.setDisable(false);
        controller.areaBox.getItems().clear();
        controller.areaBox.setValue("Área");
        controller.areaBox.getItems().addAll(Arrays.stream(areas).map(Area::getNome).toList());

        controller.areaBox.setOnAction(areaHandler);
        atualizarCadeiras();
        atualizarImagem();
    }
    public void pecaBoxAction(ActionEvent event) {
        EventHandler<ActionEvent> sessaoHandler = controller.sessaoBox.getOnAction();
        controller.sessaoBox.setOnAction(null);

        controller.sessaoBox.setDisable(false);
        controller.sessaoBox.getItems().clear();
        controller.sessaoBox.setValue("Sessões");
        controller.sessaoBox.getItems().addAll(sessoes);

        controller.sessaoBox.setOnAction(sessaoHandler);
        atualizarCadeiras();
        atualizarImagem();
    }
    public void atualizarCadeiras() {
        controller.checkListView.getItems().clear();

        String pecaSelecionada = controller.pecaBox.getValue();
        String sessaoSelecionada = controller.sessaoBox.getValue();
        String areaSelecionada = controller.areaBox.getValue();

        if (pecaSelecionada == null || sessaoSelecionada == null || areaSelecionada == null) {
            return;
        }

        if (pecaSelecionada.equals("Peças") || pecaSelecionada.isEmpty()) {
            return;
        }

        if (sessaoSelecionada.equals("Sessões") || sessaoSelecionada.isEmpty()) {
            return;
        }
        if (areaSelecionada.equals("Áreas") || areaSelecionada.isEmpty()) {
            return;
        }

        Peca peca = pecas.stream().filter(
                x -> x.getNome().equals(pecaSelecionada)
        ).findFirst().get();

        ObservableList<String> cadeirasObservable = FXCollections.observableArrayList();
        cadeirasObservable.addAll(
                peca.getCadeiraList().stream()
                        .filter(
                            x -> x.getArea().getNome().equals(areaSelecionada))
                        .filter(x -> !x.isComprada())
                        .map(x ->
                            String.format("Cadeira %02d", x.getPosicao()+1))
                        .toList()
        );

        controller.checkListView.getItems().addAll(cadeirasObservable);
    }
}