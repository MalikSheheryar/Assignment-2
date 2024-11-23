package com.example.dataform;

import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Circle;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;

public class ApplicationForm extends Application {

    private ObservableList<String[]> submittedData = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Elegant Application Form");

        // Instagram-like gradient
        LinearGradient backgroundGradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#FF7E5F")),
                new Stop(0.5, Color.web("#FD3A84")),
                new Stop(1, Color.web("#C13584"))
        );

        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(backgroundGradient, null, Insets.EMPTY)));

        // Banner
        Text bannerText = new Text("Application Form");
        bannerText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        bannerText.setFill(Color.WHITE);
        bannerText.setEffect(new DropShadow(3, Color.BLACK));
        StackPane banner = new StackPane(bannerText);
        banner.setPadding(new Insets(20));
        banner.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-border-radius: 15px;");
        root.setTop(banner);

        // Profile Picture Section
        VBox imageSection = new VBox(10);
        imageSection.setPadding(new Insets(15));
        imageSection.setStyle("-fx-alignment: center;");

        // Circular Image View
        ImageView profileImageView = new ImageView();
        profileImageView.setFitWidth(100);
        profileImageView.setFitHeight(100);
        profileImageView.setPreserveRatio(true);

        // Create a circular mask for the image
        Circle clip = new Circle(50, 50, 50); // Create a full circle clip
//        profileImageView.setClip(clip);

        // Removed the border style here
        StackPane circularContainer = new StackPane(profileImageView);
        circularContainer.setPrefSize(110, 110); // Adjust for the size of the image and clip

        // Button to upload the image
        Button uploadImageButton = new Button("Upload Image");
        styleButton(uploadImageButton);
        FileChooser fileChooser = new FileChooser();
        uploadImageButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                profileImageView.setImage(image);
            }
        });

        imageSection.getChildren().addAll(circularContainer, uploadImageButton);

        // Form layout
        GridPane formGrid = new GridPane();
        formGrid.setPadding(new Insets(20));
        formGrid.setHgap(10);
        formGrid.setVgap(15);
        formGrid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-border-radius: 15px; -fx-padding: 15px;");

        Label nameLabel = new Label("Name:");
        styleLabel(nameLabel);
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        styleInput(nameField);

        Label fatherNameLabel = new Label("Father's Name:");
        styleLabel(fatherNameLabel);
        TextField fatherNameField = new TextField();
        fatherNameField.setPromptText("Enter father's name");
        styleInput(fatherNameField);

        Label emailLabel = new Label("Email:");
        styleLabel(emailLabel);
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        styleInput(emailField);

        Label dobLabel = new Label("Date of Birth:");
        styleLabel(dobLabel);
        DatePicker dobPicker = new DatePicker();
        dobPicker.setPromptText("Select your date of birth");
        styleInput(dobPicker);

        Label cityLabel = new Label("City:");
        styleLabel(cityLabel);
        TextField cityField = new TextField();
        cityField.setPromptText("Enter your city");
        styleInput(cityField);

        Label addressLabel = new Label("Address:");
        styleLabel(addressLabel);
        TextArea addressArea = new TextArea();
        addressArea.setPromptText("Enter your address");
        addressArea.setPrefRowCount(2);
        styleInput(addressArea);

        Label genderLabel = new Label("Gender:");
        styleLabel(genderLabel);
        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton maleButton = new RadioButton("Male");
        RadioButton femaleButton = new RadioButton("Female");
        styleRadioButton(maleButton);
        styleRadioButton(femaleButton);
        HBox genderBox = new HBox(10, maleButton, femaleButton);

        formGrid.addRow(0, nameLabel, nameField);
        formGrid.addRow(1, fatherNameLabel, fatherNameField);
        formGrid.addRow(2, emailLabel, emailField);
        formGrid.addRow(3, dobLabel, dobPicker);
        formGrid.addRow(4, cityLabel, cityField);
        formGrid.addRow(5, addressLabel, addressArea);
        formGrid.addRow(6, genderLabel, genderBox);

        // Submit button
        Button submitButton = new Button("Submit");
        styleButton(submitButton);
        submitButton.setMaxWidth(Double.MAX_VALUE); // Button stretches horizontally
        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String fatherName = fatherNameField.getText();
            String email = emailField.getText();
            String dob = dobPicker.getValue() != null ? dobPicker.getValue().toString() : "";
            String city = cityField.getText();
            String address = addressArea.getText();
            String gender = maleButton.isSelected() ? "Male" : (femaleButton.isSelected() ? "Female" : "Not Selected");

            if (name.isEmpty() || fatherName.isEmpty() || email.isEmpty() || dob.isEmpty() || city.isEmpty() || address.isEmpty() || gender.equals("Not Selected")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields!");
                alert.showAndWait();
                return;
            }

            submittedData.add(new String[]{name, fatherName, email, dob, city, address, gender});
            showDataScreen();
        });

        VBox mainLayout = new VBox(15, imageSection, formGrid, submitButton);
        mainLayout.setPadding(new Insets(20));

        root.setCenter(mainLayout);
        primaryStage.setScene(new Scene(root, 500, 700));
        primaryStage.show();
    }

    private void showDataScreen() {
        Stage dataStage = new Stage();
        VBox dataLayout = new VBox(10);
        dataLayout.setPadding(new Insets(20));
        dataLayout.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-border-radius: 15px;");

        Label dataLabel = new Label("Submitted Data:");
        dataLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #C13584;");
        dataLayout.getChildren().add(dataLabel);

        for (String[] entry : submittedData) {
            String entryString = String.format(
                    "Name: %s\nFather's Name: %s\nEmail: %s\nDate of Birth: %s\nCity: %s\nAddress: %s\nGender: %s\n",
                    entry[0], entry[1], entry[2], entry[3], entry[4], entry[5], entry[6]
            );
            Label entryLabel = new Label(entryString);
            entryLabel.setStyle("-fx-background-color: #C13584; -fx-text-fill: white; -fx-padding: 10px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
            dataLayout.getChildren().add(entryLabel);
        }

        Scene dataScene = new Scene(dataLayout, 400, 500);
        dataStage.setScene(dataScene);
        dataStage.show();
    }

    private void styleLabel(Label label) {
        label.setStyle("-fx-text-fill: #333; -fx-font-weight: bold; -fx-font-size: 14px;");
    }

    private void styleInput(Control control) {
        control.setStyle("-fx-background-color: white; -fx-text-fill: #333; -fx-border-color: #C13584; -fx-border-radius: 10px;");
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #FD3A84; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 10px; -fx-padding: 10px;");
    }

    private void styleRadioButton(RadioButton radioButton) {
        radioButton.setStyle("-fx-text-fill: #333; -fx-font-weight: bold;");
    }
}
