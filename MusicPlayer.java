package com.musicplayer;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.sound.sampled.*;
import java.io.File;


public class MusicPlayer implements ActionListener {
    JFrame frame;
    JButton buttonPlay;
    JButton buttonStop;
    JPanel panelButtons;
    
    private final int WIDTH = 720;
    private final int HEIGHT = 480;
    private Color backgroundColor = new Color(200, 200, 200);
    
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
        buttonPlay = new JButton("Play");
        buttonPlay.setBounds(500, 150, 50, 50);
        buttonPlay.addActionListener(this);
        panelButtons.add(buttonPlay);
        
        // BUTTON: stop
        buttonStop = new JButton("Stop");
        buttonStop.setBounds(500, 280, 50, 50);
        buttonStop.addActionListener(this);
        panelButtons.add(buttonStop);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == buttonPlay) {
            playMusic("");
        }
        
        if (source == buttonStop) {
            stopMusic();
        }
    }
    
    
    public void playMusic(String filePath) {
        try {
            // load the audio file
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();
            
            audioClip.open(audioStream);
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
