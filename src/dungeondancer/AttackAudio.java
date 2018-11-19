/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondancer;

import javafx.scene.layout.AnchorPane;

/**
 *
 * @author beatus
 */
public interface AttackAudio {
    public void start(int numBands, AnchorPane vizPane);
    public void end();
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases);
}
