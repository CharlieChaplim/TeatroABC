package br.eng.gjkl.teatro.ui;

import br.eng.gjkl.teatro.classes.Utility;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;
import net.synedra.validatorfx.TooltipWrapper;

import java.io.IOException;

public class StartupMenu extends Application {

    private Validator validadorCadastro = new Validator();
    private Validator validadorLogin = new Validator();


    private StartupController controller;

    public StartupMenu(StartupController controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage stage) throws IOException {

        /* TODO: Reabilitar
        validadorLogin.createCheck()
                .dependsOn("CPF", controller.fieldCPFLogin.textProperty())
                .withMethod(c -> {
                    if (!((String)c.get("CPF")).isEmpty()) {
                        if (!Utility.checkCPF(c.get("CPF"))) {
                            c.error("CPF inválido.");
                        }
                    }
                })
                .decorates(controller.fieldCPFLogin)
                .immediate();
         */

        validadorCadastro.createCheck()
                .dependsOn("CPF", controller.fieldCPFCadastro.textProperty())
                .withMethod(c -> {
                    if (!((String)c.get("CPF")).isEmpty()) {
                        if (!Utility.checkCPF(c.get("CPF"))) {
                            c.error("CPF inválido.");
                        }
                    }
                })
                .decorates(controller.fieldCPFCadastro)
                .immediate();

        validadorCadastro.createCheck()
                .dependsOn("Nome", controller.fieldNomeCadastro.textProperty())
                .withMethod(c -> {
                    String nome = c.get("Nome");
                    if (!nome.isEmpty()) {
                        if (!Utility.checkName(nome)) {
                            c.error("Nome contém caracteres inválidos.");
                        }
                    }
                })
                .decorates(controller.fieldNomeCadastro)
                .immediate();


        validadorCadastro.createCheck()
                .dependsOn("Telefone", controller.fieldTelefoneCadastro.textProperty())
                .withMethod(c -> {
                    String telefone = c.get("Telefone");
                    if (!telefone.isEmpty()) {
                        if (!Utility.checkTelefone(telefone)) {

                            c.error("O telefone deve ser escrito com o DDD e o 9, contendo 11 dígitos no total!");
                        }
                    }

                })
                .decorates(controller.fieldTelefoneCadastro)
                .immediate();

        TooltipWrapper<Button> btnLoginEncapsulado = new TooltipWrapper<>(
                controller.btnLogin,
                validadorLogin.containsErrorsProperty(),
                Bindings.concat("Não é possivel logar:\n", validadorLogin.createStringBinding())
        );

        TooltipWrapper<Button> btnCadastroEncapsulado = new TooltipWrapper<>(
                controller.btnCadastro,
                validadorCadastro.containsErrorsProperty(),
                Bindings.concat("Não é possivel cadastrar-se:\n", validadorCadastro.createStringBinding())
        );


        GridPane.setMargin(btnLoginEncapsulado, new Insets(0, 0, 35,0));
        GridPane.setMargin(btnCadastroEncapsulado, new Insets(0, 0, 35,0));

        controller.gridPane.add(btnLoginEncapsulado, 2, 7);
        controller.gridPane.add(btnCadastroEncapsulado, 0, 9);
        
        stage.show();
    }
}
