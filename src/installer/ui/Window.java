package installer.ui;

import javax.swing.JRadioButton;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;

import installer.logic.extractor.UnofficialLauncherFileExtractor;
import installer.logic.injector.OfficialLauncherInjector;

import java.awt.Font;
import java.awt.event.ItemEvent;

@SuppressWarnings("serial")
public class Window extends JFrame {
    private JLabel txtChooseYourLauncher;

    public Window() {
    	this.setTitle("LAN Modpack Installer 2022");
    	
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

        JRadioButton rdbtnTLauncher = new JRadioButton("TLauncher (universal)");
        rdbtnTLauncher.setBounds(72, 67, 200, 23);
        group.add(rdbtnTLauncher);
        rdbtnTLauncher.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                btnInstall.setEnabled(true);
            }
        });
        
        btnInstall.addActionListener(e -> {
            rdbtnOfficalLauncher.setEnabled(false);
            rdbtnTLauncher.setEnabled(false);
            
        	if (rdbtnOfficalLauncher.isSelected()) {
            	btnInstall.setEnabled(false);
            	btnInstall.setText("Installing...");
                installOfficial();
            } else if (rdbtnTLauncher.isSelected()) {
            	btnInstall.setEnabled(false);
            	btnInstall.setText("Installing...");
            	installUnofficial();
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
    
    private void installOfficial() {
    	OfficialLauncherInjector injector = new OfficialLauncherInjector(); //This also calls OfficialLauncherExtractor
        Thread t = new Thread(injector);
        t.start();
    }
    
    private void installUnofficial() {
    	UnofficialLauncherFileExtractor extractor = new UnofficialLauncherFileExtractor();
    	Thread t = new Thread(extractor);
        t.start();
    }

}
