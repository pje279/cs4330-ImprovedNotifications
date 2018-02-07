/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    public enum State { NULL, STOPPED, STARTED};
    
    @FXML
    private TextArea textArea;
    
    @FXML
    private Button btnTask1;
    private State task1State = State.NULL;
    
    @FXML
    private Button btnTask2;
    private State task2State = State.NULL;
    
    @FXML
    private Button btnTask3;
    private State task3State = State.NULL;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        System.out.println("start task 1");
        if (task1 == null || this.task1State == State.NULL || this.task1State == State.STOPPED) {
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
            this.btnTask1.setText("End Task 1");
            this.task1State = State.STARTED;
        }
        else if (this.task1 != null && this.task1State == State.STARTED)
        {
            task1.end();
            this.btnTask1.setText("Start Task 1");
            this.task1State = State.STOPPED;
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        System.out.println("start task 2");
        if (task2 == null || this.task2State == State.NULL || this.task2State == State.STOPPED) {
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
            });
            
            task2.start();
            this.btnTask2.setText("End Task 2");
            this.task2State = State.STARTED;
        }        
        else if (this.task2 != null && this.task2State == State.STARTED)
        {
            task2.end();
            this.btnTask2.setText("Start Task 2");
            this.task2State = State.STOPPED;
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        System.out.println("start task 3");
        if (task3 == null || this.task3State == State.NULL || this.task3State == State.STOPPED) {
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
            });
            
            task3.start();
            this.btnTask3.setText("End Task 3");
            this.task3State = State.STARTED;
        }
        else if (this.task3 != null && this.task3State == State.STARTED)
        {
            task3.end();
            this.btnTask3.setText("Start Task 3");
            this.task3State = State.STOPPED;
    } 
}
