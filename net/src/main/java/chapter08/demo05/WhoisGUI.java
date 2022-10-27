package chapter08.demo05;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import static org.apache.commons.lang3.StringUtils.join;

/**
 * @description
 * @date:2022/10/27 18:48
 * @author: qyl
 */import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

public class WhoisGUI extends JFrame {
    private JTextField searchString = new JTextField(30);  //编辑文本，单行
    private JTextArea names = new JTextArea(15, 80);  //编辑文本，多行
    private JButton findButton = new JButton("Find");
    private ButtonGroup searchIn = new ButtonGroup();//多斥按钮
    private ButtonGroup searchFor = new ButtonGroup();
    private JCheckBox exactMatch = new JCheckBox("Exact Match", true);//复选框
    private JTextField chosenServer = new JTextField();
    private Whois server;

    public WhoisGUI(Whois whois) {
        super("Whois");
        this.server = whois;
        Container pane = this.getContentPane();//初始化一个容器

        Font f = new Font("Monospaced", Font.PLAIN, 12);
        names.setFont(f);
        names.setEditable(false);//不可编辑

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 1, 10, 10));//布局，网格布局
        JScrollPane jsp = new JScrollPane(names);//滚动条
        centerPanel.add(jsp);
        pane.add("Center", centerPanel);

        JPanel northPanel = new JPanel();
        JPanel northPanelTop = new JPanel();
        northPanelTop.setLayout(new FlowLayout(FlowLayout.LEFT));//布局，流式布局
        northPanelTop.add(new JLabel("Whois:"));
        northPanelTop.add("North", searchString);
        northPanelTop.add(exactMatch);
        northPanelTop.add(findButton);
        northPanel.setLayout(new BorderLayout(2, 1));
        northPanel.add("North", northPanelTop);
        JPanel northPanelBottom = new JPanel();
        northPanelBottom.setLayout(new GridLayout(1, 3, 5, 5));
        northPanelBottom.add(initRecordType());
        northPanelBottom.add(initSearchFields());
        northPanelBottom.add(initServerChoice());
        northPanel.add("Center", northPanelBottom);
        pane.add("North", northPanel);

        // 通过LookupNames来实现事件分发，多线程完成，防止阻塞整个应用
        ActionListener al = new LookupNames();
        findButton.addActionListener(al);
        searchString.addActionListener(al);
    }

    private JPanel initRecordType() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(6, 2, 5, 2));
        p.add(new JLabel("Search for:"));
        p.add(new JLabel(""));
        JRadioButton any = new JRadioButton("Any", true);
        any.setActionCommand("Any");
        searchFor.add(any);
        p.add(any);
        p.add(this.makeRadioButton("Network"));
        p.add(this.makeRadioButton("Person"));
        p.add(this.makeRadioButton("Host"));
        p.add(this.makeRadioButton("Domain"));
        p.add(this.makeRadioButton("Organization"));
        p.add(this.makeRadioButton("Group"));
        p.add(this.makeRadioButton("Gateway"));
        p.add(this.makeRadioButton("ASN"));
        return p;
    }

    private JRadioButton makeRadioButton(String label) {
        JRadioButton button = new JRadioButton(label, false);
        button.setActionCommand(label);
        searchFor.add(button);
        return button;
    }

    private JRadioButton makeSearchInRadioButton(String label) {
        JRadioButton button = new JRadioButton(label, false);
        button.setActionCommand(label);
        searchIn.add(button);
        return button;
    }

    private JPanel initSearchFields() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(6, 1, 5, 2));
        p.add(new JLabel("Search In:"));

        JRadioButton all = new JRadioButton("All", true);
        all.setActionCommand("All");
        searchIn.add(all);
        p.add(all);

        p.add(this.makeSearchInRadioButton("Name"));
        p.add(this.makeSearchInRadioButton("Mailbox"));
        p.add(this.makeSearchInRadioButton("Handle"));
        return p;
    }

    private JPanel initServerChoice() {
        final JPanel p = new JPanel();
        p.setLayout(new GridLayout(6, 1, 5, 2));
        p.add(new JLabel("Search At:"));

        chosenServer.setText(server.getHost().getHostName());
        p.add(chosenServer);
        chosenServer.addActionListener(event -> {
            try {
                server = new Whois(chosenServer.getText());
            } catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(p, ex.getMessage(),
                        "Alert", JOptionPane.ERROR_MESSAGE);
            }
        });
        return p;
    }

    private class LookupNames implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            names.setText("");
            SwingWorker<String, Object> worker = new Lookup();
            worker.execute();
        }
    }

    private class Lookup extends SwingWorker<String, Object> {
        // 进行IO，不和GUI交互
        @Override
        protected String doInBackground() throws Exception {

            String searchForLabel = searchFor.getSelection().getActionCommand();
            String searchInLabel = searchIn.getSelection().getActionCommand();

            Whois.SearchIn group = Whois.SearchIn.findSearchInByName(searchInLabel);
            Whois.SearchFor category = Whois.SearchFor.findSearchForByName(searchForLabel);

            server.setHost(chosenServer.getText());
            return server.lookUpNames(searchString.getText(), category, group, exactMatch.isSelected());
        }

        // 在doInBackground处理完之后更新GUI
        @Override
        protected void done() {
            try {
                names.setText(get());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(WhoisGUI.this, ex.getMessage(), "Lookup Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        try {
            Whois server = new Whois();
            WhoisGUI a = new WhoisGUI(server);
            a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            a.pack();
            EventQueue.invokeLater(new FrameShower(a));
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Could not locate default host" + Whois.DEFAULT_HOST,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class FrameShower implements Runnable {
        private final Frame frame;

        FrameShower(Frame frame) {
            this.frame = frame;
        }

        @Override
        public void run() {
            frame.setVisible(true);
        }
    }


}
