package com.github.lithualien.GUI;

import com.github.lithualien.crypting.Encryption;
import com.github.lithualien.crypting.Key;
import com.github.lithualien.message.Message;
import org.apache.commons.codec.digest.DigestUtils;
import javax.swing.*;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GUI extends JFrame{
    private JPanel mainPanel;

    private JTextField inputText = new JTextField();
    private JButton encrypt = new JButton("Encrypt");
    private Encryption encryption;
    private Key keys;

    public void setUpGUI() {
        setMainPanel();
        addToMainPanel();
        setTextFields();
        setButtons();
        setActionForEncryptButton();
    }

    private void setMainPanel() {
        add(mainPanel);
        setVisible(true);
        mainPanel.setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(350, 120);
        setResizable(false);
    }

    private void addToMainPanel() {
        mainPanel.add(inputText);
        mainPanel.add(encrypt);
    }

    private void setTextFields() {
        inputText.setBounds(10, 10, 320, 30);
    }

    private void setButtons() {
        encrypt.setBounds(10, 50, 320, 30);
    }

    private void setActionForEncryptButton() {
        encrypt.addActionListener(e -> {
            encryption = new Encryption();
            keys = new Key();
            String sha512 = DigestUtils.sha512Hex(inputText.getText());
            keys.setKeyPair();
            sendMessage(sha512);
        });
    }

    private void sendMessage(String sha512) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 8888);
            ObjectOutputStream sendMessage = new ObjectOutputStream(socket.getOutputStream());
            Message message = new Message(keys.getPubKey(), encryption.encrypt(keys.getPrivateKey(), sha512), sha512);
            System.out.println(message.getCryptedMessage());
            sendMessage.writeObject(message);
            socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
