package com.kisssusha.ksplugin;

import com.intellij.ui.JBColor;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Arrays;

public class Weather extends JFrame {
    private JPanel panel1;
    private JTextArea textArea1;
    private JTextPane textPane1;


    public Weather() {
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        WeatherApi weatherApi = new WeatherApi();
        WeatherReport weatherReport1 = new WeatherReport();
        try {
            weatherReport1 = weatherApi.timelineRequestHttpClient();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        appendToPane(textPane1, weatherReport1.getLocation(), JBColor.GREEN);
        appendToPane(textPane1, weatherReport1.getCurrentWeather(), JBColor.RED);

        textArea1.append(Arrays.toString(weatherReport1.getPredictWeatherList()));

        setVisible(true);

    }

    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}
