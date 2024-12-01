package com.musicplayer;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.sound.sampled.*;
import java.io.File;


public class MusicPlayer implements ActionListener {
    // declare frame components
    JFrame frame;
    
    // declare player components
    private JPanel panelButtons;
    private JButton buttonPlay;
    private JButton buttonPause;
    
    // screen properties
    private final int WIDTH = 720;
    private final int HEIGHT = 480;
    private Color backgroundColor = new Color(200, 200, 200);
    
    // javax sound properties
    private Clip audioClip;
    
    
    
    public MusicPlayer() {  
        initializeFrame();
        initializePlayer();
    }
    
    
    public void initializeFrame() {
        frame = new JFrame("Music Player");frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(500, 175);
        frame.setLocationRelativeTo(null);       
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setBackground(backgroundColor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    public void initializePlayer() {
        // PANEL: player
        panelButtons = new JPanel();
        panelButtons.setLayout(null);
        panelButtons.setBackground(backgroundColor);
        panelButtons.setBounds(0, 0, WIDTH, HEIGHT);
        frame.add(panelButtons);        
        panelButtons.setVisible(true);
        
        // BUTTON: play 
        buttonPlay = new JButton() {
            private boolean isHovered = false;
            private Color buttonColor = new Color(34, 153, 84); // Stylish green
            private Color hoverColor = new Color(40, 180, 99); // Lighter green for hover

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw round button background
                int diameter = Math.min(getWidth(), getHeight());
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;

                g2.setColor(isHovered ? hoverColor : buttonColor);
                g2.fillOval(x, y, diameter, diameter);

                // Draw triangular "Play" symbol
                g2.setColor(Color.WHITE);
                int[] xPoints = {x + diameter / 3, x + (2 * diameter / 3 + diameter / 8), x + diameter / 3};
                int[] yPoints = {y + diameter / 4, y + diameter / 2, y + (3 * diameter / 4)};
                g2.fillPolygon(xPoints, yPoints, 3);

                g2.dispose();
            }
        };

        // Add hover effect
        buttonPlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonPlay.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonPlay.repaint();
            }
        });

        // custom button properties
        buttonPlay.setFocusPainted(false);
        buttonPlay.setBorderPainted(false);
        buttonPlay.setContentAreaFilled(false);
        buttonPlay.setOpaque(false);
        buttonPlay.setBounds(310, 300, 100, 100);
        buttonPlay.addActionListener(this);
        panelButtons.add(buttonPlay);
        
        
        
        // BUTTON: stop
        buttonPause = new JButton() {
            private boolean isHovered = false;
            private Color buttonColor = new Color(192, 57, 43); // Stylish red
            private Color hoverColor = new Color(231, 76, 60); // Lighter red for hover

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw round button background
                int diameter = Math.min(getWidth(), getHeight());
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;

                g2.setColor(isHovered ? hoverColor : buttonColor);
                g2.fillOval(x, y, diameter, diameter);

                // Draw "Pause" symbol (||)
                g2.setColor(Color.WHITE);
                int barWidth = diameter / 8; // Width of each pause bar
                int barHeight = diameter / 2; // Height of pause bars
                int barSpacing = barWidth; // Spacing between bars

                int barX1 = x + (diameter / 3) + (diameter / 15) - (barWidth / 2); // X position for first bar
                int barX2 = barX1 + barWidth + barSpacing; // X position for second bar
                int barY = y + (diameter - barHeight) / 2; // Center the bars vertically

                g2.fillRect(barX1, barY, barWidth, barHeight); // First bar
                g2.fillRect(barX2, barY, barWidth, barHeight); // Second bar

                g2.dispose();
            }
        };
        
        // Add hover effect
        buttonPause.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonPause.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonPause.repaint();
            }
        });
        // custom button properties
        buttonPause.setFocusPainted(false);
        buttonPause.setBorderPainted(false);
        buttonPause.setContentAreaFilled(false);
        buttonPause.setOpaque(false);
        buttonPause.setBounds(310, 300, 100, 100);
        buttonPause.addActionListener(this);
        buttonPause.setVisible(false);
        panelButtons.add(buttonPause);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == buttonPlay) {
            playMusic("songs/retro-city-14099.wav");
            
            // hide play and show pause button
            buttonPause.setVisible(true);
            buttonPlay.setVisible(false);
        }
        
        if (source == buttonPause) {
            stopMusic();
            
            // hide pause and show play button
            buttonPlay.setVisible(true);
            buttonPause.setVisible(false);
        }
    }
    
    
    public void playMusic(String filePath) {
        try {
            // load the audio file
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();
            
            audioClip.open(audioStream);
            audioClip.start();
            System.out.println("PLAY");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void stopMusic() {
        if (audioClip != null && audioClip.isRunning()) {
            audioClip.stop();
        }
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // run program
                MusicPlayer systemGUI = new MusicPlayer();                
            }
        });
    }
}
