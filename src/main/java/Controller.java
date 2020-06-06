import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Класс контроллер в котором содержаться
 * методы обработчиков событий взаимодействия пользователя с формой
 */
public class Controller implements Initializable {

    /**
     * Компонент отображения графиков
     * и список построенных графиков
     */
    public LineChart<Double, Double> charting;
    public ArrayList<XYChart.Series<Double, Double>> serieses = new ArrayList<>();


    /**
     * Компонент ComboBox с названиями графиков
     */
    public ComboBox<String> listChart;


    /**
     * Текстовые поля в которых хранится
     * диапозон построения и шаг
     */
    public TextField fromX;
    public TextField beforeX;
    public TextField stepX;


    /**
     * Текстовые поля в которых храняться значения
     * того или иного коэффициента
     */
    public TextField coefK;
    public TextField coefA1;
    public TextField coefB1;
    public TextField coefC1;
    public TextField coefD;
    public TextField coefB2;
    public TextField coefA2;
    public TextField coefB3;
    public TextField coefC2;


    /**
     * Компоненты Label которые будут сигнализировать
     * о возможности построения того или иного графика
     */
    public Label chart1;
    public Label chart2;
    public Label chart3;


    /**
     * Компоненты Label для поправки текста в них
     */
    public Label x31;
    public Label x22;
    public Label x21;


    /**
     * Константы с названиями графиков
     */
    static final String ALL_CHART = "Все";
    static final String LINE_CHART = "y = kx + b";
    static final String SQUARE_CHART = "y = ax\u00b2 + bx + c";
    static final String CUBE_CHART = "y = ax\u00b3 + bx\u00b2 + cx + d";


    /**
     * Метод инициализации класса
     */
    public Controller() {
    }

    /**
     * Обработчик события при загрузке формы
     * @param url
     * @param rb
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> items = FXCollections.observableArrayList(
                ALL_CHART,
                LINE_CHART,
                SQUARE_CHART,
                CUBE_CHART
        );

        listChart.setItems(items);
        listChart.setValue(ALL_CHART);

        x31.setText("x\u00b3 +");
        x21.setText("x\u00b2 +");
        x22.setText("x\u00b2 +");

    }

    /**
     * Обработчик события нажатия на кнопку "Построить"
     * @param actionEvent
     */
    public void buildClicks(ActionEvent actionEvent) {

        serieses.clear();
        charting.getData().clear();
        listChart.setValue(ALL_CHART);

        if (fromX.getText().length() < 1){
            ModelLogic.msgWarning("Заполните x1!");
            return;
        }

        if (beforeX.getText().length() < 1){
            ModelLogic.msgWarning("Заполните x2!");
            return;
        }

        if (stepX.getText().length() < 1){
            ModelLogic.msgWarning("Заполните шаг!");
            return;
        }

        double x1 = ModelLogic.setResultNum(fromX.getText());
        double x2 = ModelLogic.setResultNum(beforeX.getText());
        double step = ModelLogic.setResultNum(stepX.getText());


        if (x1 > x2) {
            ModelLogic.msgWarning("x1 не может быть больше чем x2");
            return;
        }

        if (chart1.getTextFill().equals(Color.rgb(0, 255, 0))) {
            serieses.add(ModelLogic.buildSeries(1,LINE_CHART, x1, x2, step, coefK, coefB1));
        }

        if (chart2.getTextFill().equals(Color.rgb(0, 255, 0))) {
            serieses.add(ModelLogic.buildSeries(2, SQUARE_CHART, x1, x2, step, coefA1, coefB2, coefC1));
        }

        if (chart3.getTextFill().equals(Color.rgb(0, 255, 0))) {
            serieses.add(ModelLogic.buildSeries(3, CUBE_CHART, x1,x2,step, coefA2, coefB3, coefC2, coefD));
        }


        charting.getData().setAll(serieses);

    }

    /**
     * Обработчик события изменения значение в текстовом поле с имененм FromX
     * @param keyEvent
     */
    public void inputFromX(KeyEvent keyEvent) {
        ModelLogic.setNumberText(fromX);
    }

    /**
     * Обработчик события изменения значение в текстовом поле с именем BeforeX
     * @param keyEvent
     */
    public void inputBeforeX(KeyEvent keyEvent) {
        ModelLogic.setNumberText(beforeX);
    }

    /**
     * Обработчик события изменения значение в текстовом поле с именем Step
     * @param keyEvent
     */
    public void inputStep(KeyEvent keyEvent) {
        ModelLogic.setNumberText(stepX);
    }

