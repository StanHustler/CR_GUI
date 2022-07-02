package com.stan.gui;


import javax.swing.*;

import com.stan.exec.shellUtil;


public class mainPanel extends JFrame{
    JPanel p;
    JButton b_cr,b_re;
    JTextField tField1,tField2;

    public mainPanel(){
        this.setSize(300,75);
        this.setLocation(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Demo");

        p= new JPanel();

        addButton();

        this.add(p);
        this.setVisible(true);
    }

    private void addButton(){
        b_cr = new JButton("Create");
        p.add(b_cr);
        b_re = new JButton("Restore");
        p.add(b_re);

        tField1 = new JTextField("CNAME");
        tField1.setColumns(4);
        p.add(tField1);
        tField2 = new JTextField("CR");
        tField2.setColumns(4);
        p.add(tField2);
        
        // TODO: listener here
        b_cr.addActionListener((e) -> {
            
            shellUtil.exec("docker checkpoint create "+tField1.getText()+" "+tField2.getText());
            JOptionPane.showMessageDialog(null, "Executed!", "DEMO", JOptionPane.INFORMATION_MESSAGE);
        });

        b_re.addActionListener((e) -> {
            shellUtil.exec("docker start --checkpoint "+tField2.getText()+" "+tField1.getText());
            JOptionPane.showMessageDialog(null, "Executed!", "DEMO", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
