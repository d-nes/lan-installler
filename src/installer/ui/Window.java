package installer.ui;

import javax.swing.JRadioButton;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;

import installer.logic.FileExtractor;
import installer.logic.OfficialLauncherInjector;

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
                btnInstall.setEnabled(true);
            }
        });

        JRadioButton rdbtnTLauncher = new JRadioButton("TLauncher");
        rdbtnTLauncher.setBounds(72, 67, 200, 23);
        group.add(rdbtnTLauncher);
        rdbtnTLauncher.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                btnInstall.setEnabled(true);
            }
        });
        
        btnInstall.addActionListener(e -> {
            if (rdbtnOfficalLauncher.isSelected()) {
            	btnInstall.setEnabled(false);
                OfficialLauncherInjector injector = new OfficialLauncherInjector();
                if(injector.injectProfile()) {
                	FileExtractor extractor = new FileExtractor();
                	if(extractor.extractFiles()) {
                		JOptionPane.showMessageDialog(null, "Modpack installed successfully. Click OK to close the application.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                	}
                	else {
                		JOptionPane.showMessageDialog(null, "File extraction failed. Click OK to close the application.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                	}
                }
                else {
                	JOptionPane.showMessageDialog(null, "Profile injection failed. Click OK to close the application.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            } else if (rdbtnTLauncher.isSelected()) {
            	btnInstall.setEnabled(false);
            	FileExtractor extractor = new FileExtractor();
            	if(extractor.extractFiles()) {
            		JOptionPane.showMessageDialog(null, "Modpack installed successfully. Click OK to close the application.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
            	}
            	else {
            		JOptionPane.showMessageDialog(null, "File extraction failed. Click OK to close the application.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
            	}
            }
        });
        
        getContentPane().setLayout(null);
        getContentPane().add(txtChooseYourLauncher);
        getContentPane().add(rdbtnOfficalLauncher);
        getContentPane().add(rdbtnTLauncher);
        getContentPane().add(btnInstall);

        setPreferredSize(new Dimension(360, 180));
        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
