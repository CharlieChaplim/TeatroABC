package br.eng.gjkl.teatro.ui;

import br.eng.gjkl.teatro.classes.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static br.eng.gjkl.teatro.Main.usuarios;

public class StartupController {
    /*Campos utilizados no Login*/
    public Button btnLogin;
    public PasswordField fieldSenhaLogin;
    public TextField fieldCPFLogin;

    /*Campos utilizados no Cadastro*/
    public Button btnCadastro;
    public TextField fieldNomeCadastro;
    public PasswordField fieldSenhaCadastro;
    public DatePicker fieldDataCadastro;
    public TextField fieldEnderecoCadastro;
    public TextField fieldTelefoneCadastro;
    public TextField fieldCPFCadastro;
    public GridPane gridPane;

    public void loginAction(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CompraView.fxml"));
        Parent root = loader.load();
        CompraMenu compraMenu = new CompraMenu(loader.getController());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        compraMenu.start(stage);
    }
    public void cadastroAction(ActionEvent actionEvent) {
        String nome = fieldNomeCadastro.getText();
        String senha = fieldSenhaCadastro.getText();
        String cpf = fieldCPFCadastro.getText();
        String endereco = fieldEnderecoCadastro.getText();
        String telefone = fieldTelefoneCadastro.getText();
        LocalDate ld = fieldDataCadastro.getValue();
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date dataNascimento = Date.from(instant);

        if (usuarios.stream().anyMatch(x -> x.getCPF().equals(cpf)))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro - Cadastro");
            alert.setHeaderText("Não foi possivel realizar o Cadastro:");
            alert.setContentText("CPF já cadastrado.");
            alert.show();
            return;
        }

        StringBuilder erros = new StringBuilder();
        if (ld.isAfter(LocalDate.now())) {
            erros.append("A data de aniversario nao pode ser depois de hoje.\n");
        }
        if (ld.isBefore(LocalDate.now().minusYears(120))) {
            erros.append("O senhor ta bem velhinho né?.\n");
        }
        if (nome.isEmpty()) {
            erros.append("Nome vazio.\n");
        }
        if (senha.isEmpty()) {
            erros.append("Senha vazia.\n");
        }
        if (fieldDataCadastro.valueProperty().isNull().get()) {
            erros.append("Data vazia.\n");
        }
        if (endereco.isEmpty()) {
            erros.append("Endereço vazio.\n");
        }
        if (telefone.isEmpty()) {
            erros.append("Telefone vazio.\n");
        }

        if (!erros.toString().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro - Cadastro");
            alert.setHeaderText("Não foi possivel realizar o Cadastro:");
            alert.setContentText(erros.toString());
            alert.show();
            return;
        }

        usuarios.add(new Usuario(nome, cpf, telefone, endereco, dataNascimento, senha));

        fieldNomeCadastro.clear();
        fieldSenhaCadastro.clear();
        fieldCPFCadastro.clear();
        fieldEnderecoCadastro.clear();
        fieldTelefoneCadastro.clear();
        fieldDataCadastro.getEditor().clear();
    }
}
