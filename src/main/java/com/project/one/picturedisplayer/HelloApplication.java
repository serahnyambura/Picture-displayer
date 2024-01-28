package com.project.one.picturedisplayer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Number and Color Display");

        // Text Input
        TextField numberInput = new TextField();
        numberInput.setPromptText("Enter a number");
        numberInput.setPrefWidth(200);

        // Radio Buttons for Background Color
        ToggleGroup colorToggleGroup = new ToggleGroup();

        RadioButton whiteRadioButton = createRadioButton("White", Color.WHITE, colorToggleGroup);
        RadioButton lightBlueRadioButton = createRadioButton("Light Blue", Color.LIGHTBLUE, colorToggleGroup);

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> displayResult(numberInput.getText(), colorToggleGroup));

        // Layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(numberInput, whiteRadioButton, lightBlueRadioButton, submitButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private RadioButton createRadioButton(String text, Color color, ToggleGroup group) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setUserData(color);
        radioButton.setToggleGroup(group);
        return radioButton;
    }

    private void displayResult(String input, ToggleGroup colorToggleGroup) {
        try {
            int number = Integer.parseInt(input);

            if (number < 1) {
                showError("Please enter a positive integer.");
                return;
            }

            Color selectedColor = (Color) colorToggleGroup.getSelectedToggle().getUserData();

            String numberType = (number % 2 == 0) ? "even" : "odd";

            String imageUrl = (number % 2 == 0) ?
                    "/files/even_image.png" : "/files/odd_image.png";

            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageUrl)));
            ImageView imageView = new ImageView(image);

            Stage pictureStage = new Stage();
            VBox pictureLayout = new VBox(20, new Label("You entered " + number + ", which is " + numberType + "."), imageView);
            pictureLayout.setAlignment(Pos.CENTER);
            pictureLayout.setBackground(new Background(new BackgroundFill(selectedColor, CornerRadii.EMPTY, Insets.EMPTY)));

            Scene pictureScene = new Scene(pictureLayout, 900, 900);

            pictureStage.setScene(pictureScene);
            pictureStage.setTitle("Picture Display");
            pictureStage.show();

        } catch (NumberFormatException | NullPointerException e) {
            showError("Please enter a valid integer and select a background color.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
