package ar.com.kafka.views;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class ShowQueues extends JFrame {


    private IndexView indexView;
    private JButton aceptar;
    private JList<String> queues;


    public ShowQueues(IndexView indexView , Set<String> list){
        this.indexView = indexView;
        aceptar = new JButton("Aceptar");

        setLayout(new FlowLayout());

        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("Nothing to display");
        queues = new JList<String>(model);
        queues.setLayoutOrientation(JList.VERTICAL);


        list.forEach(x -> model.addElement(x));

        JScrollPane scrollPane = new JScrollPane(queues);
        scrollPane.setMinimumSize(new Dimension(100,50));
        add(scrollPane);
        add(aceptar);
        setSize(400,250);
        setLocation(new Point(200,200));
        setVisible(true);

        aceptar.addActionListener((x)-> {
            String selectedValue = queues.getSelectedValue();
            indexView.setTopic(selectedValue);
        });


    }
}
