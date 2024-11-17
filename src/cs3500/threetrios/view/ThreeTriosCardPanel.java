package cs3500.threetrios.view;

import java.awt.*;

import javax.swing.*;

import cs3500.threetrios.model.TeamColor;

public interface ThreeTriosCardPanel {

  int getHandInx();

  void toggleHighlight();

  TeamColor getColor();
}