    /**
     * Обработчик события изменения значение в текстовом поле с именем CoefK
     * @param keyEvent
     */
    public void inputCoefK(KeyEvent keyEvent) {
        ModelLogic.setNumberText(coefK, false, chart1, coefK, coefB1);
    }

    /**
     * Обработчик события изменения значение в текстовом поле с именем CoefA1
     * @param keyEvent
     */
    public void inputCoefA1(KeyEvent keyEvent) {
        ModelLogic.setNumberText(coefA1, true, chart2, coefA1, coefB2, coefC1);
    }

    /**
     * Обработчик события изменения значение в текстовом поле с именем CoefB1
     * @param keyEvent
     */
    public void inputCoefB1(KeyEvent keyEvent) {
        ModelLogic.setNumberText(coefB1, false, chart1, coefK, coefB1);
    }

    /**
     * Обработчик события изменения значение в текстовом поле с именем CoefC1
     * @param keyEvent
     */
    public void inputCoefC1(KeyEvent keyEvent) {
        ModelLogic.setNumberText(coefC1, false, chart2, coefA1, coefB2, coefC1);
    }

    /**
     * Обработчик события изменения значение в текстовом поле с именем CoefD
     * @param keyEvent
     */
    public void inputCoefD(KeyEvent keyEvent) {
        ModelLogic.setNumberText(coefD, false, chart3, coefA2, coefB3, coefC2, coefD);
    }

    /**
     * Обработчик события изменения значение в текстовом поле с именем CoefB2
     * @param keyEvent
     */
    public void inputCoefB2(KeyEvent keyEvent) {
        ModelLogic.setNumberText(coefB2, false, chart2, coefA1, coefB2, coefC1);
    }

    /**
     * Обработчик события изменения значение в текстовом поле с именем CoefA2
     * @param keyEvent
     */
    public void inputCoefA2(KeyEvent keyEvent) {
        ModelLogic.setNumberText(coefA2, true, chart3, coefA2, coefB3, coefC2, coefD);
    }

    /**
     * Обработчик события изменения значение в текстовом поле с именем CoefB3
     * @param keyEvent
     */
    public void inputCoefB3(KeyEvent keyEvent) {
        ModelLogic.setNumberText(coefB3, false, chart3, coefA2, coefB3, coefC2, coefD);
    }

    /**
     * Обработчик события изменения значение в текстовом поле с именем CoefC2
     * @param keyEvent
     */
    public void inputCoefC2(KeyEvent keyEvent) {
        ModelLogic.setNumberText(coefC2, false, chart3, coefA2, coefB3, coefC2, coefD);
    }

    /**
     * Обработчик события изменения значения в ComboBox
     * @param actionEvent
     */
    public void updateChart(ActionEvent actionEvent) {

        switch (listChart.getSelectionModel().getSelectedItem()) {
            case ALL_CHART:

                if (serieses.size() > 0) {

                    charting.getData().clear();
                    for (int i = 0; i < serieses.size(); i++)
                    {
                        charting.getData().add(ModelLogic.buildNewSeries(serieses.get(i)));
                    }

                }
                else {

                    ModelLogic.msgWarning("Не один график не построен!");
                    listChart.setValue(ALL_CHART);
                }

                break;
            case LINE_CHART:
                showChart(LINE_CHART);
                break;
            case SQUARE_CHART:
                showChart(SQUARE_CHART);
                break;
            case CUBE_CHART:
                showChart(CUBE_CHART);
                break;
            default:
                // это мне по дефолту IDEA добавила, в принципе пусть ошибку бросает
                // по идее я сюда попасть не должен
                throw new IllegalStateException("Unexpected value: " + listChart.getSelectionModel().getSelectedItem());
        }

    }

    /**
     * Обработчик события нажатия на кнопку "Сохранить график"
     * @param actionEvent
     */
    public void saveChartClick(ActionEvent actionEvent) {

        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранение графика....");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Файлы изображений (*.png)",".png");
        fileChooser.setFileFilter(filter);


        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {

            ModelLogic.saveImage(charting, fileChooser.getSelectedFile());
            JOptionPane.showConfirmDialog(null, "Файл сохранен!","Предупреждение", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        parentFrame.dispose();
    }

    /**
     * Метод отображения определенного графика на форме
     * @param name - Имя графика
     */
    private void showChart(String name){

        boolean msg = true;

        for (int i = 0; i < serieses.size(); i++) {

            if (serieses.get(i).getName().equals(name))
            {
                charting.getData().setAll(ModelLogic.buildNewSeries(serieses.get(i)));
                msg = false;
                break;
            }
        }

        if (msg) {
            ModelLogic.msgError("График " +name+" не построен!");
            if (serieses.size() > 0){
                listChart.setValue(ALL_CHART);
            }
        }
    }
}
