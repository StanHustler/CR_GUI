package com.stan.gui.mainPanels;

import java.awt.*;
import java.util.Properties;
import javax.swing.*;
import com.stan.exec.shellUtil;

public class checkPanel extends JPanel{
    private GridBagLayout gbl = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();
    private Properties props = System.getProperties();

    boolean bCriu = false,bDocker = false;

    public checkPanel(){
        this.setBorder(BorderFactory.createTitledBorder("依赖检查"));
        this.setLayout(gbl);
        
        setOs();
        setArch();
        setDocker();
        setCriu();
        if (bCriu) setCriuCheck();
        if (bCriu) setDirtyCheck();
        
        this.setVisible(true);
    }

    private void setOs(){
        JLabel l1 = new JLabel("OS:");
        JLabel l2 = new JLabel(props.getProperty("os.name"));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbl.setConstraints(l1, gbc);
        this.add(l1);

        gbc.gridx = 1;
        gbl.setConstraints(l2, gbc);
        this.add(l2);
    }

    private void setArch(){
        JLabel l1 = new JLabel("Arch:");
        JLabel l2 = new JLabel(props.getProperty("os.arch"));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbl.setConstraints(l1, gbc);
        this.add(l1);

        gbc.gridx = 1;
        gbl.setConstraints(l2, gbc);
        this.add(l2);
    }

    private void setDocker(){
        JLabel l1 = new JLabel("Docker:");
        JLabel l2 = new JLabel("Waiting");

        gbc.gridx=0;
        gbc.gridy=2;
        gbl.setConstraints(l1, gbc);
        this.add(l1);

        gbc.gridx=1;
        gbl.setConstraints(l2, gbc);
        this.add(l2);

        try {
            String[] dockerVersion = shellUtil.exec("docker -v");
            l2.setText(dockerVersion[0].substring(15, 23));
            // TODO: use re to recognize
            bDocker = true;
        } catch (Exception e) {
            l2.setText("Failed");
            l2.setForeground(Color.RED);
        }
    }

    private void setCriu(){
        JLabel l1 = new JLabel("CRIU:");
        JLabel l2 = new JLabel("Waiting");

        gbc.gridx=0;
        gbc.gridy=3;
        gbl.setConstraints(l1, gbc);
        this.add(l1);

        gbc.gridx=1;
        gbl.setConstraints(l2, gbc);
        this.add(l2);

        try {
            String[] criuVersion = shellUtil.exec("criu --version");
            l2.setText(criuVersion[0].substring(9));
            //TODO: use re to recognize
            bCriu = true;
        } catch (Exception e) {
            l2.setText("Failed");
            l2.setForeground(Color.RED);
        }
    }


    private void setCriuCheck() {
        JLabel l1 = new JLabel("CRIU Check:");
        JLabel l2 = new JLabel("Failed");
        l2.setForeground(Color.RED);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbl.setConstraints(l1, gbc);
        this.add(l1);

        gbc.gridx = 1;
        gbl.setConstraints(l2, gbc);
        this.add(l2);

        try {
            String[] criuVersion = shellUtil.exec("criu check");
            String tmp = criuVersion[0].substring(6,10);
            if (tmp.equals("good")){
                l2.setText("Passed");
                l2.setForeground(Color.GREEN);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void setDirtyCheck(){
        JLabel l1 = new JLabel("Track dirty:");
        JLabel l2 = new JLabel("Failed");
        l2.setForeground(Color.RED);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbl.setConstraints(l1, gbc);
        this.add(l1);

        gbc.gridx = 1;
        gbl.setConstraints(l2, gbc);
        this.add(l2);

        try {
            String[] criuVersion = shellUtil.exec("criu check --feature mem_dirty_track");
            String tmp = criuVersion[0].substring(19);
            if (tmp.equals("supported")) {
                l2.setText("Passed");
                l2.setForeground(Color.GREEN);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
