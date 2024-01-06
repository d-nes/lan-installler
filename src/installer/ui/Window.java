package installer.ui;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ItemEvent;

public class Window extends JFrame {
    private JLabel txtChooseYourLauncher;

    public Window() {
    	this.setTitle("LAN Modpack Installer 2024");
    	
        txtChooseYourLauncher = new JLabel("Select your launcher of choice:");
        txtChooseYourLauncher.setHorizontalAlignment(SwingConstants.CENTER);
        txtChooseYourLauncher.setFont(new Font("Tahoma", Font.BOLD, 11));
        txtChooseYourLauncher.setBounds(72, 11, 200, 23);

        JButton btnInstall = new JButton("Install");
        btnInstall.setBounds(122, 107, 100, 23);
        btnInstall.setEnabled(false);
        
        ButtonGroup group = new ButtonGroup();

        JRadioButton rdbtnOfficalLauncher = new JRadioButton("Official Minecraft Launcher");
        rdbtnOfficalLauncher.setBounds(72, 41, 200, 23);
        group.add(rdbtnOfficalLauncher);
        rdbtnOfficalLauncher.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // Enable the install button when selected
                btnInstall.setEnabled(true);
            }
        });

        JRadioButton rdbtnTLauncher = new JRadioButton("TLauncher");
        rdbtnTLauncher.setBounds(72, 67, 200, 23);
        group.add(rdbtnTLauncher);
        rdbtnTLauncher.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // Enable the install button when selected
                btnInstall.setEnabled(true);
            }
        });
        rdbtnTLauncher.setEnabled(false); // TODO delete when implemented

        
        getContentPane().setLayout(null);
        getContentPane().add(txtChooseYourLauncher);
        getContentPane().add(rdbtnOfficalLauncher);
        getContentPane().add(rdbtnTLauncher);
        getContentPane().add(btnInstall);

        setPreferredSize(new Dimension(360, 180));
        pack(); // Adjusts the size of the window based on its components
        setResizable(false); // Disallows resizing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window on the screen
    }
}
