import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author Дмитрий Масленников
 * @version 1.0
 *
 * Класс отвечающий за логику построение графиков,
 * создание графического файла, и исправление текста,
 * а так же вывода различных сообщений
 */
public class ModelLogic {

    /**
     * Метод контролирующий ввод только цифр, либо в дополнении не позволяет написать 0
     *
     * @param text - Текстовое поле
     * @param isNull - Флаг отвечающий за проверку на написание 0
     * @param res - Элемент Label отвечающий за разешение построения графика
     * @param texts - Набор текстовых полей для проверки наличия в них символов
     */
    public static void setNumberText(TextField text, Boolean isNull, Label res, TextField... texts) {

        Pattern p = Pattern.compile("^-?(\\d+\\.?\\d*)?");
        text.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) {
                text.setText(oldValue);
            }


            if (isNull) {
                if (text.getText().equals("-0")) {
                    text.setText("-1");
                }

                if (text.getText().equals("0")) {
                    text.setText("1");
                }
            }


            boolean isEmpryText = true;

            for (TextField n : texts) {

                if (n.getText().length() <= 0) {
                    isEmpryText = false;
                }

            }

            if (isEmpryText) {
                res.setTextFill(javafx.scene.paint.Color.rgb(0, 255, 0));
            } else {
                res.setTextFill(javafx.scene.paint.Color.rgb(255, 0, 0));
            }

        });

    }

    /**
     * Метод контролирующий ввод только цифр
     * @param text - Текстовое поле
     */
    public  static void setNumberText(TextField text){

        Pattern p = Pattern.compile("^-?(\\d+\\.?\\d*)?");
        text.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) {
                text.setText(oldValue);
            }

            if (text.getText().equals("-0")) {
                text.setText("0");
            }

        });

    }

    /**
     * Метод конвертирования текст в число
     * @param text - Исходный текст
     * @return Число типа Double
     */
    public static Double setResultNum(String text){

        if (text.equals("-"))
        {
            return -1.0;
        }
        else if (text.equals("-0"))
        {
            return 0.0;
        }
        else
        {
            return Double.parseDouble(text);
        }

    }

    /**
     * Метод создания точек координат для вывода в график
     *
     * @param num - Условный номер графика
     * @param name - Имя графика
     * @param x1 - Начало отсчета
     * @param x2 - конец отсчета
     * @param step - Шаг
     * @param values - Набор текстовых полей в которых хранят необходимы коэффициенты
     * @return Набор координат с именем графика
     */
    public static XYChart.Series<Double, Double> buildSeries(Integer num, String name, Double x1, Double x2, Double step,TextField... values){

        XYChart.Series<Double, Double> newSeries = new XYChart.Series<>();

        switch (num){

            case 1:
                newSeries.setName(name);

                double k = setResultNum(values[0].getText());
                double b1 = setResultNum(values[1].getText());

                for (double i = x1; i <= x2; i+=step)
                {
                    newSeries.getData().add(new XYChart.Data(String.valueOf(i) , k * i + b1));
                }
                break;
            case 2:
                newSeries.setName(name);

                double a1 = setResultNum(values[0].getText());
                double b2 = setResultNum(values[1].getText());
                double c1 = setResultNum(values[2].getText());

                for (double x = x1; x <= x2; x+=step)
                {
                    newSeries.getData().add(new XYChart.Data(String.valueOf(x),a1*Math.pow(x,2) + b2*x + c1));
                }


                break;
            case 3:
                newSeries.setName(name);

                double a2 = setResultNum(values[0].getText());
                double b3 = setResultNum(values[1].getText());
                double c2 = setResultNum(values[2].getText());
                double d1 = setResultNum(values[3].getText());


                for(double x = x1; x <= x2; x+=step)
                {
                    newSeries.getData().add(new XYChart.Data(String.valueOf(x),a2 * Math.pow(x,3) + b3 * Math.pow(x,2) + c2 * x + d1));
                }
                break;
            default:
                break;

        }




        return newSeries;
    }

    /**
     * Метод перебора исходного списка координат в новый
     *
     * @param oldSeries - Исходный набор координат
     * @return Новый набор координат
     */
    public static XYChart.Series<Double, Double> buildNewSeries(XYChart.Series<Double, Double> oldSeries){

        XYChart.Series<Double, Double> newSeries = new XYChart.Series<>();
        newSeries.setName(oldSeries.getName());

        for (int i = 0; i < oldSeries.getData().size(); i++)
        {
            newSeries.getData().add(new XYChart.Data(String.valueOf(oldSeries.getData().get(i).getXValue()), oldSeries.getData().get(i).getYValue()));
        }

        return newSeries;
    }

    /**
     * Метод проверки пути для сохраняемого изображения
     *
     * @param node - Фотографируемый узел
     * @param file - Исходный путь для сохранения
     */
    public static void saveImage(Node node, File file){

        String suffix = ".png";

        if(!file.getAbsolutePath().endsWith(suffix)){
            file = new File(file.getAbsoluteFile() + suffix);

        }

        saveAsPng(node, file.getAbsolutePath());
        JOptionPane.showConfirmDialog(null, "Файл сохранен!","Предупреждение", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);

    }

    /**
     * Метод переправки узла на перерисовку и сохранение
     *
     * @param node - Исходный узел
     * @param fname - Путь сохраняемого файла
     */
    public static void saveAsPng(Node node, String fname) {
        saveAsPng(node, fname, new SnapshotParameters());
    }

    /**
     * Метод создания и сохранения изображения
     *
     * @param node - Узел
     * @param fname - Путь сохранения
     * @param ssp - Экземпляр класса для создания скриншота выбранного узла
     */
    public static void saveAsPng(Node node, String fname, SnapshotParameters ssp) {
        WritableImage image = node.snapshot(ssp, null);
        File file = new File(fname);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Ошибка сохранения файла \""+file.getName()+"\" проверьте правильность указанного пути!","Предупреждение", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Метод вывода сообщения об ошибке
     *
     * @param msg - сообщение
     */
    public static void msgError(String msg) {
        JOptionPane.showConfirmDialog(null, msg,"Ошибка", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Метод вывода сообщения с предупреждением
     * @param msg - Сообщение
     */
    public static void msgWarning(String msg){
        JOptionPane.showConfirmDialog(null, msg,"Предупреждение", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
    }

}
