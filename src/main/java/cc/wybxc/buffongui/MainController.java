package cc.wybxc.buffongui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML
    private Canvas canvas;

    @FXML
    private TextField numberInput;

    @FXML
    private Label numberOutput;

    @FXML
    private BorderPane pane;

    /**
     * 两条平行线的间距
     */
    private final static double lineSpace = 30;

    @FXML
    protected void handleSubmitButtonAction() {
        var width = canvas.getWidth();
        var height = canvas.getHeight();

        var gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);


        int numOfNeedles;
        try {
            numOfNeedles = Integer.parseInt(numberInput.getText());
        } catch (NumberFormatException e) {
            numberOutput.setText("请输入一个整数");
            return;
        }

        // 绘制平行线
        gc.setStroke(Color.BLACK);
        for (double lineY = lineSpace / 2; lineY < height; lineY += lineSpace) {
            gc.strokeLine(-width, lineY, 2 * width, lineY);
        }

        // 绘制针
        var needles = Buffon.experiment(width, height, lineSpace, numOfNeedles);
        gc.setStroke(Color.BLUE);
        for (var needle : needles) {
            gc.strokeLine(needle.x1(), needle.y1(), needle.x2(), needle.y2());
        }

        var pi = Buffon.calculatePi(width, height, lineSpace, needles);
        numberOutput.setText(Double.toString(pi));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane.widthProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.doubleValue());
        });
        pane.heightProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setHeight(newValue.doubleValue() - 50);
        });
    }
}
